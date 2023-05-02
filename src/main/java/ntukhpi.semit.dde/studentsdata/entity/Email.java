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
@Table(name="emails")
@NoArgsConstructor
@Getter
@Setter
public class Email extends Contact {
    @Column(name = "email", nullable=false, unique=true, length = 80)
    @NotNull
    private String email;

    public Email(boolean isActive, boolean isPrior, Person owner, String email) {
        super(isActive, isPrior, owner);
        this.email = email;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Email{");
        sb.append("email='").append(email).append('\'');
        sb.append('}');
        sb.append(" - ").append(super.getOwner().toString());
        return sb.toString();
    }
}
