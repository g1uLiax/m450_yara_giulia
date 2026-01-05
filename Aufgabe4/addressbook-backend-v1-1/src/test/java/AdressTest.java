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
