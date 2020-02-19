package efx.com.multio;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        EXTREME,
        CUSTOM
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        nameUser = findViewById(R.id.userName);

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
                changeActivity(Gamemode.EXTREME, 3);
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

                String mode = "";
                int difficulty = -1;
                startDialog();
                if(!mode.isEmpty() && difficulty != -1)
                    changeActivity(Gamemode.CUSTOM, difficulty);
            }
        });



    }//END onCreate()

    @Override
    public void onBackPressed() {
        if (!primaryActive){
            gameModeView.setVisibility(View.GONE);
            customView.setVisibility(View.GONE);
            primaryView.setVisibility(View.VISIBLE);
            primaryActive=true;
        }
    }

    private void changeActivity(Gamemode gamemode, int difficulty){
        Intent gameplay = new Intent(getApplicationContext(), CampaignActivity.class);
        gameplay.putExtra("mode", gamemode);
        gameplay.putExtra("setting", difficulty);
        startActivity(gameplay);
        finish();
    }

    private void startDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
        builder.setTitle("Choose an animal");

        // add a radio button list
        final String[] animals = {"horse", "cow", "camel", "sheep", "goat"};
        int checkedItem = 1; // cow
        builder.setSingleChoiceItems(animals, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user checked an item

            }
        });

        // add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
                final String chosen = animals[which];
            }
        });
        builder.setNegativeButton("Cancel", null);

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
