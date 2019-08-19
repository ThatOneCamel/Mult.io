package efx.com.multio;

import android.os.CountDownTimer;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
enum Diff{
    EASY,
    MEDIUM,
    HARD
}

public class GameHandler {

    public class Problem {
        public int numberA;
        public int numberB;
        public int answer;


        public Problem(){
            Random rand = new Random();
            numberA = rand.nextInt(12)+1;
            numberB = rand.nextInt(12)+1;
            answer = numberA * numberB;
        }
        public Problem(Diff d){
            Random rand = new Random();
            switch(d){
                case EASY:
                    numberA = rand.nextInt(6)+1;
                    numberB = rand.nextInt(6)+1;
                    answer = numberA * numberB;
                    break;
                case MEDIUM:
                    numberA = rand.nextInt(3)+7;
                    numberB = rand.nextInt(9)+1;
                    answer = numberA * numberB;
                    break;
                case HARD:
                    numberA = rand.nextInt(3)+10;
                    numberB = rand.nextInt(12)+1;
                    answer = numberA * numberB;
                    break;
            }

        }

    }


    public GameHandler() {
        length = 10;
        problems = new ArrayList<Problem>(length);
        timer = new CountDownTimer(timeLeft,100) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
            }

            @Override
            public void onFinish() {

            }
        };
    }

    public GameHandler(int cap) {
        length = cap;
        problems = new ArrayList<Problem>(length);
        timer = new CountDownTimer(timeLeft,100) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
            }

            @Override
            public void onFinish() {

            }
        };
    }

    public int index = 0;
    private int length;
    private ArrayList<Problem>   problems;
    private int score = 0;
    private CountDownTimer timer;
    private long timeLeft = 4000;

    public void generateProblems(){
        for(int i = 0; i < length; i++){
            problems.add(new Problem());
        }
    }
    public void generateProblems(Diff d, int cap){
        int E=0, M=0, H=0;


        switch(d){
            case EASY:
                E = (int) Math.floor(cap * .6);
                M = cap -E;
                for(int i = 0; i < E; i++){
                    problems.add(new Problem(Diff.EASY));
                }
                for(int i = 0; i < M; i++){
                    problems.add(new Problem(Diff.MEDIUM));
                }
                break;
            case MEDIUM:
                E = (int) Math.floor(cap * .3);
                M = (int) Math.floor(cap * .5);
                H = cap - E - M;

                for(int i = 0; i < E; i++){
                    problems.add(new Problem(Diff.EASY));
                }
                for(int i = 0; i < M; i++){
                    problems.add(new Problem(Diff.MEDIUM));
                }
                for(int i = 0; i < H; i++){
                    problems.add(new Problem(Diff.HARD));
                }
                break;
            case HARD:
                M = (int) Math.floor(cap * .4);
                H = (int) Math.floor(cap * .5);
                E = cap - M - H;
                for(int i = 0; i < E; i++){
                    problems.add(new Problem(Diff.EASY));
                }
                for(int i = 0; i < M; i++){
                    problems.add(new Problem(Diff.MEDIUM));
                }
                for(int i = 0; i < H; i++){
                    problems.add(new Problem(Diff.HARD));
                }
                break;
        }
        Log.i("Easy",""+E);
        Log.i("Medium",""+M);
        Log.i("Hard",""+H);
        Collections.shuffle(problems);
    }

    public void timerStart() {
        timeLeft = 4000;
        timer.start();
    }
    public double timerStop() {
        timer.cancel();
        return timeLeft / 1000.0;
    }

    public void nextProblem(){
        index++;
    }

    public Problem getProblem(){
        return problems.get(index);
    }

    public boolean checkAnswer(int input) {
        return input == problems.get(index).answer;
    }

    public boolean finished() {
        return index == length-1;
    }

    public void addScore(double time) {

        score+= 10*getMultiplier(time);
    }

    public double getMultiplier(double time) {
        if( time >= 2.5)
            return 1.5;
        else if( time >= 2.0)
            return 1.4;
        else if (time >= 1.5)
            return 1.3;
        else if( time >= 1.0)
            return 1.2;
        else if (time >= 0.5)
            return 1.1;
        else return 1.0;
    }
    public int getScore() {
        return score;
    }
}
