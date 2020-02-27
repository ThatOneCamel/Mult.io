package efx.com.multio;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class MainMenuActivity extends AppCompatActivity {

    boolean primaryActive;
    private ConstraintLayout primaryView;
    private ConstraintLayout gameModeView;
    private TextView nameUser;

    public enum Gamemode {
        CAMPAIGN,
        TIMED,
        EXTREME20,
        ELEVENS,
        CUSTOM
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        AdManager adManager = new AdManager(this);
        adManager.createAd();
        nameUser = findViewById(R.id.userCurrText);

        primaryView = findViewById(R.id.primaryView);
        gameModeView = findViewById(R.id.gamemodeView);

        //Game Start
        findViewById(R.id.btnOpenModes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                primaryView.setVisibility(View.GONE);
                gameModeView.setVisibility(View.VISIBLE);
                primaryActive = false;

            }
        });

        findViewById(R.id.btnCampaignMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Gamemode.CAMPAIGN, Diff.MEDIUM, 10);
            }
        });

        findViewById(R.id.btnTimeTrials).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Gamemode.TIMED, Diff.MEDIUM, 1000);
            }
        });

        findViewById(R.id.btnExtremeMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Gamemode.EXTREME20, Diff.EXTREME, 20);
            }
        });

        findViewById(R.id.btnElevens).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Gamemode.ELEVENS, Diff.ELEVENS, 11);
            }
        });

        findViewById(R.id.viewProfileBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(getApplicationContext(), User_Profile.class);
                startActivity(profile);

            }
        });

        findViewById(R.id.btnCustomMode).setOnClickListener(new View.OnClickListener() {
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
            primaryView.setVisibility(View.VISIBLE);
            primaryActive = true;
        }
    }

    /*private void changeActivity(Gamemode gamemode, Diff difficulty) {
        Intent gameplay = new Intent(getApplicationContext(), CampaignActivity.class);
        gameplay.putExtra("mode", gamemode);
        gameplay.putExtra("difficulty", difficulty);
        startActivity(gameplay);
        finish();
    }*/

    //Overloaded
    private void changeActivity(Gamemode gamemode, Diff difficulty, int numOfProblems) {
        Intent gameplay = new Intent(getApplicationContext(), CampaignActivity.class);
        gameplay.putExtra("mode", gamemode);
        gameplay.putExtra("difficulty", difficulty);
        gameplay.putExtra("problems", numOfProblems);
        startActivity(gameplay);
        finish();
    }

    private void startDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
        builder.setTitle("Choose your mode.");

        // add a radio button list
        //int length = Gamemode.values().length;
        String[] modes = new String[2];
        modes[0] = "Normal";
        modes[1] = "Timed";
        /*for (int i = 0; i < length; i++) {
            modes[i] = Gamemode.values()[i].name();
        }*/

        builder.setSingleChoiceItems(modes, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                // user checked an item
                /*if(choice == 2){
                    changeActivity(Gamemode.values()[choice], Diff.EXTREME, 20);//Add difficulty choice dialog
                } else {*/
                startDiffDialog(Gamemode.values()[choice]);

                    //showInputNumDialog(Gamemode.values()[choice]);
                //}
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

    private void startDiffDialog(final Gamemode gamemode){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
        builder.setTitle("Choose your difficulty.");

        // add a radio button list
        int length = Diff.values().length;
        String[] modes = new String[length];
        for (int i = 0; i < length; i++) {
            modes[i] = Diff.values()[i].name();
        }

        builder.setSingleChoiceItems(modes, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                // If Timed, start game
                if(gamemode == Gamemode.TIMED){
                    changeActivity(gamemode, Diff.values()[choice], 1000);//Add difficulty choice dialog
                } else {
                    showInputNumDialog(Gamemode.values()[choice], Diff.values()[choice]);
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

    public void showInputNumDialog(final Gamemode gm, final Diff diff) {

        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View customView = getLayoutInflater().inflate(R.layout.display_type_number, null);
        dialog.setView(customView);
        final EditText inputNum = customView.findViewById(R.id.inputNumberView);
        Button confirmation = customView.findViewById(R.id.confirmBtn);

        confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputNum.getText().toString().isEmpty())
                    Toast.makeText(getApplication(), "Number cannot be empty", Toast.LENGTH_SHORT).show();
                else if (Integer.parseInt(inputNum.getText().toString()) < 1){
                    Toast.makeText(getApplication(), "Number cannot be negative or 0", Toast.LENGTH_SHORT).show();
                }
                else
                    try{
                        dialog.dismiss();
                        changeActivity(gm, diff ,Integer.parseInt(inputNum.getText().toString()));
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Invalid Number [ Too Large / Negative ]",Toast.LENGTH_SHORT).show();
                    }
            }
        });//End OnClickListener

        dialog.show();

    }

}
