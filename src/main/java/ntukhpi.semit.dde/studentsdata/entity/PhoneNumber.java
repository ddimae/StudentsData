package ntukhpi.semit.dde.studentsdata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Entity
@Table(name="phones")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PhoneNumber extends Contact {
    @Column(name = "email", nullable=false, unique=true, length = 12)
    @NotNull
    private String phoneNumber;

    public PhoneNumber(boolean isActive, boolean isPrior, Person owner, String phoneNumber) {
        super(isActive, isPrior, owner);
        this.phoneNumber = phoneNumber;
    }
}
