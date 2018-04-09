package home.nkavtur.hibernateexamples.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Entity
public class Location {

    @Id
    @GeneratedValue(generator = "location_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "location_id_seq", sequenceName = "location_id_seq", allocationSize = 1)
    @Column(name = "location_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private LocationType type;

    private String name;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private Blob image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(type, location.type) &&
                Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }

}
