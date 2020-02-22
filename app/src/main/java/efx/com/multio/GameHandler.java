package efx.com.multio;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

enum Diff{
    EASY,
    MEDIUM,
    HARD,
    EXTREME
}

public class GameHandler {

    public interface OnTickUpdate{
        void  updateTick();
        void  finishTimer();
    }
    class Problem {
        int numberA;
        int numberB;
        int answer;


        private Problem(){
            Random rand = new Random();
            numberA = rand.nextInt(12)+1;
            numberB = rand.nextInt(12)+1;
            answer = numberA * numberB;
        }
        private Problem(Diff d){
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
                case EXTREME:
                    numberA = rand.nextInt(10) + 7;
                    numberB = rand.nextInt(10) + 8;
                    answer = numberA * numberB;
                    break;

            }

        }

    }


    GameHandler() {
        length = 10;
        problems = new ArrayList<>(length);
        generateProblems(length);
        initTimer(4000,1000);
    }

    GameHandler(int cap) {
        length = cap;
        problems = new ArrayList<>(length);
        generateProblems(length);
        initTimer(4000,1000);
    }

    GameHandler(int cap, Diff d) {
        length = cap;
        problems = new ArrayList<>(length);
        generateProblems(d,length);
        initTimer(4000,1000);
    }

    int index = 0;
    int length;
    ArrayList<Problem> problems;
    private int score = 0;
    CountDownTimer timer;
    long timeLeft = 4000;
    OnTickUpdate ticker;
    int totalCorrect = 0;


    void setOnTickUpdate(OnTickUpdate ticker){
        this.ticker = ticker;
    }

    public void initTimer(long T, long interval) {
        timeLeft = T;
        timer = new CountDownTimer(timeLeft,100) {
            @Override
            public void onTick(long l)
            {
                timeLeft = l;
            }

            @Override
            public void onFinish() {

            }
        };
    }

    private void timerStart() {
        timeLeft = 4000;
        timer.start();
    }
    private double timerStop() {
        timer.cancel();
        return timeLeft / 1000.0;
    }

    int getTimeSeconds()
    {
        return (int) timeLeft/1000;
    }

    void generateProblems(int cap){
        for(int i = 0; i < cap; i++){
            problems.add(new Problem());
        }
    }
    void generateProblems(Diff d, int cap){
        int E=0, M=0, H=0, Ex=0;


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
            case EXTREME:
                H = (int) Math.floor(cap * .4);
                Ex = (int) Math.floor(cap * .6);
                //M = cap - Ex - H;
                for(int i = 0; i < Ex; i++){
                    problems.add(new Problem(Diff.EXTREME));
                }
                /*for(int i = 0; i < M; i++){
                    problems.add(new Problem(Diff.MEDIUM));
                }*/
                for(int i = 0; i < H; i++){
                    problems.add(new Problem(Diff.HARD));
                }
                break;
        }
        Log.i("Easy",""+E);
        Log.i("Medium",""+M);
        Log.i("Hard",""+H);
        Log.i("Extreme",""+Ex);
        Collections.shuffle(problems);
    }

    void start(){
        timerStart();
    }



    public void nextProblem(){
        index++;
        timerStart();
    }

    Problem getProblem(){
        return problems.get(index);
    }

    public boolean checkAnswer(int input, TextView v) {
        boolean correct = input == problems.get(index).answer;
        if(correct){
            timerStop();
            addScore(getTimeSeconds());
            v.setText(getScore());

            Log.i("SCORE UPDATER", getScore());
            totalCorrect++;
        }
        return correct;
    }

    boolean finished() {
        return index == length-1;
    }

    private void addScore(double time) {
        score+= 10*getMultiplier(time);
    }

    private double getMultiplier(double time) {
        if( time >= 2.5)
            return 15.0;
        else if( time >= 2.0)
            return 14.0;
        else if (time >= 1.5)
            return 13.0;
        else if( time >= 1.0)
            return 12.0;
        else if (time >= 0.5)
            return 10.0;
        else return 1.0;
    }
    String getScore() {
        return Integer.toString(score);
    }

    int getIntScore() {return score; }

    int getTotalCorrect(){
        return totalCorrect;
    }
}
