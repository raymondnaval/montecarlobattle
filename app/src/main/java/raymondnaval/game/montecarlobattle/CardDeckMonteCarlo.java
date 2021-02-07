package raymondnaval.game.montecarlobattle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by raymondnaval on 1/18/20.
 */

public class CardDeckMonteCarlo extends CardDeck {
    /**
     *
     */

    private final String TAG = "CardDeckMonteCarlo";
    private Drawable cardTest;
    private Context mContext;
    private Rect[] cardPositions;
    private ArrayList<Card> deck;
    private boolean clearSelected = false, refreshTableau = true;
    private boolean[] updateCardsSelected;

    public CardDeckMonteCarlo(Context context, Rect[] cardPositions) {
        super(context, false, false, false, 1, -1);
        mContext = context;
        this.cardPositions = cardPositions;
        deck = new ArrayList<>();

        // Initialize all cards as unselected.
        updateCardsSelected = new boolean[25];
        Arrays.fill(updateCardsSelected, false);

        shuffle();
    }

    private void shuffle() {
        HashMap<Integer, Integer> hash = new HashMap<>();
        Random rando = new Random();
        int key = 0;
        while (key < 54) {
            int r = rando.nextInt(66);
            if (r != 10 && r != 11 && r != 12 && r != 23 && r != 24 && r != 25 && r != 26 && r != 39
                    && r != 52 && r != 62 && r != 63 && r != 64) {
                if (!hash.containsValue(r)) {
                    hash.put(key, r);
                    key++;
                }
            }
        }
        for (int i = 0; i < 54; i++) {
//            deck.add(new Card(mContext, hash.get(i)));
//            Log.i("CardDeckMonteCarlo", "shuffle -- value: " + deck.get(i) + " position: " + i);

            // For testing.
            if (i % 13 == 0 && i < 4) {
                deck.add(new Card(mContext, i));
            }
            else if (i % 13 == 1 && i >= 4 && i < 8) {
                deck.add(new Card(mContext, i));
            } else {
                deck.add(new Card(mContext, i));
            }

        }

        for (int i = 0; i < 25; i++) {
            deck.get(i).setCardPosition(cardPositions[i]);
        }
    }

    public void setCardPosition(Rect[] cardPositions) {

    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isLegalMove(int cardTouched, int adjCard) {
        boolean isLegal = false;
        if (deck.get(cardTouched).getCardID() == 65 || deck.get(adjCard).getCardID() == 65) {
            isLegal = true;
        }
        if (deck.get(cardTouched).getCardID() % 13 == deck.get(adjCard).getCardID() % 13) {
            isLegal = true;
        }
        return isLegal;
    }

    public void setUpdateCardsSelected(boolean[] isCardsSelected) {
        updateCardsSelected = isCardsSelected;
        clearSelected = true;
    }

    // TODO: Update tableau.
    public void updateCardPositions() {

        int iter = 0;
        int lastCard = 24;
        int numberOfClearedCards = 0;
        for (int i = 0; i < updateCardsSelected.length; i++) {
            if (updateCardsSelected[i]) {
                numberOfClearedCards++;
            }
            deck.set(i, deck.get(i + numberOfClearedCards));
            deck.get(i).setCardPosition(cardPositions[i]);
        }

        for (int i = 0; i < deck.size(); i++) {
            Log.i("CardDeckMonteCarlo", "updateCardPositions -- value: " + deck.get(i) + " position: " + i);
        }
        Arrays.fill(updateCardsSelected, false);
//        while(lastCard > lastCard-numberOfClearedCards) {
//
//            numberOfClearedCards--;
//        }
    }

    @Override
    public void drawCards(Canvas canvas) {
        for (int i = 0; i < 25; i++) {

            // Draw only the cards that are unselected.
            if (!updateCardsSelected[i]) {
                deck.get(i).draw(canvas);
            }
        }

//        if(refreshTableau) {
//            clearSelected = false;
//        }
//        Log.i(TAG, "drawCards clearSelected: " + clearSelected);
    }
}
