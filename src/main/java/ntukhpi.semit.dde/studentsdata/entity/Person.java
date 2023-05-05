package ntukhpi.semit.dde.studentsdata.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ntukhpi.semit.dde.studentsdata.utils.Formats;
import org.hibernate.annotations.ColumnDefault;


import javax.validation.Constraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

//@MappedSuperclass
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@Getter
@Setter
public abstract class Person { //
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) //GenerationType.IDENTITY
    @Column(name = "id")
    private Long id;  //Long!!! not long, not int

    @Column(name = "sname", nullable = false, length = 25)
    @NotNull
    @ToString.Include
    private String lastName;
    @Column(name = "fname", nullable = true, length = 15)
    @NotNull
    @ToString.Include
    private String firstName;
    @Column(name = "pname", nullable = true, length = 25)
    @NotNull
    @ToString.Include
    private String middleName;
    @Column(name = "birthday",nullable = true)
    @ToString.Include
    private LocalDate dateOfBirth;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    private Set<Contact> contacts;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "persons_adresses", joinColumns = @JoinColumn(name = "id_owner"))
    @MapKeyJoinColumn(name = "id_address")
    @Column(name = "current", nullable = false)
    @ColumnDefault(value = "FALSE")
    private Map<Address, Boolean> addresses;

    public Person(@NotNull String lastName, @NotNull String firstName, @NotNull String middleName, LocalDate dateOfBirth) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        contacts = new LinkedHashSet<>();
        addresses = new HashMap<>();
    }

    public Person(@NotNull String lastName, String firstName, String middleName, String dateOfBirthStr) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.dateOfBirth = LocalDate.parse(dateOfBirthStr,Formats.FORMAT_DATE_UA);
        contacts = new LinkedHashSet<>();
        addresses = new HashMap<>();
    }
    public Person(@NotNull String lastName, String firstName, String middleName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        contacts = new LinkedHashSet<>();
        addresses = new HashMap<>();
    }

    //for addresses
    public Map<Address, Boolean> getAddresses() {
        return Collections.unmodifiableMap(addresses);
    }

    public void addAddress(Address address, Boolean isCurrent) {
        addresses.put(address, isCurrent);
    }

    public void delAddress(Address address) {
        addresses.remove(address);
    }

    //for contacts
    public Set<Contact> getContacts() {
        return Collections.unmodifiableSet(contacts);
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void delContact(Contact contact) {
        contacts.remove(contact);
    }

    //Person#toString
    @Override
    public String toString() {

        return "" + lastName +
                (firstName!=null?" " + firstName:"") +
                (middleName!=null?" " + middleName:"") +
                (dateOfBirth!=null?" " + dateOfBirth.format(Formats.FORMAT_DATE_UA):"");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!lastName.equals(person.lastName)) return false;
        if (!Objects.equals(firstName, person.firstName)) return false;
        if (!Objects.equals(middleName, person.middleName)) return false;
        return Objects.equals(dateOfBirth, person.dateOfBirth);
    }

    @Override
    public int hashCode() {
        int result = lastName.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        return result;
    }
}