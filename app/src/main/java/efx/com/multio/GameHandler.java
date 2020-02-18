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
                case EXTREME:
                    numberA = rand.nextInt(10) + 7;
                    numberB = rand.nextInt(10) + 8;
                    answer = numberA * numberB;
                    break;

            }

        }

    }




    public GameHandler() {
        length = 10;
        problems = new ArrayList<Problem>(length);
        generateProblems(length);
        initTimer(4000,1000);
    }

    public GameHandler(int cap) {
        length = cap;
        problems = new ArrayList<Problem>(length);
        generateProblems(length);
        initTimer(4000,1000);
    }

    public GameHandler(int cap, Diff d) {
        length = cap;
        problems = new ArrayList<Problem>(length);
        generateProblems(d,length);
        initTimer(4000,1000);
    }

    public int index = 0;
    protected int length;
    protected ArrayList<Problem>   problems;
    private int score = 0;
    protected CountDownTimer timer;
    protected long timeLeft = 4000;
    protected OnTickUpdate ticker;
    protected int totalCorrect = 0;


    public void setOnTickUpdate(OnTickUpdate ticker){
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

    public void timerStart() {
        timeLeft = 4000;
        timer.start();
    }
    public double timerStop() {
        timer.cancel();
        return timeLeft / 1000.0;
    }

    public int getTimeSeconds()
    {
        return (int) timeLeft/1000;
    }

    protected void generateProblems(int cap){
        for(int i = 0; i < cap; i++){
            problems.add(new Problem());
        }
    }
    protected void generateProblems(Diff d, int cap){
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
                Ex = (int) Math.floor(cap * .5);
                M = cap - Ex - H;
                for(int i = 0; i < Ex; i++){
                    problems.add(new Problem(Diff.EXTREME));
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
        Log.i("Extreme",""+Ex);
        Collections.shuffle(problems);
    }

    public void start(){
        timerStart();
    }



    public void nextProblem(){
        index++;
        timerStart();
    }

    public Problem getProblem(){
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
    public String getScore() {
        return Integer.toString(score);
    }

    public int getIntScore() {return score; }

    public int getTotalCorrect(){
        return totalCorrect;
    }
}
