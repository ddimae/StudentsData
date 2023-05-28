package ntukhpi.semit.dde.studentsdata.files;

import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Email;
import ntukhpi.semit.dde.studentsdata.entity.PhoneNumber;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelUtilities {

    public static final String STUDENTSDATA_FILES_FOLDER = "students_data/";

    public static final String ATTACHMENT_FILENAME = "attachment; filename=\"";

    public static AcademicGroup readFromWBExcel(String filename) {
        AcademicGroup group;
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
        AcademicGroup group;
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
                //Збираємо студента
                student.addContact(khpiEmail);
                student.addContact(personalEmail);
                phones.forEach(student::addContact);
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
        StringBuilder sb;
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

        // Стилі комірок
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
            case "F2" -> {
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
                break;
            }
        }
        //Створення рядків із даними студентів групи
        int rowIndex = 0;
        for (Student student : studentList) {
            Row row = sheet.createRow(headerRows + rowIndex++);

            Cell number = row.createCell(0);
            number.setCellStyle(style);
            number.setCellValue(rowIndex);

            switch (type) {
                case "F1": {
                    addRowByForm1(row, student, style);
                    break;
                }
                case "F2": {
                    addRowByForm2(row, student, academicGroup.getHeadStudent().getId().equals(student.getId()), style, boldStyle);
                    break;
                }
            }
        }
        //записати у файл
        Path filePath = Paths.get(STUDENTSDATA_FILES_FOLDER + groupName + "_" + type + ".xlsx");//
        try (FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
            workbook.write(outputStream);
        }

        workbook.close();

        return filePath.toAbsolutePath().toString();
    }

    public static String saveToWBExcelWithName(String fullSavePath, String groupName, AcademicGroup academicGroup, String type) throws IOException {
        Set<Student> studentList = academicGroup.getStudentsList();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(groupName);

        // Стилі комірок
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
                break;
            }
        }
        //Створення рядків із даними студентів групи
        int rowIndex = 0;
        List<Student> sortStudentsList = studentList.stream().sorted((s1,s2)-> {
            int res = s1.getLastName().compareTo(s2.getLastName());
            if (res!=0) {
                return s1.getLastName().compareTo(s2.getLastName());
            } else {
                return s1.getFirstName().compareTo(s2.getFirstName());
            }
        }).toList();
        for (Student student : sortStudentsList) {
            Row row = sheet.createRow(headerRows + rowIndex++);

            Cell number = row.createCell(0);
            number.setCellStyle(style);
            number.setCellValue(rowIndex);

            switch (type) {
                case "F1": {
                    addRowByForm1(row, student, style);
                    break;
                }
                case "F2": {
                    addRowByForm2(row, student, academicGroup.getHeadStudent().getId().equals(student.getId()), style, boldStyle);
                    break;
                }
            }
        }
        //записати у файл
//        Path filePath = Paths.get(RESULTS_FOLDER + groupName + "_" + type + ".xlsx");//
        Path filePath = Paths.get(fullSavePath + groupName + "_" + type + ".xlsx");//
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

    public static String rfc5987_encode(final String s) {
        final byte[] s_bytes = s.getBytes(StandardCharsets.UTF_8);
        final int len = s_bytes.length;
        final StringBuilder sb = new StringBuilder(len << 1);
        final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        final byte[] attr_char = {'!', '#', '$', '&', '+', '-', '.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '|', '~'};
        for (final byte b : s_bytes) {
            if (Arrays.binarySearch(attr_char, b) >= 0)
                sb.append((char) b);
            else {
                sb.append('%');
                sb.append(digits[0x0f & (b >>> 4)]);
                sb.append(digits[b & 0x0f]);
            }
        }

        return sb.toString();
    }

}
