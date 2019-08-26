package efx.com.multio;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class InGameUser extends Fragment {


    public InGameUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_in_game_user, container, false);

        TextView walletDisplay = v.findViewById(R.id.userCurr);
        walletDisplay.setText(Integer.toString(User.player.getPlayerWallet().getWallet()));
        Log.wtf("WHAT", Integer.toString(User.player.getPlayerWallet().getWallet()));

        return v;
    }

}
