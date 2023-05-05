package ntukhpi.semit.dde.studentsdata.files;

import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ExcelUtilities {

    public static AcademicGroup readFromWBExcel(String filename) {
        AcademicGroup group = null;
        Path path = Paths.get(filename); //отримуємо шлях до файлу
        try (FileInputStream inputStream = new FileInputStream(path.toFile())) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // отримуємо перший аркуш

            String fileName = path.getFileName().toString();

            group = new AcademicGroup(fileName.substring(0, fileName.length() - 5));

            for (Row row : sheet) {
                Student student = new Student(row);
                group.addStudent(student);
            }
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return group;
    }

    public static String saveToWBExcel(String filename, List<Student> studentList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(filename);

        int rowIndex = 0;
        for (Student student : studentList) {
            Row row = sheet.createRow(rowIndex++);

            Cell firstNameCell = row.createCell(0);
            ((Cell) firstNameCell).setCellValue(student.getFirstName());

            Cell lastNameCell = row.createCell(1);
            lastNameCell.setCellValue(student.getLastName());

            Cell middleNameCell = row.createCell(2);
            middleNameCell.setCellValue(student.getMiddleName());

            Cell dateOfBirthCell = row.createCell(3);
            dateOfBirthCell.setCellValue(student.getDateOfBirth().toString());
        }

        Path filePath = Paths.get(  "results/"+filename + ".xlsx");//
        try (FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
            workbook.write(outputStream);
        }

        workbook.close();

        return filePath.toAbsolutePath().toString();
    }
}
