package raymondnaval.game.montecarlobattle;

/**
 * Created by raymondnaval on 1/18/20.
 */
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Responsible for screen painting.
 */
public class DisplayThread extends Thread {

    private final String TAG = "DisplayThread";
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean isRunning;
    public static Canvas canvas;
    private int seconds = 0;

    public DisplayThread(SurfaceHolder shoulder, GameView gv) {
        super();
        surfaceHolder = shoulder;
        gameView = gv;
        isRunning = true;
    }

    @Override
    public void run() {

        long start;
        long timeMillis = 1000 / GameConstants.FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / GameConstants.FPS;

        while (isRunning) {
            start = System.nanoTime();
            canvas = null;

            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.draw(canvas);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
            timeMillis = (System.nanoTime() - start) / 1000000;
            waitTime = targetTime - timeMillis;
            try {
                if (waitTime > 0) {
                    this.sleep(waitTime);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            totalTime += System.nanoTime() - start;
            seconds += timeMillis;
            frameCount++;
            if (frameCount == GameConstants.FPS) {

                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;

//                Log.wtf(TAG, "FPS = " + averageFPS + " sec: " + seconds);
            }
        }

    }

    public int getSeconds() {
        return seconds / 1000;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean state) {
        this.isRunning = state;
    }

}