package home.nkavtur.hibernateexamples.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * NikolaiKavtur.
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
//@Where(clause = "active = true")
@FilterDef(name = "type_filter", parameters = @ParamDef(name = "account_type", type = "string"))
@Filter(name = "type_filter", condition = "account_type = :account_type")
public class Account {

    @Id
    @GeneratedValue(generator = "id_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_gen", sequenceName = "client_id_seq")
    private Long accountId;

    private BigDecimal amount;

    private BigDecimal rate;

    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "account_type")
    private AccountType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(amount, account.amount) &&
                Objects.equals(rate, account.rate) &&
                Objects.equals(active, account.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, rate, active);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
