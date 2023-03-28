import classes.Group;
import classes.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFileChooser fileChooser = new JFileChooser(); //створення об'єкту JFileChooser

        // Задаємо фільтр для вибору файлу з певним розширенням
        FileNameExtensionFilter filter = new FileNameExtensionFilter("xlsx", "xlsx");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null); //відображаємо діалог вибору файлу

        if (returnValue == JFileChooser.APPROVE_OPTION) { //якщо користувач обрав файл
            File file = fileChooser.getSelectedFile(); //отримуємо файл

            FileInputStream inputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // отримуємо перший аркуш

            Group group = new Group(file.getName());

            for (Row row : sheet) {
                Student student = new Student(row);
                group.addStudent(student);
            }

            group.printStudents();

            workbook.close();
            inputStream.close();

        }
    }
}