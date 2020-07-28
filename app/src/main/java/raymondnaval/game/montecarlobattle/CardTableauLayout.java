package raymondnaval.game.montecarlobattle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class will be used to track the position of each card in the 5x5 tableau.
 * It will count linearly from the top left to the bottom right, 0 to 24. For each position, this
 * class will track the valid positions left, right above and below.
 * TODO: Remove selected cards and update tableau.
 */
public class CardTableauLayout {

    private final String TAG = "CardTableauLayout";
    private int topTableauBorder;
    private int[] leftPositions, topPositions, rightPositions, bottomPositions;
    private Rect[] cardCoordinates;
    private boolean[] cardsSelected, clearedCards;
    private CardDeckMonteCarlo cards;
    private MatchedClusterOfCards cardsSelectedForMatching;
    private int cardWidthGap, cardHeightGap, numSelected = 0;
    private Context mContext;

    // The first-most and last-most cards selected in tableau.
    private int firstMost = -1, lastMost = 25;

    private Paint outlineP;

    public CardTableauLayout(Context context, int topTableauBorder) {
        mContext = context;
        this.topTableauBorder = topTableauBorder;
        cardWidthGap = (GameConstants.SCREEN_WIDTH - (GameConstants.CARD_WIDTH * 5)) / 6;
        cardHeightGap = ((GameConstants.SCREEN_HEIGHT * 7 / 12) - (GameConstants.CARD_HEIGHT * 5)) / 6;
        outlineP = new Paint();
        outlineP.setColor(Color.YELLOW);
        outlineP.setStyle(Paint.Style.STROKE);
        cardCoordinates = new Rect[25];
        setPositions();
        cards = new CardDeckMonteCarlo(mContext, cardCoordinates);
        cardsSelectedForMatching = new MatchedClusterOfCards(cards);
    }

    public void cardSelected(int position) {

        if (cardsSelected[position]) {
            cardsSelected[position] = false;
            numSelected--;
            if (numSelected > 1) {

                // If un-selecting the first-most card, loop until the next positive card.
                if (position == firstMost) {
                    for (int i = position + 1; i < lastMost; i++) {
                        if (cardsSelected[i]) {
                            firstMost = i;
                            break;
                        }
                    }
                }

                // If un-selecting the last-most card, loop back until the next positive card.
                if (position == lastMost) {
                    for (int i = position - 1; i > firstMost; i--) {
                        if (cardsSelected[i]) {
                            lastMost = i;
                            break;
                        }
                    }
                }
            } else if (numSelected == 1) {

                // If number of cards selected is one, set first-most and last-most card to that
                // card.
                for (int i = firstMost; i <= lastMost; i++) {
                    if (cardsSelected[i]) {
                        firstMost = i;
                        lastMost = i;
                    }
                }
            } else {
                resetFirstAndLast();
            }

        } else {
            cardsSelected[position] = true;
            numSelected++;

            if (numSelected == 1) {
                firstMost = position;
                lastMost = position;
            } else {
                if (position < firstMost) {
                    firstMost = position;
                }
                if (position > lastMost) {
                    lastMost = position;
                }
            }

        }
        Log.i("CardTableauLayout", "cardsSelected: " + cardsSelected[position]
                + " last most: " + lastMost + " first most: " + firstMost + " num selected: " +
                numSelected + " card position: " + position);
    }

