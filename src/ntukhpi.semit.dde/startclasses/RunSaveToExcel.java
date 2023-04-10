package startclasses;

import entity.Group;
import entity.Student;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static files.XMLUtils.readFromXML;
import static files.XMLUtils.writeToXML;

public class RunSaveToExcel {
    public static void main(String[] args) throws Exception {
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

        Path path = Paths.get("src/КН-221а.xml");
        String fileName = path.getFileName().toString();
        Group group = new Group(fileName.substring(0, fileName.length() - 5));
        try {
            List<Student> studentList = readFromXML(path.toString());
            group.setStudentList(studentList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(group);
        writeToXML(group.getGroupName(), group.getStudentList());
//
//            workbook.close();
//            inputStream.close();

//            saveToWBExcel(group.getGroupName(), group.getStudentList());

    }

}