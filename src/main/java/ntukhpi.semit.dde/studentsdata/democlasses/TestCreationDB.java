package ntukhpi.semit.dde.studentsdata.democlasses;

import ntukhpi.semit.dde.studentsdata.utils.HibernateUtil;
import org.hibernate.Session;

public class TestCreationDB {
    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("\n\nHIBERNATE підключивася до БД!!!");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(666);
        }
    }
}
