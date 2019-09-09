package efx.com.multio;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Profile extends AppCompatActivity {

    TextView m_username;
    TextView m_title;
    TextView m_wins;
    TextView m_time;
    TextView m_score;
    TextView m_tokens;
    TextView m_won;

    private DatabaseReference dbReference;
    private FirebaseAuth fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile);

        FirebaseDatabase fbDB = FirebaseDatabase.getInstance();
        dbReference = fbDB.getReference();
        fireAuth = FirebaseAuth.getInstance();

        m_username = findViewById(R.id.txtUsername);
        m_title = findViewById(R.id.txtTitle);
        m_wins = findViewById(R.id.txtWins);
        m_time = findViewById(R.id.txtTime);
        m_score = findViewById(R.id.txtScore);
        m_tokens = findViewById(R.id.txtTokens);
        m_won = findViewById(R.id.txtWins);

        loadUser();


/*
        dbReference.child(fireAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                m_username.setText(dataSnapshot.child("Username").getValue().toString());
                m_title.setText(dataSnapshot.child("Title").getValue().toString());
                m_wins.setText(dataSnapshot.child("Games Won").getValue().toString());
                m_score.setText(dataSnapshot.child("Best Score").getValue().toString());
                m_time.setText(dataSnapshot.child("Best Time").getValue().toString());
                m_tokens.setText(dataSnapshot.child("Coins").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

*/
        findViewById(R.id.btnSignOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void loadUser()
    {
        m_username.setText(User.player.getUsername());
        m_title.setText(User.player.getTitle());
        m_score.setText(""+User.player.getB_Score());
        m_time.setText(""+User.player.getB_Time()+" seconds");
        m_tokens.setText(""+User.player.getPlayerWallet().getWallet());
        m_won.setText(User.player.getG_Won()+"");

        Log.i("User", User.player.toString());
    }
}