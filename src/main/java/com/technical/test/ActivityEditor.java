package com.technical.test;

import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.themes.ValoTheme;

import java.util.EnumSet;

/**
 * @author Tom Longdon
 */

@SpringComponent
@UIScope
public class ActivityEditor extends VerticalLayout{

    private final ActivityRepository activityRepository;
    private final ContactRepository contactRepository;

    private Activity activity;

    /* Fields used to edit Activity entity properties */
    TextField title = new TextField("Activity Title");
    ComboBox<Contact> contactSelect = new ComboBox<>("Select Contact");
    ComboBox<ActivityType> activitySelect = new ComboBox<>("Select Activity Type");
    TextField notes = new TextField("Notes");
    DateField dueDate = new DateField();

    /* Action buttons */
    Button save = new Button("Save", FontAwesome.SAVE);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", FontAwesome.TRASH_O);
    CssLayout actions = new CssLayout(save, cancel, delete);


    Binder<Activity> binder = new Binder<>(Activity.class);

    @Autowired
    public ActivityEditor(ActivityRepository activityRepository, ContactRepository contactRepository) {
        this.activityRepository = activityRepository;
        this.contactRepository = contactRepository;

        addComponents(title, contactSelect, activitySelect, notes, dueDate, actions);

        binder.forField(title)
                .asRequired("Title cannot be empty")
                .bind(Activity::getTitle, Activity::setTitle);

        binder.forField(activitySelect)
                .asRequired("Activity must be selected")
                .bind(Activity::getActivityType, Activity::setActivityType);

        binder.bindInstanceFields(this);

        //Save button only enabled when form is valid
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));


        contactSelect.setItems(contactRepository.findAll());
        contactSelect.setItemCaptionGenerator(Contact::getFullName);

        activitySelect.setItems(ActivityType.CALL, ActivityType.EMAIL, ActivityType.MEETING);

        //Configure and style components
        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> {
            activityRepository.save(activity);
        });
        delete.addClickListener(e -> activityRepository.delete(activity));
        cancel.addClickListener(e -> editActivity(activity));
        setVisible(false);
    }

    public interface ChangeHandler{
        void onChange();
    }

    public final void editActivity(Activity a) {
        if(a == null) {
            setVisible(false);
            return;
        }

        final boolean persisted = a.getActivityId() != null;
        if(persisted) {
            activity = activityRepository.findOne(a.getActivityId());
        } else {
            activity = a;
        }
        cancel.setVisible(persisted);

        binder.setBean(activity);

        setVisible(true);

        save.focus();
        title.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {
        //ChangeHandler is notified when either save or delete is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }
}
