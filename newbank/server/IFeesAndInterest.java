package newbank.server;

public interface IFeesAndInterest{
    //An interface containing methods for all account types to implement
    public String performMonthlyAccounting(); // method to perform all charges and interest payments on an account
    public String payInterest(double interestRate); //method to perfoam interest payment
}
