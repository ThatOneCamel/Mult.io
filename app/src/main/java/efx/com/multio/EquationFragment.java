package efx.com.multio;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class EquationFragment extends Fragment {

    public TextView viewA, viewB;


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

        ArrayList<Integer> numberList = generateProblems(50);
        viewA.setText(Integer.toString(numberList.get(0)));
        viewB.setText(Integer.toString(numberList.get(1))); //Design will support up to 4 digits
        return fragView;

    }

    //Move below to Main?
    public ArrayList<Integer> generateProblems(int max){
        ArrayList<Integer> list = new ArrayList<Integer>();
        Random rand = new Random();

        for(int i = 0; i < max; i++){
            list.add(rand.nextInt(100) + 1);
        }

        return list;
    }

}
