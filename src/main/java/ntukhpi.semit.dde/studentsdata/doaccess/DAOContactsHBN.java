package ntukhpi.semit.dde.studentsdata.doaccess;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ntukhpi.semit.dde.studentsdata.entity.Contact;
import ntukhpi.semit.dde.studentsdata.entity.Person;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * class DAOContactsHBN
 * <p>
 * DAO - data access object
 * Provide implementation of CRUD with specified table
 * In presented view can work with any type of DBMS
 * </p>
 */
public class DAOContactsHBN implements Idao<Contact> {
    @Override
    public List<Contact> getAllList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Contact> cq = cb.createQuery(Contact.class); //<Contact>.class
        Root<Contact> root = cq.from(Contact.class);
        CriteriaQuery<Contact> all = cq.select(root);
        TypedQuery<Contact> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public Contact findById(Long id) {
        Contact entityInDB = null;
        List<Contact> results = null;
        //Find in DB by id
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //New approach
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Contact> cr = cb.createQuery(Contact.class);
            Root<Contact> root = cr.from(Contact.class);
            cr.select(root).where(cb.equal(root.get("id"), id));
            Query<Contact> query = session.createQuery(cr);
            results = query.getResultList();
            if (!results.isEmpty()) {
                entityInDB = results.get(0);
            }
        } catch (Exception e) {
            System.err.println("=== " + this.getClass() + "#findByID === Something went wrong!");
        }
        return entityInDB;
    }

    @Override
    public Contact findByKey(Contact template) {
        return null;
    }

    @Override
    public boolean insert(Contact entityToSave) {
        Transaction transaction = null;
        boolean insertOK = false;
        // Find object with key field specified in entityToSave in DB
        // Це потрібно, тому що на рівні СКБД індекс спрацьовує тільки на основі всіх заповнених полів
        Contact entityToSaveInDB = findByKey(entityToSave);
        if (entityToSaveInDB!= null) {
            return false;
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // start a transaction
            transaction = session.beginTransaction();
            //Find owner by id
            Person owner = DAOObjects.daoPerson.findByKey(entityToSave.getOwner());
            //add new contact to contacts list of specified Person
            owner.addContact(entityToSave);
            entityToSave.setOwner(owner);
            // save the PhoneNumber objects
            session.save(entityToSave);
            // update owner
            session.update(owner);
            // commit transaction
            transaction.commit();
            insertOK = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("=== " + this.getClass() + "#insert === Something went wrong!");
        }
        return insertOK;
    }

    // Змінюється тільки статус контакта - ни номер, ни владелец не меняются.
    // У випадку необхідності такої зміни контакт вилучається, а потім додається новий
    @Override
    public boolean update(Long id, Contact entityToUpdate) {
        Transaction transaction = null;
        boolean updateOK = false;
        //Find entity by id
        Contact entityById = findById(id);
        if (entityById != null) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                //prepare entity for update
                entityById.setActive(entityToUpdate.isActive());
                entityById.setPrior(entityToUpdate.isPrior());
                // start a transaction
                transaction = session.beginTransaction();
                // update the Employee objects
                session.update(entityById);
                // commit transaction
                transaction.commit();
                updateOK = true;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println("=== " + this.getClass() + "#update === Something went wrong!");
            }
        }
        return updateOK;
    }

    @Override
    public boolean delete(Long id) {
        Transaction transaction = null;
        boolean deleteOK = false;
        //Find entity by id
        Contact entityById = findById(id);
        if (entityById != null) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                //Find owner by id
                Person owner = DAOObjects.daoPerson.findByKey(entityById.getOwner());
                //delete contact from contacts list of specified Person
                owner.delContact(entityById);
                // delete the student objects
                session.delete(entityById);
                // update owner
                session.update(owner);
                // start a transaction
                transaction = session.beginTransaction();
                // update the Employee objects
                session.remove(entityById);
                // commit transaction
                transaction.commit();
                deleteOK = true;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println("=== " + this.getClass() + "#delete === Something went wrong!");
            }
        }
        return deleteOK;
    }


}


