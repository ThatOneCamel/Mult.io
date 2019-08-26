package efx.com.multio;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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

        User(){
            m_username = m_title = "";
            m_wallet = new PlayerWallet();

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
