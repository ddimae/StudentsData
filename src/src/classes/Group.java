package classes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Milka Vladislav
 * @version 1.0
 * @created 27-Mar-2023 11:35:29 AM
 */
public class Group {

    private String GroupName;
    private String Language;

    private List<Student> StudentList;

    public Group(String groupName, String language, List<Student> studentList){
        GroupName = groupName;
        Language = language;
        StudentList = studentList;
    }

    public Group(String groupName){
        GroupName = groupName;
        Language = "ua";
        StudentList = new ArrayList<>();
    }

    public Group() {
        GroupName = "KN-222C";
        Language = "ua";
        StudentList = new ArrayList<>();
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public void setStudentList(List<Student> studentList) {
        StudentList = studentList;
    }

    public String getGroupName() {
        return GroupName;
    }

    public String getLanguage() {
        return Language;
    }

    public List<Student> getStudentList() {
        return StudentList;
    }

    public void addStudent(Student student) {
        StudentList.add(student);
    }

    public int getGroup(){
        return 0;
    }

    /**
     *
     * @param group
     */
    public void setGroup(int group){

    }

    public void printStudents() {
        System.out.println("Group name: " + GroupName + "\n");
        for (Student student : StudentList) {
            System.out.println(student.getLastName() + " " + student.getFirstName() + " " + student.getMiddleName());
        }
    }

}