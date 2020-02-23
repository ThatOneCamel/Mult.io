package efx.com.multio;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import efx.com.multio.MainMenuActivity.Gamemode;

public class EndgameScreenActivity extends AppCompatActivity {

    TextView scoreText;
    TextView scoreWordText;

    String score;
    String scoreWord;
    Gamemode gamemode;
    int scoreInt;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        int money = 0;

        if(getIntent().getExtras() != null) {
            score = getIntent().getStringExtra("Score");
            scoreWord = getIntent().getStringExtra("ScoreWord");
            gamemode = (Gamemode) getIntent().getSerializableExtra("mode");
            scoreInt = getIntent().getIntExtra("ScoreInt", 0);
            money = getIntent().getIntExtra("money", 0);
        } else {
            gamemode = Gamemode.CAMPAIGN;
        }

        setContentView(R.layout.activity_endgame_screen);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        scoreText = findViewById(R.id.scoreNum);
        scoreText.setText(score);
        scoreWordText = findViewById(R.id.textScore);
        scoreWordText.setText(scoreWord);

        TextView amountEarned = findViewById(R.id.currencyNum);
        TextView walletDisplay = findViewById(R.id.walletTotal);
        User.player.getPlayerWallet().addMoney(money);
        //User.player.addGameWon();

        if (gamemode == Gamemode.CAMPAIGN && scoreInt > User.player.getB_Score())
            User.player.setB_Score(scoreInt);
        else if (gamemode == Gamemode.TIMED && scoreInt > User.player.getB_Time())
            User.player.setB_Time(scoreInt);

        User.player.saveLocalData(getApplicationContext());



        //Hardcoded atm; will be sent as an intent extra in future, with the amount being based on user's score
        amountEarned.setText("+" + money);
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
        int numOfProblems = getIntent().getIntExtra("problems", 10);
        endIntent.putExtra("mode", gamemode);
        endIntent.putExtra("problems", numOfProblems);
        startActivity(endIntent);
        finish();

    }
}