    /**
     * Clear selected cards from tableau.
     */
    public void clearSelected() {
        Log.i(TAG, "clearSelected -- firstMost: " + firstMost + " lastMost: " + lastMost);

        // Check if selection is valid.
        boolean isValid = true;
        boolean isValidForClusterCheck = false;
        boolean isValidLinearSelection = true;
        boolean horSelection = false;
        boolean verSelection = false;
        boolean[] cardsChecked = new boolean[25];

        // If at least 2 cards are selected, check if they're valid selections.
        if (atLeast2CardsSelected()) {

            // Loop from the first selected card to the last selected card.
//            int i = firstMost;
//            while (i < lastMost) {
//
//                // pos is used to determine if the iteration needs to jump ahead.
//                int pos = i;
//
//                // If card is selected.
//                if (cardsSelected[i]) {
//
//                    // If the card to the right is at or before the last card selected and is
//                    // selected, increment pos to the position to the right.
//                    if (i + 1 <= lastMost && !verSelection) {
//                        if (cardsSelected[i + 1]) {
//                            pos = i + 1;
//                            Log.i(TAG, "clearSelected -- i: " + i + " i+1: " + pos);
//                            horSelection = true;
//                        }
//                    }
//
//                    // If the card below is at or before the last card selected and is selected,
//                    // increment pos to the position below.
//                    if (i + 5 <= lastMost && !horSelection) {
//                        if (cardsSelected[i + 5]) {
//                            boolean unselectedBetweenThe5 = true;
//                            for (int j = i + 1; j < i + 5; j++) {
//                                Log.i(TAG, "clearSelected -- selected[j]: " + cardsSelected[j] + " j: " + j);
//                                if (cardsSelected[j]) {
//                                    unselectedBetweenThe5 = false;
//                                    break;
//                                }
//                            }
//                            if (unselectedBetweenThe5) {
//                                pos = i + 5;
//                            }
//
//                            Log.i(TAG, "clearSelected -- i: " + i + " i+5: " + pos);
//                            verSelection = true;
//                        }
//                    }
//
//                    // If the pos doesn't shift to the right or below, the selection isn't valid.
//                    // Else move the iterator to the new pos position.
//                    if (pos == i) {
//                        isValidLinearSelection = false;
//                    } else {
//                        i = pos;
//                    }
//                } else {
//                    i++;
//                }
//
//                if (!isValidLinearSelection) {
//                    // break;
//                }
//
//                if (isValidLinearSelection) {
//                    if (horSelection) {
//                        for (int j = firstMost; j < lastMost; j++) {
//                            if (!cards.isLegalMove(j, j + 1)) {
//                                isValid = false;
//                                break;
//                            }
//                        }
//                    } else {
//                        for (int j = firstMost; j < lastMost; j += 5) {
//                            if (!cards.isLegalMove(j, j + 5)) {
//                                isValid = false;
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
            int i = firstMost;
            while (i < lastMost) {
                if (i % 5 != 4) {
                    if (cards.isLegalMove(i, i + 1)) {
                        isValidForClusterCheck = true;
                    }
                }
                if (i < 20) {
                    if (cards.isLegalMove(i, i + 5)) {
                        isValidForClusterCheck = true;
                    }
                }
                if (isValidForClusterCheck) {
                    // Do cluster check.
                    i++;
                } else {
                    isValid = false;
                    break;
                }
            }
        }

        if (isValid) {
            for (int i = 0; i < cardsSelected.length; i++) {
                if (cardsSelected[i]) {
                    clearedCards[i] = true;
                }
            }
            cards.setUpdateCardsSelected(clearedCards);
            resetFirstAndLast();
        } else {
            clearSelection();
        }
    }

    // TODO: Track touched cards. If touched in a linear line, they are matched cluster. If the 2nd
    //  touched card isn't lined up with the first card, it becomes the first card (original first
    //  card is deselected). A new cluster will have a different colored outline. Figure out how to
    //  track when a new cluster has started. Write it out first!
    private class MatchedClusterOfCards {
        private ArrayList<Integer> firstSetOfMatches, secondSetOfMatches;
        private int numCardsSelected;
        private CardDeckMonteCarlo cdmc;

        public MatchedClusterOfCards(CardDeckMonteCarlo cdmc) {
            this.cdmc = cdmc;
            firstSetOfMatches = new ArrayList<>();
            numCardsSelected = 0;
        }

