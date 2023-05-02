package ntukhpi.semit.dde.studentsdata.files;

import ntukhpi.semit.dde.studentsdata.entity.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ExcelUtilities {
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

        Path filePath = Paths.get(filename + ".xlsx");
        try (FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
            workbook.write(outputStream);
        }

        workbook.close();

        return filePath.toAbsolutePath().toString();
    }
}
