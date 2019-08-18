package efx.com.multio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EquationFragment mathFragment;
    InputButtons inputFragment;
    ArrayList<GameHandler.Problem> problems;
    TextView editor;
    GameHandler Game;

    int answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mathFragment = (EquationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        inputFragment = (InputButtons) getSupportFragmentManager().findFragmentById(R.id.InputButtons);
        editor = findViewById(R.id.numInput);

        Game = new GameHandler();
        Game.generateProblems();
        mathFragment.load(Game.getProblem().numberA,Game.getProblem().numberB);


        /*
        mathFragment.placeholder.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    Log.i("KEY PRESSED","Enter pressed");
                    checkAnswer(null);
                }
                return false;
            }
        });
        */


//        Log.d("TAGGED", problems.toString());
    }

    public void checkAnswer(View n){
        int input = Integer.parseInt(editor.getText().toString());

        if(Game.checkAnswer(input)) {
            Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
            editor.setText("");
            Game.addScore(0);
            if(Game.finished()) {
                Toast.makeText(this, "All problems complete!", Toast.LENGTH_SHORT).show();
            }
            else{
                Game.nextProblem();
                mathFragment.load(Game.getProblem().numberA, Game.getProblem().numberB);
            }
        }
        else {
            Toast.makeText(this, "Ans is" + answer, Toast.LENGTH_SHORT).show();
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
}
