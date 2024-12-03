package com.kretsev.phonebook.service.impl;

import com.kretsev.phonebook.model.Contact;
import com.kretsev.phonebook.repository.ContactRepository;
import com.kretsev.phonebook.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public void deleteContact(String id) {
        contactRepository.deleteById(id);
    }
}
