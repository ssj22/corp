package net.corp.core.dao.impl;

import java.util.List;

import net.corp.core.dao.UserDAO;
import net.corp.core.model.Users;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class UserDAOImpl extends GenericDAOImpl<Users, Integer> implements UserDAO {

	@Override
	public Users findByUsername(String username, boolean active) {
		Criteria crit = getSession().createCriteria(Users.class);
		crit.createAlias("userLogin", "login");
		crit.add(Restrictions.eq("login.username", username));
		crit.add(Restrictions.eq("active", active));
		return (Users) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> findByName(String fullname, boolean active, boolean guest) {
		Criteria crit = getSession().createCriteria(Users.class);
		crit.add(Restrictions.eq("fullName", fullname));
		crit.add(Restrictions.eq("active", active));
		crit.add(Restrictions.eq("guest", guest));
		return crit.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Users> findByFirstLastName(String firstName, String lastName,
			boolean active, boolean guest) {
		Criteria crit = getSession().createCriteria(Users.class);
		crit.add(Restrictions.eq("firstName", firstName));
		crit.add(Restrictions.eq("lastName", lastName));
		crit.add(Restrictions.eq("active", active));
		crit.add(Restrictions.eq("guest", guest));
		return crit.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Users> findRootUser(boolean active) {
		Criteria crit = getSession().createCriteria(Users.class);
		crit.add(Restrictions.eq("root", true));
		crit.add(Restrictions.eq("active", active));
		return crit.list();
	}

}
