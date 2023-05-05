package ntukhpi.semit.dde.studentsdata.democlasses;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Person;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class TestInheritance {
    public static void main(String[] args) {
        List<Student> listEntities = null;
        List<Person> list = null;
        //  Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n\nHIBERNATE підключивася до БД!!!");
            DAOObjects.outputList(DAOObjects.daoStudent.getAllList(),"\nSHOW ALL Students");
            DAOObjects.outputList(DAOObjects.daoPerson.getAllList(),"\nSHOW ALL Person");
            //listEntities = (List<Student>) DAOObjects.daoPerson.getAllList();
            DAOObjects.outputList(DAOObjects.daoContact.getAllList(),"\nSHOW ALL Contacts");
            System.out.println("\n\n====================== HIBERNATE END WORk !!! =======================");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(111);
        }
    }
}
