package home.nkavtur.hibernateexamples.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * NikolaiKavtur.
 */
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Book {

    @Id
    @GeneratedValue(generator = "book_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "book_id_seq", sequenceName = "book_id_seq")
    @Column(name = "book_id")
    private Long id;

    private String author;

    private String title;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "books")
    @Fetch(FetchMode.JOIN)
    @BatchSize(size = 50)
    private Set<Reader> readers = new HashSet<>();

    @AttributeOverride(name = "name", column = @Column(name = "paper_publisher_name"))
    @AssociationOverride(name = "country", joinColumns = @JoinColumn(name = "paper_publisher_country_id"))
    private Publisher paperPublisher;

    @AttributeOverride(name = "name", column = @Column(name = "ebook_publisher_name"))
    @AssociationOverride(name = "country", joinColumns = @JoinColumn(name = "ebook_publisher_country_id"))
    private Publisher ebookPublisher;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "library_id")
    private Library library;

    public void addReader(Reader reader) {
        reader.getBooks().add(this);
        readers.add(reader);
    }

    public void removeReader(Reader reader) {
        reader.getBooks().remove(this);
        readers.remove(reader);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(author, book.author) &&
                Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(author, title);
    }
}
