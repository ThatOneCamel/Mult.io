package efx.com.multio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EndgameScreenActivity extends AppCompatActivity {

    TextView scoreText;
    TextView scoreWordText;

    String score;
    String scoreWord;
    String gamemode;
    int scoreInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int money = 0;

        if(getIntent().getExtras() != null) {
            score = getIntent().getStringExtra("Score");
            scoreWord = getIntent().getStringExtra("ScoreWord");
            gamemode = getIntent().getStringExtra("mode");
            scoreInt = getIntent().getIntExtra("ScoreInt", 0);
            money = getIntent().getIntExtra("money", 0);
        } else {
            gamemode = "Campaign";
        }

        setContentView(R.layout.activity_endgame_screen);
        scoreText = findViewById(R.id.scoreNum);
        scoreText.setText(score);
        scoreWordText = findViewById(R.id.textScore);
        scoreWordText.setText(scoreWord);

        TextView amountEarned = findViewById(R.id.currencyNum);
        TextView walletDisplay = findViewById(R.id.walletTotal);
        User.player.getPlayerWallet().addMoney(money);
        User.player.addGameWon();

        if (gamemode.equals("Campaign") && scoreInt > User.player.getB_Score())
            User.player.setB_Score(scoreInt);
        else if (gamemode.equals("Sixty") && scoreInt > User.player.getB_Time())
            User.player.setB_Time(scoreInt);

        User.player.saveLocalData(getApplicationContext());



        //Hardcoded atm; will be sent as an intent extra in future, with the amount being based on user's score
        amountEarned.setText(Integer.toString(money));
        walletDisplay.setText(Integer.toString(User.player.getPlayerWallet().getWallet()));


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
        endIntent.putExtra("mode", gamemode);
        startActivity(endIntent);
        finish();

    }
}
