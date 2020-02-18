package efx.com.multio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class MainMenuActivity extends AppCompatActivity {

    boolean primaryActive;
    private ConstraintLayout primaryView;
    private LinearLayout gameModeView;
    private Button customView;
    private TextView nameUser;



    //public static User player;


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
                changeActivity("Campaign", 1);
            }
        });

        findViewById(R.id.btnTimeTrials).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity("Sixty", 1);
            }
        });

        findViewById(R.id.btnExtremeMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity("Extreme20", 3);
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
                if(!mode.isEmpty() && difficulty != -1)
                    changeActivity(mode, difficulty);
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

    private void changeActivity(String gamemode, int difficulty){
        Intent gameplay = new Intent(getApplicationContext(), CampaignActivity.class);
        gameplay.putExtra("mode", gamemode);
        gameplay.putExtra("setting", difficulty);
        startActivity(gameplay);
        finish();
    }



}
