package ntukhpi.semit.dde.studentsdata.democlasses;

import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Student;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static ntukhpi.semit.dde.studentsdata.files.XMLUtils.readFromXML;
import static ntukhpi.semit.dde.studentsdata.files.XMLUtils.writeToXML;

public class TestWorkWithXML {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("input/КН-221а.xml");
        String fileName = path.getFileName().toString();
        AcademicGroup group = new AcademicGroup(fileName.substring(0, fileName.length() - 4));
        try {
            List<Student> studentList = readFromXML(path.toString());
            //
            group.setStudentsList(studentList.stream().collect(Collectors.toSet()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("TestWorkWithXML#main: Group from xml:" + group.toStringWithGrouplist());

        System.out.println("File saved successfully to " + writeToXML(group.getGroupName(), group.getStudentsList().stream().toList()));

    }
}
