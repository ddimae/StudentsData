package ntukhpi.semit.dde.studentsdata.democlasses;

import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Student;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static ntukhpi.semit.dde.studentsdata.files.JSONUtilities.readFromJSON;
import static ntukhpi.semit.dde.studentsdata.files.JSONUtilities.writeToJSON;
import static ntukhpi.semit.dde.studentsdata.files.XMLUtils.readFromXML;

public class TestWorkWithJSON {
        public static void main(String[] args) throws Exception {
                Path path = Paths.get("input/КН-221а.json");
                String fileName = path.getFileName().toString();

                AcademicGroup group = new AcademicGroup(fileName.substring(0, fileName.length() - 5));
                group.setStudentsList(readFromJSON(path.toString()));
                System.out.println("TestWorkWithJSON#main: Group from JSON:"+group.toStringWithGrouplist());

                String filePath = writeToJSON(group.getGroupName(), group.getStudentsList().stream().toList());
                System.out.println("File saved successfully to " + filePath);

        }

}
