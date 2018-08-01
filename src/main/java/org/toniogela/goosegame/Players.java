package org.toniogela.goosegame;

import static org.toniogela.goosegame.Game.println;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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


      positions.put(playerName, Integer.valueOf(newPosition));
      println(builder.toString());
    } else {
      println("Unexisting player!");
    }
  }

  private int checkPosition(StringBuilder builder, String playerName, int position, int offset) {

    if ((position + offset) == 6) {
      builder.append(playerName + " moves form " + position + " to The Bridge. " + playerName
          + " jumps to 12");
      return 12;
    }

    int newPosition = position + offset;

    // AGGIUNGI QUI IL CONTROLLO GOOSE

    newPosition = checkForBounce(builder, playerName, position, newPosition);

  }


  private int gooseSteps(int position, int offset) {
    List<Integer> gooses = Arrays.asList(5, 9, 14, 18, 23, 27);

    List<Integer> gooseSteps = new ArrayList<>(6);
    
    int newPosition = position + offset;
    while (gooses.contains(newPosition)) {
      gooseSteps.add(newPosition);
      newPosition += offset;
    }
    
    if (!gooseSteps.isEmpty()) {
      //SCANNA LA LISTA E LOGGA PIU PASSI
      
    } else {
      //LOGGA 1 PASSO
      return newPosition;
    }
  }


  private int checkForBounce(StringBuilder builder, String playerName, int oldPosition,
      int newPosition) {
    if (newPosition > 63) {
      int actualPosition = 2 * 63 - newPosition;
      builder.append(playerName + " moves form " + oldPosition + " to 63. " + playerName
          + " bounces! " + playerName + " return to " + actualPosition);
      return actualPosition;
    }
    builder.append(" " + playerName + " moves form " + oldPosition + " to " + newPosition);
    return newPosition;
  }

  private int checkForBridge(StringBuilder builder, String playerName, int oldPosition,
      int newPosition) {
    if (newPosition == 6) {
      builder.append(playerName + " moves form " + oldPosition + " to The Bridge. " + playerName
          + " jumps to 12");
      return 12;
    }
    return newPosition;
  }

  public void movePlayer(String playerName) {
    movePlayer(playerName, diceRoll(), diceRoll());
  }

  private int diceRoll() {
    return new Random().nextInt(6) + 1;
  }

}
