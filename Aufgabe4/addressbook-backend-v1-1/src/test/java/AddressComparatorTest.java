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
