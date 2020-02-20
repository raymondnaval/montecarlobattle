package raymondnaval.game.montecarlobattle;

/**
 * Created by raymondnaval on 1/17/20.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;

/**
 *
 */


public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        getSupportActionBar().hide();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        GameConstants.SCREEN_WIDTH = dm.widthPixels;
        GameConstants.SCREEN_HEIGHT = dm.heightPixels;
        GameConstants.CARD_WIDTH = GameConstants.SCREEN_WIDTH / 11;
        GameConstants.CARD_HEIGHT = GameConstants.CARD_WIDTH + (GameConstants.CARD_WIDTH / 2);
        GameConstants.PLAYER_HUD_SIZE = GameConstants.SCREEN_HEIGHT * 2 / 12;

        gameView = new GameView(this);
        setContentView(gameView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gameView.stopThread();

    }

}
