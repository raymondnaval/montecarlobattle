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
    private Rect rect;
    private ArrayList<Card> deck;
    private CardTableauLayout ctl;
    private boolean clearSelected = false, refreshTableau = true;
    private boolean[] updateCardsSelected = new boolean[25];

    public CardDeckMonteCarlo(Context context, CardTableauLayout ctl) {
        super(context, false, false, false, 1, -1);
        mContext = context;
        deck = new ArrayList<>();
        this.ctl = ctl;

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
            deck.get(i).setCardPosition(ctl.getPosition(i));
        }
    }

    public boolean isLegalMove(int cardTouched, int adjCard) {
        boolean isLegal = false;
        if(cardTouched == 65 || adjCard == 65) {
            isLegal = true;
        }
        if(cardTouched % 13 == adjCard % 13) {
            isLegal = true;
        }
        return isLegal;
    }

    public void setClearSelected(boolean clearSelected) {
        this.clearSelected = clearSelected;
    }

    public boolean getClearSelected() {
        return clearSelected;
    }

    @Override
    public void drawCards(Canvas canvas) {
        for(int i=0; i<25; i++) {

            // If player clears selection, updateCardsSelected array will copy which cards have been
            // selected. At the end of the array, set clearSelected back to false.
            if(clearSelected) {
                updateCardsSelected[i] = ctl.isCardsSelected()[i];
                if(i==24) {
                    clearSelected = false;
                }
            }
        }
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
