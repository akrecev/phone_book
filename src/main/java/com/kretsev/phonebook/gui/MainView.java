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

        // Настройка таблицы
        contactGrid.setColumns("name", "phoneNumber", "email", "notes");
        refreshGrid();

        // Кнопки
        Button addButton = new Button("Добавить контакт", e -> openContactForm(new Contact()));
        contactGrid.addItemClickListener(event -> openContactForm(event.getItem())); // Редактирование по клику

        add(addButton, contactGrid);
    }

    private void refreshGrid() {
        contactGrid.setItems(contactService.getAllContacts());
    }

    private void openContactForm(Contact contact) {
        ContactFormDialog dialog = new ContactFormDialog(contact, contactService, this::refreshGrid);
        dialog.open();
    }
}
