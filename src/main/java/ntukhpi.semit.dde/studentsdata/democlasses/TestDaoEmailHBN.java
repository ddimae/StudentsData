package ntukhpi.semit.dde.studentsdata.democlasses;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Email;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.utils.Formats;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class TestDaoEmailHBN {
    public static void main(String[] args) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        List<Email> emailsOfOwner  = null;
        String nameForInsert = "";
        Student owner = null;
        Student ownerInDB = null;
        Email emailFromDB = null;
        if (sf == null) {
            System.err.println("Connect to database not executed!!!");
            System.exit(777);
        } else {
            Transaction transaction = null;
            try (Session session = sf.openSession()) {
                transaction = session.beginTransaction();
                //Add new Student
                nameForInsert = "Шилов";
                String key2 = "Володимир";
                String key3 = "Іванович";
                LocalDate dr = LocalDate.parse("05.12.2003", Formats.FORMAT_DATE_UA);
                owner = new Student(nameForInsert,key2,key3,dr);
                System.out.println(owner);
                if (DAOObjects.daoStudent.insert(owner)) {
                    System.out.println("\nNew Student added");
                }

                //Load added student from db
                //Its give record with id
                ownerInDB = DAOObjects.daoStudent.findByKey(owner);
                //Add Emails to new Student
                System.out.println("\nGo add emails!");
                Email email = new Email(true,  true, ownerInDB,"mail111@gmail.com");
                System.out.println(email);
                if (DAOObjects.daoEmail.insert(email)){
                    System.out.println(email.getEmail()+" successfully added");
                }
                Email email2 = new Email(false, false, ownerInDB, "mail222@gmail.com");
                System.out.println(email2);
                if (DAOObjects.daoEmail.insert(email2)){
                    System.out.println(email2.getEmail()+" successfully added");
                }
                Email email3 = new Email(true, false,ownerInDB, "mail333@gmail.com");
                System.out.println(email3);
                if (DAOObjects.daoEmail.insert(email3)){
                    System.out.println(email3.getEmail()+" successfully added");
                }
                transaction.commit();
            } catch (Exception ex) {
                System.err.println("===== save Email == Something went wrong! ====");
                if (transaction != null) {
                    transaction.rollback();
                }
            }
            //Read phones by owner
            emailsOfOwner = DAOObjects.daoPerson.findAllEmailByOwner(ownerInDB);
            System.out.println("\nEmails of Person");
            emailsOfOwner.stream().forEach(System.out::println);
            //Delay for sequence output in console
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("\nAdd Phones - using DAOEmailsHBN");
            ownerInDB = DAOObjects.daoStudent.findByKey(owner);
            Email email4 = new Email(true, false, ownerInDB,"mail444@gmail.com");
            System.out.println(email4);
            if (DAOObjects.daoEmail.insert(email4)) {
                System.out.println(email4.getEmail()+ " successfully added!");
            }
            Email email5 = new Email(false, false, ownerInDB, "mail555@gmail.com");
            System.out.println(email5);
            if (DAOObjects.daoEmail.insert(email5)) {
                System.out.println(email5.getEmail()+ " successfully added!");
            }
            //Delay for sequence output in console
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\nInfo about Phones for owner "+owner.getLastName());
            emailsOfOwner = DAOObjects.daoPerson.findAllEmailByOwner(ownerInDB);
            if (!emailsOfOwner.isEmpty()){
                emailsOfOwner.stream().forEach((email)-> System.out.println(email));
                            } else {
                System.out.println("Emails ABSENT!");
            }
            //Delay for sequence output in console
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\n========== Find By Number and Update ==========");
            emailFromDB = DAOObjects.daoEmail.findByKey(email5);
            System.out.println(emailFromDB);
            Email emailUpdate1 = new Email(true, false, ownerInDB, emailFromDB.getEmail());
            if (DAOObjects.daoEmail.update(emailFromDB.getId(),emailUpdate1)){
                System.out.print("WAS UPDATED ===> ");
                emailFromDB =  DAOObjects.daoEmail.findByKey(emailUpdate1);
                System.out.println(emailFromDB);
            }
            //Delay for sequence output in console
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\n========== Find By ID and Update ========== ");
            //Data truncation: Data too long for column 'phone_number' at row 1 - Mistake appeared for phone with more 10 numbers
            Email emailUpdate2 = new Email(true, true, ownerInDB, emailFromDB.getEmail());
            emailFromDB = DAOObjects.daoEmail.findById(emailFromDB.getId());
            System.out.println(emailFromDB);
            if (DAOObjects.daoEmail.update(emailFromDB.getId(),emailUpdate2)){
                emailFromDB = (Email) DAOObjects.daoEmail.findById(emailFromDB.getId());
                System.out.print("WAS UPDATED 2 ===> ");
                System.out.println(emailFromDB);
            }
            //Delay for sequence output in console
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\nInfo about Phones for owner after UPDATE "+owner.getLastName());
            emailsOfOwner = DAOObjects.daoPerson.findAllEmailByOwner(ownerInDB);
            if (!emailsOfOwner.isEmpty()){
                emailsOfOwner.stream().forEach((email)-> System.out.println(email));
            } else {
                System.out.println("Phones ABSENT!");
            }
            System.out.println("\nPhone will deleted...");
            if (DAOObjects.daoEmail.delete(emailFromDB.getId())){
                System.out.println("\n===== DELETING Done!!! =====");
            }
            System.out.println("\nTry take Info about Phones for owner "+owner.getLastName());
            emailsOfOwner = DAOObjects.daoPerson.findAllEmailByOwner(ownerInDB);
            if (!emailsOfOwner.isEmpty()){
                emailsOfOwner.stream().forEach((email)-> System.out.println(email));
            } else {
                System.out.println("Emails ABSENT!");
            }

        }
    }

}
