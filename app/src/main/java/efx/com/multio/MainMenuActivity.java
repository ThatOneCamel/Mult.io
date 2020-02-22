package efx.com.multio;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class MainMenuActivity extends AppCompatActivity {

    boolean primaryActive;
    private ConstraintLayout primaryView;
    private LinearLayout gameModeView;
    private Button customView;
    private TextView nameUser;

    public enum Gamemode {
        CAMPAIGN,
        TIMED,
        EXTREME20,
        CUSTOM
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        nameUser = findViewById(R.id.userCurrText);

        primaryView = findViewById(R.id.primaryView);
        gameModeView = findViewById(R.id.singleplayerView);
        customView = findViewById(R.id.btnCustomMode);

        //Game Start
        findViewById(R.id.btnOpenModes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                primaryView.setVisibility(View.GONE);
                gameModeView.setVisibility(View.VISIBLE);
                customView.setVisibility(View.VISIBLE);
                primaryActive = false;

            }
        });

        findViewById(R.id.btnCampaignMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Gamemode.CAMPAIGN, 1);
            }
        });

        findViewById(R.id.btnTimeTrials).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Gamemode.TIMED, 1);
            }
        });

        findViewById(R.id.btnExtremeMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Gamemode.EXTREME20, 3);
            }
        });

        findViewById(R.id.viewProfileBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(getApplicationContext(), User_Profile.class);
                startActivity(profile);

            }
        });

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog();

            }
        });


    }//END onCreate()

    @Override
    public void onBackPressed() {
        if (!primaryActive) {
            gameModeView.setVisibility(View.GONE);
            customView.setVisibility(View.GONE);
            primaryView.setVisibility(View.VISIBLE);
            primaryActive = true;
        }
    }

    private void changeActivity(Gamemode gamemode, int difficulty) {
        Intent gameplay = new Intent(getApplicationContext(), CampaignActivity.class);
        gameplay.putExtra("mode", gamemode);
        gameplay.putExtra("setting", difficulty);
        startActivity(gameplay);
        finish();
    }

    private void changeActivity(Gamemode gamemode, int difficulty, int numOfProblems) {
        Intent gameplay = new Intent(getApplicationContext(), CampaignActivity.class);
        gameplay.putExtra("mode", gamemode);
        gameplay.putExtra("setting", difficulty);
        gameplay.putExtra("problems", numOfProblems);
        startActivity(gameplay);
        finish();
    }

    private void startDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
        builder.setTitle("Choose your difficulty.");

        // add a radio button list
        int length = Gamemode.values().length;
        String[] modes = new String[length];
        for (int i = 0; i < length; i++) {
            modes[i] = Gamemode.values()[i].name();
        }

        builder.setSingleChoiceItems(modes, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                // user checked an item
                if(choice == 1){
                    changeActivity(Gamemode.values()[choice], 2);//Add difficulty choice dialog
                } else {
                    showInputNumDialog(Gamemode.values()[choice]);
                }
                dialog.dismiss();
                //startNumberPicker(Gamemode.values()[choice]);


            }
        });

        // Cancel buttons
        builder.setNegativeButton("Cancel", null);

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startNumberPicker(Gamemode gm) {
        /*typeFragment.getView().setVisibility(View.VISIBLE);
        EditText input = typeFragment.getView().findViewById(R.id.inputNumberView);
        input.setFocusableInTouchMode(true);
        input.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(input, InputMethodManager.SHOW_FORCED);*/

    }

    public void showInputNumDialog(final Gamemode gm) {

        final AlertDialog dialog = new AlertDialog.Builder(this).setCancelable(false).create();
        View customView = getLayoutInflater().inflate(R.layout.display_type_number, null);
        dialog.setView(customView);

        final EditText inputNum = customView.findViewById(R.id.inputNumberView);
        Button confirmation = customView.findViewById(R.id.confirmBtn);

        confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputNum.getText().toString().isEmpty())
                    Toast.makeText(getApplication(), "Number cannot be empty", Toast.LENGTH_SHORT).show();
                else
                    try{
                        changeActivity(gm, 2 ,Integer.parseInt(inputNum.getText().toString()));
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Invalid Number [ Too Large / Negative ]",Toast.LENGTH_SHORT).show();
                    }
            }
        });//End OnClickListener

        dialog.show();

    }

}
