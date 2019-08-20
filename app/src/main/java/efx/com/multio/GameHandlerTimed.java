package efx.com.multio;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.ArrayList;

public class GameHandlerTimed extends GameHandler {




    public GameHandlerTimed() {
        length = 10;
        problems = new ArrayList<Problem>(length);
        generateProblems(length);
        ticker = null;
        initTimer(4000,1000);
    }

    public GameHandlerTimed(int cap, long time) {
        length = cap;
        problems = new ArrayList<Problem>(length);
        generateProblems(length);
        ticker = null;
        initTimer(time,1000);
    }

    public GameHandlerTimed(int cap, Diff d) {
        length = cap;
        problems = new ArrayList<Problem>(length);
        generateProblems(d,length);
        ticker = null;
        initTimer(4000,1000);
    }



    public void initTimer(long T, long interval) {
        timeLeft = T;
        timer = new CountDownTimer(timeLeft,100) {
            @Override
            public void onTick(long l)
            {
                timeLeft = l;
                ticker.updateTick();

            }

            @Override
            public void onFinish() {
                ticker.finishTimer();;
            }
        };
    }

    public boolean checkAnswer(int input, TextView v) {
        boolean correct = input == problems.get(index).answer;
        totalCorrect++;
        return correct;
    }

    public void nextProblem(){
        index++;
    }

}


