package rcp.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import rcp.bean.LoginBean;
import rcp.conf.YetkiEnum;
import rcp.entity.SorgulaEntity;
import rcp.entity.UserEntity;
import rcp.entity.YetkiKaydet;
import rcp.model.HangiKurs;
import rcp.model.Person;
import rcp.model.PersonList;
import rcp.model.User;
import rcp.model.Yetki;

public class DAO {

	private static DAO uniqueInstance;

	public static DAO getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new DAO();
		}
		return uniqueInstance;
	}

	SessionFactory sessionFactory;

	public DAO() {
		sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}

	public User checkUser(UserEntity userEntity) {
		User user = null;
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("userName", userEntity.getUserName()));
		criteria.add(Restrictions.eq("password", userEntity.getPassword()));
		List myList = criteria.list();
		if (myList.size() > 0)
			user = (User) myList.get(0);
		session.close();
		return user;
	}

	public List<User> getUserList() {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(User.class);
		List<User> myList = criteria.list();
		session.close();
		return myList;
	}

	public void addUser(User userAdd) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(userAdd);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public Person addPerson(Person personAdd) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(personAdd);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return personAdd;
	}

	public Person deletePerson(Person personAdd) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(personAdd);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return personAdd;
	}

	public void updatePerson(Person personAdd) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.merge(personAdd);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public List<PersonList> getPersonList() {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Person.class);
		List<Person> myList = criteria.list();

		List<PersonList> personLists = getPersonList2(myList);

		session.close();
		return personLists;
	}

	public List<HangiKurs> getHangiKursListSetect(int id) {
		List<HangiKurs> hangikurs = null;
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Person.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.eq("hangiKurs", id));
		List<Person> myList = criteria.list();

		if (myList.size() > 0)
			hangikurs = myList.get(0).getHangiKurs();

		session.close();
		return hangikurs;
	}

	public List<PersonList> sorgula(SorgulaEntity sorgulaEntity) {
		Session session = sessionFactory.openSession();
		List<Person> myList = null;
		List<PersonList> personList = null;
		try {
			Criteria criteria = session.createCriteria(Person.class);
			if (sorgulaEntity.getAd() != null && sorgulaEntity.getAd().length() > 0)
				criteria.add(Restrictions.ilike("ad", sorgulaEntity.getAd()));

			if (sorgulaEntity.getSoyad() != null && sorgulaEntity.getSoyad().length() > 0)
				criteria.add(Restrictions.ilike("soyad", sorgulaEntity.getSoyad()));

			// if (sorgulaEntity.getHangiKurs() != null &&
			// sorgulaEntity.getHangiKurs().size() > 0)
			// criteria.add(Restrictions.ilike("hangiKurs",
			// sorgulaEntity.getHangiKurs()));

			if (sorgulaEntity.getBasTarihi() != null)
				criteria.add(Restrictions.ge("gorusmeZamani", sorgulaEntity.getBasTarihi()));

			if (sorgulaEntity.getSonTarihi() != null)
				criteria.add(Restrictions.lt("gorusmeZamani", sorgulaEntity.getSonTarihi()));

			if (sorgulaEntity.getType() != 0)
				criteria.add(Restrictions.eq("gorusmeTipi", sorgulaEntity.getType()));

			myList = (List<Person>) criteria.list();

			personList = getPersonList2(myList);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return personList;
	}

	public List<HangiKurs> getHangiKursList() {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(HangiKurs.class);
		List<HangiKurs> myList = criteria.list();
		session.close();
		return myList;
	}

	public List<PersonList> getPersonList2(List<Person> myList) {

		boolean listeYetki = false;

		if (YetkiEnum.ADMIN.getId() == LoginBean.getYetkiGenel()) {
			listeYetki = true;
		}

		List<PersonList> personLists = new ArrayList<>();

		for (int i = 0; i < myList.size(); i++) {

			if (myList.get(i).getUser() != null) {

				if (myList.get(i).getUser().getId() == LoginBean.getMevcutUser().getId() || listeYetki) {

					List<HangiKurs> myListHangi = myList.get(i).getHangiKurs();

					List<String> hangiKurs = new ArrayList<>();
					for (int k = 0; k < myListHangi.size(); k++) {
						hangiKurs.add(myListHangi.get(k).getKursAdi());
					}

					String gorusmeTipi = null;
					if (myList.get(i).getGorusmeTipi() == 1) {
						gorusmeTipi = "Telefonla";
					} else {
						gorusmeTipi = "Yüz Yüze";
					}

					PersonList personList = new PersonList(myList.get(i).getId(), myList.get(i).getAd(),
							myList.get(i).getSoyad(), myList.get(i).getTelefon(), myList.get(i).getEmail(), hangiKurs,
							gorusmeTipi, myList.get(i).getMesaj(), myList.get(i).getGorusmeZamani());

					personLists.add(personList);

				}
			}

		}

		return personLists;

	}

	public Person getPerson(int id) {
		Person person = null;
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Person.class);
		criteria.add(Restrictions.eq("id", id));
		List myList = criteria.list();

		if (myList.size() > 0)
			person = (Person) myList.get(0);
		session.close();
		return person;

	}

	public List<YetkiKaydet> getYetkiList() {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Yetki.class);
		List<Yetki> myList = criteria.list();

		List<YetkiKaydet> yetkiKaydets = new ArrayList<>();
		YetkiKaydet yetkiKaydet;
		for (int i = 0; i < myList.size(); i++) {

			yetkiKaydet = new YetkiKaydet();

			yetkiKaydet.setId(myList.get(i).getId());

			if (myList.get(i).getYetki() == YetkiEnum.ADMIN.getId()) {

				yetkiKaydet.setAd(YetkiEnum.ADMIN.getValue());

			} else if (myList.get(i).getYetki() == YetkiEnum.KULLANICI.getId()) {

				yetkiKaydet.setAd(YetkiEnum.KULLANICI.getValue());

			}

			yetkiKaydets.add(yetkiKaydet);
		}

		session.close();
		return yetkiKaydets;
	}

	public void deleteUser(User user) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void updateUser(User user) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.merge(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public void addKurs(HangiKurs hangiKurs) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(hangiKurs);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public void deleteKurs(HangiKurs hangiKurs) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(hangiKurs);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public void updateKurs(HangiKurs hangiKurs) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.merge(hangiKurs);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
