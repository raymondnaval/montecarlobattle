package raymondnaval.game.montecarlobattle;

import android.content.Context;

/**
 * Created by raymondnaval on 1/18/20.
 */

public class CardDeckMonteCarlo extends CardDeck {
    /**
     * Difficulty: 0 = easy, 1 = medium, 2 = hard.
     *
     * @param context
     * @param peekTopOfStack
     * @param bigDeck
     * @param biggerDeck
     * @param jokers
     * @param difficulty
     */
    public CardDeckMonteCarlo(Context context, boolean peekTopOfStack, boolean bigDeck, boolean biggerDeck, int jokers, int difficulty) {
        super(context, peekTopOfStack, bigDeck, biggerDeck, jokers, difficulty);
    }
}
