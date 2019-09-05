package efx.com.multio;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    }

    User(FireUser fUser){
        m_username = fUser.getUsername();
        m_title = "Mathlete";//fUser.getTitle();
        m_wallet = new PlayerWallet(fUser.getCoins());
        B_Score = fUser.getB_score();
        B_Time = fUser.getB_time();
        TitleArray = fUser.getTitles();
        BadgeArray = fUser.getBadges();
        friends = fUser.getF_IDs();
        G_Won = fUser.getG_won();
    }

    public int getG_Won() {
        return G_Won;
    }

    public void setG_Won(int g_Won) {
        G_Won = g_Won;
    }


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

class FireUser implements Serializable{
    private String username,titles,badges,tag;
    private int B_score,B_time, coins, title, G_won;
    private ArrayList<String> f_IDs;

    public FireUser(User u){
        username = u.getUsername();
        titles = u.getTitleArray();
        badges = u.getBadgeArray();
        B_score = u.getB_Score();
        B_time = u.getB_Time();
        coins = u.getM_wallet().getWallet();
        //title = u.getTitle();
        G_won = u.getG_Won();
        f_IDs = u.getFriends();

    }

    public FireUser(){}


    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("username",username);
        result.put("titles",titles);
        result.put("badges",badges);
        result.put("B_score",B_score);
        result.put("B_time",B_time);
        result.put("coins",coins);
        result.put("G_won",G_won);
        result.put("f_IDs",f_IDs);
        return result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getBadges() {
        return badges;
    }

    public void setBadges(String badges) {
        this.badges = badges;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getB_score() {
        return B_score;
    }

    public void setB_score(int b_score) {
        B_score = b_score;
    }

    public int getB_time() {
        return B_time;
    }

    public void setB_time(int b_time) {
        B_time = b_time;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getG_won() {
        return G_won;
    }

    public void setG_won(int g_won) {
        G_won = g_won;
    }

    public ArrayList<String> getF_IDs() {
        return f_IDs;
    }

    public void setF_IDs(ArrayList<String> f_IDs) {
        this.f_IDs = f_IDs;
    }
}