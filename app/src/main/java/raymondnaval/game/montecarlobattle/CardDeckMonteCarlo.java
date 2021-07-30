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
    private int player2FirstTurnBonus = 12;

    public CardDeckMonteCarlo(Context context, Rect[] cardPositions) {
        super(context, false, false, false, 1, -1);
        mContext = context;
        this.cardPositions = cardPositions;
        deck = new ArrayList<>();

        // Initialize all cards as unselected.
        updateCardsSelected = new boolean[25];
        noCardsSelected();

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

            // For testing.
            if (i % 13 == 0) {
                int j = i;
                while (j < 54) {
                    deck.add(new Card(mContext, j));
                    j += 13;
                    i++;
                }
            } else if (i % 13 == 1) {
                int k = i;
                while (k < 54) {
                    deck.add(new Card(mContext, k));
                    k += 13;
                    i++;
                }
            } else {
                deck.add(new Card(mContext, hash.get(i)));
            }
        }

        for (int i = 0; i < 25; i++) {
            deck.get(i).setCardPosition(cardPositions[i]);
        }
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

    public void noCardsSelected() {
        Arrays.fill(updateCardsSelected, false);
    }

    // TODO: Figure out how to reset numberOfClearedCards. It's being added exponentially.
    public void updateCardPositions() {

        int numberOfClearedCards = 0;

        // Cards that were cleared from the tableau are changed to a value of -1 in the deck. Keep
        // track of the number of cards that are set to -1.
        for (int i = 0; i < updateCardsSelected.length; i++) {
            if (updateCardsSelected[i]) {
                numberOfClearedCards++;
                deck.set(i, new Card(mContext, -1));
            }
        }

        // Update the positions of the cards in the tableau. Keep track of the number of cards that
        // are still on the tableau.
        for (int i = 0; i < updateCardsSelected.length; i++) {
            deck.get(i).setCardPosition(cardPositions[i]);
            if (i >= updateCardsSelected.length - numberOfClearedCards) {
                updateCardsSelected[i] = true;
            }
        }

        // Remove cards from the deck that are labeled with -1.
        for (int i = 0; i < deck.size(); i++) {
            while (deck.get(i).getCardID() == -1) {
                deck.remove(i);
            }
//            Log.i("CardDeckMonteCarlo", "updateCardPositions -- value: " + deck.get(i) + " position: " + i);
        }
        Log.i("CardDeckMonteCarlo", "updateCardPositions -- numberOfClearedCards: " + numberOfClearedCards);
        Log.i("CardDeckMonteCarlo", "updateCardPositions -- deck size: " + deck.size());

        for (int i = 0; i < 25; i++) {
            deck.get(i).setCardPosition(cardPositions[i]);

            // The cards cleared won't be drawn.
            if(i < updateCardsSelected.length - numberOfClearedCards) {
                updateCardsSelected[i] = false;
            }
        }
    }

    @Override
    public void drawCards(Canvas canvas) {
        for (int i = 0; i < 25; i++) {

            // Draw only the cards that are unselected.
            if (!updateCardsSelected[i]) {
                deck.get(i).draw(canvas);
            }
        }
    }
}
