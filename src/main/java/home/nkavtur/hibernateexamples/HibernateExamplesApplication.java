package home.nkavtur.hibernateexamples;

import home.nkavtur.hibernateexamples.domain.*;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Blob;
import java.util.*;

import static java.util.Arrays.*;

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
//            hibernateExamplesApplication.accountsAndDebitFilter();
//            hibernateExamplesApplication.deleteBookReaders();
            hibernateExamplesApplication.saveBooksReader();
            hibernateExamplesApplication.fetchBooksReaders();
//            hibernateExamplesApplication.saveDepartmentEmployees();
//            hibernateExamplesApplication.property();
//            hibernateExamplesApplication.propertyRepository();
        };
    }

    @Transactional
    public void fetchBooksReaders()  {
        List<Book> resultList = entityManager.createQuery(
                "from Book", Book.class)
                .getResultList();

        System.out.println(resultList);
        resultList.forEach(b -> System.out.println(b.getTitle()));

    }

    @Transactional
    public void saveBooksReader() {
        Library nyLibrary = new Library().setName("NY Library");
        Library leninLibrary = new Library().setName("Lenin Library");

        Country newYork = new Country().setName("New York");
        Country london = new Country().setName("London");
        Country monsow = new Country().setName("Moscow");
        Country paris = new Country().setName("Paris");

        Book cloudNativeJava = new Book().setTitle("Cloud native java").setAuthor("Josh Long")
                .setEbookPublisher(new Publisher().setName("ebook").setCountry(newYork))
                .setPaperPublisher(new Publisher().setName("paper").setCountry(paris));
        Book effectiveJava = new Book().setTitle("Effective Java").setAuthor("Joshua Bloch")
                .setEbookPublisher(new Publisher().setName("ebook1").setCountry(newYork))
                .setPaperPublisher(new Publisher().setName("paper1").setCountry(london));

        Book hadoopDefinitiveGuide = new Book().setTitle("Hadoop The Definitive guide").setAuthor("Tom White")
                .setEbookPublisher(new Publisher().setName("ebook2").setCountry(newYork))
                .setPaperPublisher(new Publisher().setName("paper2").setCountry(monsow));

        Reader nikolai = new Reader().setName("Nikolai Kavtur");
        nikolai.addBook(cloudNativeJava);
        nikolai.addBook(effectiveJava);

        Reader vasya = new Reader().setName("Vasya Pupkin");
        vasya.addBook(cloudNativeJava);
        vasya.addBook(hadoopDefinitiveGuide);

        leninLibrary.addBook(cloudNativeJava);
        nyLibrary.addBook(effectiveJava);
        nyLibrary.addBook(hadoopDefinitiveGuide);

        entityManager.persist(leninLibrary);
        entityManager.persist(nyLibrary);
    }

    @Transactional
    public void deleteBookReaders() {
        Reader nikolai = entityManager.createQuery("from Reader r where r.name = :name", Reader.class)
                .setParameter("name", "Nikolai Kavtur").getSingleResult();

//        List<Book> books = new ArrayList<>(nikolai.getBooks());
//        Book cloudNativeJava = books.stream().filter(b -> b.getTitle().equalsIgnoreCase("Cloud native java")).findAny().get();
//        Book effectiveJava = books.stream().filter(b -> b.getTitle().equalsIgnoreCase("Effective Java")).findAny().get();

        entityManager.remove(nikolai);
        nikolai.getBooks().forEach(b -> {
            b.getReaders().remove(nikolai);
            if (b.getReaders().isEmpty()) {
                entityManager.remove(b);
            }
        });

    }

    @Transactional
    public void propertyRepository() {
//        PropertyRepository propertyRepository = new PropertyRepository();
//        propertyRepository.setName("My Repo");
//        StringProperty stringProperty1 = new StringProperty().setName("power").setValue("POwer fg123");
//        StringProperty stringProperty2 = new StringProperty().setName("engine").setValue("Big Engine");
//        IntegerProperty integerProperty = new IntegerProperty().setName("volume").setValue(1);
//
//        propertyRepository.getProperties().addAll(Arrays.asList(stringProperty1, integerProperty, stringProperty2));

//        entityManager.persist(propertyRepository);
        PropertyRepository propertyRepository = entityManager.find(PropertyRepository.class, -40L);
        propertyRepository.getProperties().forEach(p -> System.out.println(p.getName()));
    }

    @Transactional
    public void property() {
        PropertyHolder propertyHolder = new PropertyHolder();

        StringProperty stringProperty1 = new StringProperty().setName("power").setValue("POwer fg123");
        StringProperty stringProperty2 = new StringProperty().setName("engine").setValue("Big Engine");
        IntegerProperty integerProperty = new IntegerProperty().setName("volume").setValue(1);

        entityManager.persist(stringProperty1);
        entityManager.persist(stringProperty2);
        entityManager.persist(integerProperty);
        entityManager.persist(propertyHolder);
    }


    @Transactional
    public void saveDepartmentEmployees() {
        Employee nikolaiKavtur = new Employee().setFirstName("Nikolai").setLastName("Kavtur").setPhoneNumber("+375445536507");
        Employee joshLong = new Employee().setFirstName("Josh").setLastName("Long").setPhoneNumber("+375114241414");
        Employee vasyaPupkin = new Employee().setFirstName("Vasya").setLastName("Pupkin").setPhoneNumber("+3754442341245");

        Department it = new Department().setName("IT");
        it.addEmployees(nikolaiKavtur, joshLong);
        Department marketing = new Department().setName("Marketing");
        marketing.addEmployees(joshLong, vasyaPupkin);

        entityManager.persist(it);
        entityManager.persist(marketing);
    }

    @Transactional
    public void accountsAndDebitFilter() {
        entityManager.unwrap(Session.class)
                .enableFilter("type_filter")
                .setParameter("account_type", "CREDIT");

        Client client = entityManager.find(Client.class, -45L);
        client.getDebitAccounts().forEach(System.out::println);
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
    public void merge(Donor donor) {
        donor.setName("Mr. " + donor.getName());
        entityManager.merge(donor);
    }
}
