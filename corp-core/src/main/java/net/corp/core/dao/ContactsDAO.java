package net.corp.core.dao;

import net.corp.core.model.Contacts;

import java.util.List;

public interface ContactsDAO extends GenericDAO<Contacts, Integer> {
    public List<Contacts> findIndividualContacts();
    public List<Contacts> findGroupContacts();
}
