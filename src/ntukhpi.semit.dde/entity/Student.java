package entity;

import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.time.Month;

/**
 * @author Milka Vladislav
 * @version 1.0
 * @created 27-Mar-2023 11:32:15 AM
 */
public class Student {

    private int id;
    private LocalDate dateOfBirth;
    private String firstName;
    private String lastName;
    private String middleName;
    private int id_group;
    private boolean isContract;
    private boolean isHead;
    private boolean scholarship;
    private Address address;
    private Email email;
    private PhoneNumber phoneNumber;
    private Group m_Group;

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Student(LocalDate dateOfBirth, String firstName, int id_group, boolean isContract, boolean isHead, String lastName, String middleName, boolean scholarship) {
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.id_group = id_group;
        this.isContract = isContract;
        this.isHead = isHead;
        this.lastName = lastName;
        this.middleName = middleName;
        this.scholarship = scholarship;
    }

    public Student(Row row) {
        String[] fullNameArray = row.getCell(1).getStringCellValue().split(" ");
        firstName = fullNameArray[1];
        lastName = fullNameArray[0];
        middleName = fullNameArray[2];
        // generate a random date of birth between January 1st, 2004 and December 31st, 2004
        dateOfBirth = LocalDate.of(2004, Month.JANUARY, 1)
                .plusDays((long) (Math.random() * 365));

    }

    public Student(String firstName, String lastName, String middleName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
    }

    public Student(){

    }

    @Override
    public String toString() {
        return "" + lastName +" "+firstName +" "+ middleName + " " + dateOfBirth.toString();
    }

}