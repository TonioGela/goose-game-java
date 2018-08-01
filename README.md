# The Goose Game
This project was developed as a response to an hiring interview.

This is an interactive console game whose aim is to move a player from the cell 1 to cell 63. 

You can add a player typing `add [P/p]layer <playerName>`

You can move an existing player typing `move <playerName>` or `move <playerName> n,m` where `n` and `m` are the single dice throw results (obviously they have to be between 1 and 6)

During the path there are some bonus cells with a custom behaviour. Discover them!

You have the ability to prank other players: when you step in a cell populated by another player, that player will return to your previous cell.

Enjoy!

![Gameplay example](http://toniogela.org/Usage.gif)

## Installing instuctions

The game requires `java 8+` and [maven](https://maven.apache.org/download.cgi) to be built and run.

Once pulled the project, move in the directory (it contains a `pom.xml` file) and run `mvn clean package -U`

Once compiled you'll find a file named `goosegame-0.0.1-SNAPSHOT-jar-with-dependencies.jar` in the newly created `target` folder.

Run the jar using `java -jar goosegame-0.0.1-SNAPSHOT-jar-with-dependencies.jar`