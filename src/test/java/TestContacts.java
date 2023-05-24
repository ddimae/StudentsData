import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Contact;
import ntukhpi.semit.dde.studentsdata.entity.Person;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestContacts {
    Student stud = null;
    @BeforeAll
    static void beforeAll() {

    }

    @Test
    void testPhoneWithOwner() {
        stud =  new Student("Петров","Петр","");
        System.out.println(stud.initialsToString());
        Contact phone = new PhoneNumber(true,true,stud,"380956362481");
        System.out.println(phone.toStringWithOwner());
    }
    @Test
    void testPriorPhone() {
        //Не додуман.... Главным Contact может быть Email and PhoneNumber
        stud =  new Student("Петров","Петр","");
        Contact phone = new PhoneNumber(true,true,stud,"380956362481");
        Contact phone1 = new PhoneNumber(true,false,stud,"380591818231");
        System.out.println(stud.contactsPersonToString());
        DAOObjects.daoStudent.insert(stud);
        DAOObjects.daoPhone.insert((PhoneNumber) phone);
        DAOObjects.daoPhone.insert((PhoneNumber) phone1);
        stud = DAOObjects.daoStudent.findByKey(stud);
        Contact phonePrior = DAOObjects.daoPhone.findPrior(stud);
        System.out.println(phonePrior);
    }
}
