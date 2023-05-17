package ntukhpi.semit.dde.studentsdata.doaccess;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Person;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.entity.Contact;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class DAOPhonesHBN implements Idao<PhoneNumber> {

    @Override
    public List<PhoneNumber> getAllList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PhoneNumber> cq = cb.createQuery(PhoneNumber.class);
        Root<PhoneNumber> rootEntry = cq.from(PhoneNumber.class);
        CriteriaQuery<PhoneNumber> all = cq.select(rootEntry);
        TypedQuery<PhoneNumber> allQuery = session.createQuery(all);
        TreeSet<PhoneNumber> results = new TreeSet<>(allQuery.getResultList());
        return results.stream().toList();
    }

    @Override
    public PhoneNumber findById(Long id) {
        return (PhoneNumber) DAOObjects.daoContact.findById(id);
    }

    @Override
    public PhoneNumber findByKey(PhoneNumber template) {
        PhoneNumber entityInDB = null;
        List<PhoneNumber> results = null;
        //Find in DB by id
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //New approach
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<PhoneNumber> cr = cb.createQuery(PhoneNumber.class);
            Root<PhoneNumber> root = cr.from(PhoneNumber.class);
            cr.select(root).where(cb.equal(root.get("phoneNumber"), template.getPhoneNumber()));
            Query<PhoneNumber> query = session.createQuery(cr);
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
    public boolean insert(PhoneNumber entityToSave) {
        return DAOObjects.daoContact.insert(entityToSave);
    }

    @Override
    public boolean update(Long id, PhoneNumber entityToUpdate) {
        return DAOObjects.daoContact.update(id,entityToUpdate);
    }

    @Override
    public boolean delete(Long id) {
        return DAOObjects.daoContact.delete(id);
    }

    public PhoneNumber findPrior(Person owner) {
        PhoneNumber entityInDB = null;
        List<PhoneNumber> results = null;
        //Find in DB by id
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //New approach
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<PhoneNumber> cr = cb.createQuery(PhoneNumber.class);
            Root<PhoneNumber> root = cr.from(PhoneNumber.class);
            cr.select(root).where(cb.and(cb.equal(root.get("prior"), true)),
                    cb.and(cb.equal(root.get("owner"), owner)));
            Query<PhoneNumber> query = session.createQuery(cr);
            results = query.getResultList();
            if (!results.isEmpty()) {
                entityInDB = results.get(0);
            }
        } catch (Exception e) {
            System.err.println("=== " + this.getClass() + "#findByID === Something went wrong!");
        }
        return entityInDB;
    }
}
