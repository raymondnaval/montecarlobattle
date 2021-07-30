package raymondnaval.game.montecarlobattle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static android.content.ContentValues.TAG;

/**
 * Created by raymondnaval on 1/18/20.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private Context mContext;
    private DisplayThread thread;
    private int verticalCenter, horizontalCenter, actionHUDHeight,
            tableauFieldHeight, cardHeightGap, cardWidthGap;
    private Paint topPlayerHUDPaint, bottomPlayerHUDPaint, actionHUDPaint, clearButtonPaint,
            clearButtonTextP, refreshButtonPaint, collapseButtonPaint, endTurnButtonTextP;
    private Rect clearButton, refreshButton, clearText, collapseButton, endTurnText;
    private CardTableauLayout ctLayout;
    private boolean repeatClearButtonPress;
    private final int PAUSE_LENGTH = 25;
    private int pauseTimer;
    private float clearTextY;

    public GameView(Context context) {
        super(context);
        mContext = context;
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        thread = new DisplayThread(getHolder(), this);
        setFocusable(true);
        repeatClearButtonPress = true;
        pauseTimer = PAUSE_LENGTH;
        positionCards();
    }

    private void positionCards() {
        verticalCenter = GameConstants.SCREEN_HEIGHT / 2;
        horizontalCenter = GameConstants.SCREEN_WIDTH / 2;
        tableauFieldHeight = GameConstants.SCREEN_HEIGHT * 7 / 12;

        ctLayout = new CardTableauLayout(mContext, GameConstants.PLAYER_HUD_SIZE);

//        // Token image.
//        int tokenLocationX = col5X + (GameConstants.CARD_WIDTH / 2);
//        token = ContextCompat.getDrawable(mContext, R.drawable.token);
//        tokenLocation = new Rect(tokenLocationX, vertGap, tokenLocationX + GameConstants.CARD_WIDTH / 2, vertGap + GameConstants.CARD_WIDTH / 2);
//
//        // Back button.
//        back = ContextCompat.getDrawable(mContext, R.drawable.back);
//        int backX = horizontalCenter - GameConstants.CARD_HEIGHT * 2 - vertGap / 2;
//        int backY = stackY + GameConstants.CARD_HEIGHT + (vertGap * 2);
//        backLocation = new Rect(backX, backY, backX + GameConstants.CARD_HEIGHT * 2, backY + GameConstants.CARD_HEIGHT);
//
//        // Play Again button.
//        playAgain = ContextCompat.getDrawable(mContext, R.drawable.play_again);
//        int playX = horizontalCenter + vertGap / 2;
//        playAgainLocation = new Rect(playX, backY, playX + GameConstants.CARD_HEIGHT * 2, backY + GameConstants.CARD_HEIGHT);
//
//        // **********
//        // Position text.
//        // **********
//        // Cards remaining text.
//        textPaint = new Paint();
//        textPaint.setColor(Color.BLACK);
//        textPaint.setTextSize(48f);
//        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
//        cardsRemainingX = col2X + (GameConstants.CARD_WIDTH / 2);
//        cardsRemainingY = stackY + GameConstants.CARD_HEIGHT + vertGap;
//
//        // Winner text.
//        winTextPaint = new Paint();
//        winTextPaint.setColor(Color.BLACK);
//        winTextPaint.setTextSize(64f);
//        winTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
//        winTextPosY = verticalCenter + (vertGap * 2);
//
//        // Combo counter.
//        comboTextPaint = new Paint();
//        comboTextPaint.setColor(mContext.getResources().getColor(R.color.app_orange));
//        comboTextPaint.setTextSize(84f);
//        comboTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
//        comboTextPosY = GameConstants.CARD_HEIGHT;
//
        // Top player HUD. bottomPlayerHUDPaint, actionHUDPaint, tableauSpacePaint
        topPlayerHUDPaint = new Paint();
        topPlayerHUDPaint.setColor(Color.RED);
        topPlayerHUDPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // Bottom player HUD.
        bottomPlayerHUDPaint = new Paint();
        bottomPlayerHUDPaint.setColor(Color.GREEN);
        bottomPlayerHUDPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // Action buttons.
        actionHUDPaint = new Paint();
        actionHUDPaint.setColor(Color.BLUE);
        actionHUDPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // Clear button on action HUD.
        clearButtonPaint = new Paint();
        clearButtonPaint.setColor(Color.WHITE);
        clearButtonPaint.setStyle(Paint.Style.FILL);

        // Clear button text.
        clearButtonTextP = new Paint();
        clearButtonTextP.setColor(Color.BLACK);
        clearButtonTextP.setTextSize(42f);
        clearButtonTextP.setTypeface(Typeface.DEFAULT_BOLD);
        clearText = new Rect();

        // Collapse button on action HUD (change locations when functioning.)
        collapseButtonPaint = new Paint();
        collapseButtonPaint.setColor(Color.CYAN);
        collapseButtonPaint.setStyle(Paint.Style.FILL);

        // End turn text.
        endTurnButtonTextP = new Paint();
        endTurnButtonTextP.setColor(Color.BLACK);
        endTurnButtonTextP.setTextSize(42f);
        endTurnButtonTextP.setTypeface(Typeface.DEFAULT_BOLD);
        endTurnText = new Rect();

        // Refresh button on action HUD.
        refreshButtonPaint = new Paint();
        refreshButtonPaint.setColor(Color.YELLOW);
        refreshButtonPaint.setStyle(Paint.Style.FILL);

        // Parameters for the Clear button.
        clearButton = new Rect(1, (GameConstants.PLAYER_HUD_SIZE * 2) + tableauFieldHeight + 1,
                GameConstants.SCREEN_WIDTH * 2 / 7, GameConstants.SCREEN_HEIGHT - 1);

        refreshButton = new Rect((GameConstants.SCREEN_WIDTH * 2 / 7) + 1, (GameConstants
                .PLAYER_HUD_SIZE * 2) + tableauFieldHeight + 1, (GameConstants
                .SCREEN_WIDTH * 5 / 7) - 1, GameConstants.SCREEN_HEIGHT - 1);

        collapseButton = new Rect(GameConstants
                .SCREEN_WIDTH * 5 / 7, (GameConstants.PLAYER_HUD_SIZE * 2) + tableauFieldHeight + 1,
                GameConstants.SCREEN_WIDTH - 1, GameConstants.SCREEN_HEIGHT - 1);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.GRAY);
        // Top HUD.
        canvas.drawRect(0, 0, GameConstants.SCREEN_WIDTH, GameConstants.PLAYER_HUD_SIZE,
                topPlayerHUDPaint);

        // Bottom HUD.
        canvas.drawRect(0, GameConstants.PLAYER_HUD_SIZE + tableauFieldHeight,
                GameConstants.SCREEN_WIDTH, (GameConstants.PLAYER_HUD_SIZE * 2) + tableauFieldHeight,
                bottomPlayerHUDPaint);

        // Action HUD.
        canvas.drawRect(0, (GameConstants.PLAYER_HUD_SIZE * 2) + tableauFieldHeight,
                GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT,
                actionHUDPaint);

        // Clear cards button.
        canvas.drawRect(clearButton, clearButtonPaint);

        // Condense cards button.
        canvas.drawRect(collapseButton, collapseButtonPaint);

        // Refresh cards button.
        canvas.drawRect(refreshButton, refreshButtonPaint);

        // Clear cards text.
        // TODO: Center Clear text in button.
        canvas.getClipBounds(clearText);
        clearButtonTextP.getTextBounds("Clear", 0, 5, clearText);
        clearTextY = GameConstants.SCREEN_HEIGHT - ((GameConstants.SCREEN_HEIGHT) - ((GameConstants
                .PLAYER_HUD_SIZE * 2) + tableauFieldHeight + clearText.height() + 1));
        if (repeatClearButtonPress) {
            canvas.drawText("Clear", 2, clearTextY, clearButtonTextP);
        } else {
            canvas.drawText("Wait...", 2, clearTextY, clearButtonTextP);
        }

        // Refresh tableau text.
        endTurnButtonTextP.getTextBounds("End Turn", 0, 8, endTurnText);
        canvas.drawText("End Turn", GameConstants.SCREEN_WIDTH * 5 / 7, clearTextY,
                endTurnButtonTextP);

        ctLayout.drawSelectedCards(canvas);

        pauseTimer++;
        repeatClearButtonPress = pauseTimer >= PAUSE_LENGTH;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (repeatClearButtonPress) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if ((event.getX() >= ctLayout.getPosition(0).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(0).right) &&
                        event.getY() >= ctLayout.getPosition(0).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(0).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(0);
                }
                if ((event.getX() >= ctLayout.getPosition(1).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(1).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(1).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(1).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(1);
                }
                if ((event.getX() >= ctLayout.getPosition(2).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(2).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(2).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(2).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(2);
                }
                if ((event.getX() >= ctLayout.getPosition(3).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(3).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(3).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(3).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(3);
                }
                if ((event.getX() >= ctLayout.getPosition(4).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(4).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(4).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(4).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(4);
                }
                if ((event.getX() >= ctLayout.getPosition(5).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(5).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(5).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(5).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(5);
                }
                if ((event.getX() >= ctLayout.getPosition(6).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(6).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(6).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(6).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(6);
                }
                if ((event.getX() >= ctLayout.getPosition(7).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(7).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(7).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(7).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(7);
                }
                if ((event.getX() >= ctLayout.getPosition(8).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(8).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(8).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(8).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(8);
                }
                if ((event.getX() >= ctLayout.getPosition(9).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(9).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(9).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(9).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(9);
                }
                if ((event.getX() >= ctLayout.getPosition(10).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(10).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(10).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(10).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(10);
                }
                if ((event.getX() >= ctLayout.getPosition(11).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(11).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(11).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(11).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(11);
                }
                if ((event.getX() >= ctLayout.getPosition(12).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(12).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(12).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(12).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(12);
                }
                if ((event.getX() >= ctLayout.getPosition(13).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(13).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(13).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(13).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(13);
                }
                if ((event.getX() >= ctLayout.getPosition(14).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(14).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(14).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(14).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(14);
                }
                if ((event.getX() >= ctLayout.getPosition(15).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(15).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(15).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(15).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(15);
                }
                if ((event.getX() >= ctLayout.getPosition(16).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(16).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(16).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(16).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(16);
                }
                if ((event.getX() >= ctLayout.getPosition(17).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(17).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(17).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(17).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(17);
                }
                if ((event.getX() >= ctLayout.getPosition(18).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(18).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(18).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(18).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(18);
                }
                if ((event.getX() >= ctLayout.getPosition(19).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(19).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(19).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(19).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(19);
                }
                if ((event.getX() >= ctLayout.getPosition(20).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(20).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(20).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(20).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(20);
                }
                if ((event.getX() >= ctLayout.getPosition(21).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(21).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(21).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(21).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(21);
                }
                if ((event.getX() >= ctLayout.getPosition(22).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(22).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(22).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(22).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(22);
                }
                if ((event.getX() >= ctLayout.getPosition(23).left - (ctLayout.getCardWidthGap() / 2f)
                        && event.getX() <= ctLayout.getPosition(23).right + (ctLayout.getCardWidthGap() / 2f)) &&
                        event.getY() >= ctLayout.getPosition(23).top - (ctLayout.getCardHeightGap() / 2f)
                        && event.getY() <= ctLayout.getPosition(23).bottom + (ctLayout.getCardHeightGap() / 2f)) {
                    ctLayout.cardSelected(23);
                }
                if ((event.getX() >= ctLayout.getPosition(24).left - ((float) ctLayout.getCardWidthGap() / 2)
                        && event.getX() <= ctLayout.getPosition(24).right + ((float) ctLayout.getCardWidthGap() / 2)) &&
                        event.getY() >= ctLayout.getPosition(24).top - ((float) ctLayout.getCardHeightGap() / 2)
                        && event.getY() <= ctLayout.getPosition(24).bottom + ((float) ctLayout.getCardHeightGap() / 2)) {
                    ctLayout.cardSelected(24);
                }

                // Clear cards button.
                if ((event.getX() >= clearButton.left && event.getX() <= clearButton.right)
                        && event.getY() >= clearButton.top && event.getY() <= clearButton.bottom) {

                    pauseTimer = 0;
                    ctLayout.clearSelected();
                }

                // End turn button.
                if ((event.getX() >= collapseButton.left && event.getX() <= collapseButton.right)
                        && event.getY() >= collapseButton.top && event.getY() <= collapseButton.bottom) {
                    ctLayout.refreshTableau();
                }
                return true;
            }
        }
        return false;
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread = new DisplayThread(getHolder(), this);
        thread.setIsRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setIsRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void stopThread() {
        try {
            thread.setIsRunning(false);
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("TAG", "stopThread: " + thread.isRunning());
    }
}
