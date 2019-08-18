package efx.com.multio;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class EquationFragment extends Fragment {

    public TextView viewA, viewB;
    public TextView placeholder;
    int n = 0;


    public EquationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_equation, container, false);

        viewA = fragView.findViewById(R.id.numberA);
        viewB = fragView.findViewById(R.id.numberB);
        placeholder = fragView.findViewById(R.id.numInput);

        return fragView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

    }

    //Move below to Main?
    public ArrayList<Integer> generateProblems(int max){
        ArrayList<Integer> list = new ArrayList<>();
        Random rand = new Random();

        for(int i = 0; i < max; i++){
            list.add(rand.nextInt(10) + 1);
        }

        return list;
    }

    public int nextProblem(ArrayList<Integer> numberList){
        int a = numberList.get(n);
        int b = numberList.get(n+1);
        n+=2;

        viewA.setText(Integer.toString(a));
        viewB.setText(Integer.toString(b));
        placeholder.setText("");

        return a*b;
    }

    public int getIndex(){
        return n;
    }

}
