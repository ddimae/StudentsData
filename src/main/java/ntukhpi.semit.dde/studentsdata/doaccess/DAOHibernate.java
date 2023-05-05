package ntukhpi.semit.dde.studentsdata.doaccess;

import jakarta.persistence.Entity;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.EntityType;
import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * class DAOHibernate<E>
 * <p>
 * DAO - data access object
 * Provide implementation of CRUD with specified table
 * In presented view can work with any type of DBMS
 * E - class with what made CRUD
 * </p>
 */
public class  DAOHibernate<E> {
    /**
     * Method returned list of all E get from DB table define in @Table
     *
     * @return List of Class E
     */
    public List<E> getAllList() {
        Entity e = (Entity) new Object();
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(e.getClass());
        Root rootEntry = cq.from(e.getClass());
        CriteriaQuery all = cq.select(rootEntry);

        TypedQuery allQuery = session.createQuery(all);
        return allQuery.getResultList();

    }

    //https://vladmihalcea.com/the-best-way-to-use-entity-inheritance-with-jpa-and-hibernate/
//    public List<T> findAll() {
//        CriteriaBuilder builder = entityManager
//                .getCriteriaBuilder();
//
//        CriteriaQuery<T> criteria = builder
//                .createQuery( entityClass );
//        criteria.from( entityClass );
//
//        return entityManager
//                .createQuery( criteria )
//                .getResultList();
//    }

}
