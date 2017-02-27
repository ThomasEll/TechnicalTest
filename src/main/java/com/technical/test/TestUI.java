package com.technical.test;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;

/**
 * @author Tom Longdon
 */
@SpringUI
public class TestUI extends UI{

    private final ContactRepository contactRepository;
    private final ActivityRepository activityRepository;

    private final ContactEditor contactEditor;
    private final ActivityEditor activityEditor;

    final Grid<Contact> contactGrid;
    final Grid<Activity> activityGrid;

    final TextField contactFilter;
    final TextField activityFilter;

    private final Button addNewContactBtn;
    private final Button addNewActivityBtn;

    private final TabSheet pages;

    @Autowired
    public TestUI(ContactRepository contactRepository, ActivityRepository activityRepository, ContactEditor contactEditor, ActivityEditor activityEditor){
        this.contactRepository = contactRepository;
        this.activityRepository = activityRepository;
        this.contactEditor = contactEditor;
        this.activityEditor = activityEditor;
        this.contactGrid = new Grid<>(Contact.class);
        this.activityGrid = new Grid<>(Activity.class);
        this.contactFilter = new TextField();
        this.activityFilter = new TextField();
        this.addNewContactBtn = new Button("New Contact", FontAwesome.PLUS);
        this.addNewActivityBtn = new Button("New Activity", FontAwesome.PLUS);
        this.pages = new TabSheet();

    }

    @Override
    protected void init(VaadinRequest request){
        //build contact page layout
        VerticalLayout contactActions = new VerticalLayout(contactFilter, contactGrid, addNewContactBtn);
        HorizontalLayout contactLayout = new HorizontalLayout(contactActions, contactEditor);

        //build activity page layout
        VerticalLayout activityActions = new VerticalLayout(activityFilter, activityGrid, addNewActivityBtn);
        HorizontalLayout activityLayout = new HorizontalLayout(activityActions, activityEditor);


        //Add page tabs
        pages.setHeight(100.0f, Unit.PERCENTAGE);
        pages.addStyleName(ValoTheme.TABSHEET_FRAMED);
        pages.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

        pages.addTab(contactLayout, "Contacts");
        pages.addTab(activityLayout, "Activities");


        setContent(pages);

        contactGrid.setHeight(200, Unit.PIXELS);
        contactGrid.setColumns("firstName", "lastName");

        activityGrid.setHeight(200, Unit.PIXELS);
        activityGrid.setColumns("title", "activityType");

        contactFilter.setPlaceholder("Search by first name or last name");
        activityFilter.setPlaceholder("Search by title");

        contactFilter.setValueChangeMode(ValueChangeMode.LAZY);
        contactFilter.addValueChangeListener(e -> listContacts(e.getValue()));

        activityFilter.setValueChangeMode(ValueChangeMode.LAZY);
        activityFilter.addValueChangeListener(e -> listActivities(e.getValue()));

        contactGrid.asSingleSelect().addValueChangeListener(e -> {
            contactEditor.editContact(e.getValue());
        });

        activityGrid.asSingleSelect().addValueChangeListener(e -> {
            activityEditor.editActivity(e.getValue());
        });

        addNewContactBtn.addClickListener(e -> contactEditor.editContact(new Contact("", "", "", "", "", "", "", "")));
        addNewActivityBtn.addClickListener(e -> activityEditor.editActivity(new Activity(null, "", "", null, null)));

        contactEditor.setChangeHandler(() -> {
            contactEditor.setVisible(false);
            listContacts(contactFilter.getValue());
        });

        activityEditor.setChangeHandler(() -> {
            activityEditor.setVisible(false);
            listActivities(activityFilter.getValue());
        });

        //Initialise listing
        listContacts(null);
        listActivities(null);
    }

    //tag::listContacts[]
    void listContacts(String filterText){
        if (StringUtils.isEmpty(filterText)){
            contactGrid.setItems(contactRepository.findAll());
        } else {
            contactGrid.setItems(contactRepository.findContactByFirstNameOrLastNameStartsWithIgnoreCase(filterText, filterText));
        }
    }
    //end::listContacts[]

    // tag::listActivities[]
    void listActivities(String filterText){
        if(StringUtils.isEmpty(filterText)){
            activityGrid.setItems(activityRepository.findAll());
        } else {
            activityGrid.setItems(activityRepository.findActivityByTitle(filterText));
        }
    }
}
