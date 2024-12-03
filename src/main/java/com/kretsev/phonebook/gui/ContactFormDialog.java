package com.kretsev.phonebook.gui;

import com.kretsev.phonebook.model.Contact;
import com.kretsev.phonebook.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ContactFormDialog extends Dialog {
    private final TextField nameField = new TextField("Имя");
    private final TextField phoneField = new TextField("Телефон");
    private final TextField emailField = new TextField("Email");
    private final TextField notesField = new TextField("Примечания");

    private final ContactService contactService;
    private final Runnable onSave; // Callback для обновления таблицы
    private final Contact contact; // Контакт, переданный в форму

    public ContactFormDialog(Contact contact, ContactService contactService, Runnable onSave) {
        this.contactService = contactService;
        this.onSave = onSave;
        this.contact = contact;

        // Инициализация формы с данными
        nameField.setValue(contact.getName() != null ? contact.getName() : "");
        phoneField.setValue(contact.getPhoneNumber() != null ? contact.getPhoneNumber() : "");
        emailField.setValue(contact.getEmail() != null ? contact.getEmail() : "");
        notesField.setValue(contact.getNotes() != null ? contact.getNotes() : "");

        // Кнопки
        Button saveButton = new Button("Сохранить", event -> saveContact());
        Button deleteButton = new Button("Удалить", event -> deleteContact());
        deleteButton.getElement().setAttribute("theme", "error"); // Красная кнопка

        // Показываем кнопку "Удалить" только для существующих контактов
        deleteButton.setVisible(contact.getId() != null);

        // Разметка
        FormLayout formLayout = new FormLayout(nameField, phoneField, emailField, notesField);
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, deleteButton);
        VerticalLayout layout = new VerticalLayout(formLayout, buttonLayout);

        add(layout);
    }

    private void saveContact() {
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

    private void deleteContact() {
        if (contact.getId() != null) {
            // Создаем диалог
            Dialog dialog = new Dialog();

            // Сообщение
            dialog.add("Вы уверены, что хотите удалить этот контакт?");

            // Кнопки
            Button confirmButton = new Button("Удалить", event -> {
                contactService.deleteContact(contact.getId());
                dialog.close();
                close(); // Закрыть форму редактирования
                onSave.run(); // Обновить таблицу
                Notification.show("Контакт удален!");
            });

            Button cancelButton = new Button("Отмена", event -> dialog.close());

            // Добавляем кнопки в диалог
            dialog.add(new HorizontalLayout(confirmButton, cancelButton));

            // Открываем диалог
            dialog.open();
        }
    }
}
