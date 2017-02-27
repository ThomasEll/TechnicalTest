package com.technical.test;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Tom Longdon
 */
public interface ContactRepository extends JpaRepository<Contact, Long>{

    List<Contact> findContactByFirstNameOrLastNameStartsWithIgnoreCase(String firstName, String lastName);

}
