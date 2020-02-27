package efx.com.multio;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ApplicationStart extends Application {

    //Both the constructor and onCreate method fire once when app starts
    //Constructor has no context, onCreate does
    public ApplicationStart() {


    }

    @Override
    public void onCreate() {
        super.onCreate();
        User.player.loadLocalData(getApplicationContext());
        /*MobileAds.initialize(this, new OnInitializationCompleteListener() {

            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });*/

    }

}

