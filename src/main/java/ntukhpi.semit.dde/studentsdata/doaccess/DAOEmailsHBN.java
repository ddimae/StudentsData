package ntukhpi.semit.dde.studentsdata.doaccess;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ntukhpi.semit.dde.studentsdata.entity.Email;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.TreeSet;

public class DAOEmailsHBN implements Idao<Email> {

    @Override
    public List<Email> getAllList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Email> cq = cb.createQuery(Email.class);
        Root<Email> rootEntry = cq.from(Email.class);
        CriteriaQuery<Email> all = cq.select(rootEntry);
        TypedQuery<Email> allQuery = session.createQuery(all);
        TreeSet<Email> results = new TreeSet<>(allQuery.getResultList());
        return results.stream().toList();
    }

    @Override
    public Email findById(Long id) {
        return (Email) DAOObjects.daoContact.findById(id);
    }

    @Override
    public Email findByKey(Email template) {
        Email entityInDB = null;
        List<Email> results = null;
        //Find in DB by id
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //New approach
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Email> cr = cb.createQuery(Email.class);
            Root<Email> root = cr.from(Email.class);
            cr.select(root).where(cb.equal(root.get("email"), template.getEmail()));
            Query<Email> query = session.createQuery(cr);
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
    public boolean insert(Email entityToSave) {
        return DAOObjects.daoContact.insert(entityToSave);
    }

    @Override
    public boolean update(Long id, Email entityToUpdate) {
        return DAOObjects.daoContact.update(id, entityToUpdate);
    }

    @Override
    public boolean delete(Long id) {
        return DAOObjects.daoContact.delete(id);
    }

}


