package ntukhpi.semit.dde.studentsdata.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name="teachers")
@NoArgsConstructor
@Getter
@Setter
public class Teacher extends Person {

    //Constructors
    public Teacher(@NotNull String lastName, @NotNull String firstName, @NotNull String middleName, LocalDate dateOfBirth) {
        super(lastName, firstName, middleName, dateOfBirth);
    }

    public Teacher(String lastName, String firstName, String middleName, String dateOfBirthStr) {
        super(lastName, firstName, middleName, dateOfBirthStr);
    }
}
