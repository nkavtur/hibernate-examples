package home.nkavtur.hibernateexamples.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
public class Contact {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen")
    @Id
    @SequenceGenerator(name = "gen", sequenceName = "contact_id_seq", schema = "hibernate_examples", allocationSize = 1)
    @Column(name = "contact_id")
    private Long id;

    private String first;

    private String last;

    private String middle;

    private String notes;

    private boolean starred;

    @Type(type = "home.nkavtur.hibernateexamples.config.type.BooleanBitStringType")
//    @Convert(converter = BooleanToBitStringConverter.class)
    private Boolean flag;


    private String website;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return starred == contact.starred &&
                Objects.equals(first, contact.first) &&
                Objects.equals(last, contact.last) &&
                Objects.equals(middle, contact.middle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, last, middle);
    }

}