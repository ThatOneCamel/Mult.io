package efx.com.multio;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        gamemode = getIntent().getStringExtra("mode");


        //Assigning references to all Fragments
        mathFragment = (EquationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        inputFragment = (InputButtons) getSupportFragmentManager().findFragmentById(R.id.fragment_buttons);
        countDownFragment = (CountDownScreen) getSupportFragmentManager().findFragmentById(R.id.fragment_countdown);

        //Assigning references to the scoreboard and userInput
        score = findViewById(R.id.ScoreView);
        scoreWord = findViewById(R.id.ScoreWordView);
        editor = findViewById(R.id.numInput);

        setGamemode(gamemode);

        //Hiding elements except for countdown timer
        try {
            mathFragment.getView().setVisibility(View.INVISIBLE);
            inputFragment.getView().setVisibility(View.INVISIBLE);
        } catch (NullPointerException e){
            Log.e("NullPointerException", e.toString());
        }

        //Callback method for InputFragment ImageButtons
        inputFragment.setCustomCallback(new InputButtons.OnClickCallback() {

            //OnClick for Keypad Input Buttons
            public void onClick(View v, int num) {
                if(editor.getText().length() < 4){
                    v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                    editor.setText(editor.getText().toString()+num);

                }
            }


            //OnClick for Clear Button
            public void onClear(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                editor.setText("");
            }

            //OnClick for Enter Button
            public void onEnter(View v) {
                //HapticFeedback performed on correct answer
                checkAnswer(v);
            }


        });

        Game.generateProblems(Diff.MEDIUM,10);
        mathFragment.load(Game.getProblem().numberA, Game.getProblem().numberB);


        //Setting up timer
        beginCountdown();


    }//End OnCreate

    private void setGamemode(String gamemode){

        switch (gamemode){
            case "Campaign":
                Game = new GameHandler();
                break;

            case "Sixty":
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
                break;

            default:
                Game = new GameHandler();
                break;
        }
    }

    private void beginCountdown(){
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
    private void updateStartCounter()
    {
        String timeStr = "";
        timeStr += (int) timeLeft/1000;

        countDownFragment.updateCounter(timeStr);
    }

    //Countdown has finished, game starts
    private void loadGame()
    {
        mathFragment.getView().setVisibility(View.VISIBLE);
        inputFragment.getView().setVisibility(View.VISIBLE);
        countDownFragment.getView().setVisibility(View.INVISIBLE);
        Game.start();
    }

    private void checkAnswer(View v){
        //If no input is found...
        if(editor.getText().toString().equals(""))
            Toast.makeText(this,"Text is empty",Toast.LENGTH_SHORT).show();
        //
        else {
            int input = Integer.parseInt(editor.getText().toString());

            //Compare input to answer
            if (Game.checkAnswer(input, score)) {
                //Correct answer found
                Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                editor.setText("");

                if (Game.finished()) {
                    //All questions answered
                    Toast.makeText(this, "All problems complete!", Toast.LENGTH_SHORT).show();
                    endGame();

                } else {
                    //New problem displayed
                    Game.nextProblem();
                    mathFragment.load(Game.getProblem().numberA, Game.getProblem().numberB);
                }

            //User input was incorrect
            } else {
                Toast.makeText(this, "Ans is" + answer, Toast.LENGTH_SHORT).show();
            }//End If-Else [Nested]

        }//End If-Else

    }


    public void goToUserProfile(View v){
        Intent mIntent = new Intent(getApplicationContext(),User_Profile.class);
        startActivity(mIntent);
        //finish();
    }

    //Display results screen
    private void endGame()
    {
        Intent endIntent = new Intent(this,EndgameScreenActivity.class);
        endIntent.putExtra("ScoreWord","Score");
        endIntent.putExtra("Score",Game.getScore());
        startActivity(endIntent);
        finish();
    }

    //Display results screen for time trial mode
    private void endGameTimed()
    {
        Intent endIntent = new Intent(this,EndgameScreenActivity.class);
        endIntent.putExtra("ScoreWord","Total Correct");
        endIntent.putExtra("Score",""+Game.getTotalCorrect());
        startActivity(endIntent);
        finish();
    }



    //Disabling the system's back button
    @Override
    public void onBackPressed() {

    }
}
