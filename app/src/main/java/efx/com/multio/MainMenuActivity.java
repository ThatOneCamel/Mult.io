package efx.com.multio;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class MainMenuActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    public GoogleSignInClient mGoogleSignInClient;
    boolean primaryActive;
    private ConstraintLayout primaryView;
    private LinearLayout gameModeView;
    private TextView nameUser;
    private DatabaseReference dataRef;



    //public static User player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
        mAuth = FirebaseAuth.getInstance();

        dataRef = FirebaseDatabase.getInstance().getReference();

        final Button logoutBtn = findViewById(R.id.btnlogout);
        final Button signInBtn = findViewById(R.id.mpButton);
        nameUser = findViewById(R.id.userName);

        primaryView = findViewById(R.id.primaryView);
        gameModeView = findViewById(R.id.singleplayerView);
        //Initializing User class
        //player = new User();
        //TODO CHANGE THIS TO BE USED PROPERLY
        String name;
        if(mAuth.getCurrentUser() == null){

            name = "User";
            logoutBtn.setEnabled(false);
        } else {
            signInBtn.setEnabled(false);
        }

        //loadUser();




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
                changeActivity("Campaign");
            }
        });

        findViewById(R.id.btnTimeTrials).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity("Sixty");
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser() == null){
                    beginLogin();
                    logoutBtn.setEnabled(true);
                    signInBtn.setEnabled(false);
                }
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

        findViewById(R.id.btnUsername).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent endGame = new Intent(getApplicationContext(), EndgameScreenActivity.class);
                endGame.putExtra("Score","50");
                endGame.putExtra("mode", "Campaign");
                startActivity(endGame);
                finish();

                */

               FireUser f = new FireUser(User.player);
               Log.i("FireUser",f.toString());
               String key = dataRef.child(mAuth.getCurrentUser().getUid()).push().getKey();
               Map<String, Object> userVal = f.toMap();
               Map<String,Object> childUpdates = new HashMap<>();
               childUpdates.put(key,userVal);
               dataRef.child(mAuth.getCurrentUser().getUid()).child("fireUser").setValue(f);


            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAuth.getCurrentUser() == null){
                    Toast.makeText(getApplicationContext(), "Not Logged In...", Toast.LENGTH_SHORT).show();
                } else {
                    // Firebase sign out
                    mAuth.signOut();
                    try {
                        // Google sign out
                        mGoogleSignInClient.signOut();
                    } catch (Exception e){
                        Log.e("ERROR SIGN OUT", "message", e);
                    }
                    Toast.makeText(getApplicationContext(), "Signed Out...", Toast.LENGTH_SHORT).show();
                    logoutBtn.setEnabled(false);
                    signInBtn.setEnabled(true);
                }

            }
        });

        findViewById(R.id.saveUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }//END onCreate()

    private void loadUser(){

        dataRef.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                FireUser temp = dataSnapshot.child("fireUser").getValue(FireUser.class);

                User.player =new User(temp);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void beginLogin(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 9001);



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 9001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed
                Log.w("FAILURE", "Google sign in failed", e);
                Toast.makeText(getApplicationContext(), "Sign In Failed...", Toast.LENGTH_SHORT).show();

            }
        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("ACCOUNT INFO", "firebaseAuthWithGoogle:" + acct.getId());


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Google Auth Success...", Toast.LENGTH_SHORT).show();
                            Log.d("SUCCESSFUL", "signInWithCredential:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                        }

                    }
                });
    }


    private void triggerEvent(){

        //Bundle bundle = new Bundle();
        //mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_START, bundle);


        //Games.getEventsClient(this, GoogleSignIn.getLastSignedInAccount(this)).increment("CgkIhqKnwJIIEAIQAQ", 1);

    }

    @Override
    public void onBackPressed() {
        if (!primaryActive){
            gameModeView.setVisibility(View.GONE);
            primaryView.setVisibility(View.VISIBLE);
            primaryActive=true;
        }
    }

    private void changeActivity(String gamemode){
        Intent gameplay = new Intent(getApplicationContext(), CampaignActivity.class);
        gameplay.putExtra("mode", gamemode);
        triggerEvent();
        startActivity(gameplay);
        finish();
    }



}