        public void add(int position) {
            numCardsSelected = numSelected;
            if (numCardsSelected == 1) {

            }
        }
    }


    // If no cards are selected, reset to default values and clear all selected cards.
    private void resetFirstAndLast() {
        firstMost = -1;
        lastMost = 25;
        numSelected = 0;
        clearSelection();
    }

    public int getFirstMostCard() {
        return firstMost;
    }

    public int getLastMostCard() {
        return lastMost;
    }

    public boolean atLeast2CardsSelected() {
        boolean twoSelected = false;
        if (numSelected > 1) {
            twoSelected = true;
        }
        return twoSelected;
    }

    public boolean[] isCardsSelected() {
        return cardsSelected;
    }

    // Clear all selected cards.
    private void clearSelection() {
        for (int i = 0; i < cardsSelected.length; i++) {
            cardsSelected[i] = false;
            Log.i(TAG, "clearSelection -- i: " + i + " cardsSelected[i]: " + cardsSelected[i]);
        }
        numSelected = 0;
    }

    public void drawSelectedCards(Canvas canvas) {
        cards.drawCards(canvas);
        for (int i = 0; i < cardsSelected.length; i++) {

            // If card is selected but not cleared yet, draw the selection outline.
            if (cardsSelected[i] && !clearedCards[i]) {
                canvas.drawRect(cardCoordinates[i].left - (cardWidthGap / 4),
                        cardCoordinates[i].top - (cardHeightGap / 4),
                        cardCoordinates[i].right + (cardWidthGap / 4),
                        cardCoordinates[i].bottom + (cardHeightGap / 4),
                        outlineP);
            }
        }
    }

    // Return width and height coordinates.
    public Rect getPosition(int position) {
        return cardCoordinates[position];
    }

