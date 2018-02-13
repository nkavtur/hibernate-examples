package home.nkavtur.hibernateexamples.domain;

import home.nkavtur.hibernateexamples.config.generator.TypeCodeGenerator;
import home.nkavtur.hibernateexamples.config.generator.value.annotation.CreateTs;
import home.nkavtur.hibernateexamples.config.generator.value.annotation.UpdateTs;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * NikolaiKavtur.
 */
@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Donor {

    @Id
    @Column(name = "donor_id")
    private UUID id;

    private String name;

    @GeneratorType(type = TypeCodeGenerator.class)
    private String typeCode;

    @CreationTimestamp
    private Instant testCreateTs;

    @UpdateTimestamp
    private Instant testUpdateTs;

    @CreateTs
    private Instant anotherTestCreateTs;

    @UpdateTs
    private Instant anotherTestUpdateTs;

    @Generated(GenerationTime.INSERT)
    @Column(name = "create_ts", insertable = false, updatable = false)
    private Instant createTs;

    @Generated(GenerationTime.ALWAYS)
    @Column(name = "update_ts", insertable = false, updatable = false)
    private Instant updateTs;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donor donor = (Donor) o;
        return Objects.equals(name, donor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}





