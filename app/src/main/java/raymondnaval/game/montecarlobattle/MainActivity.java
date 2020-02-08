package raymondnaval.game.montecarlobattle;

/**
 * Created by raymondnaval on 1/17/20.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

/**
 * TODO: Divide game screen into top HUD, bottom HUD, middle tableau. Create 5x5 Tableau
 */


public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);

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
