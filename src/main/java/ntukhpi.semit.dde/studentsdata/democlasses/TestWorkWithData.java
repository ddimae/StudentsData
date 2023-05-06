package ntukhpi.semit.dde.studentsdata.democlasses;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.entity.Address;
import ntukhpi.semit.dde.studentsdata.entity.Parent;
import ntukhpi.semit.dde.studentsdata.entity.Teacher;
import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Contact;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;
import ntukhpi.semit.dde.studentsdata.entity.Email;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import ntukhpi.semit.dde.studentsdata.utils.KinshipDegree;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static ntukhpi.semit.dde.studentsdata.files.ExcelUtilities.readFromWBExcelFullToClearDB;

public class TestWorkWithData {
    public static void main(String[] args) {
        //Create students
        Student s1 = new Student("Dugin", "Jan", "Petrovich", "24.05.2005");
        Student s2 = new Student("Sak", "Iurij", "Ivanovich", "14.05.2005");
        Student s3 = new Student("Somov", "Denis", "Artemovych", "12.09.2004");
        Student s4 = new Student("Melnik", "Daria", "Artemovna", "13.03.2005");
        Student s5 = new Student("Melnikova", "Daria", "Artemovna");
        Student s6 = new Student("Усамма", "Баркейн", null);
        Student s7 = new Student("Хо Вєет Конг", "", "");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
        Parent p1 = new Parent("Dugin", "Petr", "Petrovich", "17.08.1970");
        Parent p2 = new Parent("Dugina", "Ludmila", "Petrivna", "12.08.1975");
        Parent p3 = new Parent("Melnik", "Ludmila", "Ivanivna", "11.01.1963");
        System.out.println(p1);
        System.out.println(p2);
        s1.addParent(p1, KinshipDegree.FATHER);
        s1.addParent(p2, KinshipDegree.MOTHER);
        System.out.println(p3);
        s4.addParent(p3, KinshipDegree.GRANDMOTHER);
        Teacher t = new Teacher("Dvukhglavov", "Dmytro", "Eduardovych", "14.08.1972");
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n\nHIBERNATE add students and parents!!!");
            tx = session.beginTransaction();
            session.persist(s1);
            session.persist(p1);
            session.persist(p2);
            session.persist(s2);
            session.persist(s3);
            session.persist(s4);
            session.persist(p3);
            session.persist(t);
            session.persist(s5);
            session.persist(s6);
            session.persist(s7);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            System.exit(666);
        }
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Student> myList = session.createQuery("from Student", Student.class).list();
            myList.forEach((s) -> {
                System.out.println(s);
                System.out.println(s.showParents());
            });

        }
        //create groupe
        AcademicGroup gr1 = new AcademicGroup("KN221a");
        AcademicGroup gr2 = new AcademicGroup("KN221b");
        AcademicGroup gr3 = new AcademicGroup("KN221v");
        AcademicGroup gr4 = new AcademicGroup("KN222s");

        gr1.addStudent(s1);
        s1.setAcademicGroup(gr1);
        gr1.addStudent(s4);
        s4.setAcademicGroup(gr1);
        gr1.setHeadStudent(s1);
        gr1.setCuratorTeacher(t);
        gr2.addStudent(s2);
        s2.setAcademicGroup(gr2);
        gr2.addStudent(s3);
        s3.setAcademicGroup(gr2);
        gr2.setHeadStudent(s3);
        gr2.setCuratorTeacher(t);
        gr4.setCuratorTeacher(t);
        gr3.addStudent(s5);
        s5.setAcademicGroup(gr3);
        gr3.addStudent(s6);
        s6.setAcademicGroup(gr3);
        gr3.setHeadStudent(s6);
        gr4.addStudent(s7);
        s7.setAcademicGroup(gr4);
        gr4.setHeadStudent(s7);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n\nHIBERNATE create group!!!");
            session.persist(gr1);
            session.persist(gr2);
            session.persist(gr3);
            session.persist(gr4);
            session.update(s1);
            session.update(s2);
            session.update(s3);
            session.update(s4);
            session.update(s5);
            session.update(s6);
            session.update(s7);
            tx = session.beginTransaction();
            tx.commit();
            List<AcademicGroup> myList = session.createQuery("from AcademicGroup", AcademicGroup.class).list();
            myList.forEach((academicGroup) -> System.out.println(academicGroup.toStringWithGrouplist()));//.toStringWithGrouplist()
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            System.exit(666);
        }

        //create adresses
        Address a1 = new Address("", "", "Kharkiv", "Zubenka Vladyslava st., 76, 24");
        Address a2 = new Address("Ukraine", "", "Kharkiv", "Pushkinskaia st., 45/47, room.3405");
        Address a3 = new Address("Ukraine", "", "Kharkiv", "Pushkinskaia st., 45/47, room.3408");
        Address a4 = new Address("Ukraine", "Poltavska", "smt Shishoki", "Zelena st., 16");
        Address a5 = new Address("Germany", "", "Nurnberg", "");
        Address a6 = new Address("", "Vinnitzkaia", "Vinnitza", "Vishenka");

        a1.addOwner(t, false);
        a6.addOwner(t, true);
        a5.addOwner(p1,true);
        a5.addOwner(p2,true);
        a5.addOwner(s1,true);
        a4.addOwner(p3,true);
        a4.addOwner(s4,false);
        a2.addOwner(s2,true);
        a3.addOwner(s3,true);
        a3.addOwner(s4,true);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n\nHIBERNATE adresses!!!");
            tx = session.beginTransaction();
            session.persist(a1);
            session.persist(a2);
            session.persist(a3);
            session.persist(a4);
            session.persist(a5);
            session.persist(a6);
            tx.commit();
            List<Address> myList = session.createQuery("from Address", Address.class).list();
            myList.forEach((address) -> System.out.println(address.toStringWithOwners()));//
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            System.exit(666);
        }

        //create Contacts
        Contact phone1 = new PhoneNumber(true,false,t,"380951203066");
        Contact phone2 = new PhoneNumber(true,true,t,"380682520045");
        Contact email1 = new Email(true,true,t,"ddimae72@gmail.com");
        Contact email2 = new Email(true,false,t,"dmytro.dvukhhlavov@khpi.edu.ua");
        Contact email3 = new Email(true,true,t,"dde.semit.ntukhpi@gmail.com");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n\nHIBERNATE Contacts!!!");
            tx = session.beginTransaction();
            session.persist(phone1);
            session.persist(phone2);
            session.persist(email1);
            session.persist(email2);
            session.persist(email3);
            tx.commit();
            List<Contact>  myList = session.createQuery("from Contact", Contact.class).list();
            myList.forEach((contact) -> System.out.println(contact.toString()));//
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            System.exit(666);
        }
        //Upload for "КН-221a.xlsx" and save into
        AcademicGroup group = null;
        String filenameToRead = "input/КН-221а.xlsx";
        group = readFromWBExcelFullToClearDB(filenameToRead);
        System.out.println("TestWorkWithExcel#readFromWBExcelFullToClearDB: Group from Excel:\n"+group.toStringWithGrouplist());
        if (DAOObjects.daoAcademicGroup.saveAcademicGroupToDB(group)) {
            System.out.println("Дані збережені в БД");
        };

    }
}
