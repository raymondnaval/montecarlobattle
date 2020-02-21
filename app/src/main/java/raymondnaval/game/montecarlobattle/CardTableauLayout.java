package raymondnaval.game.montecarlobattle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * This class will be used to track the position of each card in the 5x5 tableau.
 * It will count linearly from the top left to the bottom right, 0 to 24. For each position, this
 * class will track the valid positions left, right above and below.
 */
public class CardTableauLayout {

    private int topTableauBorder;
    private int[] leftPositions, topPositions, rightPositions, bottomPositions;
    private Rect[] cardCoordinates;
    private boolean[] cardsSelected;
    private int cardWidthGap, cardHeightGap;
    private Paint outlineP;

    public CardTableauLayout(int topTableauBorder) {
        this.topTableauBorder = topTableauBorder;
        cardWidthGap = (GameConstants.SCREEN_WIDTH - (GameConstants.CARD_WIDTH * 5)) / 6;
        cardHeightGap = ((GameConstants.SCREEN_HEIGHT * 7 / 12) - (GameConstants.CARD_HEIGHT * 5)) / 6;
        outlineP = new Paint();
        outlineP.setColor(Color.YELLOW);
        outlineP.setStyle(Paint.Style.STROKE);
        setPositions();
    }

    // TODO: Check if card selected is in legal position. Suggestion: track last 5 cards selected.
    public void cardSelected(int position) {

        if (cardsSelected[position]) {
            cardsSelected[position] = false;
        } else {

            // Check all four sides of the selected card.
            boolean leftSideSelected = false, rightSideSelected = false, topSideSelected = false,
                    bottomSideSelected = false;

            /* Check if card to the left is selected. */
            // Do not check cards already on the left side.
            if (position % 5 != 0) {
                if (cardsSelected[position - 1]) {
                    leftSideSelected = true;
                }
            }

            /* Check if card to the right is selected. */
            // Do not check cards on the right side.
            if ((position + 1) % 5 != 0) {
                if (cardsSelected[position + 1]) {
                    rightSideSelected = true;
                }
            }

            /* Check if card to the top is selected. */
            // Do not check cards on the top side.
            if (position > 4) {
                if (cardsSelected[position - 5]) {
                    topSideSelected = true;
                }
            }

            /* Check if card to the bottom is selected. */
            // Do not check cards on the bottom side.
            if (position < 20) {
                if (cardsSelected[position + 5]) {
                    bottomSideSelected = true;
                }
            }

            // If no cards are selected, card is selected.
            if (numCardsSelected() == 0) {
                cardsSelected[position] = true;
            } else {

                if (leftSideSelected && !rightSideSelected && !topSideSelected && !bottomSideSelected) {

                    // Check if cards to the top and bottom right are selected. If not, set to true.
                    if (position <= 4 && !cardsSelected[position + 6]) {
                        cardsSelected[position] = true;
                    } else if (position >= 20 && !cardsSelected[position - 4]) {
                        cardsSelected[position] = true;
                    } else {
                        if(!cardsSelected[position + 6] && !cardsSelected[position - 4]) {
                            cardsSelected[position] = true;
                        }
                    }


                }
                if (!leftSideSelected && rightSideSelected && !topSideSelected && !bottomSideSelected) {
                    cardsSelected[position] = true;
                }
                if (!leftSideSelected && !rightSideSelected && topSideSelected && !bottomSideSelected) {
                    cardsSelected[position] = true;
                }
                if (!leftSideSelected && !rightSideSelected && !topSideSelected && bottomSideSelected) {
                    cardsSelected[position] = true;
                }
            }
        }
        Log.i("CardTableauLayout", "cardsSelected: " + cardsSelected[position]);
    }

    public int numCardsSelected() {
        int numSelected = 0;
        for (int i = 0; i < cardsSelected.length; i++) {
            if (cardsSelected[i]) {
                numSelected++;
            }
        }
        return numSelected;
    }

    public void drawSelectedCards(Canvas canvas) {
        for (int i = 0; i < cardsSelected.length; i++) {
            if (cardsSelected[i]) {
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
        cardCoordinates = new Rect[25];
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

        // Initialize all cards as unselected.
        cardsSelected = new boolean[25];
        for (int i = 0; i < cardsSelected.length; i++) {
            cardsSelected[i] = false;
        }
    }

    public int getCardWidthGap() {
        return cardWidthGap;
    }

    public int getCardHeightGap() {
        return cardHeightGap;
    }

}
