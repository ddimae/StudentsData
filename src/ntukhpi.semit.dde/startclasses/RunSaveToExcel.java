package startclasses;

import entity.Group;
import entity.Student;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static files.JSONUtilities.readFromJSON;
import static files.JSONUtilities.writeToJSON;
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
//      workbook.close();
//      inputStream.close();
//      System.out.println("File saved successfully to " + saveToWBExcel(group.getGroupName(), group.getStudentList()));
//


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
//
//        System.out.println("File saved successfully to " + writeToXML(group.getGroupName(), group.getStudentList()));
//
//        Path path = Paths.get("src/КН-221а.xml");
//        String fileName = path.getFileName().toString();
//        Group group = new Group(fileName.substring(0, fileName.length() - 5));
//        group.setStudentList(readFromJSON(path.toString()));
//        System.out.println(group);

        String filePath = writeToJSON(group.getGroupName(), group.getStudentList());
        System.out.println("File saved successfully to " + filePath);


        Group groupFromJson = new Group(fileName.substring(0, fileName.length() - 5));
        groupFromJson.setStudentList(readFromJSON(filePath));
        System.out.println(groupFromJson);

    }

}