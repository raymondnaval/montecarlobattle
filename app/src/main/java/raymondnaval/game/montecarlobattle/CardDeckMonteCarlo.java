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

    private Drawable cardTest;
    private Context mContext;
    private Rect rect;
    private ArrayList<Card> deck;
    private CardTableauLayout ctl;

    public CardDeckMonteCarlo(Context context) {
        super(context, false, false, false, 1, -1);
        mContext = context;
        deck = new ArrayList<>();
        ctl = new CardTableauLayout(GameConstants.PLAYER_HUD_SIZE);
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

    // TODO: Check if card is legal move.
    public boolean isLegalMove(int cardTouched) {
        boolean isLegal = false;
        switch(getCardIDs(cardTouched)) {
            case 0:
                isLegal = true;
                break;
        }
        return isLegal;
    }

    @Override
    public void drawCards(Canvas canvas) {
        for(int i=0; i<25; i++) {
            deck.get(i).draw(canvas);
        }
    }
}
