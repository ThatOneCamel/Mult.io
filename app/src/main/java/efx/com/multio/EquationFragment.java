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

    public void load(int a, int b)
    {
        if(b > a) {
            viewA.setText(Integer.toString(b));
            viewB.setText(Integer.toString(a));
        }
        else {
            viewA.setText(Integer.toString(a));
            viewB.setText(Integer.toString(b));
        }
    }




}
