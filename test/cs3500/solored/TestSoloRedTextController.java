package cs3500.solored;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

import cs3500.solored.controller.RedGameController;
import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloCard;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Tests for the SoloRedTextController.
 */
public class TestSoloRedTextController {

  /**
   * Main method that allows the user to run the controller in console.
   * @param args main method arguments.
   */
  public static void main(String[] args) {
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    RedGameController controller = new SoloRedTextController(rd, ap);

    RedGameModel<SoloCard> model = new SoloRedGameModel();

    controller.playGame(model, model.getAllCards().subList(0,10), false, 4, 4);
  }

  @Test
  public void testPlayGameWithInvalidMoveAndGameQuit() throws IOException {
    String input = "canvas 0\n" + "q\n";
    Readable rd = new StringReader(input);
    Appendable ap = new StringBuilder();
    ap.append("Canvas: R\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "> P4: V4\n" +
            "Hand: V5 V6 V7 I1\n" +
            "Number of cards in deck: 27\n");
    ap.append("Invalid move. Try again. CardIndexInHand was OOB.\n" +
            "Canvas: R\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "> P4: V4\n" +
            "Hand: V5 V6 V7 I1\n" +
            "Number of cards in deck: 27\n");
    ap.append("Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "> P4: V4\n" +
            "Hand: V5 V6 V7 I1\n" +
            "Number of cards in deck: 27\n");
    Appendable ap2 = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(rd, ap2);
    RedGameModel<SoloCard> model = new SoloRedGameModel();

    controller.playGame(model, model.getAllCards(), false, 4, 4);
    Assert.assertEquals(ap.toString(), ap2.toString());

  }

  @Test
  public void testPlayGameWithNormalWinDoesNotQuit() throws IOException {
    Readable rd = new StringReader("palette 1 1 palette 2 1 palette 3 1 " +
            "canvas 1 palette 4 2 palette 3 1 q");
    Appendable ap = new StringBuilder();
    ap.append("Canvas: R\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "> P4: V4\n" +
            "Hand: V5 V6 V7 I1\n" +
            "Number of cards in deck: 2\n");
    ap.append("Canvas: R\n" +
            "> P1: V1 V5\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "P4: V4\n" +
            "Hand: V6 V7 I1 I2\n" +
            "Number of cards in deck: 1\n");
    ap.append("Canvas: R\n" +
            "P1: V1 V5\n" +
            "> P2: V2 V6\n" +
            "P3: V3\n" +
            "P4: V4\n" +
            "Hand: V7 I1 I2 I3\n" +
            "Number of cards in deck: 0\n");
    ap.append("Canvas: R\n" +
            "P1: V1 V5\n" +
            "P2: V2 V6\n" +
            "> P3: V3 V7\n" +
            "P4: V4\n" +
            "Hand: I1 I2 I3\n" +
            "Number of cards in deck: 0\n");
    ap.append("Canvas: I\n" +
            "P1: V1 V5\n" +
            "P2: V2 V6\n" +
            "> P3: V3 V7\n" +
            "P4: V4\n" +
            "Hand: I2 I3\n" +
            "Number of cards in deck: 0\n");
    ap.append("Canvas: I\n" +
            "P1: V1 V5\n" +
            "P2: V2 V6\n" +
            "P3: V3 V7\n" +
            "> P4: V4 I3\n" +
            "Hand: I2\n" +
            "Number of cards in deck: 0\n");
    ap.append("Canvas: I\n" +
            "P1: V1 V5\n" +
            "P2: V2 V6\n" +
            "> P3: V3 V7 I2\n" +
            "P4: V4 I3\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n");
    ap.append("Game won.\n" +
            "Canvas: I\n" +
            "P1: V1 V5\n" +
            "P2: V2 V6\n" +
            "> P3: V3 V7 I2\n" +
            "P4: V4 I3\n" +
            "Hand: \n" +
            "Number of cards in deck: 0\n");
    Appendable ap2 = new StringBuilder();
    RedGameController controller = new SoloRedTextController(rd, ap2);

    RedGameModel<SoloCard> model = new SoloRedGameModel();

    controller.playGame(model, model.getAllCards().subList(0,10), false, 4, 4);

    Assert.assertEquals(ap2.toString(), ap.toString());
  }

