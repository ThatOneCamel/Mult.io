package efx.com.multio;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

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

    }


    public GameHandler() {
        length = 10;
        problems = new ArrayList<Problem>(length);

    }

    public GameHandler(int cap) {
        length = cap;
        problems = new ArrayList<Problem>(length);

    }

    private int index = 0;
    private int length;
    private ArrayList<Problem>   problems;
    private int score = 0;

    public void generateProblems(){
        for(int i = 0; i < length; i++){
            problems.add(new Problem());
        }
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

    public void addScore(float time) {
        score+= 15*getMultiplier(time);
    }

    public double getMultiplier(float time) {
        if( time > 2.5)
            return 1.5;
        if( time > 2.0)
            return 1.4;
        if (time > 1.5)
            return 1.3;
        if( time > 1.0)
            return 1.2;
        if (time > 0.5)
            return 1.1;
        return 1.0;
    }
    public int getScore() {
        return score;
    }
}
