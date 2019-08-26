package efx.com.multio;

import android.app.Application;

public class ApplicationStart extends Application {

    //Both the constructor and onCreate method fire once when app starts
    //Constructor has no context, onCreate does
    public ApplicationStart() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        User.player.loadLocalData(getApplicationContext());

    }
}

