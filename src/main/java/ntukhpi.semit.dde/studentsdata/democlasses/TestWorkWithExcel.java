package ntukhpi.semit.dde.studentsdata.democlasses;


import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Student;

import java.util.Set;

import static ntukhpi.semit.dde.studentsdata.files.ExcelUtilities.*;

public class TestWorkWithExcel {
    public static void main(String[] args) throws Exception {
//        AcademicGroup group = null;
//        String filenameToRead = "input/КН-221а.xlsx";
////        group = readFromWBExcel(filenameToRead);
////        System.out.println("TestWorkWithExcel#main: Group
//
// from Excel:"+group.toStringWithGrouplist());
////        //=========================================
//////        group = new AcademicGroup("КН-221а");
//////        System.out.println(group);
////        System.out.println("\nFile saved successfully to " +
////                saveToWBExcel(group.getGroupName(), group.getStudentsList().stream().toList()));
////        //******************************************
//        group = readFromWBExcelFullToClearDB(filenameToRead);
//        System.out.println("TestWorkWithExcel#readFromWBExcelFullToClearDB: Group from Excel:\n"+group.toStringWithGrouplist());
//        if (DAOObjects.daoAcademicGroup.saveAcademicGroupToDB(group)) {
//            System.out.println("Дані збережені в БД");
//        };
        String groupName = "KN221b";

        AcademicGroup academicGroup = DAOObjects.daoAcademicGroup.findByKey(new AcademicGroup(groupName));

        System.out.println(saveToWBExcel(groupName + "_F1", academicGroup.getStudentsList()));
        academicGroup.printStudents();

    }

}