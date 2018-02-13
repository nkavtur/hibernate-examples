package home.nkavtur.hibernateexamples;

import home.nkavtur.hibernateexamples.domain.*;
import lombok.SneakyThrows;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Blob;
import java.util.*;

@SpringBootApplication
@Transactional
public class HibernateExamplesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateExamplesApplication.class, args);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private HibernateExamplesApplication hibernateExamplesApplication;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return str -> {
//            hibernateExamplesApplication.contacts();
//            hibernateExamplesApplication.locations();
//            Donor donor = hibernateExamplesApplication.donor();
//            hibernateExamplesApplication.merge(donor);
//            hibernateExamplesApplication.accountsAndDebit();
                hibernateExamplesApplication.booksReader();
        };
    }

    @Transactional
    public void locations() {
        Location location1 = new Location()
                .setName("San Francisco GM")
                .setType(LocationType.DEALERSHIP)
                .setImage(blob("images/Inventory.jpg"));

        Location location2 = new Location()
                .setName("Chicago BMW")
                .setType(LocationType.DEALERSHIP);

        Location location3 = new Location()
                .setName("NY Ford")
                .setType(LocationType.DEALERSHIP);

        entityManager.persist(location1);
        entityManager.persist(location2);
        entityManager.persist(location3);

    }

    @SneakyThrows
    private Blob blob(String fileName) {
        ClassPathResource resource = new ClassPathResource(fileName);
        return BlobProxy.generateProxy(resource.getInputStream(), resource.getFile().length());
    }

    @Transactional
    public Donor donor() {
        Donor donor = new Donor()
                .setId(UUID.randomUUID())
                .setName("Nick Kavtur");

        entityManager.persist(donor);
//
//        entityManager.flush();
//
        System.out.println("in transaction createTs: " + donor.getCreateTs());
        System.out.println("in transaction updateTs: " + donor.getUpdateTs());
        System.out.println("in transaction typeCode: " + donor.getTypeCode());
        System.out.println("in transaction test_update_ts: " + donor.getUpdateTs());
        System.out.println("in transaction test_create_ts: " + donor.getCreateTs());

        return donor;
    }

    @Transactional
    public void contacts() {
        Contact contact1 = new Contact()
                .setLast("Josh")
                .setFirst("Long")
                .setWebsite("http://josh-long.com")
                .setStarred(true)
                .setNotes("Spring developer advocate")
                .setFlag(false);

        Contact contact2 = new Contact()
                .setLast("Josh")
                .setFirst("Long")
                .setWebsite("http://josh-long.com")
                .setStarred(true)
                .setNotes("Spring developer advocate")
                .setFlag(true);

        Contact contact3 = new Contact()
                .setLast("Josh")
                .setFirst("Long")
                .setWebsite("http://josh-long.com")
                .setStarred(true)
                .setNotes("Spring developer advocate")
                .setFlag(true);

        entityManager.persist(contact1);
        entityManager.persist(contact2);
        entityManager.persist(contact3);
    }

    @Transactional
    public void accountsAndDebit() {
//        Account account1 = new Account().setActive(true)
//                .setRate(BigDecimal.valueOf(3.14))
//                .setAmount(BigDecimal.valueOf(42.4))
//                .setType(AccountType.DEBIT);
//
//        Account account2 = new Account().setActive(true)
//                .setRate(BigDecimal.valueOf(634.7))
//                .setAmount(BigDecimal.valueOf(42.3))
//                .setType(AccountType.CREDIT);
//
//        Account account3 = new Account().setActive(false)
//                .setRate(BigDecimal.valueOf(34.1))
//                .setAmount(BigDecimal.valueOf(9.456456456))
//                .setType(AccountType.CREDIT);
//
//
//        Client client = new Client()
//                .setName("Nikolai Kavtur")
//                .setDebitAccounts(Arrays.asList(account1, account2, account3));


//        entityManager.persist(client);

        Client client = entityManager.createQuery("from Client", Client.class).getSingleResult();
        System.out.println(client.getCreditAccounts().size());
        System.out.println(client.getDebitAccounts().size());

    }

    @Transactional
    public void booksReader() {
//        Book book1 = new Book()
//                .setTitle("Cloud native java")
//                .setAuthor("Josh Long");
//
//        Book book2 = new Book()
//                .setTitle("Effective Java")
//                .setAuthor("Joshua Bloch");
//
//        Reader reader = new Reader()
//                .setName("Nikolai Kavtur")
//                .setBooks(new HashSet<>(Arrays.asList(book1, book2)));
//
//        book1.setReaders(new HashSet<>());
//        book1.getReaders().add(reader);
//
//        book2.setReaders(new HashSet<>());
//        book2.getReaders().add(reader);
//
//        entityManager.persist(book1);


        Reader reader = entityManager.createQuery("from Reader", Reader.class).getSingleResult();
    }

    @Transactional
    public void merge(Donor donor) {
        donor.setName("Mr. " + donor.getName());
        entityManager.merge(donor);
    }
}
