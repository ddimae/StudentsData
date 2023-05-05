package ntukhpi.semit.dde.studentsdata.democlasses;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.utils.Formats;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class TestDaoStudentHBN {
    public static void main(String[] args) {
        List<Student> listEntities = null;
        //  Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n\nHIBERNATE підключивася до БД!!!");
            String  entityName= "Student";
            System.out.println("\nSHOW ALL RECORDS");
            DAOObjects.outputList(DAOObjects.daoStudent.getAllList(), entityName+" list:");

            //INSERT
            System.out.println("\nINSERT");
            String key1 = "Петров";
            String key2 = "Петр";
            String key3 = "Петрович";
            LocalDate dr = LocalDate.parse("01.02.2004", Formats.FORMAT_DATE_UA);
            Student entityToInsert = new Student(key1,key2,key3,dr);
            boolean insertOk = DAOObjects.daoStudent.insert(entityToInsert);
            if (insertOk) {
                DAOObjects.outputList(DAOObjects.daoStudent.getAllList(), entityName+" list AFTER INSERT:");
            } else {
                System.err.println(entityName+": mistake inserting new entity instance");
            }
            Thread.sleep(3000);
            //Find in DB by KEY FIELDS
            System.out.println("\nFIND BY KEY Fields");
            Student entityToFind = new Student(key1,key2,key3,dr);
            Student entityInDB = DAOObjects.daoStudent.findByKey(entityToFind);
            if (entityInDB != null) {
                System.out.println("Instance was FOUND");
                System.out.println(entityToFind);
            } else {
                System.out.println("Instance was NOT FOUND");
            }

            Thread.sleep(3000);

            //UPDATE
            //!!! Impotant !!! Until you not implements delete, before new start
            // its necessary to DELETE record with inserted names from DB or change its
            System.out.println("\nUPDATE");
//            String key1edit = "Шевченко";
//            String key2edit = "Тарас";
//            String key3edit ="Володимирович"; //null;
            LocalDate drEdit = null; //LocalDate.parse("03.03.2003", Formats.FORMAT_DATE_UA); //null; //
            Student entityToUpdate = new Student(key1, key2, key3, drEdit,true,true);
            boolean updateOk = DAOObjects.daoStudent.update(entityInDB.getId(),entityToUpdate);
            if (updateOk) {
                DAOObjects.outputList(DAOObjects.daoStudent.getAllList(), entityName+" list AFTER UPDATE:");
            } else {
                System.err.println(entityName+": mistake updating entity instance");
            }
            Thread.sleep(3000);
            //DELETE NEW&Updated and Show Employee after deleting
            System.out.println("\nDELETE");
            entityToFind = new Student(key1,key2,key3);
            entityInDB = DAOObjects.daoStudent.findByKey(entityToFind);
            boolean deleteOk = DAOObjects.daoStudent.delete(entityInDB.getId());
            if (deleteOk) {
                DAOObjects.outputList(DAOObjects.daoStudent.getAllList(), entityName+" list AFTER DELETE:");
            } else {
                System.err.println(entityName+": mistake deleting entity instance");
            }
            Thread.sleep(3000);
            //
            System.out.println("\nLisT STUDEnts");
            DAOObjects.daoStudent.getAllList().stream().forEach(s->{
                System.out.println(s.showInfoWithGroup());
                System.out.println(s.showParents());
            });
            System.out.println("\n\n====================== HIBERNATE END WORk !!! =======================");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(111);
        }

    }
}
