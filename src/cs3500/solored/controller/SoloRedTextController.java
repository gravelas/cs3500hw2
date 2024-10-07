package cs3500.solored.controller;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.view.hw02.SoloRedGameTextView;

public class SoloRedTextController implements RedGameController {

  private Readable readable;
  private Scanner scan;
  private Appendable appendable;
  private SoloRedGameTextView textView;

  public SoloRedTextController(Readable rd, Appendable ap) {
    if (rd == null) {
      throw new IllegalArgumentException("Readable is null");
    }
    if (ap == null) {
      throw new IllegalArgumentException("Appendable is null");
    }
    readable = rd;
    appendable = ap;
    scan = new Scanner(readable);
  }

  @Override
  public <C extends Card> void playGame(RedGameModel<C> model, List<C> deck, boolean shuffle, int numPalettes, int handSize) throws IllegalArgumentException, IllegalStateException {
    if (model == null) {
      throw new IllegalArgumentException("RedGameModel is null");
    }
    model.startGame(deck, shuffle, numPalettes, handSize);
    textView = new SoloRedGameTextView(model, appendable);
    printState(model, appendable);
    while (!model.isGameOver()) {
        try {
          parseCommand(model, read(model));
        } catch (QuitException e) {
          gameQuit(model);
          return;
      }
    }
    printState(model, appendable);
    if (model.isGameWon()) {
      append(appendable, "Game won." + "\n");
    } else {
      append(appendable, "Game lost." + "\n");
    }
    printState(model, appendable);
  }

  private void render(SoloRedGameTextView textView) {
    try {
      textView.render();
    } catch (IOException e) {
      throw new IllegalStateException("Cannot render game", e);
    }
  }

  private void append(Appendable appendable, String msg) {
    try {
      appendable.append(msg);
    } catch (IOException e) {
      throw new IllegalStateException("Cannot append message", e);
    }
  }

  private <C extends Card> String read(RedGameModel<C> model) {
    String output = "";
    try {
      output = scan.nextLine();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Cannot read message", e);
    }
    return output;
  }

  private <C extends Card> void parseCommand(RedGameModel<C> model, String input) {
    Deque<String> commandAndArgs = new ArrayDeque<>(List.of(input.split(" ")));
    while (!commandAndArgs.isEmpty()) {
      switch (commandAndArgs.pop()) {
        case "palette":
          while (commandAndArgs.size() < 2) {
            commandAndArgs.addAll(List.of(read(model).split(" ")));
          }
          playPalette(model, commandAndArgs);
          break;
        case "canvas":
          while (commandAndArgs.isEmpty()) {
            commandAndArgs.addAll(List.of(read(model).split(" ")));
          }
          playCanvas(model, commandAndArgs);
          break;
        case "q":
        case "Q":
          throw new QuitException();
        default:
          append(appendable, "Invalid Command. Try again. Enter palette, canvas, or q/Q.\n");
          input = read(model);
          parseCommand(model, input);
          break;
      }
      printState(model, appendable);
    }
  }

  private <C extends Card> void gameQuit(RedGameModel<C> model) {
    append(appendable, "Game quit!\n");
    append(appendable, "State of game when quit:\n");
    render(textView);
    append(appendable, "\nNumber of cards in deck: " + model.numOfCardsInDeck() + "\n");
  }

  private <C extends Card> void playCanvas(RedGameModel<C> model, Deque<String> indices) {
    String arg = indices.pop();
    if (arg.equalsIgnoreCase("q")) {
      throw new QuitException();
    }
    try {
      model.playToCanvas(Integer.parseInt(arg)-1);
    } catch (IllegalArgumentException e) {
      append(appendable, "Invalid move. Try again. CardIndexInHand was OOB.\n");
      if (indices.isEmpty()) {
        indices.addAll(List.of(read(model).split(" ")));
      }
    } catch (IllegalStateException e) {
      append(appendable, "Invalid move. Try again. Only one card left in hand.\n");
      if (indices.isEmpty()) {
        indices.addAll(List.of(read(model).split(" ")));
      }
    }
  }

  private <C extends Card> void playPalette(RedGameModel<C> model, Deque<String> indices) {
    String firstArg = indices.pop();
    String secondArg = indices.pop();
    if (firstArg.equalsIgnoreCase("q") || secondArg.equalsIgnoreCase("q")) {
      throw new QuitException();
    }
    try {
      model.playToPalette(Integer.parseInt(firstArg)-1, Integer.parseInt(secondArg)-1);
    } catch (IllegalArgumentException e) {
      append(appendable, "Invalid move. Try again. CardIndexInHand was OOB.\n");
      if (indices.isEmpty()) {
        indices.addAll(List.of(read(model).split(" ")));
      }
      return;
    } catch (IllegalStateException e) {
      append(appendable, "Invalid move. Try again. Palette was already winning.\n");
      if (indices.isEmpty()) {
        indices.addAll(List.of(read(model).split(" ")));
      }
      return;
    }
    try {
      model.drawForHand();
    } catch (IllegalStateException e) {

    }
  }

  private <C extends Card> void printState(RedGameModel<C> model, Appendable appendable) {
    render(textView);
    append(appendable, "\nNumber of cards in deck: " + model.numOfCardsInDeck() + "\n");
  }
}

class QuitException extends RuntimeException {

}
