package ntukhpi.semit.dde.studentsdata.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "parents")
@NoArgsConstructor
@Getter
@Setter
public class Parent extends Person {

    //Constructors
    public Parent(@NotNull String lastName, @NotNull String firstName, @NotNull String middleName, String dateOfBirthStr) {
        super(lastName, firstName, middleName, dateOfBirthStr);
    }

}
