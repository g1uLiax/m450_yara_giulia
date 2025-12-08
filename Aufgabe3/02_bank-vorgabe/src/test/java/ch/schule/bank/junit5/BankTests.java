package ch.schule.bank.junit5;

import ch.schule.Account;
import ch.schule.Bank;
import ch.schule.SavingsAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests f�r die Klasse 'Bank'.
 *
 * @author xxxx
 * @version 1.0
 */
public class BankTests {
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
     * Tests to create new Accounts
     */
    @Test
    public void testCreate() {
        Bank test = new Bank();
        /*
        String pysaId = test.createPromoYouthSavingsAccount();
        String salaryId = test.createSalaryAccount(0);
        String savingsId = test.createSavingsAccount();

        assertEquals("Y-1000", pysaId);
        assertEquals("P-1001", salaryId);
        assertEquals("S-1002", savingsId);*/

    }
    /**
     * Testet das Einzahlen auf ein Konto.
     */
    @Test
    public void testDeposit() {
        Bank bank = new Bank();
        bank.createSavingsAccount();
        bank.deposit("S-1000", 12122025, 100);
        assertEquals(100, bank.getBalance("S-1000"));
    }
    /**
     * Testet das Abheben von einem Konto.
     */
    @Test
    public void testWithdraw() {
        Bank bank = new Bank();
        bank.createSalaryAccount(-1000);
        bank.withdraw("P-1000", 12122025, 100);
        assertEquals(-100, bank.getBalance("P-1000"));
    }

    /**
     * Experimente mit print().
     */
    @Test
    public void testPrint() {
        Bank bank = new Bank();
        bank.createSavingsAccount();
        bank.print("S-1000");
        assertEquals("Kontoauszug 'S-1000'\r\n" +
                "Datum          Betrag      Saldo", outputStreamCaptor.toString().trim());
    }

    /**
     * Experimente mit print(year, month).
     */
    @Test
    public void testMonthlyPrint() {
        Bank bank = new Bank();
        bank.createSavingsAccount();
        bank.print("S-1000", 2025, 12);
        assertEquals("Kontoauszug 'S-1000' Monat: 12.2025\r\n" +
                "Datum          Betrag      Saldo", outputStreamCaptor.toString().trim());
    }

    /**
     * Testet den Gesamtkontostand der Bank.
     */
    @Test
    public void testBalance() {
        Bank bank = new Bank();
        bank.createSavingsAccount();
        bank.createSalaryAccount(-1000);
        bank.deposit("S-1000", 5122025, 1000);
        bank.withdraw("P-1001", 5122025, 2000);
        assertEquals(-1000, bank.getBalance());
    }

    /**
     * Tested die Ausgabe der "top 5" Konten.
     */
    @Test
    public void testTop5() {
        fail("toDo");
    }

    /**
     * Tested die Ausgabe der "top 5" Konten.
     */
    @Test
    public void testBottom5() {
        fail("toDo");
    }

}
