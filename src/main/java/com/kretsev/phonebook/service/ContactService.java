package com.kretsev.phonebook.service;

import com.kretsev.phonebook.model.Contact;

import java.util.List;

public interface ContactService {
    public List<Contact> getAllContacts();
    public Contact saveContact(Contact contact);
    public void deleteContact(String id);
}
    