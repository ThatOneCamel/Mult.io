package efx.com.multio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndgameScreenActivity extends AppCompatActivity {

    TextView scoreText;
    String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getExtras() != null)
        {
            score = getIntent().getStringExtra("Score");
        }
        setContentView(R.layout.activity_endgame_screen);
        scoreText = findViewById(R.id.scoreNum);
        scoreText.setText(score);

    }

    @Override
    public void onBackPressed() {

    }

    public void Menu(View v) {
        Intent endIntent = new Intent(this,MainMenuActivity.class);
        startActivity(endIntent);
        finish();
    }

    public void Replay(View v) {
        Intent endIntent = new Intent(this,CampaignActivity.class);
        startActivity(endIntent);
        finish();

    }
}
