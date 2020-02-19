package raymondnaval.game.montecarlobattle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

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

    public CardDeckMonteCarlo(Context context, boolean peekTopOfStack, boolean bigDeck, boolean biggerDeck, int jokers, int difficulty) {
        super(context, peekTopOfStack, bigDeck, biggerDeck, jokers, difficulty);
    }

    public CardDeckMonteCarlo(Context context) {
        super(context, false, false, false, 1, -1);
        mContext = context;

    }

    // Temporary method.

    public void drawCard() {

    }



    @Override
    public void drawCards(Canvas canvas) {
    }
}
