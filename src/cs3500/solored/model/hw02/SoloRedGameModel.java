package cs3500.solored.model.hw02;

import java.util.Random;

import cs3500.solored.model.hw04.AbstractModel;

/**
 * Implementation of the SoloRedGame.
 */
public class SoloRedGameModel extends AbstractModel {

  /**
   * initializes a SoloRedGameModel with values that allow startGame to be called.
   */
  public SoloRedGameModel() {
    super();
  }

  /**
   * initializes a SoloRedGameModel with a preset random seed.
   *
   * @param random preset random object.
   * @throws IllegalArgumentException if the random object is null
   */
  public SoloRedGameModel(Random random) {
    super(random);
  }

  @Override
  public void drawForHand() {
    if (gameOver || !gameStart) {
      throw new IllegalStateException("Game is over/hasn't started.");
    }
    while (hand.size() < handSize) {
      if (numOfCardsInDeck() > 0) {
        hand.add(dealFromDeck());
      } else {
        break;
      }
    }
    drawCanvas = false;
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    if (gameOver || !gameStart) {
      throw new IllegalStateException("Game is over/hasn't started.");
    }
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException(
              "cardIdxInHand must be between 0 and " + (hand.size() - 1));
    }
    if (drawCanvas) {
      throw new IllegalStateException("method already called once in a turn");
    }
    if (hand.size() == 1) {
      throw new IllegalStateException("Only 1 card left in hand");
    }
    canvas = hand.get(cardIdxInHand);
    hand.remove(cardIdxInHand);
    drawCanvas = true;
  }
}
