package ch.schule;

import ch.schule.Account;
import ch.schule.SavingsAccount;



/**
 * Tests f�r die Klasse SavingsAccount.
 *
 * @author Roger H. J&ouml;rg
 * @version 1.0
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests für die Klasse SavingsAccount.
 *
 * @author XXX
 * @version 1.0
 */
public class SavingsAccountTests
{
	@Test
	public void testWithdrawNonNegative() {
		Account account = new SavingsAccount("1");
		account.withdraw(12122025, 100);
		assertEquals(0, account.getBalance());
	}
}

