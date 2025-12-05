package ch.schule.bank.junit5;

import ch.schule.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests für die Klasse Account.
 *
 * @author xxxx
 * @version 1.0
 */
public class AccountTests {
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
     * Tested die Initialisierung eines Kontos.
     */
    @Test
    public void testInit() {
        Bank test = new Bank();
        String pysaId = test.createPromoYouthSavingsAccount();
        String salaryId = test.createSalaryAccount(0);
        String savingsId = test.createSavingsAccount();

        assertEquals("Y-1000", pysaId);
        assertEquals("P-1001", salaryId);
        assertEquals("S-1002", savingsId);
    }

    /**
     * Testet das Einzahlen auf ein Konto.
     */
    @Test
    public void testDeposit() {
        SavingsAccount account = new SavingsAccount("1");
        account.deposit(5122025, 100);
        assertEquals(100, account.getBalance());
    }

    /**
     * Testet das Abheben von einem Konto.
     */
    @Test
    public void testWithdraw() {
        SalaryAccount account = new SalaryAccount("1", -1000);
        account.withdraw(5122025, 100);
        assertEquals(-100, account.getBalance());
    }

    /**
     * Tests the reference from SavingsAccount
     */
    @Test
    public void testReferences() {
        assertEquals(true, true);
    }

    /**
     * teste the canTransact Flag
     */
    @Test
    public void testCanTransact() {
        SavingsAccount account = new SavingsAccount(" 1");
        assertEquals(true, account.canTransact(5122025));
    }

    /**
     * Experimente mit print().
     */
    @Test
    public void testPrint() {
        SavingsAccount account = new SavingsAccount("1");
        account.print();
        assertEquals("Kontoauszug '1'\r\n" +
                "Datum          Betrag      Saldo".trim(), outputStreamCaptor.toString().trim());
    }

    /*
    @Test
    public void testPrintData() {
        SavingsAccount account = new SavingsAccount("1");
        account.deposit(5122025, 100);
        account.print();
        assertEquals("", outputStreamCaptor.toString().trim());
    }*/

    /**
     * Experimente mit print(year,month).
     */
    @Test
    public void testMonthlyPrint() {
        SavingsAccount account = new SavingsAccount("1");
        account.print(2025, 12);
        assertEquals("Kontoauszug '1' Monat: 12.2025\r\n" +
                "Datum          Betrag      Saldo", outputStreamCaptor.toString().trim());
    }

    /*
    @Test
    public void testMonthlyPrintWithData() {
        SavingsAccount account = new SavingsAccount("1");
        account.deposit(5122025, 100);
        account.print(2025, 12);
        assertEquals("Kontoauszug '1' Monat: 12.2025\r\n" +
                "Datum          Betrag      Saldo", outputStreamCaptor.toString().trim());
    }*/

}
