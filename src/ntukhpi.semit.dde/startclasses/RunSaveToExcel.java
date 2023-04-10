package startclasses;

import entity.Group;
import entity.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static files.ExcelUtilities.saveToWBExcel;
import static files.XMLUtils.readFromXML;

public class RunSaveToExcel {
    public static void main(String[] args) throws IOException {
//            Path path = Paths.get("src/КН-221а.xlsx"); //отримуємо шлях до файлу
//
//            FileInputStream inputStream = new FileInputStream(path.toFile());
//            Workbook workbook = new XSSFWorkbook(inputStream);
//            Sheet sheet = workbook.getSheetAt(0); // отримуємо перший аркуш
//
//            String fileName = path.getFileName().toString();
//
//            Group group = new Group(fileName.substring(0, fileName.length() - 5));
//
//            for (Row row : sheet) {
//                Student student = new Student(row);
//                group.addStudent(student);
//            }

        List<Student> studentList = null;
        try {
            studentList = readFromXML("src/КН-221а.xml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(studentList);
//
//            workbook.close();
//            inputStream.close();

//            saveToWBExcel(group.getGroupName(), group.getStudentList());

    }

}