package raymondnaval.game.montecarlobattle;

import android.graphics.Rect;

/**
 * This class will be used to track the position of each card in the 5x5 tableau.
 * It will count linearly from the top left to the bottom right, 0 to 24. For each position, this
 * class will track the valid positions left, right above and below.
 */
public class CardTableauLayout {

    private int topTableauBorder;
    private int[] leftPositions, topPositions, rightPositions, bottomPositions;
    private Rect[] cardCoordinates;
    private int cardWidthGap, cardHeightGap;

    public CardTableauLayout(int topTableauBorder) {
        this.topTableauBorder = topTableauBorder;
        cardWidthGap = (GameConstants.SCREEN_WIDTH - (GameConstants.CARD_WIDTH * 5)) / 6;
        cardHeightGap = ((GameConstants.SCREEN_HEIGHT * 7 / 12) - (GameConstants.CARD_HEIGHT * 5)) / 6;
        setPositions();
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
        leftPositions = new int[25];
        leftPositions[0] = cardWidthGap;
        leftPositions[1] = leftPositions[0] + GameConstants.CARD_WIDTH + cardWidthGap;
        leftPositions[2] = leftPositions[1] + GameConstants.CARD_WIDTH + cardWidthGap;
        leftPositions[3] = leftPositions[2] + GameConstants.CARD_WIDTH + cardWidthGap;
        leftPositions[4] = leftPositions[3] + GameConstants.CARD_WIDTH + cardWidthGap;
        leftPositions[5] = leftPositions[0];
        leftPositions[6] = leftPositions[1];
        leftPositions[7] = leftPositions[2];
        leftPositions[8] = leftPositions[3];
        leftPositions[9] = leftPositions[4];
        leftPositions[10] = leftPositions[0];
        leftPositions[11] = leftPositions[1];
        leftPositions[12] = leftPositions[2];
        leftPositions[13] = leftPositions[3];
        leftPositions[14] = leftPositions[4];
        leftPositions[15] = leftPositions[0];
        leftPositions[16] = leftPositions[1];
        leftPositions[17] = leftPositions[2];
        leftPositions[18] = leftPositions[3];
        leftPositions[19] = leftPositions[4];
        leftPositions[20] = leftPositions[0];
        leftPositions[21] = leftPositions[1];
        leftPositions[22] = leftPositions[2];
        leftPositions[23] = leftPositions[3];
        leftPositions[24] = leftPositions[4];
        topPositions = new int[25];
        topPositions[0] = cardHeightGap + topTableauBorder;
        topPositions[1] = topPositions[0] + GameConstants.CARD_HEIGHT + cardHeightGap;
        topPositions[2] = topPositions[1] + GameConstants.CARD_HEIGHT + cardHeightGap;
        topPositions[3] = topPositions[2] + GameConstants.CARD_HEIGHT + cardHeightGap;
        topPositions[4] = topPositions[3] + GameConstants.CARD_HEIGHT + cardHeightGap;
        topPositions[5] = topPositions[0];
        topPositions[6] = topPositions[1];
        topPositions[7] = topPositions[2];
        topPositions[8] = topPositions[3];
        topPositions[9] = topPositions[4];
        topPositions[10] = topPositions[0];
        topPositions[11] = topPositions[1];
        topPositions[12] = topPositions[2];
        topPositions[13] = topPositions[3];
        topPositions[14] = topPositions[4];
        topPositions[15] = topPositions[0];
        topPositions[16] = topPositions[1];
        topPositions[17] = topPositions[2];
        topPositions[18] = topPositions[3];
        topPositions[19] = topPositions[4];
        topPositions[20] = topPositions[0];
        topPositions[21] = topPositions[1];
        topPositions[22] = topPositions[2];
        topPositions[23] = topPositions[3];
        topPositions[24] = topPositions[4];
        rightPositions = new int[25];
        rightPositions[0] = cardWidthGap + GameConstants.CARD_WIDTH;
        rightPositions[1] = rightPositions[0] + cardWidthGap + GameConstants.CARD_WIDTH;
        rightPositions[2] = rightPositions[1] + cardWidthGap + GameConstants.CARD_WIDTH;
        rightPositions[3] = rightPositions[2] + cardWidthGap + GameConstants.CARD_WIDTH;
        rightPositions[4] = rightPositions[3] + cardWidthGap + GameConstants.CARD_WIDTH;
        rightPositions[5] = rightPositions[0];
        rightPositions[6] = rightPositions[1];
        rightPositions[7] = rightPositions[2];
        rightPositions[8] = rightPositions[3];
        rightPositions[9] = rightPositions[4];
        rightPositions[10] = rightPositions[0];
        rightPositions[11] = rightPositions[1];
        rightPositions[12] = rightPositions[2];
        rightPositions[13] = rightPositions[3];
        rightPositions[14] = rightPositions[4];
        rightPositions[15] = rightPositions[0];
        rightPositions[16] = rightPositions[1];
        rightPositions[17] = rightPositions[2];
        rightPositions[18] = rightPositions[3];
        rightPositions[19] = rightPositions[4];
        rightPositions[20] = rightPositions[0];
        rightPositions[21] = rightPositions[1];
        rightPositions[22] = rightPositions[2];
        rightPositions[23] = rightPositions[3];
        rightPositions[24] = rightPositions[4];
        bottomPositions = new int[25];
        bottomPositions[0] = cardHeightGap + topTableauBorder + GameConstants.CARD_HEIGHT;
        bottomPositions[1] = bottomPositions[0] + GameConstants.CARD_HEIGHT + cardHeightGap;
        bottomPositions[2] = bottomPositions[1] + GameConstants.CARD_HEIGHT + cardHeightGap;
        bottomPositions[3] = bottomPositions[2] + GameConstants.CARD_HEIGHT + cardHeightGap;
        bottomPositions[4] = bottomPositions[3] + GameConstants.CARD_HEIGHT + cardHeightGap;
        bottomPositions[5] = bottomPositions[0];
        bottomPositions[6] = bottomPositions[1];
        bottomPositions[7] = bottomPositions[2];
        bottomPositions[8] = bottomPositions[3];
        bottomPositions[9] = bottomPositions[4];
        bottomPositions[10] = bottomPositions[0];
        bottomPositions[11] = bottomPositions[1];
        bottomPositions[12] = bottomPositions[2];
        bottomPositions[13] = bottomPositions[3];
        bottomPositions[14] = bottomPositions[4];
        bottomPositions[15] = bottomPositions[0];
        bottomPositions[16] = bottomPositions[1];
        bottomPositions[17] = bottomPositions[2];
        bottomPositions[18] = bottomPositions[3];
        bottomPositions[19] = bottomPositions[4];
        bottomPositions[20] = bottomPositions[0];
        bottomPositions[21] = bottomPositions[1];
        bottomPositions[22] = bottomPositions[2];
        bottomPositions[23] = bottomPositions[3];
        bottomPositions[24] = bottomPositions[4];

    }

}
