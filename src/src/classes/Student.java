package classes;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author Milka Vladislav
 * @version 1.0
 * @created 27-Mar-2023 11:32:15 AM
 */
public class Student {

    private String DateOfBirth;
    private String FirstName;
    private int id_group;
    private int id_student;
    private boolean IsContract;
    private boolean IsHead;

    public Student(String firstName, String lastName, String middleName) {
        FirstName = firstName;
        LastName = lastName;
        MiddleName = middleName;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public boolean isContract() {
        return IsContract;
    }

    public void setContract(boolean contract) {
        IsContract = contract;
    }

    public boolean isHead() {
        return IsHead;
    }

    public void setHead(boolean head) {
        IsHead = head;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public boolean isScholarship() {
        return Scholarship;
    }

    public void setScholarship(boolean scholarship) {
        Scholarship = scholarship;
    }

    private String LastName;
    private String MiddleName;
    private boolean Scholarship;
    private Group m_Group;

    public Student(){

    }

    public Student(String dateOfBirth, String firstName, int id_group, boolean isContract, boolean isHead, String lastName, String middleName, boolean scholarship) {
        DateOfBirth = dateOfBirth;
        FirstName = firstName;
        this.id_group = id_group;
        IsContract = isContract;
        IsHead = isHead;
        LastName = lastName;
        MiddleName = middleName;
        Scholarship = scholarship;
    }

    public Student(Row row) {
        String[] fullNameArray = row.getCell(1).getStringCellValue().split(" ");
        FirstName = fullNameArray[0];
        LastName = fullNameArray[2];
        MiddleName = fullNameArray[1];
    }

    /**
     *
     * @param id_student
     */
    public Student getStudent(int id_student){
        return null;
    }

}