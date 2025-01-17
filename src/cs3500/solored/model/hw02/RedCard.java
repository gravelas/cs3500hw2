package cs3500.solored.model.hw02;

import java.util.List;

/**
 * SoloCard with the color Red.
 */
public class RedCard extends SoloCard {

  /**
   * Creates a RedCard. Number must be between 1-7 (inclusive).
   * @param num number of card
   */
  public RedCard(int num) {
    super(Color.RED, num);
  }

  /**
   * Loops through the Palettes provided and finds the one with the largest indexed card.
   * @param palettes the palettes it searches through
   * @return the palette with the largest indexed card. ties broken by color in ROBIV order
   */
  @Override
  public List<SoloCard> canvasRule(List<List<SoloCard>> palettes) {
    if (palettes == null) {
      throw new IllegalArgumentException("Palette must not be null");
    }
    if (palettes.isEmpty()) {
      throw new IllegalArgumentException("Palette must not be empty.");
    }
    int largestNumber = 0;
    SoloCard bestCard = null;
    List<SoloCard> bestPalette = null;
    for (List<SoloCard> palette : palettes) {
      for (SoloCard card : palette) {
        if (card.number() > largestNumber) {
          largestNumber = card.number();
          bestCard = card;
          bestPalette = palette;
        } else if (card.number() == largestNumber) {
          if (card.color().ordinal() > bestCard.color().ordinal()) {
            largestNumber = card.number();
            bestCard = card;
            bestPalette = palette;
          }
        }
      }
    }
    return bestPalette;
  }

}
