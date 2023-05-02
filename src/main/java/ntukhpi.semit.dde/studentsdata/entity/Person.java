package ntukhpi.semit.dde.studentsdata.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ntukhpi.semit.dde.studentsdata.utils.Formats;


import javax.validation.constraints.NotNull;
import java.time.LocalDate;

//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public abstract class Person { //
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) //GenerationType.IDENTITY
    @Column(name = "id")
    private Long id;  //Long!!! not long, not int

    @Column(name = "sname", nullable = false, length = 25)
    @NotNull
    @ToString.Include
    private String lastName;
    @Column(name = "fname", nullable = false, length = 15)
    @NotNull
    @ToString.Include
    private String firstName;
    @Column(name = "pname", nullable = false, length = 25)
    @NotNull
    @ToString.Include
    private String middleName;
    @Column(name = "birthday")
    @ToString.Include
    private LocalDate dateOfBirth;

    public Person(@NotNull String lastName, @NotNull String firstName, @NotNull String middleName, LocalDate dateOfBirth) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
    }

    public Person(@NotNull String lastName, @NotNull String firstName, @NotNull String middleName, String dateOfBirthStr) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.dateOfBirth = LocalDate.parse(dateOfBirthStr,Formats.FORMAT_DATE_UA);
    }
    public Person(@NotNull String lastName, @NotNull String firstName, @NotNull String middleName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }



    //Person

    @Override
    public String toString() {

        return "" + lastName + " " + firstName + " " + middleName + " " + dateOfBirth.format(Formats.FORMAT_DATE_UA);
    }

}
