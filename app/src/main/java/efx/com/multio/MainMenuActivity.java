package efx.com.multio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainMenuActivity extends AppCompatActivity {

    int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        /* GOOGLE SIGN IN*/
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            updateUI(account);

        /* END GOOGLE SIGN IN */

        //Game Start
        findViewById(R.id.gameStartBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameplay = new Intent(getApplicationContext(), CampaignActivity.class);
                startActivity(gameplay);
                finish();
            }
        });

        //
        findViewById(R.id.viewProfileBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(getApplicationContext(), User_Profile.class);
                startActivity(profile);
            }
        });

        findViewById(R.id.btnEG).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent endGame = new Intent(getApplicationContext(), EndgameScreenActivity.class);
                endGame.putExtra("Score","50");
                startActivity(endGame);
                finish();
            }
        });


        findViewById(R.id.btnlogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                if(GoogleSignIn.getLastSignedInAccount(view.getContext()) == null)
                Toast.makeText(view.getContext(),"Signed out", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void signOut(){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
    private void updateUI(GoogleSignInAccount account) {
        if(account != null){ return; }
        else { signIn(); }
    }

}
