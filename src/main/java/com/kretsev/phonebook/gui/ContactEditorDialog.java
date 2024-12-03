package com.kretsev.phonebook.gui;

import com.kretsev.phonebook.model.Contact;
import com.kretsev.phonebook.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ContactEditorDialog extends Dialog {
    public ContactEditorDialog(Contact contact, ContactService contactService, Runnable onSave) {
        TextField nameField = new TextField("Имя");
        TextField phoneField = new TextField("Телефон");
        TextField emailField = new TextField("Email");
        TextField notesField = new TextField("Примечания");

        nameField.setValue(contact.getName() != null ? contact.getName() : "");
        phoneField.setValue(contact.getPhoneNumber() != null ? contact.getPhoneNumber() : "");
        emailField.setValue(contact.getEmail() != null ? contact.getEmail() : "");
        notesField.setValue(contact.getNotes() != null ? contact.getNotes() : "");

        Button saveButton = new Button("Сохранить", e -> {
            contact.setName(nameField.getValue());
            contact.setPhoneNumber(phoneField.getValue());
            contact.setEmail(emailField.getValue());
            contact.setNotes(notesField.getValue());

            contactService.saveContact(contact);
            onSave.run();
            close();
        });

        add(new VerticalLayout(new FormLayout(nameField, phoneField, emailField, notesField), saveButton));
    }
}
