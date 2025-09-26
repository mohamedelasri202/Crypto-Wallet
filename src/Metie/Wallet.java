package Metie;

public abstract class Wallet {

    private String adress;
    private double balance ;

    public  Wallet(String adress ,double balance ){
        this.adress = adress;
        this.balance = balance;

    }

    public String  getadress(){
        return adress;
    }
    public void setadress(String adress){
        this.adress = adress;
    }

    public double getbalance(){
        return balance;
    }
    public void setbalance(double balance){
        this.balance= balance;
    }


}
