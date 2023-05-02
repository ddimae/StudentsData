package ntukhpi.semit.dde.studentsdata.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class Contact { //

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) //GenerationType.IDENTITY
    @Column(name = "id")
    private Long id;  //Long!!! not long, not int

    @Column(name = "active",nullable = false)
    @ColumnDefault(value="TRUE")
    private boolean isActive = true;

    @Column(name = "prior",nullable = false)
    @ColumnDefault(value="FALSE")
    private boolean isPrior = false;

    public Contact(boolean isActive, boolean isPrior, Person owner) {
        this.isActive = isActive;
        this.isPrior = isPrior;
        this.owner = owner;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Person owner;






}
