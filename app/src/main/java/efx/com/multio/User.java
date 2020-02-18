package efx.com.multio;

import android.content.Context;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class User implements Serializable {

    static User player = new User();

    /* FUNCTIONS */
        public String getUsername(){ return m_username; }
        //public String getUUID() { return m_UUID; }
        public String getTitle(){ return m_title; }
        public PlayerWallet getPlayerWallet() { return m_wallet; }
        public void setUsername(String entry) { m_username = entry; }
        //public void setUUID(String entry) { m_UUID = entry; }
        public void setTitle(String entry) { m_title = entry; }
        //public void setPla(Currency entry) { m_currency = entry; }

    /* VARIABLES */
    private String m_username;
    private String m_title;
    //private String m_UUID = "";
    private PlayerWallet m_wallet;
    private int B_Score;
    private int B_Time;
    private int G_Won;
    private String TitleArray;
    private String BadgeArray;
    private ArrayList<String> friends;


    User(){
        /*
        m_username = "";
        m_wallet = new PlayerWallet();
        m_title = "Mathlete";
        B_Score = 200;
        B_Time = 22;
        TitleArray = "101010";
        BadgeArray = "10101010101";
        friends = new ArrayList<String>();
        G_Won = 17;
        */
        m_wallet = new PlayerWallet();
        setB_Score(0);

    }

    public int getG_Won() {
        return G_Won;
    }

    public void setG_Won(int g_Won) {
        G_Won = g_Won;
    }

    public void addGameWon() { G_Won += 1; }

    public String getTitleArray() {
        return TitleArray;
    }

    public void setTitleArray(String titleArray) {
        TitleArray = titleArray;
    }

    public String getBadgeArray() {
        return BadgeArray;
    }

    public void setBadgeArray(String badgeArray) {
        BadgeArray = badgeArray;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }



    public String getM_username() {
        return m_username;
    }

    public void setM_username(String m_username) {
        this.m_username = m_username;
    }

    public String getM_title() {
        return m_title;
    }

    public void setM_title(String m_title) {
        this.m_title = m_title;
    }

    public PlayerWallet getM_wallet() {
        return m_wallet;
    }

    public void setM_wallet(PlayerWallet m_wallet) {
        this.m_wallet = m_wallet;
    }

    public int getB_Score() {
        return B_Score;
    }

    public void setB_Score(int b_Score) {
        B_Score = b_Score;
    }

    public int getB_Time() {
        return B_Time;
    }

    public void setB_Time(int b_Time) {
        B_Time = b_Time;
    }


    public class PlayerWallet implements Serializable {
        private int wallet;

        int getWallet(){ return wallet;}

        void addMoney(int amount){ wallet += amount; }

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
        PlayerWallet(int m){
            wallet = m;
        }
    }

    void saveLocalData(Context context){
        try {
            //FileOutputStream file = new FileOutputStream(filepath);
            FileOutputStream file = context.openFileOutput("user.dat", Context.MODE_PRIVATE);

            ObjectOutputStream objectOut = new ObjectOutputStream(file);
            objectOut.writeObject(this);
            objectOut.close();
            file.close();
            Log.e("FILE", "DATA_SAVED");
        } catch (IOException error){
            Log.e("FILE_ERROR", error.getLocalizedMessage());
            error.printStackTrace();
        }
    }

    void deleteLocalData(Context context){
        context.deleteFile("user.dat");
        Log.i("LOCALDATA", "DELETED USER FILES");

    }

    void loadLocalData(Context context){
        try {
            //Finding and opening the user.dat file, then assigning it to an InputStream
            FileInputStream file = context.openFileInput("user.dat");
            ObjectInputStream objectIn = new ObjectInputStream(file);

            //Assigning the data that was saved in our file to a temporary object
            //This is because we do not want to overwrite local changes when there are potentially
            // newer changes server-side
            //UserInfo fileObject = (UserInfo) objectIn.readObject();
            player = (User) objectIn.readObject();
            //Log.e("HOW MUCH", Integer.toString(((User) objectIn.readObject()).m_wallet.getWallet()));

            //Closing the InputStream and the file
            objectIn.close();
            file.close();

            Log.i("FILE", "DATA_LOADED_SUCCESSFULLY");

        }catch(IOException error){
            //File not found
            Log.e("INPUT_EXCEPTION", error.getLocalizedMessage());

        }catch(ClassNotFoundException error){
            //Error with the class object that was saved
            Log.e("CLASS_EXCEPTION", error.getLocalizedMessage());

        }catch (NullPointerException error){
            Log.e("NULL_EXCEPTION", error.getLocalizedMessage());
        }

    }

}