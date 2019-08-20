package efx.com.multio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Game Start
        findViewById(R.id.gameStartBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameplay = new Intent(getApplicationContext(), CampaignActivity.class);
                startActivity(gameplay);
                finish();
            }
        });

        //
        findViewById(R.id.viewProfileBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(getApplicationContext(), User_Profile.class);
                startActivity(profile);
            }
        });

        findViewById(R.id.btnEG).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent endGame = new Intent(getApplicationContext(), EndgameScreenActivity.class);
                endGame.putExtra("Score","50");
                startActivity(endGame);
                finish();
            }
        });
    }

}
