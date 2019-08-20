package efx.com.multio;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountDownScreen extends Fragment {


    TextView timeView;

    public CountDownScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View fragView = inflater.inflate(R.layout.fragment_count_down_screen, container, false);
       timeView = fragView.findViewById(R.id.timeText);


        return fragView;
    }

    public void updateCounter(String str){
        timeView.setText(str);
    }



}
