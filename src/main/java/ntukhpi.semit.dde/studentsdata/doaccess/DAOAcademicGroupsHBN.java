package ntukhpi.semit.dde.studentsdata.doaccess;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Contact;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.TreeSet;

/**
 * class DAOAcademicGroupsHBN
 * <p>
 * DAO - data access object
 * Provide implementation of CRUD with specified table
 * In presented view can work with any type of DBMS
 * </p>
 */
public class DAOAcademicGroupsHBN implements Idao<AcademicGroup> {
    @Override
    public List<AcademicGroup> getAllList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<AcademicGroup> cq = cb.createQuery(AcademicGroup.class); //AcademicGroup.class
        Root<AcademicGroup> rootEntry = cq.from(AcademicGroup.class);
        CriteriaQuery<AcademicGroup> all = cq.select(rootEntry);
        TypedQuery<AcademicGroup> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }
    @Override
    public AcademicGroup findById(Long id) {
        AcademicGroup entityInDB = null;
        List<AcademicGroup> results = null;
        //Find in DB by id
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //New approach
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<AcademicGroup> cr = cb.createQuery(AcademicGroup.class);
            Root<AcademicGroup> root = cr.from(AcademicGroup.class);
            cr.select(root).where(cb.equal(root.get("id"), id));
            Query<AcademicGroup> query = session.createQuery(cr);
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
    public AcademicGroup findByKey(AcademicGroup template) {
        AcademicGroup entityInDB = null;
        List<AcademicGroup> results = null;
        //System.out.println(template.getGroupName());
        //Find in DB by KEY FIELD -
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<AcademicGroup> cr = cb.createQuery(AcademicGroup.class);
            Root<AcademicGroup> root = cr.from(AcademicGroup.class);
            //Here you must adapt select condition to your set of key fields
            //Attentively - check name of field!
            cr.select(root).where(cb.equal(root.get("groupName"), template.getGroupName()));
            Query<AcademicGroup> query = session.createQuery(cr);
            results = query.getResultList();
            if (!results.isEmpty()) {
                entityInDB = results.get(0);
                System.out.println("=== " + this.getClass() + "#findByKey ===");
                System.out.println(entityInDB);
            } else {
                System.out.println("=== " + this.getClass() + "#findByKey === NOT FOUND");
            }
        } catch (Exception e) {
            System.err.println("=== " + this.getClass() + "#findByKey === Something went wrong!");
        }
        return entityInDB;
    }

    @Override
    public boolean insert(AcademicGroup entityToSave) {
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
    public boolean update(Long id, AcademicGroup entityToUpdate) {
        Transaction transaction = null;
        boolean updateOK = false;
        //Find entity by id
        AcademicGroup entityById = findById(id);
        if (entityById != null) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                //prepare entity for update
                entityById.setGroupName(entityToUpdate.getGroupName());
                entityById.setLanguage(entityToUpdate.getLanguage());
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
        AcademicGroup entityById = findById(id);
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

    public boolean saveAcademicGroupToDB(AcademicGroup group) {

        boolean flOK = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            AcademicGroup groupInDB = DAOObjects.daoAcademicGroup.findByKey(group);
            // if exist full clear data about it
            if (groupInDB == null) {
                DAOObjects.daoAcademicGroup.insert(group);
            }

            for (Student stud : group.getStudentsList()) {
                Student studInDB = DAOObjects.daoStudent.findByKey(stud);
                try {
                    if (studInDB == null) {
                        //new student
                        stud.setAcademicGroup(group);
                        DAOObjects.daoStudent.insert(stud);
                    } else {
                        // student already in db - change new group name and update
                       stud.setAcademicGroup(groupInDB);
                       DAOObjects.daoStudent.update(studInDB.getId(), stud);
                    }
                } catch (Exception e) {
                    System.err.println("DAOAcademicGroupsHBN#saveAcademicGroupToDB May be Student already in DB");

                }

            }
            for (Student stud : group.getStudentsList()) {
                for (Contact contact : stud.getContacts()) {
                    try {
                        DAOObjects.daoContact.insert(contact);
                    } catch (Exception e) {
                        System.err.println("DAOAcademicGroupsHBN#saveAcademicGroupToDB May be Contact already in DB");
                    }

                }
            }

        }
        return flOK;
    }

    public boolean deleteAcademicGroupFromDB(AcademicGroup group) {

        boolean flOK = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Student> studentsList = DAOObjects.daoStudent.getAllListByGroup(group);
            for (Student stud : studentsList) {
                DAOObjects.daoStudent.deletePhonesNumberByOwner(stud);
                DAOObjects.daoStudent.deleteEmailsByOwner(stud);
                DAOObjects.daoStudent.delete(stud.getId());
            }
            AcademicGroup groupINDB = DAOObjects.daoAcademicGroup.findByKey(group);
            this.delete(groupINDB.getId());
        }
        return flOK;
    }

}


