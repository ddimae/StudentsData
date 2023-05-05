package ntukhpi.semit.dde.studentsdata.democlasses;

import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import ntukhpi.semit.dde.studentsdata.utils.Language;
import org.hibernate.Session;

import java.util.List;

public class TestDaoAcademicGroupHBN {
    public static void main(String[] args) {
        List<AcademicGroup> listEntities = null;
        //  Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n\nHIBERNATE підключивася до БД!!!");
            String  entityName= "Academic group";
            System.out.println("\nSHOW ALL RECORDS");
            DAOObjects.outputList(DAOObjects.daoAcademicGroup.getAllList(), entityName+" list:");

            //INSERT
            System.out.println("\nINSERT");
            String keyEntity = "2222s";
            AcademicGroup entityToInsert = new AcademicGroup(keyEntity);
            boolean insertOk = DAOObjects.daoAcademicGroup.insert(entityToInsert);
            if (insertOk) {
                DAOObjects.outputList(DAOObjects.daoAcademicGroup.getAllList(), entityName+" list AFTER INSERT:");
            } else {
                System.err.println(entityName+": mistake inserting new entity instance");
            }

            //Find in DB by KEY FIELDS
            System.out.println("\nFIND BY KEY Fields");
            AcademicGroup entityToFind = new AcademicGroup(keyEntity);
            AcademicGroup entityInDB = DAOObjects.daoAcademicGroup.findByKey(entityToFind);
            if (entityInDB != null) {
                System.out.println(entityToFind.getGroupName() + " was FOUND");
                System.out.println(entityToFind);
            } else {
                System.out.println(entityToFind.getGroupName() + " was NOT FOUND");
            }

            Thread.sleep(3000);

            //UPDATE
            //!!! Impotant !!! Until you not implements delete, before new start
            // its necessary to DELETE record with inserted names from DB or change its
            System.out.println("\nUPDATE");
            String keyEntityUpdate = "КН-2222is";
            AcademicGroup entityToUpdate = new AcademicGroup(keyEntityUpdate, Language.EN);
            boolean updateOk = DAOObjects.daoAcademicGroup.update(entityInDB.getId(),entityToUpdate);
            if (updateOk) {
                DAOObjects.outputList(DAOObjects.daoAcademicGroup.getAllList(), entityName+" list AFTER UPDATE:");
            } else {
                System.err.println(entityName+": mistake updating entity instance");
            }
            Thread.sleep(3000);
            //DELETE NEW&Updated and Show Employee after deleting
            System.out.println("\nDELETE");
            entityToFind = new AcademicGroup(keyEntityUpdate);
            entityInDB = DAOObjects.daoAcademicGroup.findByKey(entityToFind);
            boolean deleteOk = DAOObjects.daoAcademicGroup.delete(entityInDB.getId());
            if (deleteOk) {
                DAOObjects.outputList(DAOObjects.daoAcademicGroup.getAllList(), entityName+" list AFTER DELETE:");
            } else {
                System.err.println(entityName+": mistake deleting entity instance");
            }
            Thread.sleep(3000);
            DAOObjects.daoAcademicGroup.getAllList().stream().forEach(gr-> System.out.println(gr.toStringWithGrouplist()));
            System.out.println("\n\n====================== HIBERNATE END WORk !!! =======================");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(111);
        }

    }
}
