package efx.com.multio;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class User_Profile extends AppCompatActivity {

    TextView m_username;
    TextView m_title;
    TextView m_complete;
    TextView m_time;
    TextView m_score;
    TextView m_tokens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        m_username = findViewById(R.id.txtUsername);
        m_title = findViewById(R.id.txtTitle);
        m_complete = findViewById(R.id.txtWins);
        m_time = findViewById(R.id.txtTime);
        m_score = findViewById(R.id.txtScore);
        m_tokens = findViewById(R.id.txtTokens);

        loadUser();

    }

    private void loadUser()
    {
        m_username.setText(User.player.getUsername());
        m_title.setText(User.player.getTitle());
        m_score.setText(""+User.player.getB_Score());
        m_time.setText(""+User.player.getB_Time()+" seconds");
        m_tokens.setText(""+User.player.getPlayerWallet().getWallet());
        m_complete.setText(User.player.getG_Won()+"");

        Log.i("User", User.player.toString());
    }
}