    private void setPositions() {
        cardCoordinates[0] = new Rect(
                cardWidthGap,
                cardHeightGap + topTableauBorder,
                cardWidthGap + GameConstants.CARD_WIDTH,
                cardHeightGap + topTableauBorder + GameConstants.CARD_HEIGHT);
        cardCoordinates[1] = new Rect(
                cardCoordinates[0].left + GameConstants.CARD_WIDTH + cardWidthGap,
                cardCoordinates[0].top,
                cardCoordinates[0].right + GameConstants.CARD_WIDTH + cardWidthGap,
                cardCoordinates[0].bottom);
        cardCoordinates[2] = new Rect(
                cardCoordinates[1].left + GameConstants.CARD_WIDTH + cardWidthGap,
                cardCoordinates[0].top,
                cardCoordinates[1].right + GameConstants.CARD_WIDTH + cardWidthGap,
                cardCoordinates[0].bottom);
        cardCoordinates[3] = new Rect(
                cardCoordinates[2].left + GameConstants.CARD_WIDTH + cardWidthGap,
                cardCoordinates[0].top,
                cardCoordinates[2].right + GameConstants.CARD_WIDTH + cardWidthGap,
                cardCoordinates[0].bottom);
        cardCoordinates[4] = new Rect(
                cardCoordinates[3].left + GameConstants.CARD_WIDTH + cardWidthGap,
                cardCoordinates[0].top,
                cardCoordinates[3].right + GameConstants.CARD_WIDTH + cardWidthGap,
                cardCoordinates[0].bottom);
        cardCoordinates[5] = new Rect(
                cardCoordinates[0].left,
                cardCoordinates[0].top + GameConstants.CARD_HEIGHT + cardHeightGap,
                cardCoordinates[0].right,
                cardCoordinates[0].bottom + GameConstants.CARD_HEIGHT + cardHeightGap);
        cardCoordinates[6] = new Rect(cardCoordinates[1].left, cardCoordinates[5].top,
                cardCoordinates[1].right, cardCoordinates[5].bottom);
        cardCoordinates[7] = new Rect(cardCoordinates[2].left, cardCoordinates[5].top,
                cardCoordinates[2].right, cardCoordinates[5].bottom);
        cardCoordinates[8] = new Rect(cardCoordinates[3].left, cardCoordinates[5].top,
                cardCoordinates[3].right, cardCoordinates[5].bottom);
        cardCoordinates[9] = new Rect(cardCoordinates[4].left, cardCoordinates[5].top,
                cardCoordinates[4].right, cardCoordinates[5].bottom);
        cardCoordinates[10] = new Rect(
                cardCoordinates[0].left,
                cardCoordinates[5].top + GameConstants.CARD_HEIGHT + cardHeightGap,
                cardCoordinates[0].right,
                cardCoordinates[5].bottom + GameConstants.CARD_HEIGHT + cardHeightGap);
        cardCoordinates[11] = new Rect(cardCoordinates[1].left, cardCoordinates[10].top,
                cardCoordinates[1].right, cardCoordinates[10].bottom);
        cardCoordinates[12] = new Rect(cardCoordinates[2].left, cardCoordinates[10].top,
                cardCoordinates[2].right, cardCoordinates[10].bottom);
        cardCoordinates[13] = new Rect(cardCoordinates[3].left, cardCoordinates[10].top,
                cardCoordinates[3].right, cardCoordinates[10].bottom);
        cardCoordinates[14] = new Rect(cardCoordinates[4].left, cardCoordinates[10].top,
                cardCoordinates[4].right, cardCoordinates[10].bottom);
        cardCoordinates[15] = new Rect(
                cardCoordinates[0].left,
                cardCoordinates[10].top + GameConstants.CARD_HEIGHT + cardHeightGap,
                cardCoordinates[0].right,
                cardCoordinates[10].bottom + GameConstants.CARD_HEIGHT + cardHeightGap);
        cardCoordinates[16] = new Rect(cardCoordinates[1].left, cardCoordinates[15].top,
                cardCoordinates[1].right, cardCoordinates[15].bottom);
        cardCoordinates[17] = new Rect(cardCoordinates[2].left, cardCoordinates[15].top,
                cardCoordinates[2].right, cardCoordinates[15].bottom);
        cardCoordinates[18] = new Rect(cardCoordinates[3].left, cardCoordinates[15].top,
                cardCoordinates[3].right, cardCoordinates[15].bottom);
        cardCoordinates[19] = new Rect(cardCoordinates[4].left, cardCoordinates[15].top,
                cardCoordinates[4].right, cardCoordinates[15].bottom);
        cardCoordinates[20] = new Rect(
                cardCoordinates[0].left,
                cardCoordinates[15].top + GameConstants.CARD_HEIGHT + cardHeightGap,
                cardCoordinates[0].right,
                cardCoordinates[15].bottom + GameConstants.CARD_HEIGHT + cardHeightGap);
        cardCoordinates[21] = new Rect(cardCoordinates[1].left, cardCoordinates[20].top,
                cardCoordinates[1].right, cardCoordinates[20].bottom);
        cardCoordinates[22] = new Rect(cardCoordinates[2].left, cardCoordinates[20].top,
                cardCoordinates[2].right, cardCoordinates[20].bottom);
        cardCoordinates[23] = new Rect(cardCoordinates[3].left, cardCoordinates[20].top,
                cardCoordinates[3].right, cardCoordinates[20].bottom);
        cardCoordinates[24] = new Rect(cardCoordinates[4].left, cardCoordinates[20].top,
                cardCoordinates[4].right, cardCoordinates[20].bottom);

        // Initialize all cards as unselected and uncleared.
        cardsSelected = new boolean[25];
        clearedCards = new boolean[25];
        clearSelection();
        for (int i = 0; i < cardsSelected.length; i++) {
            clearedCards[i] = false;
        }
    }

    public int getCardWidthGap() {
        return cardWidthGap;
    }

    public int getCardHeightGap() {
        return cardHeightGap;
    }

}
