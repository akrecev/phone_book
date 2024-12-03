package com.kretsev.phonebook.gui;

import com.kretsev.phonebook.model.Contact;
import com.kretsev.phonebook.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ContactFormDialog extends Dialog {
    private final TextField nameField = new TextField("Имя");
    private final TextField phoneField = new TextField("Телефон");
    private final TextField emailField = new TextField("Email");
    private final TextField notesField = new TextField("Примечания");

    private final ContactService contactService;
    private final Runnable onSave; // Callback для обновления таблицы

    public ContactFormDialog(Contact contact, ContactService contactService, Runnable onSave) {
        this.contactService = contactService;
        this.onSave = onSave;

        // Инициализация формы с данными
        nameField.setValue(contact.getName() != null ? contact.getName() : "");
        phoneField.setValue(contact.getPhoneNumber() != null ? contact.getPhoneNumber() : "");
        emailField.setValue(contact.getEmail() != null ? contact.getEmail() : "");
        notesField.setValue(contact.getNotes() != null ? contact.getNotes() : "");

        // Кнопка сохранения
        Button saveButton = new Button("Сохранить", event -> saveContact(contact));

        // Разметка
        FormLayout formLayout = new FormLayout(nameField, phoneField, emailField, notesField);
        VerticalLayout layout = new VerticalLayout(formLayout, saveButton);

        add(layout);
    }

    private void saveContact(Contact contact) {
        // Обновляем данные объекта
        contact.setName(nameField.getValue());
        contact.setPhoneNumber(phoneField.getValue());
        contact.setEmail(emailField.getValue());
        contact.setNotes(notesField.getValue());

        // Сохраняем контакт
        contactService.saveContact(contact);

        // Закрываем диалог и вызываем обновление
        close();
        onSave.run();
        Notification.show("Контакт сохранен!");
    }
}
