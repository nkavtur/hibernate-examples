package home.nkavtur.hibernateexamples.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;

/**
 * NikolaiKavtur.
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity(name = "property_holder")
public class PropertyHolder {

    @GeneratedValue(generator = "property_holder_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "property_holder_id_seq", sequenceName = "property_holder_id_seq")
    @Id
    @Column(name = "property_holder_id")
    private Long id;

    @Any(metaDef = "PropertyMetaDef", metaColumn = @Column(name = "property_type"))
    @AnyMetaDef( name= "PropertyMetaDef", metaType = "string", idType = "long",
            metaValues = {
                    @MetaValue(value = "S", targetEntity = StringProperty.class),
                    @MetaValue(value = "I", targetEntity = IntegerProperty.class)
            }
    )
    @JoinColumn(name = "property_id")
    private Property property;

}
