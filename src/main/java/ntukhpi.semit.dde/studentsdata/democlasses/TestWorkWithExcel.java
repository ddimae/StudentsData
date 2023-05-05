package ntukhpi.semit.dde.studentsdata.democlasses;


import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;

import static ntukhpi.semit.dde.studentsdata.files.ExcelUtilities.readFromWBExcel;
import static ntukhpi.semit.dde.studentsdata.files.ExcelUtilities.saveToWBExcel;

public class TestWorkWithExcel {
    public static void main(String[] args) throws Exception {
        AcademicGroup group = null;
        String filenameToRead = "input/КН-221а.xlsx";
        group = readFromWBExcel(filenameToRead);
        System.out.println("TestWorkWithExcel#main: Group from Excel:"+group.toStringWithGrouplist());
        //=========================================
//        group = new AcademicGroup("КН-221а");
//        System.out.println(group);
        System.out.println("\nFile saved successfully to " +
                saveToWBExcel(group.getGroupName(), group.getStudentsList().stream().toList()));
        //******************************************


    }

}