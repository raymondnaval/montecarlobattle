package raymondnaval.game.montecarlobattle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static android.content.ContentValues.TAG;

/**
 * Created by raymondnaval on 1/18/20.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private Context mContext;
    private DisplayThread thread;
    private int verticalCenter, horizontalCenter, hud, tableauField;

    public GameView(Context context) {
        super(context);
        mContext = context;
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        thread = new DisplayThread(getHolder(), this);
        setFocusable(true);
//        positionCards();
    }

//    private void positionCards() {
//        verticalCenter = GameConstants.SCREEN_HEIGHT / 2;
//        horizontalCenter = GameConstants.SCREEN_WIDTH / 2;
//        hud = GameConstants.SCREEN_HEIGHT / 5;
//        tableauField = GameConstants.SCREEN_HEIGHT * 3 / 5;
////        horzGap = GameConstants.CARD_WIDTH / 5;
////        vertGap = GameConstants.CARD_HEIGHT / 24 * 7;
//
//        // X coordinates for 4- and 6-column tableau.
//        col1X = (horizontalCenter) - (horzGap / 2) - (horzGap * 2) - (GameConstants.CARD_WIDTH * 3);
//        col2X = (horizontalCenter) - (horzGap / 2) - (horzGap) - (GameConstants.CARD_WIDTH * 2);
//        col3X = (horizontalCenter) - (horzGap / 2) - (GameConstants.CARD_WIDTH);
//        col4X = (horizontalCenter) + (horzGap / 2);
//        col5X = (horizontalCenter) + (horzGap / 2) + (horzGap) + (GameConstants.CARD_WIDTH);
//        col6X = (horizontalCenter) + (horzGap / 2) + (horzGap * 2) + (GameConstants.CARD_WIDTH * 2);
//
//        // X coordinates for 5-column tableau.
//        colHardX3 = horizontalCenter - (GameConstants.CARD_WIDTH / 2);
//        colHardX2 = colHardX3 - GameConstants.CARD_WIDTH - horzGap;
//        colHardX1 = colHardX2 - GameConstants.CARD_WIDTH - horzGap;
//        colHardX4 = colHardX3 + GameConstants.CARD_WIDTH + horzGap;
//        colHardX5 = colHardX4 + GameConstants.CARD_WIDTH + horzGap;
//
//        // Y coordinates.
//        col1Y = (verticalCenter) - GameConstants.CARD_HEIGHT - (vertGap * 7);
//        col2Y = (verticalCenter) - GameConstants.CARD_HEIGHT - (vertGap * 6);
//        col3Y = (verticalCenter) - GameConstants.CARD_HEIGHT - (vertGap * 5);
//        col4Y = (verticalCenter) - GameConstants.CARD_HEIGHT - (vertGap * 4);
//        col5Y = (verticalCenter) - GameConstants.CARD_HEIGHT - (vertGap * 3);
//        col6Y = (verticalCenter) - GameConstants.CARD_HEIGHT - (vertGap * 2);
//        col7Y = (verticalCenter) - GameConstants.CARD_HEIGHT - (vertGap);
//        col8Y = (verticalCenter) - GameConstants.CARD_HEIGHT;
//        col9Y = (verticalCenter) - GameConstants.CARD_HEIGHT + (vertGap);
//
//        setTableauPositions();
//
//        // Position the Stack pile.
//        int stackX = col3X;
//        int stackY = verticalCenter + (verticalCenter / 4);
//        stackPos = new Rect(stackX, stackY, stackX + GameConstants.CARD_WIDTH, stackY
//                + GameConstants.CARD_HEIGHT);
//
//        // Booster card. Add 4 random cards to top of Stack pile.
//        if (isBigDeck) {
//            cardsRemainingInStack += 4;
//            cardDeck.addCards(4, stackPos, false);
//        }
//
//        // Booster card. Add 7 random cards to top of Stack pile.
//        if (isBiggerDeck) {
//            cardsRemainingInStack += 7;
//            cardDeck.addCards(7, stackPos, false);
//        }
//
//        // If Peeker Booster is used, position the top stack card. Else position the last card in
//        // the deck (back of the deck card).
//        if (isPeeker) {
//            cardDeck.setCardPosition(cardDeck.getTopStackCard(), stackPos);
////            DebugMethods.debugLog("GameView", "positionCards",
////                    "top stack card: " + cardDeck.getTopStackCard()
////                            + " stackPos: " + stackPos);
//        } else {
//            cardDeck.setCardPosition(cardDeck.getDeckSize() - 1, stackPos);
//        }
//
//        // Position the Foundation pile.
//        foundationX = col4X;
//        foundationY = verticalCenter + (verticalCenter / 4);
//        switch (difficulty) {
//            case 0:
//                cardDeck.addToFoundation(GameConstants.EASY_TABLEAU);
//                cardDeck.setCardPosition(GameConstants.EASY_TABLEAU, new Rect(foundationX, foundationY, foundationX + GameConstants.CARD_WIDTH, foundationY + GameConstants.CARD_HEIGHT));
//
//                colPos1 = cardDeck.getCardPosition(3);
//                colPos2 = cardDeck.getCardPosition(7);
//                colPos3 = cardDeck.getCardPosition(11);
//                colPos4 = cardDeck.getCardPosition(15);
//                colPos5 = cardDeck.getCardPosition(19);
//                colPos6 = cardDeck.getCardPosition(23);
//                break;
//            case 2:
//                cardDeck.addToFoundation(GameConstants.HARD_TABLEAU);
//                cardDeck.setCardPosition(GameConstants.HARD_TABLEAU, new Rect(foundationX, foundationY, foundationX + GameConstants.CARD_WIDTH, foundationY + GameConstants.CARD_HEIGHT));
//
//                colPos1 = cardDeck.getCardPosition(6);
//                colPos2 = cardDeck.getCardPosition(13);
//                colPos3 = cardDeck.getCardPosition(20);
//                colPos4 = cardDeck.getCardPosition(27);
//                colPos5 = cardDeck.getCardPosition(34);
//                break;
//            case 3:
//                cardDeck.addToFoundation(GameConstants.HARDER_TABLEAU);
//                cardDeck.setCardPosition(GameConstants.HARDER_TABLEAU, new Rect(foundationX, foundationY, foundationX + GameConstants.CARD_WIDTH, foundationY + GameConstants.CARD_HEIGHT));
//
//                colPos1 = cardDeck.getCardPosition(8);
//                colPos2 = cardDeck.getCardPosition(17);
//                colPos3 = cardDeck.getCardPosition(26);
//                colPos4 = cardDeck.getCardPosition(35);
//                break;
//            default:
//                cardDeck.addToFoundation(GameConstants.MEDIUM_TABLEAU);
//                cardDeck.setCardPosition(GameConstants.MEDIUM_TABLEAU, new Rect(foundationX, foundationY, foundationX + GameConstants.CARD_WIDTH, foundationY + GameConstants.CARD_HEIGHT));
//
//                // Set touch region for each column.
//                colPos1 = cardDeck.getCardPosition(3);
//                colPos2 = cardDeck.getCardPosition(8);
//                colPos3 = cardDeck.getCardPosition(13);
//                colPos4 = cardDeck.getCardPosition(18);
//                colPos5 = cardDeck.getCardPosition(23);
//                colPos6 = cardDeck.getCardPosition(27);
//                break;
//        }
//
//        // Set touch region for stack pile.
//        // If peeker booster is used, reference the top stack card's position. Else reference the
//        // back of the card.
////        if (isPeeker) {
////            stackPile = cardDeck.getCardPosition(cardDeck.getTopStackCard());
////            DebugMethods.debugLog("GameView", "positionCards",
////                    "top stack card: " + cardDeck.getTopStackCard()
////                            + " stackPile: " + stackPile);
////        } else {
////            stackPile = cardDeck.getCardPosition(cardDeck.getDeckSize() - 1);
////        }
//
//        // Active cards for each column.
//        col1ActiveCard = cardDeck.initialActiveColumnCards()[0];
//        col2ActiveCard = cardDeck.initialActiveColumnCards()[1];
//        col3ActiveCard = cardDeck.initialActiveColumnCards()[2];
//        col4ActiveCard = cardDeck.initialActiveColumnCards()[3];
//        if (difficulty < 3) {
//            col5ActiveCard = cardDeck.initialActiveColumnCards()[4];
//            if (difficulty < 2) {
//                col6ActiveCard = cardDeck.initialActiveColumnCards()[5];
//            }
//        }
//
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
//        // Token counter.
//        tokenTextPaint = new Paint();
//        tokenTextPaint.setColor(mContext.getResources().getColor(R.color.app_orange));
//        tokenTextPaint.setTextSize(48f);
//        tokenTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
//        tokenTextPosX = col6X;
//        tokenTextPosY = vertGap * 2;
//
//        // Combo moving text flair.
//        comboFlairTextPaint = new Paint();
//        comboFlairTextPaint.setColor(mContext.getResources().getColor(R.color.white));
//        comboFlairTextPaint.setTextSize(36f);
//        comboFlairTextX = col4X + horzGap;
//        comboFlairTextY = GameConstants.CARD_HEIGHT;
//
//        // Fail safe paint outline.
//        failSafePaint = new Paint();
//        failSafePaint.setStyle(Paint.Style.STROKE);
//        failSafePaint.setStrokeWidth(2.0f);
//        failSafePaint.setColor(mContext.getResources().getColor(R.color.app_orange));
//
//        // Timer
//        if (!isNoTime) {
//            timerPaint = new Paint();
//            timerPaint.setColor(mContext.getResources().getColor(R.color.white));
//            timerPaint.setTextSize(48f);
//            timerX = col1X;
//            timerY = tokenTextPosY;
//            timePromptPaint = new Paint();
//            timePromptPaint.setColor(mContext.getResources().getColor(R.color.white));
//            timePromptPaint.setTextSize(30f);
//            timePX = timerX;
//            timePY = timerY / 2;
//        }
//
//
//    }

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

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.YELLOW);
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
