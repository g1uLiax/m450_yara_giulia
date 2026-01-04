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
