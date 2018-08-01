package org.toniogela.goosegame;

import java.io.Console;

public class Game {

  private static Console console = System.console();
  private Players players;
  private String winner;

  public void start() {
    players = new Players();
    println("Welcome to the Goose Game!");
    printInstructions();
    while (winner == null) {
      parseCommand(console.readLine());
    }
    println(winner + " Wins!!");
  }

  private void parseCommand(String command) {
    String[] args = command.trim().split(" ");

    switch (args[0]) {
      case "":
        break;

      case "move":
        if (args.length >= 2 && args.length <= 4) {
          moveBehaviour(args);
          break;
        }

        //$FALL-THROUGH$
      case "add":
        if (args.length == 3 && ("player".equals(args[1]) || "Player".equals(args[1]))) {
          players.registerPlayer(args[2]);
          break;
        }

        //$FALL-THROUGH$
      default:
        println("Unexisting command!");
        break;
    }
  }

  private void moveBehaviour(String[] args) {
    switch (args.length) {
      case 2:
        players.movePlayer(args[1]);
        break;

      case 3:
        parseSteps(args[1], args[2]);
        break;

      case 4:
        parseSteps(args[1], args[2] + args[3]);
        break;
        
      default:
        println("This should never had happened");
        break;
    }
  }

  private void parseSteps(String playerName, String steps) {
    /* Fast preliminar check */
    if (steps.length() == 3 && steps.charAt(1) == ',') {

      int diceOne = Character.getNumericValue(steps.charAt(0));
      int diceTwo = Character.getNumericValue(steps.charAt(2));

      if (diceOne > 0 && diceOne < 7 && diceTwo > 0 && diceTwo < 7) {
        players.movePlayer(playerName, diceOne, diceTwo);
      } else {
        println("Dices have number between 0 and 6!");
      }

    } else {
      println("Invalid move command!");
    }
  }

  private void printInstructions() {
    println("To add a player type \"add player Pippo\".\n"
        + "To move a player type \"move Pippo\" or \"move Pippo 4,2\".");
  }

  public static void println(String string) {
    console.printf(string + "\n", "");
  }

}
