import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStudents {

    private static Session session;
    private Transaction transaction;

    @BeforeAll
    static void beforeAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("\n\nHIBERNATE підключивася до БД!!!");

    }

    @Test
    void TestInsert(){
        AcademicGroup group = new AcademicGroup("КН-22222а");
        System.out.println(group);
        Student stud = new Student("КОСТОМАРОВA", "Євгенія", "Ігоровна");
        stud.setAcademicGroup(group);
        DAOObjects.daoStudent.insert(stud);
        group.addStudent(stud);
        DAOObjects.daoAcademicGroup.insert(group);
        assertEquals(null,DAOObjects.daoStudent.findByKey(stud));
    }

    @Test
    void TestStudentByGroup() {
        AcademicGroup group = new AcademicGroup("КН-221а");
        System.out.println(group);
        AcademicGroup groupInDB = DAOObjects.daoAcademicGroup.findById(5l);
        System.out.println(groupInDB);
        List<Student> students = DAOObjects.daoStudent.getAllListByGroup(groupInDB);
        System.out.println(students);

    }

    @Test
    void TestAllStudent() {
        List<Student> students = DAOObjects.daoStudent.getAllList();
        System.out.println(students);
    }

    @Test
    void TestAllGroup() {
        List<AcademicGroup> groups = DAOObjects.daoAcademicGroup.getAllList();
        System.out.println(groups);
    }

    @Test
    void TestDeleteStudentsPhones() {
        Student stud = new Student("КОСТОМАРОВ", "Євгеній", "Ігорович");
        DAOObjects.daoStudent.deletePhonesNumberByOwner(stud);
    }

    @Test
    void TestDeleteStudentsEmails() {
        Student stud = new Student("КОСТОМАРОВ", "Євгеній", "Ігорович");
        DAOObjects.daoStudent.deleteEmailsByOwner(stud);
    }

    @Test
    void TestDeleteAllContacts() {
        Student stud = new Student("БАХИШОВ", "Вагід", "Самед");
        DAOObjects.daoStudent.deletePhonesNumberByOwner(stud);
        DAOObjects.daoStudent.deleteEmailsByOwner(stud);
        stud = DAOObjects.daoStudent.findByKey(stud);
        assertEquals(0,stud.getContacts().size());

    }

    @AfterAll
    static void afterAll() {
        System.out.println("\n\nHIBERNATE завершив роботу!!!");
        HibernateUtil.shutdown();
    }

}
