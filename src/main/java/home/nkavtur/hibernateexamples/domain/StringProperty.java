package home.nkavtur.hibernateexamples.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * NikolaiKavtur.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
public class StringProperty implements Property<String> {

    @GeneratedValue(generator = "string_property_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "string_property_id_seq", sequenceName = "string_property_id_seq")
    @Id
    @Column(name = "string_property_id")
    private Long id;

    private String name;

    private String value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }
}
