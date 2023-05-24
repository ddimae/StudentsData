package ntukhpi.semit.dde.studentsdata.doaccess;

import ntukhpi.semit.dde.studentsdata.entity.AcademicGroup;
import ntukhpi.semit.dde.studentsdata.entity.Student;
import ntukhpi.semit.dde.studentsdata.files.JSONUtilities;
import ntukhpi.semit.dde.studentsdata.utils.Language;

public class InformationExpert {
    public static void main(String[] args) {
        AcademicGroup group = new AcademicGroup("KN220a.e", Language.EN);
        Student s1 = new Student("Anufriev","M","I","06.12.2002");
        Student s2 = new Student("Scherbak","Alona","Yu","18.11.2002" );
        group.addStudent(s1);
        group.addStudent(s2);
        group.printInfo();

    }


}
