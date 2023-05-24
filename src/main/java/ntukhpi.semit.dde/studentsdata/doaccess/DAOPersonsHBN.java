package ntukhpi.semit.dde.studentsdata.doaccess;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ntukhpi.semit.dde.studentsdata.entity.Person;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.entity.Parent;
import ntukhpi.semit.dde.studentsdata.entity.Teacher;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;
import ntukhpi.semit.dde.studentsdata.entity.Email;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class DAOPersonsHBN implements Idao<Person> {

    @Override
    public List<Person> getAllList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Person.class);
        Root rootEntry = cq.from(Person.class);
        CriteriaQuery all = cq.select(rootEntry);

        TypedQuery allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }

    //Common method for all Person implemented in DAOObjects.daoStudent.findById(id);
    @Override
    public Person findById(Long id) {
        Person entityInDB = null;
        List<Person> results = null;
        //Find in DB by id
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //New approach
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Person> cr = cb.createQuery(Person.class);
            Root<Person> root = cr.from(Person.class);
            cr.select(root).where(cb.equal(root.get("id"), id));
            Query<Person> query = session.createQuery(cr);
            results = query.getResultList();
            if (!results.isEmpty()) {
                entityInDB = results.get(0);
            }
        } catch (Exception e) {
            System.err.println("=== " + this.getClass() + "#findByID === Something went wrong!");
        }
        return entityInDB;
    }

    //Common method for all Person implemented in DAOObjects.daoStudent.findByKey(template);
    public Person findByKey(Person template) {
        Person entityInDB = null;
        List<Person> results = null;
        //System.out.println(template.getGroupName());
        //Find in DB by KEY FIELD -
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Person> cr = cb.createQuery(Person.class);
            Root<Person> root = cr.from(Person.class);
            //Here you must adapt select condition to your set of key fields
            //Atentively - check name of fields!
//            Predicate[] predicates = new Predicate[4];
//            predicates[0] = cb.equal(root.get("lastName"), template.getLastName());
//            predicates[1] = cb.equal(root.get("firstName"), template.getFirstName());
//            predicates[2] = cb.equal(root.get("middleName"), template.getMiddleName());
//            predicates[3] = cb.equal(root.get("dateOfBirth"), template.getDateOfBirth());
            List<Predicate> predicateList = new ArrayList<>();
            predicateList.add(cb.equal(root.get("lastName"), template.getLastName()));
            final String firstname = template.getFirstName();
            if (firstname == null) {
                predicateList.add(cb.isNull(root.get("firstName")));
            } else {
                predicateList.add(cb.equal(root.get("firstName"), template.getFirstName()));
            }
            final String middlename = template.getMiddleName();
            if (middlename == null) {
                predicateList.add(cb.isNull(root.get("middleName")));
            } else {
                predicateList.add(cb.equal(root.get("middleName"), template.getMiddleName()));
            }
            if (template.getDateOfBirth() == null) {
                predicateList.add(cb.isNull(root.get("dateOfBirth")));
            } else {
                predicateList.add(cb.equal(root.get("dateOfBirth"), template.getDateOfBirth()));
            }
            Predicate[] predicates = predicateList.toArray(new Predicate[0]);
            cr.select(root).where(predicates);
            Query<Person> query = session.createQuery(cr);
            results = query.getResultList();
            if (!results.isEmpty()) {
                entityInDB = results.get(0);
//                System.out.println("=== " + this.getClass() + "#findByKey ===");
//                System.out.println(entityInDB);
            } else {
                System.out.println("=== " + this.getClass() + "#findByKey === NOT FOUND");
            }
        } catch (Exception e) {
            System.err.println("=== " + this.getClass() + "#findByKey === Something went wrong!");
        }
        return entityInDB;
    }

    @Override
    public boolean insert(Person entityToSave) {
        Transaction transaction = null;
        boolean insertOK = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            session.persist(entityToSave);
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

    @Override
    public boolean update(Long id, Person entityToUpdate) {
        Transaction transaction = null;
        boolean updateOK = false;
        //Find entity by id
        Person entityById = findById(id);
        if (entityById != null) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                //prepare entity for update
                entityById.setLastName(entityToUpdate.getLastName());
                entityById.setFirstName(entityToUpdate.getFirstName());
                entityById.setMiddleName(entityToUpdate.getMiddleName());
                entityById.setDateOfBirth(entityToUpdate.getDateOfBirth());
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
        Person entityById = findById(id);
        if (entityById != null) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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

    /**
     * Method to find all phones for specified owner (Person)
     *
     * @param owner - specified Person
     * @return boolean - true if record has been updated, false - in other case
     */
    public List<PhoneNumber> findAllPhonesNumberByOwner(Person owner) {
        Person ownerInDB = findByKey(owner);
        List<PhoneNumber> results = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<PhoneNumber> cr = cb.createQuery(PhoneNumber.class);
            Root<PhoneNumber> root = cr.from(PhoneNumber.class);
            cr.select(root).where(cb.equal(root.get("owner"), ownerInDB));
            Query<PhoneNumber> query = session.createQuery(cr);
            results = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Method to find all emails for specified owner (Person)
     *
     * @param owner - specified Person
     * @return boolean - true if record has been updated, false - in other case
     */
    public List<Email> findAllEmailByOwner(Person owner) {
        Person ownerInDB = findByKey(owner);
        List<Email> results = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Email> cr = cb.createQuery(Email.class);
            Root<Email> root = cr.from(Email.class);
            cr.select(root).where(cb.equal(root.get("owner"), ownerInDB));
            Query<Email> query = session.createQuery(cr);
            results = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public String linkToReturn(Person person) {
        if (person instanceof Student){
            return "students?id_group="+((Student) person).getAcademicGroup().getId();
        }
        if (person instanceof Parent) {
            return "parents?id_owner=" + person.getId();
        }
        if (person instanceof Teacher){
                    return "groups";
        }
        return "";
    }
}
