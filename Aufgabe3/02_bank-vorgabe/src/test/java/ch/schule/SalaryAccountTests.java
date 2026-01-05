package ch.schule;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests der Klasse SalaryAccount.
 *
 * @author XXX
 * @version 1.1
 */
public class SalaryAccountTests {
	/**
	 * Der Test.
	 */
	@Test
	public void test() {
		Account account = new SalaryAccount("1", -100);
		account.deposit(12122025, 100);
	}
}
