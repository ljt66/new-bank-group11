package tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import newbank.server.CustomerID;
import newbank.server.NewBank;

public class TestServer {
   final String SUCCESS = "SUCCESS";
   final String FAIL = "FAIL";

   NewBank bank;
   String result; // Variable to store server results

   @Before
   public void init() {
      bank = NewBank.getBank();
   }

   @Test
   public void testProcessRequest() {
      // Add new customer for the tests
      String dummy = "dummy";
      bank.addCustomer(dummy);
      CustomerID dummyId = new CustomerID(dummy);

      // Check that initially there are no accounts
      result = bank.processRequest(dummyId, "SHOWMYACCOUNTS");
      assertEquals("", result);

      // Add an account and check it exists
      result = bank.processRequest(dummyId, "NEWACCOUNT Main");
      assertEquals(SUCCESS, result);
      result = bank.processRequest(dummyId, "SHOWMYACCOUNTS");
      assertEquals("Main: 0.0\n", result);

      // Add second account and check it exists
      result = bank.processRequest(dummyId, "NEWACCOUNT Savings");
      assertEquals(SUCCESS, result);
      result = bank.processRequest(dummyId, "SHOWMYACCOUNTS");
      assertEquals("Main: 0.0\nSavings: 0.0\n", result);

      // Try to add an account that already exists
      result = bank.processRequest(dummyId, "NEWACCOUNT Main");
      assertEquals(FAIL, result);
      result = bank.processRequest(dummyId, "SHOWMYACCOUNTS");
      assertEquals("Main: 0.0\nSavings: 0.0\n", result);
   }

   @Test
   public void testPayAccount() {
      // Check Bhagy test account has money
      String bhagy = "Bhagy";
      CustomerID bhagyId = new CustomerID(bhagy);
      result = bank.processRequest(bhagyId, "SHOWMYACCOUNTS");
      assertEquals("Main: 1000.0\n", result);

      // Check John test account has money
      String john = "John";
      CustomerID johnId = new CustomerID(john);
      result = bank.processRequest(johnId, "SHOWMYACCOUNTS");
      assertEquals("Checking: 250.0\n", result);

      // Pay 100 Bhagy -> John, check accounts
      result = bank.processRequest(bhagyId, "PAY John 100");
      assertEquals(SUCCESS, result);

      result = bank.processRequest(bhagyId, "SHOWMYACCOUNTS");
      assertEquals("Main: 900.0\n", result);

      result = bank.processRequest(johnId, "SHOWMYACCOUNTS");
      assertEquals("Checking: 350.0\n", result);

      // Pay 100 John -> Bhagy, check accounts
      result = bank.processRequest(johnId, "PAY Bhagy 50");
      assertEquals(SUCCESS, result);

      result = bank.processRequest(bhagyId, "SHOWMYACCOUNTS");
      assertEquals("Main: 950.0\n", result);

      result = bank.processRequest(johnId, "SHOWMYACCOUNTS");
      assertEquals("Checking: 300.0\n", result);

      // Pay 5000 John -> Bhagy, check it failed and accounts haven't changed
      result = bank.processRequest(johnId, "PAY Bhagy 5000");
      assertEquals(FAIL, result);

      result = bank.processRequest(bhagyId, "SHOWMYACCOUNTS");
      assertEquals("Main: 950.0\n", result);

      result = bank.processRequest(johnId, "SHOWMYACCOUNTS");
      assertEquals("Checking: 300.0\n", result);
   }

   @Test
   public void testAdminLogin() {
      CustomerID user = bank.checkLogInDetails(NewBank.adminUsername, NewBank.adminPassword);
      assertEquals(NewBank.adminUsername, user.getKey());
      assertTrue(user.isAdmin());
   }
}