package newbank.server;
import java.time.*;

public class FixedDepositAccount extends Account{
    // As a subclass of Account, Fixed Deposit extends features of Account to
    // charge prohibit withdrawls if the fixed period has not expired and pays a
    // higher rate of interest

    int fixedDepositTerm;
    float maximumInterestRate = 10; // maximum interest rate for a maximum fixed deposit term
    int maximumFixedDepositTerm = 36; // Three year maximum fixed deposit term

    FixedDepositAccount(int accountID, String accountName,  double openingBalance, int fixedDepositTerm) {
        super(accountID, accountName,  openingBalance);
        this.fixedDepositTerm = fixedDepositTerm;
        this.accountType = AccountType.fixedDeposit;
        this.accountOpenDate = LocalDate.now();
    }

    double calculateMonthlyInterestRate(int fixedDepositTerm) {
        if (fixedDepositTerm > maximumFixedDepositTerm) {
            fixedDepositTerm = maximumFixedDepositTerm;
        }
        return  (double) fixedDepositTerm / (double) maximumFixedDepositTerm;
    }

    @Override
    public String performMonthlyAccounting() {
        // As part of the monthly accounting, a current account charges a monthly
        // maintenance fee and pays interest.
        payInterest(calculateMonthlyInterestRate(fixedDepositTerm));
        return "SUCCESS: MONTHLY ACCOUNTING";
    }

    @Override
    public String payInterest(double interestRate) {
        double oldbalance = openingBalance;
        double newbalance = openingBalance * (1 + interestRate/ 100f / 12f);
        String s = String.format("SUCCESS: INTEREST PAID | old balance: %f | new balance: %f", oldbalance, newbalance);
        System.out.println(s);
        return s;
    }

    @Override
    public void withdraw(double amount) {
        int term = fixedDepositTerm;
        LocalDate startDate = accountOpenDate;
        LocalDate today = LocalDate.now();
        LocalDate expiry = startDate.plusMonths(term);
        if(expiry.isBefore(today)){
            this.openingBalance -= amount;
            System.out.println("SUCCESS");
        }
        else{
          System.out.println("FAIL: The fixed deposit term has not expired. You may withdraw after: " + expiry.toString() + " 23h59");
        }
    }

}

