package ch.schule;

import ch.schule.Booking;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Tests für die Klasse Booking.
 *
 * @author Luigi Cavuoti
 * @version 1.1
 */
public class BookingTests
{
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outputStreamCaptor));
	}

	@AfterEach
	public void tearDown() {
		System.setOut(standardOut);
	}
	/**
	 * Tests für die Erzeugung von Buchungen.
	 */
	@Test
	public void testInitialization() {
		Booking booking = new Booking(20251212, 800);
		assertEquals(20251212, booking.getDate());
		assertEquals(800, booking.getAmount());
	}

	/**
	 * Experimente mit print().
	 */
	@Test
	public void testPrint() {
		Booking booking = new Booking(20251212, 7000);
		booking.print(6000);
		assertEquals("13.05.58223       0.07       0.13", outputStreamCaptor.toString().trim());
		assertEquals(20251212, booking.getDate());
		assertEquals(7000, booking.getAmount());
	}
}
