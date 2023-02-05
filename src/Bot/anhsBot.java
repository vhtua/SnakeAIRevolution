package Bot;

import DefaultBotFrameWork.Bot;
import DefaultBotFrameWork.Coordinate;
import DefaultBotFrameWork.Direction;
import DefaultBotFrameWork.Snake;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class anhsBot implements Bot {
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

        // remove backward move
        Direction[] validMoves = Arrays.stream(DIRECTIONS)
                .filter(d -> !head.moveTo(d).equals(afterHead))
                .sorted()
                .toArray(Direction[]::new);

        // avoid losing moves...
        Direction[] notLosing = Arrays.stream(validMoves)
                .filter(d -> head.moveTo(d).inBounds(mazeSize))             	// Snake do not want to touch the borders
                .filter(d -> !opponent.elements.contains(head.moveTo(d)))   	// Snake do not want to touch the opponent's body
                .filter(d -> !snake.elements.contains(head.moveTo(d)))      	// Snake do not want to touch its own body
                .sorted()
                .toArray(Direction[]::new);

        if (notLosing.length > 0) {
            int lengthDifferent = 4;
            int sizeThreshold = 13;

            // Condition 1 //
            if (snake.body.size() >= (opponent.body.size() + lengthDifferent)) {												// Snake is much longer than the opponent
                Arrays.sort(notLosing, new SortByDistance(opponent.getHead(), head));
                return notLosing[0];
            }

            // create a set of coordinates for the region
            Coordinate mid = new Coordinate(mazeSize.x / 2, mazeSize.y / 2);	// Middle of the maze
            Coordinate r1 = new Coordinate(2, 2);								// Top left of the maze
            Coordinate r2 = new Coordinate(mazeSize.x - 2, 2);					// Top right of the maze
            Coordinate r3 = new Coordinate(2, mazeSize.y - 2);					// Bottom left of the maze
            Coordinate r4 = new Coordinate(mazeSize.x - 2, mazeSize.y - 2);		// Bottom right of the maze

            // determine the region that snake is in
            int snakeRegion = 0;
            if (head.x < mid.x && head.y < mid.y) {
                snakeRegion = 1;
            } else if (apple.x >= head.x && apple.y >= head.y) {
                snakeRegion = 4;
            } else if (apple.x < head.x && apple.y >= head.y) {
                snakeRegion = 3;
            } else if (apple.x >= head.x && apple.y < head.y) {
                snakeRegion = 2;
            }

            // Condition 2 //
            if (snake.body.size() >= sizeThreshold) {																		// Snake is reaching the danger length
                if (snake.body.size() > opponent.body.size()) {														// Snake is longer than the opponent
                    Arrays.sort(notLosing, new SortByDistance(opponent.getHead(), head));
                    return notLosing[0];
                } else {																							// Snake is shorter than the opponent
                    // -- decide the target for the snake -- //
                    // if the snake is in a particular region, then the target will be the opposite diagonal region
                    if (snakeRegion == 1 || snakeRegion == 4) {
                        if (Distance(head, r1) <= 3) {
                            Arrays.sort(notLosing, new SortByDistance(head, r4));
                            return notLosing[0];
                        } else {
                            Arrays.sort(notLosing, new SortByDistance(head, r1));
                            return notLosing[0];
                        }
                    } else {
                        if (Distance(head, r2) <= 3) {
                            Arrays.sort(notLosing, new SortByDistance(head, r3));
                            return notLosing[0];
                        } else {
                            Arrays.sort(notLosing, new SortByDistance(head, r2));
                            return notLosing[0];
                        }
                    }
                }
            }

            // Condition 3 //
            if ((opponent.body.size() - snake.body.size()) >= lengthDifferent && opponent.body.size() >= sizeThreshold) {		// The opponent is too long to catch up
                // -- decide the target for the snake -- //
                // if the snake is in a particular region, then the target will be the opposite diagonal region
                if (snakeRegion == 1 || snakeRegion == 4) {
                    if (Distance(head, r1) <= 3) {
                        Arrays.sort(notLosing, new SortByDistance(head, r4));
                        return notLosing[0];
                    } else {
                        Arrays.sort(notLosing, new SortByDistance(head, r1));
                        return notLosing[0];
                    }
                } else {
                    if (Distance(head, r2) <= 3) {
                        Arrays.sort(notLosing, new SortByDistance(head, r3));
                        return notLosing[0];
                    } else {
                        Arrays.sort(notLosing, new SortByDistance(head, r2));
                        return notLosing[0];
                    }
                }
            }

            // get the distance from the snake to the apple
            int headToApple = Distance(head, apple);

            // Condition 4 //
            if (snake.body.size() <= (opponent.body.size() + 1)) {								//				// Snake is shorter than the opponent
                if (Distance(apple, opponent.getHead()) <= headToApple) {						//				|	// Snake is further than the opponent to apple
                    if (head.x == opponent.getHead().x) {										//				|	|	// Both heads are colliding in vertical line
                        // -- decide the target for the snake -- //								//				|	|	|
                        // if the snake is in a particular region, then the target will be the opposite region in horizontal
                        if (snakeRegion == 1) {													// 				|	|	|
                            Arrays.sort(notLosing, new SortByDistance(r2, head));				//				|	|	|
                            return notLosing[0];												//				|	|	|
                        } else if (snakeRegion == 2) {											// 				|	|	|
                            Arrays.sort(notLosing, new SortByDistance(r1, head));				//				|	|	|
                            return notLosing[0];												//				|	|	|
                        } else if (snakeRegion == 3) {											// 				|	|	|
                            Arrays.sort(notLosing, new SortByDistance(r4, head));				//				|	|	|
                            return notLosing[0];												//				|	|	|
                        } else {																// 				|	|	|
                            Arrays.sort(notLosing, new SortByDistance(r3, head));				//				|	|	|
                            return notLosing[0];												//				|	|	|
                        }																		//				|	|	|
                    } else if (head.y == opponent.getHead().y) {								//				|	|	// Both heads are colliding in horizontal line
                        // -- decide the target for the snake -- //								//				|	|
                        // if the snake is in a particular region, then the target will be the opposite region in vertical
                        if (snakeRegion == 1) {													// 				|	|
                            Arrays.sort(notLosing, new SortByDistance(r3, head));				//				|	|
                            return notLosing[0];												//				|	|
                        } else if (snakeRegion == 2) {											// 				|	|
                            Arrays.sort(notLosing, new SortByDistance(r4, head));				//				|	|
                            return notLosing[0];												//				|	|
                        } else if (snakeRegion == 3) {											// 				|	|
                            Arrays.sort(notLosing, new SortByDistance(r1, head));				//				|	|
                            return notLosing[0];												//				|	|
                        } else {																// 				|	|
                            Arrays.sort(notLosing, new SortByDistance(r2, head));				//				|	|
                            return notLosing[0];												//				|	|
                        }																		//				|	|
                    } 																			//				|	|
                } else {																		//				|	// Snake is closer than or having the same distance as the opponent to apple
                    Arrays.sort(notLosing, new SortByDistance(apple, head));					//				|
                    return notLosing[0];														//				|
                }																				//				|
            } else {																			//				// Snake is longer than the opponent
                Arrays.sort(notLosing, new SortByDistance(opponent.getHead(), head));
                return notLosing[0];
            }
        }

        // Condition 5 //
        // -- decide the target for the snake -- //
        // target for the snake now is a "fake apple", which lies at the opposite side of the apple
        // this case is for avoiding the collision with the opponent's head when all of the above cases are not applied
        Coordinate fakeApple = new Coordinate(mazeSize.x - apple.x, mazeSize.y - apple.y);
        Arrays.sort(notLosing, new SortByDistance(fakeApple, head));
        return notLosing[0];
    }

    private static int Distance(Coordinate a, Coordinate b) {
        return Math.abs(b.x - a.x) + Math.abs(b.y - a.y);
    }

    private static class SortByDistance implements Comparator<Direction> {
        private Coordinate destination;
        private Coordinate head;

        public SortByDistance(Coordinate destination, Coordinate head) {
            this.destination = destination;
            this.head = head;
        }

        public int compare(Direction a, Direction b) {
            return Integer.compare(Distance(head.moveTo(a), destination), Distance(head.moveTo(b), destination));
        }
    }

}