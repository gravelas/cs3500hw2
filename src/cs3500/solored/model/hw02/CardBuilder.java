package cs3500.solored.model.hw02;

/**
 * Class that can create SoloCards based on a String.
 */
public class CardBuilder {

  /**
   * A method that can create SoloCards just based off of the toString value.
   * @param cardString toString value "R1" for example.
   * @return SoloCard with the Color and Number provided
   */
  public static SoloCard makeCard(String cardString) {
    switch (cardString.charAt(0)) {
      case 'R':
        return (SoloCard) new RedCard((Integer.parseInt(cardString.substring(1))));
      case 'O':
        return (SoloCard) new OrangeCard((Integer.parseInt(cardString.substring(1))));
      case 'B':
        return (SoloCard) new BlueCard((Integer.parseInt(cardString.substring(1))));
      case 'I':
        return (SoloCard) new IndigoCard((Integer.parseInt(cardString.substring(1))));
      case 'V':
        return (SoloCard) new VioletCard((Integer.parseInt(cardString.substring(1))));
      default:
        return null;
    }
  }
}
