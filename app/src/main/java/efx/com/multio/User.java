package efx.com.multio;

import java.util.Currency;

public class User {

    /* FUNCTIONS */
        public String getUsername(){ return m_username; }
        public String getUUID() { return m_UUID; }
        public String getTitle(){ return m_title; }
        public Currency getCurrency() { return m_currency; }
        public void setUsername(String entry) { m_username = entry; }
        public void setUUID(String entry) { m_UUID = entry; }
        public void setTitle(String entry) { m_title = entry; }
        public void setM_currency(Currency entry) { m_currency = entry; }

    /* VARIABLES */
        private String m_username = "";
        private String m_title = "";
        private String m_UUID = "";
        private Currency m_currency;

}
