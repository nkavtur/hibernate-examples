package home.nkavtur.hibernateexamples.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * NikolaiKavtur.
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserId implements Serializable {

    private Integer number;
    private String email;

    public Integer getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public static SystemUserId valueOf(Integer id, String email) {
        return new SystemUserId(id, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemUserId that = (SystemUserId) o;
        return Objects.equals(number, that.number) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, email);
    }

    @Override
    public String toString() {
        return "SystemUserId{" +
                "number=" + number +
                ", email='" + email + '\'' +
                '}';
    }
}
