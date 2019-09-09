package efx.com.multio;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ApplicationStart extends Application {


    private DatabaseReference dataRef;
    private FirebaseAuth mAuth;
    //Both the constructor and onCreate method fire once when app starts
    //Constructor has no context, onCreate does
    public ApplicationStart() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        User.player.loadLocalData(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();
        dataRef = FirebaseDatabase.getInstance().getReference();

    }

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
}

