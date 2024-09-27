package cs3500.solored.model.hw02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SoloCard with the Color Blue.
 */
public class BlueCard extends SoloCard {

  /**
   * Creates a BlueCard. Num must be between 1-7 (inclusive).
   * @param num number of card
   */
  public BlueCard(int num) {
    super(num);
    this.color = Color.BLUE;
  }

  /**
   * Finds the palette with the most amount of same colors.
   * @param palettes the palettes it searches through
   * @return the palette with the most amount of same colors.
   */
  @Override
  public List<SoloCard> canvasRule(List<List<SoloCard>> palettes) {
    if (palettes == null) {
      throw new IllegalArgumentException("Palette must not be null");
    }
    if (palettes.isEmpty()) {
      throw new IllegalArgumentException("Palette must not be empty.");
    }
    Map<List<SoloCard>, Integer> paletteBestColorCount = new HashMap<>();
    for (List<SoloCard> palette : palettes) {
      int bestCardColorCount = 0;
      for (Color color : Color.values()) {
        int cardColorCount = (int) palette.stream().filter
                ((card) -> (card.color() == color)).count();
        if (cardColorCount > bestCardColorCount) {
          bestCardColorCount = cardColorCount;
        }
      }
      paletteBestColorCount.put(palette, bestCardColorCount);
    }
    return biggestOrRedCardWinner(palettes, paletteBestColorCount);
  }
}
