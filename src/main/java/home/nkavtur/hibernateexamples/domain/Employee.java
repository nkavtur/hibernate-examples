package home.nkavtur.hibernateexamples.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.*;

/**
 * NikolaiKavtur.
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Employee {

    @GeneratedValue(generator = "employee_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "employee_id_seq", sequenceName = "employee_id_seq")
    @Id
    @Column(name = "employee_id")
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @ManyToMany(mappedBy = "employees")
    private Set<Department> departments = new HashSet<>();

    public Employee addDepartment(Department department) {
        departments.add(department);
        department.getEmployees().add(this);
        return this;
    }

    public Employee addDepartments(Collection<Department> departments) {
        this.departments.addAll(departments);
        departments.forEach(d -> d.getEmployees().add(this));
        return this;
    }

    public Employee addDepartments(Department... departments) {
        Arrays.stream(departments).forEach(d -> {
            this.departments.add(d);
            d.getEmployees().add(this);
        });
        return this;
    }

    public Employee removeDepartment(Department department) {
        departments.remove(department);
        department.getEmployees().remove(this);
        return this;
    }

    public Employee removeDepartments(Collection<Department> departments) {
        this.departments.removeAll(departments);
        departments.forEach(d -> d.getEmployees().remove(this));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(phoneNumber, employee.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phoneNumber);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
}
