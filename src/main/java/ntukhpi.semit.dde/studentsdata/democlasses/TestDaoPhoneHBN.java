package ntukhpi.semit.dde.studentsdata.democlasses;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.doaccess.DAOPhonesHBN;
import ntukhpi.semit.dde.studentsdata.doaccess.DAOStudentsHBN;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.utils.Formats;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class TestDaoPhoneHBN {
    public static void main(String[] args) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        String phoneNumber = "";
        String newPhoneNumber = "";
        List<PhoneNumber> phonesOfOwner  = null;
        String nameForInsert = "";
        Student owner = null;
        Student ownerInDB = null;
        PhoneNumber phoneFromDB = null;
        if (sf == null) {
            System.err.println("Connect to database not executed!!!");
            System.exit(777);
        } else {
            Transaction transaction = null;
            try (Session session = sf.openSession()) {
                transaction = session.beginTransaction();
                //Add new Employee
                nameForInsert = "Lozhkin";
                String key2 = "Петр";
                String key3 = "Петрович";
                LocalDate dr = LocalDate.parse("01.02.2004", Formats.FORMAT_DATE_UA);
                owner = new Student(nameForInsert,key2,key3,dr);
                System.out.println(owner);
                if (DAOObjects.daoStudent.insert(owner)) {
                    System.out.println("\nNew Student added");
                }

                //Load added student from db
                //Its give record with id
                ownerInDB = DAOObjects.daoStudent.findByKey(owner);
                //Add Phones to new Student
                System.out.println("\nGo add phones!");
                PhoneNumber mobile = new PhoneNumber(true,  true, ownerInDB,"380952223333");
                System.out.println(mobile);
                if (DAOObjects.daoPhone.insert(mobile)){
                    System.out.println(mobile.getPhoneNumber()+" successfully added");
                }
                PhoneNumber mobile2 = new PhoneNumber(false, false, ownerInDB, "380673334444");
                System.out.println(mobile2);
                if (DAOObjects.daoPhone.insert(mobile2)){
                    System.out.println(mobile2.getPhoneNumber()+" successfully added");
                }
                PhoneNumber mobile3 = new PhoneNumber(true, false,ownerInDB, "380674445555");
                System.out.println(mobile3);
                if (DAOObjects.daoPhone.insert(mobile3)){
                    System.out.println(mobile3.getPhoneNumber()+" successfully added");
                }
                transaction.commit();
            } catch (Exception ex) {
                System.err.println("===== save Phone == Something went wrong! ====");
                if (transaction != null) {
                    transaction.rollback();
                }
            }
            //Read phones by owner
            phonesOfOwner = DAOObjects.daoPerson.findAllPhonesNumberByOwner(ownerInDB);
            System.out.println("\nPhones of Person");
            phonesOfOwner.stream().forEach(System.out::println);
            //Delay for sequence output in console
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("\nAdd Phones - using DAOPhonesHBN");
            ownerInDB = DAOObjects.daoStudent.findByKey(owner);
            PhoneNumber mobile4 = new PhoneNumber(true, false, ownerInDB,"380679999777");
            System.out.println(mobile4);
            if (DAOObjects.daoPhone.insert(mobile4)) {
                System.out.println(mobile4.getPhoneNumber()+ " successfully added!");
            }
            PhoneNumber mobile5 = new PhoneNumber(false, false, ownerInDB, "380978888666");
            System.out.println(mobile5);
            if (DAOObjects.daoPhone.insert(mobile5)) {
                System.out.println(mobile5.getPhoneNumber()+ " successfully added!");
            }
            //Delay for sequence output in console
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\nInfo about Phones for owner "+owner.getLastName());
            phonesOfOwner = DAOObjects.daoPerson.findAllPhonesNumberByOwner(ownerInDB);
            if (!phonesOfOwner.isEmpty()){
                phonesOfOwner.stream().forEach((phone)-> System.out.println(phone));
                            } else {
                System.out.println("Phones ABSENT!");
            }
            //Delay for sequence output in console
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\n========== Find By Number and Update ==========");
            phoneFromDB = DAOObjects.daoPhone.findByKey(mobile5);
            System.out.println(phoneFromDB);
            PhoneNumber phoneUpdate1 = new PhoneNumber(true, false, ownerInDB, phoneFromDB.getPhoneNumber());
            if (DAOObjects.daoPhone.update(phoneFromDB.getId(),phoneUpdate1)){
                System.out.print("WAS UPDATED ===> ");
                phoneFromDB =  DAOObjects.daoPhone.findByKey(phoneUpdate1);
                System.out.println(phoneFromDB);
            }
            //Delay for sequence output in console
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\n========== Find By ID and Update ========== ");
            //Data truncation: Data too long for column 'phone_number' at row 1 - Mistake appeared for phone with more 10 numbers
            PhoneNumber phoneUpdate2 = new PhoneNumber(true, true, ownerInDB, phoneFromDB.getPhoneNumber());
            phoneFromDB = DAOObjects.daoPhone.findById(phoneFromDB.getId());
            System.out.println(phoneFromDB);
            if (DAOObjects.daoPhone.update(phoneFromDB.getId(),phoneUpdate2)){
                phoneFromDB = (PhoneNumber) DAOObjects.daoPhone.findById(phoneFromDB.getId());
                System.out.print("WAS UPDATED 2 ===> ");
                System.out.println(phoneFromDB);
            }
            //Delay for sequence output in console
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\nInfo about Phones for owner after UPDATE "+owner.getLastName());
            phonesOfOwner = DAOObjects.daoPerson.findAllPhonesNumberByOwner(ownerInDB);
            if (!phonesOfOwner.isEmpty()){
                phonesOfOwner.stream().forEach((phone)-> System.out.println(phone));
            } else {
                System.out.println("Phones ABSENT!");
            }
            System.out.println("\nPhone will deleted...");
            if (DAOObjects.daoPhone.delete(phoneFromDB.getId())){
                System.out.println("\n===== DELETING Done!!! =====");
            }
            System.out.println("\nTry take Info about Phones for owner "+owner.getLastName());
            phonesOfOwner = DAOObjects.daoPerson.findAllPhonesNumberByOwner(ownerInDB);
            if (!phonesOfOwner.isEmpty()){
                phonesOfOwner.stream().forEach((phone)-> System.out.println(phone));
            } else {
                System.out.println("Phones ABSENT!");
            }

        }
    }

}
