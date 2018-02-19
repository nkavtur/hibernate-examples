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
public class Department {

    @GeneratedValue(generator = "department_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "department_id_seq", sequenceName = "department_id_seq")
    @Id
    @Column(name = "department_id")
    private Long id;

    private String name;

    @JoinTable(name = "department_employee",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Set<Employee> employees = new HashSet<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.getDepartments().add(this);
    }

    public Department addEmployees(Collection<Employee> employees) {
        this.employees.addAll(employees);
        employees.forEach(e -> e.getDepartments().add(this));
        return this;
    }

    public Department addEmployees(Employee... employees) {
        Arrays.stream(employees).forEach(e -> {
            this.employees.add(e);
            e.getDepartments().add(this);
        });
        return this;
    }

    public Department removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.getDepartments().remove(this);
        return this;
    }

    public Department removeEmployees(Collection<Employee> employees) {
        this.employees.removeAll(employees);
        employees.forEach(d -> d.getDepartments().remove(this));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
}














