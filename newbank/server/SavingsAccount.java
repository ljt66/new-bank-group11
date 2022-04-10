package newbank.server;

import java.time.LocalDate;

public class SavingsAccount extends Account{
    //As a subclass of Account, SAVINGS Account extends features of Account to charge monthly maintanance costs and pay a low monthly interest.
    float maintananceCostPerMonth = 1.0f;
    float annualInterestRate = 2f;

    SavingsAccount(int accountID, String accountName, double openingBalance){
        super(accountID, accountName, openingBalance);
        this.accountType = AccountType.savings;
        this.accountOpenDate = LocalDate.now();
    }

    public void chargeMaintenance(){
        openingBalance -= maintananceCostPerMonth;
    }

    public void monthlyChargesAndInterest(){
        performMonthlyAccounting();
    }
    @Override
    public String performMonthlyAccounting() {
      //As part of the monthly accounting, a current account charges a monthly maintenance fee and pays interest. 
        chargeMaintenance();
        payInterest(annualInterestRate);
        return "SUCCESS: MONTHLY ACCOUNTING";
    }

    @Override
    public String payInterest(double interestRate) {
        double interest = openingBalance * ((interestRate / 12) / 100);
        openingBalance = openingBalance + interest;
        return String.format("SUCCESS: Interest to the value of %f has been paid to %s", interest,
                this.accountName);
    }

    
}

