package home.nkavtur.hibernateexamples.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.*;

/**
 * NikolaiKavtur.
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Library {

    @GeneratedValue(generator = "library_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "library_id_seq", sequenceName = "library_id_seq")
    @Id
    @Column(name = "library_id")
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "library")
    private Set<Book> books = new HashSet<>();

    public Library addBook(Book book) {
        books.add(book);
        return this;
    }

    public Library removeBook(Book book) {
        books.remove(book);
        book.setLibrary(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return  Objects.equals(name, library.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
