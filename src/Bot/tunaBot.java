package Bot;

import DefaultBotFrameWork.Bot;
import DefaultBotFrameWork.Coordinate;
import DefaultBotFrameWork.Direction;
import DefaultBotFrameWork.Snake;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class tunaBot implements Bot {
    private final Random rnd = new Random();
    private static final Direction[] DIRECTIONS = new Direction[] {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

    @Override
    public Direction chooseDirection(final Snake snake, final Snake opponent, final Coordinate mazeSize, final Coordinate apple) {

        Coordinate head = snake.getHead();

        Coordinate afterHeadNotFinal = null;
        if (snake.body.size() >= 2) {
            Iterator<Coordinate> it = snake.body.iterator();
            it.next();
            afterHeadNotFinal = it.next();
        }

        final Coordinate afterHead = afterHeadNotFinal;

        /* The only illegal move is going backwards. Here we are checking for not doing it */
        Direction[] validMoves = Arrays.stream(DIRECTIONS)
                .filter(d -> !head.moveTo(d).equals(afterHead))
                .sorted()
                .toArray(Direction[]::new);

        /* Just naïve greedy algorithm that tries not to die at each moment in time */
        Direction[] notLosing = Arrays.stream(validMoves)
                .filter(d -> head.moveTo(d).inBounds(mazeSize))             // Don't leave maze
                .filter(d -> !opponent.elements.contains(head.moveTo(d)))   // Don't collide with opponent...
                .filter(d -> !snake.elements.contains(head.moveTo(d)))      // and yourself
                .sorted()
                .toArray(Direction[]::new);

        int minimum = 0;
        int maximum = notLosing.length - 1;
        if (notLosing.length > 0){
            for (int i = 0; i < notLosing.length; ++i) {
                //debug
               //System.out.print(i + " --- " + notLosing[i].dx + ", " + notLosing[i].dy + "\n");
               //System.out.print(afterHead.toString() + "\n");
            }

            // new implementation for randomness of the indexes of notLosing Direction array
            int range = maximum - minimum + 1;
            int randomNum =  rnd.nextInt(range) + minimum;

            return notLosing[randomNum];
        }
        else
            return validMoves[0];
    }

}
