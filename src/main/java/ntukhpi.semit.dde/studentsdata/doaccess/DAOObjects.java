package ntukhpi.semit.dde.studentsdata.doaccess;

import java.util.List;

public class DAOObjects {
    public static DAOAcademicGroupsHBN daoAcademicGroup = new DAOAcademicGroupsHBN();
    public static DAOPersonsHBN daoPerson = new DAOPersonsHBN();
    public static DAOStudentsHBN daoStudent = new DAOStudentsHBN();
    public static DAOContactsHBN daoContact = new DAOContactsHBN();
    public static DAOPhonesHBN daoPhone = new DAOPhonesHBN();
    public static DAOEmailsHBN daoEmail = new DAOEmailsHBN();
    //Output list to console
    public static void outputList(List list,String label) {
        System.out.println(label);
        System.out.println();
        if (list.isEmpty()) {
            System.out.println("list is empty");
        } else {
            list.stream().forEach(System.out::println);
        }
    }
}