  @Test
  public void testInvalidCommandWithSomeValid() throws IOException {
    Readable rd = new StringReader("canvas 1 lkj q");
    Appendable ap = new StringBuilder();
    ap.append("Canvas: R\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "> P4: V4\n" +
            "Hand: V5 V6 V7 I1\n" +
            "Number of cards in deck: 2\n" +
            "Canvas: V\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "> P3: V3\n" +
            "P4: V4\n" +
            "Hand: V6 V7 I1\n" +
            "Number of cards in deck: 2\n" +
            "Invalid command. Try again. Enter palette, canvas, or q/Q.\n" +
            "Canvas: V\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "> P3: V3\n" +
            "P4: V4\n" +
            "Hand: V6 V7 I1\n" +
            "Number of cards in deck: 2\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: V\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "> P3: V3\n" +
            "P4: V4\n" +
            "Hand: V6 V7 I1\n" +
            "Number of cards in deck: 2\n");

    Appendable ap2 = new StringBuilder();
    RedGameController controller = new SoloRedTextController(rd, ap2);

    RedGameModel<SoloCard> model = new SoloRedGameModel();

    controller.playGame(model, model.getAllCards().subList(0,10), false, 4, 4);

    Assert.assertEquals(ap2.toString(), ap.toString());
  }

  @Test
  public void testModelAlreadyStartedThrowsIllegalArgumentException() throws IOException {
    Readable rd = new StringReader("canvas 1 lkj q");
    Appendable ap2 = new StringBuilder();
    RedGameController controller = new SoloRedTextController(rd, ap2);

    RedGameModel<SoloCard> model = new SoloRedGameModel();
    model.startGame(model.getAllCards().subList(0,10), false, 4, 4);

    Assert.assertThrows(
            IllegalArgumentException.class, () -> controller.playGame(
                    model, model.getAllCards().subList(0,10), false, 4, 4
            )
    );
  }

  @Test
  public void testModelIsNullThrowsIllegalArgumentException() throws IOException {
    Readable rd = new StringReader("canvas 1 lkj q");
    Appendable ap2 = new StringBuilder();
    RedGameController controller = new SoloRedTextController(rd, ap2);

    RedGameModel<SoloCard> model = null;

    Assert.assertThrows(
            IllegalArgumentException.class, () -> controller.playGame(
                    model, List.of(), false, 4, 4
            )
    );
  }

  @Test
  public void testReadableNullThrowsIllegalArgumentException() throws IOException {
    Readable rd = null;
    Appendable ap2 = new StringBuilder();
    Assert.assertThrows(IllegalArgumentException.class, () -> new SoloRedTextController(rd, ap2));
  }

  @Test
  public void testAppendableIOExceptionThrowsIllegalStateException() {
    Readable rd = new StringReader("canvas 1 lkj q");
    Appendable ap2 = new ThrowingAppendableMock();
    RedGameController controller = new SoloRedTextController(rd, ap2);

    RedGameModel<SoloCard> model = new SoloRedGameModel();

    Assert.assertThrows(
            IllegalStateException.class, () -> controller.playGame(
                    model, model.getAllCards().subList(0,10), false, 4, 4
            )
    );
  }

  @Test
  public void testNegativeNumberAndLetterFiltering() {
    Readable rd = new StringReader("canvas -1 w e r t y 2 palette -2 -3 -4 4 2 q");
    Appendable ap2 = new StringBuilder();
    Appendable ap = new StringBuilder("Canvas: R\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "> P4: V4\n" +
            "Hand: V5 V6 V7 I1\n" +
            "Number of cards in deck: 2\n" +
            "Canvas: V\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "> P3: V3\n" +
            "P4: V4\n" +
            "Hand: V5 V7 I1\n" +
            "Number of cards in deck: 2\n" +
            "Canvas: V\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "> P3: V3\n" +
            "P4: V4 V7\n" +
            "Hand: V5 I1\n" +
            "Number of cards in deck: 2\n" +
            "Game lost.\n" +
            "Canvas: V\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "> P3: V3\n" +
            "P4: V4 V7\n" +
            "Hand: V5 I1\n" +
            "Number of cards in deck: 2\n");
    RedGameController controller = new SoloRedTextController(rd, ap2);

    RedGameModel<SoloCard> model = new SoloRedGameModel();

    controller.playGame(model, model.getAllCards().subList(0,10), false, 4, 4);

    Assert.assertEquals(ap.toString(), ap2.toString());
  }

  @Test
  public void testQuittingMidCommand() {
    Readable rd = new StringReader("canvas q 1");
    Appendable ap2 = new StringBuilder("Canvas: R\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "> P4: V4\n" +
            "Hand: V5 V6 V7 I1\n" +
            "Number of cards in deck: 2\n" +
            "Game quit!\n" +
            "State of game when quit:\n" +
            "Canvas: R\n" +
            "P1: V1\n" +
            "P2: V2\n" +
            "P3: V3\n" +
            "> P4: V4\n" +
            "Hand: V5 V6 V7 I1\n" +
            "Number of cards in deck: 2\n");
    Appendable ap = new StringBuilder();
    RedGameController controller = new SoloRedTextController(rd, ap);

    RedGameModel<SoloCard> model = new SoloRedGameModel();

    controller.playGame(model, model.getAllCards().subList(0,10), false, 4, 4);

    Assert.assertEquals(ap2.toString(), ap.toString());
  }
}
