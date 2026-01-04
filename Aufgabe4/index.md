## Abhängigkeiten zu Schnittstellen 

### Aufgabe 1

#### Implement Adress comparator
- sorts it by lastname, then firstname
- receive sorted results from database
- if lastname and firstname are the same, will be sorted by registration date

````java
package ch.tbz.m450.util;

import ch.tbz.m450.repository.Address;

import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class AddressComparator implements Comparator<Address> {

    @Override
    public int compare(Address a1, Address a2) {

        if (a1 == a2) return 0;
        if (a1 == null) return -1;
        if (a2 == null) return 1;

        // 1) Lastname
        int ln = compareStrings(a1.getLastname(), a2.getLastname());
        if (ln != 0) return ln;

        // 2) Firstname
        int fn = compareStrings(a1.getFirstname(), a2.getFirstname());
        if (fn != 0) return fn;

        // 3) Date
        int dt = compareDates(a1.getRegistrationDate(), a2.getRegistrationDate());
        if (dt != 0) return dt;

        // 4) Falls wirklich alles gleich: id
        return Integer.compare(a1.getId(), a2.getId());
    }

    private int compareStrings(String s1, String s2) {
        if (Objects.equals(s1, s2)) return 0;
        if (s1 == null) return -1;
        if (s2 == null) return 1;
        return s1.compareToIgnoreCase(s2);
    }

    private int compareDates(Date d1, Date d2) {
        if (Objects.equals(d1, d2)) return 0;
        if (d1 == null) return -1;
        if (d2 == null) return 1;
        return d1.compareTo(d2);
    }
}
````

#### Test for Adress.java
Testcases: 
- get function
- instance can be created 
- attributes be set and changed

````java
import ch.tbz.m450.repository.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AdressTest {
    Address address;
    Date now;

    @BeforeEach
    void init() {
        now = new Date();
        address = new Address(1, "Max", "Muster", "+41790000000", now);
    }

    @Test
    void testGetter() {
        assertEquals(1,address.getId());
        assertEquals("Max", address.getFirstname());
        assertEquals("Muster", address.getLastname());
        assertEquals("+41790000000", address.getPhonenumber());
        assertEquals(now, address.getRegistrationDate());
    }


    @Test
    void testSetter() {
        address.setFirstname("Anna");
        address.setLastname("Müller");
        address.setPhonenumber("0791112222");

        Date later = new Date(now.getTime() + 1000);
        address.setRegistrationDate(later);

        assertEquals("Anna", address.getFirstname());
        assertEquals("Müller", address.getLastname());
        assertEquals("0791112222", address.getPhonenumber());
        assertEquals(later, address.getRegistrationDate());
    }
}
````


#### Test for AddressService.java
(repo was mocked, "rebuild" with mockito)
<br>
Testcases: 
- logic function of endpoint
- should run fast and independently of DB

`````java 
import ch.tbz.m450.repository.Address;
import ch.tbz.m450.repository.AddressRepository;
import ch.tbz.m450.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddressServiceTest {
    AddressRepository repository;
    AddressService service;

    @BeforeEach
    void setUp() {
        repository = mock(AddressRepository.class);
        service = new AddressService(repository);
    }

    @Test
    void testSave() {
        Address a = new Address(1, "Max", "Muster", "123", new Date());
        when(repository.save(a)).thenReturn(a);

        Address saved = service.save(a);

        assertSame(a, saved);
        verify(repository).save(a);
    }


    @Test
    void testGetAllSorted() {
        Address a1 = new Address(1, "Zoe", "Alpha", "111", new Date());
        Address a2 = new Address(2, "Ben", "Alpha", "222", new Date());

        when(repository.findAll()).thenReturn(List.of(a1, a2));

        List<Address> sorted = service.getAll(); // Comparator wird angewendet

        assertEquals(a2, sorted.get(0)); // "Ben" kommt alphabetisch vor "Zoe"
        verify(repository).findAll();
    }

    @Test
    void testGetById() {
        Address a = new Address(5, "Max", "Muster", "123", new Date());
        when(repository.findById(5)).thenReturn(Optional.of(a));

        Optional<Address> found = service.getAddress(5);

        assertTrue(found.isPresent());
        assertEquals("Max", found.get().getFirstname());
        verify(repository).findById(5);
    }


}
`````

#### Test for AddressComparator.java
Testcases: 
- does it sort properly (lastname first)

````java
import ch.tbz.m450.repository.Address;
import ch.tbz.m450.util.AddressComparator;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AddressComparatorTest {

    @Test
    void shouldSortAddressesByLastnameFirstnameDate() throws InterruptedException {
        // Arrange
        Date d1 = new Date();
        Thread.sleep(10); // kleine Zeitdifferenz
        Date d2 = new Date();

        Address a1 = new Address(1, "Anna", "Muster", "111", d1);
        Address a2  = new Address(2, "Max",  "Muster", "222", d2);

        List<Address> addresses = new ArrayList<>(List.of(a1, a2));

        // Act
        addresses.sort(new AddressComparator());

        // Assert
        List<Address> expectedOrder = List.of(a1, a2);
        // Lastname first
        assertEquals(expectedOrder, addresses,
                "Addresses should be sorted by lastname, then firstname, then registrationDate");

    }
}
````

