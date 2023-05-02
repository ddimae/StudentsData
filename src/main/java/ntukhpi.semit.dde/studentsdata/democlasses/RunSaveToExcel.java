package ntukhpi.semit.dde.studentsdata.democlasses;


import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static ntukhpi.semit.dde.studentsdata.files.ExcelUtilities.saveToWBExcel;
import static ntukhpi.semit.dde.studentsdata.files.JSONUtilities.writeToJSON;
import static ntukhpi.semit.dde.studentsdata.files.XMLUtils.readFromXML;
import static ntukhpi.semit.dde.studentsdata.files.XMLUtils.writeToXML;

public class RunSaveToExcel {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("КН-221а.xlsx"); //отримуємо шлях до файлу

        FileInputStream inputStream = new FileInputStream(path.toFile());
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // отримуємо перший аркуш

        String fileName = path.getFileName().toString();

        AcademicGroup group = new AcademicGroup(fileName.substring(0, fileName.length() - 5));

        for (Row row : sheet) {
            Student student = new Student(row);
            group.addStudent(student);
        }
        workbook.close();
        inputStream.close();
//        System.out.println("File saved successfully to " + saveToWBExcel(group.getGroupName(), group.getStudentsList().stream().toList()));


//        Path path = Paths.get("src/КН-221а.xml");
//        String fileName = path.getFileName().toString();
//        AcademicGroup group = new AcademicGroup(fileName.substring(0, fileName.length() - 5));
//        try {
//            List<Student> studentList = readFromXML(path.toString());
//            //
//            group.setStudentsList(studentList.stream().collect(Collectors.toSet()));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(group);

//        System.out.println("File saved successfully to " + writeToXML(group.getGroupName(), group.getStudentsList().stream().toList()));
//
//        Path path = Paths.get("src/КН-221а.xml");
//        String fileName = path.getFileName().toString();
//        AcademicGroup group = new AcademicGroup(fileName.substring(0, fileName.length() - 5));
//        group.setStudentList(readFromJSON(path.toString()));
//        System.out.println(group);

//        String filePath = writeToJSON(group.getGroupName(), group.getStudentsList().stream().toList());
//        System.out.println("File saved successfully to " + filePath);
//
//
//        AcademicGroup groupFromJson = new AcademicGroup(fileName.substring(0, fileName.length() - 5));
//        List<Student> studentList = readFromXML(path.toString());
//        groupFromJson.setStudentsList(studentList.stream().collect(Collectors.toSet()));
//        System.out.println(groupFromJson);

    }

}