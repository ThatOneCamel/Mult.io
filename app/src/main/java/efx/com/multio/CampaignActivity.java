package efx.com.multio;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CampaignActivity extends AppCompatActivity {

    EquationFragment mathFragment;
    InputButtons inputFragment;
    CountDownScreen countDownFragment;
    ArrayList<GameHandler.Problem> problems;
    TextView editor;
    GameHandler Game;
    ImageButton backButton;
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
        backButton = findViewById(R.id.backButton);
        score = findViewById(R.id.ScoreView);

        mathFragment.getView().setVisibility(View.INVISIBLE);
        inputFragment.getView().setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.INVISIBLE);

        Game = new GameHandler();
        Game.generateProblems();
        mathFragment.load(Game.getProblem().numberA,Game.getProblem().numberB);
/*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },1);
*/


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
        backButton.setVisibility(View.VISIBLE);
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
                score.setText(Integer.toString(Game.getScore()));
                if (Game.finished()) {
                    Toast.makeText(this, "All problems complete!", Toast.LENGTH_SHORT).show();
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

    public void erase(View v)
    {
        String str = editor.getText().toString();
        if(str.length() > 0)
            editor.setText(str.substring(0,str.length()-1));
    }

    public void IBaddNum(View v) {
        inputFragment.IBaddNum(v,editor);
    }

    public void goToUserProfile(View v){
        Intent mIntent = new Intent(getApplicationContext(),User_Profile.class);
        startActivity(mIntent);
        //finish();
    }
}
