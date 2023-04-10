package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Milka Vladislav
 * @version 1.0
 * @created 27-Mar-2023 11:35:29 AM
 */
public class Group {

    private String groupName;
    private String language;
    private List<Student> studentList;

    public String getGroupName() {
        return groupName;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public Group(String groupName, String language, List<Student> studentList) {
        this.groupName = groupName;
        this.language = language;
        this.studentList = studentList;
    }

    public Group(String groupName) {
        this.groupName = groupName;
        language = "ua";
        studentList = new ArrayList<>();
    }

    public Group() {
        groupName = "KN-222C";
        language = "ua";
        studentList = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        studentList.stream().forEach((Student stud) -> sb.append(stud.toString()).append(System.lineSeparator()));
        return sb.toString();
    }

    public void printStudents() {
        System.out.println("Group name: " + groupName + "\n");
        for (Student student : studentList) {
            System.out.println(student.getLastName() + " " + student.getFirstName() + " " + student.getMiddleName());
        }
    }

    public void addStudent(Student student) {
        studentList.add(student);
    }
}