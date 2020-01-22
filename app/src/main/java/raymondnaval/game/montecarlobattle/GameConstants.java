package raymondnaval.game.montecarlobattle;

/**
 * Created by raymondnaval on 1/18/20.
 */

public class GameConstants {
    public static final int FPS = 30;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int CARD_WIDTH;
    public static int CARD_HEIGHT; // 1.5:1 ratio height to width.
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;
    public static final int HARDER = 3;
    public static final int EASY_TIME = 480;
    public static final int MEDIUM_TIME = 420;
    public static final int HARD_TIME = 300;
    public static final int HARDER_TIME = 270;
    public static final int EASY_TABLEAU = 24;
    public static final int MEDIUM_TABLEAU = 28;
    public static final int HARD_TABLEAU = 35;
    public static final int HARDER_TABLEAU = 36;
    public static final int STANDARD_DECK = 52;
    public static final String TOKEN_COUNT = "TOKEN_COUNT";
    public static final String NO_TIME = "NO_TIME";
    public static final String FAIL_SAFE = "FAIL_SAFE";
    public static final String PEEKER = "PEEKER";
    public static final String BIG_DECK = "BIG_DECK";
    public static final String BIGGER_DECK = "BIGGER_DECK";
    public static final String JOKER = "JOKER";
    public static final String TWO_JOKERS = "TWO_JOKERS";
    public static int[] BOOSTER_PRICES = new int[]{1, 2, 3, 4, 7, 7, 10};
    public static final String NO_TIME_INTENT_KEY = "noTime";
    public static final String FAIL_SAFE_INTENT_KEY = "failSafe";
    public static final String PEEKER_INTENT_KEY = "peeker";
    public static final String BIG_DECK_INTENT_KEY = "bigDeck";
    public static final String BIGGER_DECK_INTENT_KEY = "biggerDeck";
    public static final String JOKER_INTENT_KEY = "joker";
    public static final String TWO_JOKERS_INTENT_KEY = "twoJokers";
    public static final String DIFFICULTY_INTENT_KEY = "difficulty";
}
