package efx.com.multio;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AdManager {

    public AdManager(){

    }

    private static InterstitialAd ad;
    private Context ctx;

    AdManager(Context ctx) {
        this.ctx = ctx;
        createAd();
    }

    void createAd() {
        // Create an ad.
        ad = new InterstitialAd(ctx);
        ad.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        final AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
                //.addTestDevice(TEST_DEVICE_ID).build();

        // Load the interstitial ad.
        ad.loadAd(adRequest);
    }

    void loadAd() {
        ad.loadAd(new AdRequest.Builder().build());
    }

    InterstitialAd getAd() {
        return ad;
    }
}
