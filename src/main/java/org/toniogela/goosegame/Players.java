package org.toniogela.goosegame;

import static org.toniogela.goosegame.Game.println;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Players {

  private final HashMap<String, Integer> positions = new HashMap<>();

  public void registerPlayer(String playerName) {
    /*
     * if we want ignoreCase names we can use a TreeMap but the efficency of put/get/containsKey
     * drops from O(1) to O(log n)
     */
    if (!positions.containsKey(playerName)) {
      positions.put(playerName, Integer.valueOf(1));
      println("Players: " + positions.keySet().parallelStream().collect(Collectors.joining(", ")));
    } else {
      println(playerName + ": already existing player");
    }
  }

  public void movePlayer(String playerName, int diceOne, int diceTwo) {
    StringBuilder builder =
        new StringBuilder(playerName + " rolls " + diceOne + ", " + diceTwo + ". ");

    if (positions.containsKey(playerName)) {
      int position = positions.get(playerName).intValue();
      int offset = diceOne + diceTwo;
      int newPosition = checkPosition(builder, playerName, position, offset);
      positions.put(playerName, Integer.valueOf(newPosition));
      prank(builder, playerName, position, newPosition);
      if (newPosition == 63) {
        Game.setWinner(playerName);
      }
      println(builder.toString());
    } else {
      println("Unexisting player!");
    }
  }

  private int checkPosition(StringBuilder builder, String playerName, int position, int offset) {

    if ((position + offset) == 6) {
      builder.append(playerName + " moves from " + position + " to The Bridge. " + playerName
          + " jumps to 12");
      return 12;
    }
    return gooseSteps(builder, playerName, position, offset);
  }


  private int gooseSteps(StringBuilder builder, String playerName, int position, int offset) {
    List<Integer> gooses = Arrays.asList(Integer.valueOf(5), Integer.valueOf(9),
        Integer.valueOf(14), Integer.valueOf(18), Integer.valueOf(23), Integer.valueOf(27));

    List<Integer> gooseSteps = new ArrayList<>(6);

    // calculate goose steps
    int newPosition = position + offset;
    while (gooses.contains(Integer.valueOf(newPosition))) {
      gooseSteps.add(Integer.valueOf(newPosition));
      newPosition += offset;
    }

    if (!gooseSteps.isEmpty()) {
      // First Goose Step
      builder.append(
          playerName + " moves from " + position + " to " + gooseSteps.get(0) + ", The Goose. ");

      // Center GooseSteps
      if (gooseSteps.size() > 1) {
        for (int i = 1; i < gooseSteps.size(); i++) {
          builder.append(
              playerName + " moves again and goes to " + gooseSteps.get(i) + ", The Goose. ");
        }
      }

      // Last Step
      int lastPosition = gooseSteps.get(gooseSteps.size() - 1).intValue() + offset;
      builder.append(playerName + " moves again and goes to " + lastPosition + ".");
      return lastPosition;
    }
    newPosition = checkForBounce(builder, playerName, position, position + offset);
    return newPosition;
  }


  private int checkForBounce(StringBuilder builder, String playerName, int oldPosition,
      int newPosition) {
    if (newPosition > 63) {
      int actualPosition = 2 * 63 - newPosition;
      builder.append(playerName + " moves from " + oldPosition + " to 63. " + playerName
          + " bounces! " + playerName + " returns to " + actualPosition + ".");
      return actualPosition;
    }
    builder.append(playerName + " moves from " + (oldPosition == 1 ? "Start" : oldPosition) + " to "
        + newPosition + ".");
    return newPosition;
  }

  private void prank(StringBuilder builder, String playerName, int position, int newPosition) {
    for (Map.Entry<String, Integer> entry : positions.entrySet()) {
      if (!entry.getKey().equals(playerName) && entry.getValue().intValue() == newPosition) {
        entry.setValue(Integer.valueOf(position));
        builder.append(" On " + newPosition + " there is " + entry.getKey() + ", who returns on "
            + position + ".");
        break;
      }
    }


  }

  public void movePlayer(String playerName) {
    movePlayer(playerName, diceRoll(), diceRoll());
  }

  private int diceRoll() {
    return new Random().nextInt(6) + 1;
  }

}
