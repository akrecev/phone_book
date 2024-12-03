package com.kretsev.phonebook.repository;

import com.kretsev.phonebook.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepository extends MongoRepository<Contact, String> {
}
