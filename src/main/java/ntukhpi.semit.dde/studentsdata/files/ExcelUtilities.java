package ntukhpi.semit.dde.studentsdata.files;

import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Email;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static AcademicGroup readFromWBExcelFullToClearDB(String filename) {
        AcademicGroup group = null;
        Path path = Paths.get(filename); //отримуємо шлях до файлу
        try (FileInputStream inputStream = new FileInputStream(path.toFile())) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // отримуємо перший аркуш

            String fileName = path.getFileName().toString();

            group = new AcademicGroup(fileName.substring(0, fileName.length() - 5));

            for (Row row : sheet) {
                //Student student = new Student(row);
                //Комірка 1 - ПІБ
                String[] nameParts = row.getCell(1).getStringCellValue().split(" ");
                Student student = new Student(nameParts[0], nameParts[1], nameParts[2]);
                //Комірка 2 - email in Office365
                Email khpiEmail = new Email(true, true, student, row.getCell(2).getStringCellValue());
                //Комірка 3 - personal email
                Email personalEmail = new Email(true, false, student, row.getCell(3).getStringCellValue());
                //Комірка 4 - phonesNumbers
                List<PhoneNumber> phones = parsePhones(row.getCell(4).getStringCellValue(), student);
                //Збираєм студента
                student.addContact(khpiEmail);
                student.addContact(personalEmail);
                phones.stream().forEach(phone -> student.addContact(phone));
                group.addStudent(student);
            }
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return group;
    }

    private static List<PhoneNumber> parsePhones(String row, Student owner) {
        final String regex = "\\+(380)\\((\\d{2})\\)-(\\d{3})-(\\d{2})-(\\d{2})";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(row);
        List<PhoneNumber> phones = new ArrayList<>();
        StringBuilder sb = null;
        while (matcher.find()) {
            sb = new StringBuilder();
            //System.out.println("Full match: " + matcher.group(0));
            for (int i = 1; i <= matcher.groupCount(); i++) {
                sb.append(matcher.group(i));
            }
            phones.add(new PhoneNumber(true, false, owner, sb.toString()));
        }
        return phones;
    }

    public static String saveToWBExcel(String groupName, AcademicGroup academicGroup, String type) throws IOException {
        Set<Student> studentList = academicGroup.getStudentsList();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(groupName);

        // Стилі ячейки
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        Font font = workbook.createFont();
        font.setBold(true);
        CellStyle boldStyle = workbook.createCellStyle();
        boldStyle.setBorderTop(BorderStyle.THIN);
        boldStyle.setBorderBottom(BorderStyle.THIN);
        boldStyle.setBorderLeft(BorderStyle.THIN);
        boldStyle.setBorderRight(BorderStyle.THIN);
        boldStyle.setFont(font);
        boldStyle.setAlignment(HorizontalAlignment.CENTER);

        int headerRows = 0;
        // add header
        switch (type) {
            case "F2": {
                headerRows = 2;
                Row row0 = sheet.createRow(0);
                Cell cell0 = row0.createCell(0);
                cell0.setCellStyle(style);
                cell0.setCellStyle(boldStyle);
                cell0.setCellValue("СПИСОК СТУДЕНТІВ НАЧАЛЬНОЇ ГРУПИ " + groupName + " (дата)");
                CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 4);
                sheet.addMergedRegion(mergedRegion);

                String[] headers = {"№", "ПІПб", "Дата народження", "Бюджет/Контракт", "Стипендія"};

                Row row1 = sheet.createRow(1);
                int cellIndex = 0;
                for (String header : headers) {
                    Cell cell = row1.createCell(cellIndex++);
                    cell.setCellStyle(style);
                    cell.setCellValue(header);

                }
            }
        }

        int rowIndex = 0;
        for (Student student : studentList) {
            Row row = sheet.createRow(headerRows + rowIndex++);

            Cell number = row.createCell(0);
            number.setCellStyle(style);
            number.setCellValue(rowIndex);

            switch (type){
                case "F1":
                {
                    addRowByForm1(row, student, style);
                }
                case "F2":
                {
                    addRowByForm2(row, student,academicGroup.getHeadStudent().getId() == student.getId(),  style, boldStyle);
                }
            }
        }

        Path filePath = Paths.get("results/" + groupName + "_" + type + ".xlsx");//
        try (FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
            workbook.write(outputStream);
        }

        workbook.close();

        return filePath.toAbsolutePath().toString();
    }

    public static void addRowByForm1(Row row, Student student, CellStyle style) {

        Cell lastNameCell = row.createCell(1);
        lastNameCell.setCellStyle(style);
        lastNameCell.setCellValue(student.getLastName().toUpperCase());

        Cell firstNameCell = row.createCell(2);
        firstNameCell.setCellStyle(style);
        firstNameCell.setCellValue(student.getFirstName());

        Cell middleNameCell = row.createCell(3);
        middleNameCell.setCellStyle(style);
        middleNameCell.setCellValue(student.getMiddleName());
    }

    public static void addRowByForm2(Row row, Student student, boolean isHead, CellStyle style, CellStyle boldStyle) {
        boldStyle.setAlignment(HorizontalAlignment.LEFT);
        Cell fullNameCell = row.createCell(1);
        fullNameCell.setCellStyle(isHead ? boldStyle : style);
        fullNameCell.setCellValue(student.getLastName().toUpperCase() + " " + student.getFirstName() + " " + student.getMiddleName() +
               (isHead ? " (староста)" : ""));

        Cell dateOfBirthCell = row.createCell(2);
        dateOfBirthCell.setCellStyle(style);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            dateOfBirthCell.setCellValue(student.getDateOfBirth().format(formatter));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Cell isContractCell = row.createCell(3);
        isContractCell.setCellStyle(style);
        if (student.isContract()) {
            isContractCell.setCellValue("Контракт");
        }

        Cell isTakeScholarshipCell = row.createCell(4);
        isTakeScholarshipCell.setCellStyle(style);
        if (student.isTakeScholarship()) {
            isTakeScholarshipCell.setCellValue("Так");
        }
    }

}
