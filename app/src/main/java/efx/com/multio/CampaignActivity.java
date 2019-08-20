package efx.com.multio;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CampaignActivity extends AppCompatActivity {

    EquationFragment mathFragment;
    InputButtons inputFragment;
    CountDownScreen countDownFragment;
    TextView editor;
    GameHandler Game;
    CountDownTimer timer;
    TextView score;
    TextView scoreWord;
    String gamemode;

    private long timeLeft = 3500;

    int answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);
        gamemode = "Sixty";

        mathFragment = (EquationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        inputFragment = (InputButtons) getSupportFragmentManager().findFragmentById(R.id.fragment_buttons);
        countDownFragment = (CountDownScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_countdown);
        editor = findViewById(R.id.numInput);
        score = findViewById(R.id.ScoreView);
        scoreWord = findViewById(R.id.ScoreWordView);

        if(gamemode == "Campaign")
            Game = new GameHandler();
        else if(gamemode == "Sixty")
        {
            Game = new GameHandlerTimed(1000, 60000);
            scoreWord.setText("Time Left");
            score.setText(""+Game.getTimeSeconds());
            Game.setOnTickUpdate(new GameHandler.OnTickUpdate() {
                @Override
                public void updateTick() {
                    score.setText(""+Game.getTimeSeconds());
                }

                @Override
                public void finishTimer() {
                    endGameTimed();
                }
            });
        }


        mathFragment.getView().setVisibility(View.INVISIBLE);
        inputFragment.getView().setVisibility(View.INVISIBLE);

        inputFragment.setCustomCallback(new InputButtons.OnClickCallback() {

            public void onClick(View v, int num) {
                if(editor.getText().length() < 4){
                    editor.setText(editor.getText().toString()+num);
                }
            }


            public void onClear(View v) {
                editor.setText("");
            }

            public void onEnter(View v) {
                checkAnswer(v);
            }


        });




        Game.generateProblems(Diff.MEDIUM,10);

        mathFragment.load(Game.getProblem().numberA,Game.getProblem().numberB);


        timer = new CountDownTimer(timeLeft,100) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateStartCounter();
            }

            @Override
            public void onFinish() {
                loadGame();
            }
        }.start();


        updateStartCounter();


    }

    public void updateStartCounter()
    {
        String timeStr = "";
        timeStr += (int) timeLeft/1000;

        countDownFragment.updateCounter(timeStr);
    }

    public void loadGame()
    {
        mathFragment.getView().setVisibility(View.VISIBLE);
        inputFragment.getView().setVisibility(View.VISIBLE);
        countDownFragment.getView().setVisibility(View.INVISIBLE);
        Game.start();
    }

    public void checkAnswer(View n){
        if(editor.getText().toString().equals(""))
            Toast.makeText(this,"Text is empty",Toast.LENGTH_SHORT).show();
        else {
            int input = Integer.parseInt(editor.getText().toString());

            if (Game.checkAnswer(input, score)) {
                Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
                editor.setText("");
                if (Game.finished()) {
                    Toast.makeText(this, "All problems complete!", Toast.LENGTH_SHORT).show();
                    endGame();
                } else {
                    Game.nextProblem();
                    mathFragment.load(Game.getProblem().numberA, Game.getProblem().numberB);
                }
            } else {
                Toast.makeText(this, "Ans is" + answer, Toast.LENGTH_SHORT).show();
            }
        }

    }






    public void goToUserProfile(View v){
        Intent mIntent = new Intent(getApplicationContext(),User_Profile.class);
        startActivity(mIntent);
        //finish();
    }

    public void endGame()
    {
        Intent endIntent = new Intent(this,EndgameScreenActivity.class);
        endIntent.putExtra("ScoreWord","Score");
        endIntent.putExtra("Score",Game.getScore());
        startActivity(endIntent);
        finish();
    }
    public void endGameTimed()
    {
        Intent endIntent = new Intent(this,EndgameScreenActivity.class);
        endIntent.putExtra("ScoreWord","Total Correct");
        endIntent.putExtra("Score",""+Game.getTotalCorrect());
        startActivity(endIntent);
        finish();
    }



    @Override
    public void onBackPressed() {

    }
}
