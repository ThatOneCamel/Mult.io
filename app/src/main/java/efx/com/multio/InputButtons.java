package efx.com.multio;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputButtons extends Fragment{

    public InputButtons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_inputbuttons, container, false);
        return fragView;
    }

    public String getNum(View v)
    {
        ImageButton b = (ImageButton) v;
        return b.getContentDescription().toString();
    }

    public void IBaddNum(View v, TextView e) {
        if(e.getText().toString().length() < 4)
            e.setText(e.getText() + getNum(v));
    }
}
