package efx.com.multio;

import java.util.Currency;

public class User {

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

        static User player = new User();

}
