package com.kretsev.phonebook.gui;

import com.kretsev.phonebook.model.Contact;
import com.kretsev.phonebook.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
public class MainView extends VerticalLayout {
    private final ContactService contactService;
    private final Grid<Contact> contactGrid = new Grid<>(Contact.class);

    @Autowired
    public MainView(ContactService contactService) {
        this.contactService = contactService;

        contactGrid.setColumns("name", "phoneNumber", "email", "notes");
        refreshGrid();

        Button addButton = new Button("Добавить контакт", e -> openEditor(new Contact()));
        add(addButton, contactGrid);
    }

    private void refreshGrid() {
        contactGrid.setItems(contactService.getAllContacts());
    }

    private void openEditor(Contact contact) {
        ContactEditorDialog editor = new ContactEditorDialog(contact, contactService, this::refreshGrid);
        editor.open();
    }
}
