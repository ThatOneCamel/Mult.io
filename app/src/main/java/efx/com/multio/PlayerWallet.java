package efx.com.multio;

public class PlayerWallet {
    private int wallet;

    public int getWallet(){ return wallet;}

    public void addMoney(int amount){ wallet += amount; }

    public boolean makePurchase(int amount){
        if (amount > wallet)
            return false;
        else {
            wallet -= amount;
            return true;
        }

    }


    PlayerWallet(){
        wallet = 500;
    }
}
