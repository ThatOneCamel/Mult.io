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


    private long timeLeft = 3500;

    int answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);



        mathFragment = (EquationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        inputFragment = (InputButtons) getSupportFragmentManager().findFragmentById(R.id.fragment_buttons);
        countDownFragment = (CountDownScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_countdown);
        editor = findViewById(R.id.numInput);
        score = findViewById(R.id.ScoreView);


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


        Game = new GameHandler();
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
        Game.timerStart();
    }

    public void checkAnswer(View n){
        if(editor.getText().toString().equals(""))
            Toast.makeText(this,"Text is empty",Toast.LENGTH_SHORT).show();
        else {
            int input = Integer.parseInt(editor.getText().toString());

            if (Game.checkAnswer(input)) {
                Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
                editor.setText("");
                Game.addScore(Game.timerStop());
                score.setText(Game.getScore());
                if (Game.finished()) {
                    Toast.makeText(this, "All problems complete!", Toast.LENGTH_SHORT).show();
                    endGame();
                } else {
                    Game.nextProblem();

                    mathFragment.load(Game.getProblem().numberA, Game.getProblem().numberB);
                    Game.timerStart();
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
        endIntent.putExtra("Score",Game.getScore());
        startActivity(endIntent);
        finish();
    }
}
