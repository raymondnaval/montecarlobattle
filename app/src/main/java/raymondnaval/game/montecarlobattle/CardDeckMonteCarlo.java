package raymondnaval.game.montecarlobattle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
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
    private boolean[] updateCardsSelected = new boolean[25];

    public CardDeckMonteCarlo(Context context, Rect[] cardPositions) {
        super(context, false, false, false, 1, -1);
        mContext = context;
        this.cardPositions = cardPositions;
        deck = new ArrayList<>();

        // Initialize all cards as unselected.
        updateCardsSelected = new boolean[25];
        for (int i = 0; i < updateCardsSelected.length; i++) {
            updateCardsSelected[i] = false;
        }

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
        for(int i=0; i<54; i++) {
            deck.add(new Card(mContext, hash.get(i)));
            Log.i("CardDeckMonteCarlo", "shuffle -- value: " + hash.get(i) + " position: " + i);
        }

        for(int i=0; i<25; i++) {
            deck.get(i).setCardPosition(cardPositions[i]);
        }
    }

    public void setCardPosition(Rect[] cardPositions) {

    }

    public boolean isLegalMove(int cardTouched, int adjCard) {
        boolean isLegal = false;
        if(deck.get(cardTouched).getCardID() == 65 || deck.get(adjCard).getCardID() == 65) {
            isLegal = true;
        }
        if(deck.get(cardTouched).getCardID() % 13 == deck.get(adjCard).getCardID() % 13) {
            isLegal = true;
        }
        return isLegal;
    }

    public void setUpdateCardsSelected(boolean[] isCardsSelected) {
        updateCardsSelected = isCardsSelected;
        clearSelected = true;
    }

    @Override
    public void drawCards(Canvas canvas) {
        for(int i=0; i<25; i++) {

            // Draw only the cards that are unselected.
            if(!updateCardsSelected[i]) {
                deck.get(i).draw(canvas);
            }
        }

//        if(refreshTableau) {
//            clearSelected = false;
//        }
//        Log.i(TAG, "drawCards clearSelected: " + clearSelected);
    }
}
