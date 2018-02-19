package home.nkavtur.hibernateexamples.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.MERGE;
import static org.hibernate.annotations.CascadeType.PERSIST;

/**
 * NikolaiKavtur.
 */
@Entity(name = "property_repositoty")
@Getter
@Setter
@Accessors(chain = true)
public class PropertyRepository {

    @GeneratedValue(generator = "property_repository_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "property_repository_id_seq", sequenceName = "property_repository_id_seq")
    @Id
    @Column(name = "property_repository_id")
    private Long id;

    private String name;

    @ManyToAny(metaDef = "propertyRepoMetaDef", metaColumn = @Column(name = "property_type"))
    @JoinTable(name = "repository_properties",
            joinColumns = @JoinColumn(name = "repository_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
    )
    @AnyMetaDef( name= "propertyRepoMetaDef", metaType = "string", idType = "long",
            metaValues = {
                    @MetaValue(value = "S", targetEntity = StringProperty.class),
                    @MetaValue(value = "I", targetEntity = IntegerProperty.class)
            }
    )
    @Cascade({PERSIST, MERGE})
    @BatchSize(size = 10)
    @Fetch(FetchMode.SELECT)
    private Set<Property> properties = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyRepository that = (PropertyRepository) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
