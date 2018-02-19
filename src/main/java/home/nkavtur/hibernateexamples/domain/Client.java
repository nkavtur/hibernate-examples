package home.nkavtur.hibernateexamples.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * NikolaiKavtur.
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Client {

    @Id
    @GeneratedValue(generator = "id_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_gen", sequenceName = "account_id_seq")
    private Long clientId;

    private String name;


    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @Filter(name = "type_filter", condition = "account_type = :account_type")
    private List<Account> debitAccounts;

    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @Where( clause = "account_type = 'CREDIT'")
    private List<Account> creditAccounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