### Aufgabe 2
#### Advanced AddressComparator Class
- enum field to restrict options
- sort list can be decided 
- comparison logic has been made generic (dynamically tested, if all same field => obj will be compared to id)

````java
package ch.tbz.m450.util;

import ch.tbz.m450.repository.Address;

import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class AddressComparator implements Comparator<Address> {

    public enum Field {
        FIRSTNAME,
        LASTNAME,
        PHONENUMBER,
        REGISTRATIONDATE
    }

    private final Field[] fields;

    public AddressComparator() {
        // default sort list
        this.fields = new Field[]{
                Field.LASTNAME,
                Field.FIRSTNAME,
                Field.REGISTRATIONDATE
        };
    }

    public AddressComparator(Field... fields) {
        if (fields == null || fields.length == 0) {
            this.fields = new Field[]{
                    Field.LASTNAME,
                    Field.FIRSTNAME,
                    Field.REGISTRATIONDATE
            };
        } else {
            this.fields = fields;
        }
    }

    @Override
    public int compare(Address a1, Address a2) {
        if (a1 == a2) return 0;
        if (a1 == null) return -1;
        if (a2 == null) return 1;

        for (Field f : fields) {
            int result = switch (f) {
                case LASTNAME -> compareString(a1.getLastname(), a2.getLastname());
                case FIRSTNAME -> compareString(a1.getFirstname(), a2.getFirstname());
                case PHONENUMBER -> compareString(a1.getPhonenumber(), a2.getPhonenumber());
                case REGISTRATIONDATE -> compareDate(a1.getRegistrationDate(), a2.getRegistrationDate());
            };
            if (result != 0)
                return result;
        }

        // Fallback = ID
        return Integer.compare(a1.getId(), a2.getId());
    }

    private int compareString(String s1, String s2) {
        if (Objects.equals(s1, s2)) return 0;
        if (s1 == null) return -1;
        if (s2 == null) return 1;
        return s1.compareToIgnoreCase(s2);
    }

    private int compareDate(Date d1, Date d2) {
        if (Objects.equals(d1, d2)) return 0;
        if (d1 == null) return -1;
        if (d2 == null) return 1;
        return d1.compareTo(d2);
    }

}
````
#### Test for advanced AddressComparator.java 
Testcases:
- sorts by phonenumber (if names match)
- sorts by restigrationnumber (if names and phonenumber match)
- sort by lastname and phonenumber (although lastname match)
- sort by lastname and firstname
- 
````java
import ch.tbz.m450.repository.Address;
import ch.tbz.m450.util.AddressComparator;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static ch.tbz.m450.util.AddressComparator.Field.*;
import static org.junit.jupiter.api.Assertions.*;

public class AddressComparatorTest {

    @Test
    void testSortByPhoneNumber() {
        Date now = new Date();

        Address a1 = new Address(10, "Laura", "Keller", "050", now);
        Address a2 = new Address(11, "Laura", "Keller", "200", now);

        AddressComparator cmp = new AddressComparator(PHONENUMBER);

        // "050" comes before "200"
        assertTrue(cmp.compare(a1, a2) < 0);
    }

    @Test
    void testSortByRegistrationDate() {
        Date earlier = new Date(System.currentTimeMillis() - 5_000);
        Date later   = new Date();

        Address a1 = new Address(3, "Tom", "Schneider", "888", earlier);
        Address a2 = new Address(4, "Tom", "Schneider", "888", later);

        AddressComparator cmp = new AddressComparator(REGISTRATIONDATE);

        // older registration date should come first
        assertTrue(cmp.compare(a1, a2) < 0);
    }

    @Test
    void testCustomOrderLastnameThenPhone() {
        Date now = new Date();

        Address a1 = new Address(20, "Mia", "Huber", "900", now);
        Address a2 = new Address(21, "Mia", "Huber", "300", now);

        AddressComparator cmp = new AddressComparator(LASTNAME, PHONENUMBER);

        // Same lastname -> comparison continues with PHONENUMBER
        assertTrue(cmp.compare(a2, a1) < 0);
    }

    @Test
    void testMultiFieldSorting() {
        Date now = new Date();

        Address a1 = new Address(30, "Sophie", "Weber", "111", now);
        Address a2 = new Address(31, "Daniel", "Weber", "111", now);

        AddressComparator cmp = new AddressComparator(LASTNAME, FIRSTNAME);

        // "Daniel" comes before "Sophie"
        int result = cmp.compare(a1, a2);
        assertTrue(result > 0);
    }
}
````








