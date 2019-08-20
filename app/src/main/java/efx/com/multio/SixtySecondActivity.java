package efx.com.multio;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SixtySecondActivity extends AppCompatActivity {

    GameHandler Game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixty_second);

        Game = new GameHandler(1000);

    }
}
