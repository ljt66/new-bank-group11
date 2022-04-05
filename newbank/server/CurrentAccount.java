package newbank.server;

import java.time.LocalDate;

public class CurrentAccount extends Account implements IFeesAndInterest{
    //As a subclass of Account, Current Account extends features of Account to charge monthly maintanance costs and pay a low monthly interest.
    float maintananceCostPerMonth = 6.50f;
    float annualInterestRate = 0.5f;

    CurrentAccount(String accountName, AccountType accountType, double openingBalance, LocalDate accountOpenDate){
        super(accountName, accountType, openingBalance, accountOpenDate);
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
