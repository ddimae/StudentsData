import ntukhpi.semit.dde.studentsdata.doaccess.DAOObjects;
import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.files.ExcelUtilities;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static ntukhpi.semit.dde.studentsdata.files.ExcelUtilities.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExcel {
    @Test
    void TestWorkLoadGroupFromExcelToDB()  throws Exception {
        AcademicGroup group = null;
        String filenameToRead = "input/КН-222а.е.xlsx";
        group = readFromWBExcelFullToClearDB(filenameToRead);
        System.out.println("TestWorkWithExcel#readFromWBExcelFullToClearDB: Group from Excel:\n"+group.toStringWithGrouplist());
        if (DAOObjects.daoAcademicGroup.saveAcademicGroupToDB(group)) {
            System.out.println("Дані збережені в БД");
        };
        assertEquals(30,group.getStudentsList().size());
    }

    @Test
    void TestDeleteAcademicGroup()  throws Exception {
        AcademicGroup group = new AcademicGroup("КН-222а.е");
        DAOObjects.daoAcademicGroup.deleteAcademicGroupFromDB(group);
        AcademicGroup groupAfter = DAOObjects.daoAcademicGroup.findByKey(group);
        assertEquals(null,groupAfter);

    }

    @Test
    void TestSaveToExcelF1() throws IOException {
        String agName = "KН-221в";
        String reportType = "F1";
        AcademicGroup group = new AcademicGroup(agName);
        AcademicGroup groupFromDB = DAOObjects.daoAcademicGroup.findByKey(group);
        ExcelUtilities.saveToWBExcelWithName(STUDENTSDATA_FILES_FOLDER,agName,groupFromDB,reportType);
    }

    @Test
    void TestSaveToExcelF2() throws IOException {
        String agName = "KН-221в";
        String reportType = "F2";
        AcademicGroup group = new AcademicGroup(agName);
        AcademicGroup groupFromDB = DAOObjects.daoAcademicGroup.findByKey(group);
        ExcelUtilities.saveToWBExcelWithName(STUDENTSDATA_FILES_FOLDER,agName,groupFromDB,reportType);
    }

    @Test
    void TestSaveToExcelF3() throws IOException {
        String agName = "KN221a";
        String reportType = "F3";
        AcademicGroup group = new AcademicGroup(agName);
        AcademicGroup groupFromDB = DAOObjects.daoAcademicGroup.findByKey(group);
        ExcelUtilities.saveToWBExcelWithName(STUDENTSDATA_FILES_FOLDER,agName,groupFromDB,reportType);
    }

    @Test
    void TestSaveToExcelF2_3() throws IOException {
        String agName = "KН-221в";
        String reportType = "F2";
        AcademicGroup group = new AcademicGroup(agName);
        AcademicGroup groupFromDB = DAOObjects.daoAcademicGroup.findByKey(group);
        ExcelUtilities.saveToWBExcelWithName(STUDENTSDATA_FILES_FOLDER,agName,groupFromDB,reportType);
    }
}
