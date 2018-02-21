package home.nkavtur.hibernateexamples.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * NikolaiKavtur.
 */
@Getter
@Setter
@Accessors(chain = true)
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {

    @Column(name = "publisher_name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private Country country;
}
