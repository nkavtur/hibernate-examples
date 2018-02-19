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
public class IntegerProperty implements Property<Integer> {

    @GeneratedValue(generator = "integer_property_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "integer_property_id_seq", sequenceName = "integer_property_id_seq")
    @Id
    @Column(name = "integer_property_id")
    private Long id;

    private String name;

    private Integer value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
