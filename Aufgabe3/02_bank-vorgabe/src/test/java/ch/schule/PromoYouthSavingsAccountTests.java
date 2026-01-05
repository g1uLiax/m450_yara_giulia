package ch.schule;

import ch.schule.Account;
import ch.schule.PromoYouthSavingsAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests für das Promo-Jugend-Sparkonto.
 *
 * @author XXXX
 * @version 1.0
 */
public class PromoYouthSavingsAccountTests
{
	/**
	 * Der Test.
	 */
	@Test
	public void testDeposit() {
		Account promo = new PromoYouthSavingsAccount("1");
		promo.deposit(12122025, 100);
		assertEquals(101, promo.getBalance());
	}
}
