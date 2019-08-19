package efx.com.multio;


import android.content.Context;
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

    ImageButton [] Buttons;
    Button clearBtn, enterBtn;

    private OnClickCallback callback;

    public interface OnClickCallback{
        void onClick(View v, int num);
        void onClear(View v);
        void onEnter(View v);

    }

    public InputButtons() {
        this.callback = null;
    }
    public void setCustomCallback(OnClickCallback callback){
        this.callback = callback;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Buttons = new ImageButton[10];
        View fragView = inflater.inflate(R.layout.fragment_inputbuttons, container, false);
        int ID = R.id.btn0;
        for(int i = 0; i < 10; i ++)
        {
            final int num = i;
            Buttons[i] = fragView.findViewById(ID+i);
            Buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(v,num);
                }
            });
        }
        clearBtn = fragView.findViewById(R.id.btnClear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClear(v);
            }
        });
        enterBtn = fragView.findViewById(R.id.btnEnter);
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onEnter(v);
            }
        });

        return fragView;
    }


}
