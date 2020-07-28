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
    private Paint topPlayerHUDPaint, bottomPlayerHUDPaint, actionHUDPaint, tableauSpacePaint,
            clearButtonPaint, refreshButtonPaint;
    private Rect clearButton, refreshButton;
    private CardTableauLayout ctLayout;

    public GameView(Context context) {
        super(context);
        mContext = context;
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        thread = new DisplayThread(getHolder(), this);
        setFocusable(true);
        positionCards();
    }

    private void positionCards() {
        verticalCenter = GameConstants.SCREEN_HEIGHT / 2;
        horizontalCenter = GameConstants.SCREEN_WIDTH / 2;
        tableauFieldHeight = GameConstants.SCREEN_HEIGHT * 7 / 12;

        ctLayout = new CardTableauLayout(mContext, GameConstants.PLAYER_HUD_SIZE);

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

        // Refresh button on action HUD.
        refreshButtonPaint = new Paint();
        refreshButtonPaint.setColor(Color.YELLOW);
        refreshButtonPaint.setStyle(Paint.Style.FILL);
//
//        // Combo moving text flair.
//        comboFlairTextPaint = new Paint();
//        comboFlairTextPaint.setColor(mContext.getResources().getColor(R.color.white));
//        comboFlairTextPaint.setTextSize(36f);
//        comboFlairTextX = col4X + horzGap;
//        comboFlairTextY = GameConstants.CARD_HEIGHT;
//
        // Parameters for the Clear button.
        clearButton = new Rect(1, (GameConstants.PLAYER_HUD_SIZE * 2) + tableauFieldHeight + 1,
                GameConstants.SCREEN_WIDTH * 2 / 7, GameConstants.SCREEN_HEIGHT - 1);

        refreshButton = new Rect((GameConstants.SCREEN_WIDTH * 2 / 7) + 1, (GameConstants
                .PLAYER_HUD_SIZE * 2) + tableauFieldHeight + 1, (GameConstants
                .SCREEN_WIDTH * 5 / 7) - 1, GameConstants.SCREEN_HEIGHT - 1);
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

        // Refresh cards button.
        canvas.drawRect(refreshButton, refreshButtonPaint);

//        cards.drawCards(canvas);
        ctLayout.drawSelectedCards(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.i(TAG, "onTouchEvent -- event.getAction: " + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if ((event.getX() >= ctLayout.getPosition(0).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(0).right) &&
                    event.getY() >= ctLayout.getPosition(0).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(0).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(0);
            }
            if ((event.getX() >= ctLayout.getPosition(1).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(1).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(1).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(1).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(1);
            }
            if ((event.getX() >= ctLayout.getPosition(2).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(2).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(2).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(2).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(2);
            }
            if ((event.getX() >= ctLayout.getPosition(3).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(3).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(3).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(3).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(3);
            }
            if ((event.getX() >= ctLayout.getPosition(4).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(4).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(4).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(4).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(4);
            }
            if ((event.getX() >= ctLayout.getPosition(5).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(5).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(5).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(5).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(5);
            }
            if ((event.getX() >= ctLayout.getPosition(6).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(6).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(6).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(6).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(6);
            }
            if ((event.getX() >= ctLayout.getPosition(7).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(7).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(7).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(7).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(7);
            }
            if ((event.getX() >= ctLayout.getPosition(8).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(8).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(8).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(8).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(8);
            }
            if ((event.getX() >= ctLayout.getPosition(9).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(9).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(9).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(9).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(9);
            }
            if ((event.getX() >= ctLayout.getPosition(10).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(10).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(10).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(10).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(10);
            }
            if ((event.getX() >= ctLayout.getPosition(11).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(11).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(11).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(11).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(11);
            }
            if ((event.getX() >= ctLayout.getPosition(12).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(12).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(12).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(12).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(12);
            }
            if ((event.getX() >= ctLayout.getPosition(13).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(13).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(13).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(13).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(13);
            }
            if ((event.getX() >= ctLayout.getPosition(14).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(14).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(14).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(14).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(14);
            }
            if ((event.getX() >= ctLayout.getPosition(15).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(15).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(15).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(15).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(15);
            }
            if ((event.getX() >= ctLayout.getPosition(16).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(16).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(16).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(16).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(16);
            }
            if ((event.getX() >= ctLayout.getPosition(17).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(17).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(17).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(17).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(17);
            }
            if ((event.getX() >= ctLayout.getPosition(18).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(18).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(18).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(18).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(18);
            }
            if ((event.getX() >= ctLayout.getPosition(19).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(19).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(19).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(19).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(19);
            }
            if ((event.getX() >= ctLayout.getPosition(20).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(20).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(20).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(20).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(20);
            }
            if ((event.getX() >= ctLayout.getPosition(21).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(21).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(21).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(21).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(21);
            }
            if ((event.getX() >= ctLayout.getPosition(22).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(22).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(22).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(22).bottom + (ctLayout.getCardHeightGap() / 2)) {
                ctLayout.cardSelected(22);
            }
            if ((event.getX() >= ctLayout.getPosition(23).left - (ctLayout.getCardWidthGap() / 2)
                    && event.getX() <= ctLayout.getPosition(23).right + (ctLayout.getCardWidthGap() / 2)) &&
                    event.getY() >= ctLayout.getPosition(23).top - (ctLayout.getCardHeightGap() / 2)
                    && event.getY() <= ctLayout.getPosition(23).bottom + (ctLayout.getCardHeightGap() / 2)) {
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
                Log.i(TAG, "Clear button");
                ctLayout.clearSelected();
            }

            Log.i(TAG, "onTouchEvent -- event.getX(): " + event.getX() + " getY: " + event.getY());

        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            return false;
        } else {
            return true;
        }


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
