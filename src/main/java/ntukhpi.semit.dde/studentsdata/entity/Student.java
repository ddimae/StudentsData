package ntukhpi.semit.dde.studentsdata.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ntukhpi.semit.dde.studentsdata.utils.KinshipDegree;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Milka Vladislav
 * @version 1.0
 * @created 27-Mar-2023 11:32:15 AM
 */

@Entity
@Table(name="students")
@NoArgsConstructor
@Getter
@Setter
public class Student extends Person {

    @Column(name = "contract",nullable = false)
    @ColumnDefault(value="FALSE")
    private boolean isContract;
    @Column(name = "scholarship",nullable = false)
    @ColumnDefault(value="FALSE")
    private boolean isTakeScholarship;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "students_parents", joinColumns = @JoinColumn(name = "id_parent"))
    @MapKeyJoinColumn(name = "id_student")
    @Column(name = "kinship_degree", nullable = false)
    private Map<Parent, KinshipDegree> parents = new HashMap<>();

    //Constructors
    public Student(String lastName, String firstName, String middleName, LocalDate dateOfBirth) {
        super(lastName,firstName,middleName,dateOfBirth);
    }

    public Student(String lastName, String firstName, String middleName, String dateOfBirthStr) {
        super(lastName, firstName, middleName, dateOfBirthStr);
    }

    public Student(LocalDate dateOfBirth, String firstName, boolean isContract, boolean isHead, String lastName, String middleName, boolean scholarship) {
        super(lastName,firstName,middleName,dateOfBirth);
        this.isContract = isContract;
        this.isTakeScholarship = scholarship;
    }

    //for parents
    public Map<Parent, KinshipDegree> getParents() {
        return Collections.unmodifiableMap(parents);
    }

    public void addParent(Parent parent, KinshipDegree kinshipDegree) {
        parents.put(parent,kinshipDegree);
    }

    public void delParent(Parent parent) {
        parents.remove(parent);
    }

    public Student(Row row) {
        this(row.getCell(1).getStringCellValue());
    }

    public Student(String fullName){
        this(fullName.split(" ")[0], fullName.split(" ")[1], fullName.split(" ")[2], LocalDate.of(2004, Month.JANUARY, 1)
                .plusDays((long) (Math.random() * 365)));
    }

    public String showParents(){
        StringBuilder sb = new StringBuilder("Parents:");
        if (parents.isEmpty()){
            sb.append("absent");
        } else {
            parents.entrySet().stream().forEach(e->sb.append(System.lineSeparator())
                    .append(e.getValue()).append(" ").append(e.getKey().toString()));
        }
        return sb.toString();

    }



}