package efx.com.multio;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class User_Profile extends AppCompatActivity {

    TextView m_username;
    TextView m_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        m_username = findViewById(R.id.txtUsername);
        m_title = findViewById(R.id.txtTitle);


    }
}