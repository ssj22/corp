package net.corp.core.dao.impl;

import net.corp.core.dao.ContactsDAO;
import net.corp.core.dao.ItemsMainDAO;
import net.corp.core.model.Contacts;
import net.corp.core.model.Items;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ContactsDAOImpl extends GenericDAOImpl<Contacts, Integer> implements ContactsDAO {

    @Override
    public List<Contacts> findIndividualContacts() {
        Criteria crit = getSession().createCriteria(Contacts.class);
        crit.add(Restrictions.eq("groupInd", false));
        return crit.list();
    }

    @Override
    public List<Contacts> findGroupContacts() {
        Criteria crit = getSession().createCriteria(Contacts.class);
        crit.add(Restrictions.eq("groupInd", true));
        return crit.list();
    }
}
