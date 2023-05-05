package ntukhpi.semit.dde.studentsdata.doaccess;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ntukhpi.semit.dde.studentsdata.entity.Person;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

import static java.util.Locale.Category.DISPLAY;


/**
 * class DAOStudentsHBN
 * <p>
 * DAO - data access object
 * Provide implementation of CRUD with specified table
 * In presented view can work with any type of DBMS
 */
public class DAOStudentsHBN implements Idao<Student> {
    @Override
    public List<Student> getAllList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Student.class);
        Root rootEntry = cq.from(Student.class);
        CriteriaQuery all = cq.select(rootEntry);

        TypedQuery allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public Student findById(Long id) {
        return (Student) DAOObjects.daoPerson.findById(id);
    }

    @Override
    public Student findByKey(Student template) {
        return (Student) DAOObjects.daoPerson.findByKey(template);
    }

    @Override
    public boolean insert(Student entityToSave) {
        return DAOObjects.daoPerson.insert(entityToSave);
    }

    @Override
    public boolean update(Long id, Student entityToUpdate) {
        Transaction transaction = null;
        boolean updateOK = false;
        //Find entity by id
        Student entityById = findById(id);
        if (entityById != null) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                //prepare entity for update
//                entityById.setLastName(entityToUpdate.getLastName());
//                entityById.setFirstName(entityToUpdate.getFirstName());
//                entityById.setMiddleName(entityToUpdate.getMiddleName());
//                entityById.setAcademicGroup(entityToUpdate.getAcademicGroup());
                entityById.setDateOfBirth(entityToUpdate.getDateOfBirth());
                entityById.setContract(entityToUpdate.isContract());
                entityById.setTakeScholarship(entityToUpdate.isTakeScholarship());

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
        return DAOObjects.daoPerson.delete(id);
    }

    /**
     * Method to delete all phones for specified owner (Person)
     *
     * @param owner - specified owner
     * @return boolean - true if record has been updated, false - in other case
     */
    public boolean deletePhonesNumberByOwner(Person owner) {
        boolean deleteOk = false;
        if (owner != null) {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                // start a transaction
                transaction = session.beginTransaction();
                Person ownerINDB = DAOObjects.daoPerson.findByKey(owner);
                ownerINDB.getContacts().stream().filter(contact->contact instanceof PhoneNumber)
                        .forEach(phone ->{
                            ownerINDB.getContacts().remove(phone);
                            session.delete(phone);
                        });
                // commit transaction
                transaction.commit();
                deleteOk = true;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println("=== " + this.getClass() + "#delete === Something went wrong!");
                deleteOk = false;
            }
        }
        return deleteOk;
    }
}


