package com.technical.test;

import com.vaadin.data.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Tom Longdon
 */

@SpringComponent
@UIScope
public class ContactEditor extends VerticalLayout {

    private final ContactRepository conRepo;

    private Contact contact;

    /*Fields used to edit contact entity properties */
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField emailAddress = new TextField("Email Address");
    TextField telephoneNumber = new TextField("Phone Number");
    TextField address1 = new TextField("Address Line 1");
    TextField address2 = new TextField("Address Line 2");
    TextField city = new TextField("City");
    TextField postCode = new TextField("Post Code");

    /*Group fields into lines */
    CssLayout names = new CssLayout(firstName, lastName);
    CssLayout contactDetails = new CssLayout(emailAddress, telephoneNumber);

    /* Action buttons */
    Button save = new Button("Save", FontAwesome.SAVE);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", FontAwesome.TRASH_O);
    CssLayout actions = new CssLayout(save, cancel, delete);

    Binder<Contact> binder = new Binder<>(Contact.class);

    @Autowired
    public ContactEditor(ContactRepository conRepo){
        this.conRepo = conRepo;

        addComponents(names, contactDetails, address1, address2, city, postCode, actions);

        binder.forField(firstName)
                .asRequired("First Name may not be empty")
                .bind(Contact::getFirstName, Contact::setFirstName);

        binder.forField(lastName)
                .asRequired("Last Name may not be empty")
                .bind(Contact::getLastName, Contact::setLastName);

        binder.forField(emailAddress)
                .asRequired("Email Address may not be empty")
                .withValidator(new EmailValidator("Not a valid email address"))
                .bind(Contact::getEmailAddress, Contact::setEmailAddress);

        binder.bindInstanceFields(this);

        //Save button only enabled when form is valid
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));


        //Configure and style components
        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> {
            conRepo.save(contact);
        });
        delete.addClickListener(e -> conRepo.delete(contact));
        cancel.addClickListener(e -> editContact(contact));
        setVisible(false);

    }

    public interface ChangeHandler{
        void onChange();
    }

    public final void editContact(Contact c) {
        if (c == null) {
            setVisible(false);
            return;
        }

        final boolean persisted = c.getId() != null;
        if(persisted) {
            //Find fresh entity for editing
            contact = conRepo.findOne(c.getId());
        } else {
            contact = c;
        }
        cancel.setVisible(persisted);

        //Bind contact properties to similarly named fields
        binder.setBean(contact);

        setVisible(true);

        save.focus();
        firstName.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {
        //ChangeHandler is notified when either save or delete is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }
}
