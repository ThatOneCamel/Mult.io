package efx.com.multio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EquationFragment mathFragment;
    ArrayList<Integer> problems;
    EditText editor;
    int answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mathFragment = (EquationFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
        problems = mathFragment.generateProblems(10);
        editor = findViewById(R.id.editText);

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

        answer = mathFragment.nextProblem(problems);
        Log.d("TAGGED", problems.toString());
    }

    public void checkAnswer(View n){
        if(editor.getText().toString().equals(Integer.toString(answer))){
            Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
            if(mathFragment.getIndex() == problems.size())
                Toast.makeText(this, "All problems complete!", Toast.LENGTH_SHORT).show();
            else
                answer = mathFragment.nextProblem(problems);
        } else {
            Toast.makeText(this, "Ans is" + answer, Toast.LENGTH_SHORT).show();
        }

    }
}
