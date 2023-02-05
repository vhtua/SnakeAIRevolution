package Bot;

import DefaultBotFrameWork.Bot;
import DefaultBotFrameWork.Coordinate;
import DefaultBotFrameWork.Direction;
import DefaultBotFrameWork.Snake;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class anhsBotv1 implements Bot {
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

        // remove backward move
        Direction[] validMoves = Arrays.stream(DIRECTIONS)
                .filter(d -> !head.moveTo(d).equals(afterHead))
                .sorted()
                .toArray(Direction[]::new);

        // avoid losing moves...
        Direction[] notLosing = Arrays.stream(validMoves)
                .filter(d -> head.moveTo(d).inBounds(mazeSize))             // do not touch the borders
                .filter(d -> !opponent.elements.contains(head.moveTo(d)))   // do not touch other player
                .filter(d -> !snake.elements.contains(head.moveTo(d)))      // do not touch our own body
                .sorted()
                .toArray(Direction[]::new);

        if (notLosing.length > 0) {
        	// get distance from our head to apple
        	int headToApple = Distance(head, apple);
        	// decide which mode we'll in
        	if (headToApple > 9) {															// we are too far away from apple
        		if (snake.body.size() <= opponent.body.size()) {							// we are shorter than opponent --> engage safe mode
        			// find the apple's region
        			int appleRegion = 0;
        			Coordinate mid = new Coordinate(mazeSize.x/2, mazeSize.y/2);
        			if (apple.x < mid.x && apple.y < mid.y) {
        				appleRegion = 1;
        			} else if (apple.x >= mid.x && apple.y >= mid.y) {
        				appleRegion = 4;
        			} else if (apple.x < mid.x && apple.y >= mid.y) {
        				appleRegion = 3;
        			} else if (apple.x >= mid.x && apple.y < mid.y) {
        				appleRegion = 2;
        			}
        			// find our snake's region
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
        			if (appleRegion + snakeRegion == 5) {									// we are in diagonal region according to the apple --> try to move to other regions
        				if (snakeRegion == 1 || snakeRegion == 4) {							// run to R3's corner
        					Coordinate target = new Coordinate(2, mazeSize.y - 2);
                    		Arrays.sort(notLosing, new SortByDistance(target, head));
                    		return notLosing[0];
        				} else {															// run to R4's corner
        					Coordinate target = new Coordinate(mazeSize.x - 2, mazeSize.y - 2);
                    		Arrays.sort(notLosing, new SortByDistance(target, head));
                    		return notLosing[0];
        				}
        			} else {																// we are near the apple's region --> engage hungry mode
        				Arrays.sort(notLosing, new SortByDistance(apple, head));
                		return notLosing[0];
        			}
            	} else {																	// we are longer than opponent --> engage aggressive mode
            		Arrays.sort(notLosing, new SortByDistance(opponent.getHead(), head));
            		return notLosing[0];
            	}
        	} else {																		// we are near the apple --> engage hungry mode
        		if (snake.body.size() <= opponent.body.size() && Distance(apple, opponent.getHead()) <= headToApple) {		// opponent is near the apple than us
        			// find our snake's region
        			int snakeRegion = 0;
        			Coordinate mid = new Coordinate(mazeSize.x/2, mazeSize.y/2);
        			if (head.x < mid.x && head.y < mid.y) {									// snakeRegion = 1
        				snakeRegion = 1;
        			} else if (apple.x >= head.x && apple.y >= head.y) {					// snakeRegion = 4
        				snakeRegion = 4;
        			} else if (apple.x < head.x && apple.y >= head.y) {						// snakeRegion = 3
        				snakeRegion = 3;
        			} else if (apple.x >= head.x && apple.y < head.y) {						// snakeRegion = 2
        				snakeRegion = 2;
        			}
        			if (head.x == opponent.getHead().x) {						// heads are colliding in vertical line
        				if (snakeRegion == 1) {														// run to R2
        					Coordinate target = new Coordinate(mazeSize.x - 3, 3);
                    		Arrays.sort(notLosing, new SortByDistance(target, head));
                    		return notLosing[0];
        				} else if (snakeRegion == 2) {												// run to R1
        					Coordinate target = new Coordinate(3, 3);
                    		Arrays.sort(notLosing, new SortByDistance(target, head));
                    		return notLosing[0];
        				} else if (snakeRegion == 3) {												// run to R4
        					Coordinate target = new Coordinate(mazeSize.x - 3, mazeSize.y - 3);
                    		Arrays.sort(notLosing, new SortByDistance(target, head));
                    		return notLosing[0];
        				} else if (snakeRegion == 4) {												// run to R3
        					Coordinate target = new Coordinate(3, mazeSize.y - 3);
                    		Arrays.sort(notLosing, new SortByDistance(target, head));
                    		return notLosing[0];
        				}
        			} else if (head.y == opponent.getHead().y) {				// heads are colliding in horizontal line
        				if (snakeRegion == 1) {														// run to R3
        					Coordinate target = new Coordinate(3, mazeSize.y - 3);
                    		Arrays.sort(notLosing, new SortByDistance(target, head));
                    		return notLosing[0];
        				} else if (snakeRegion == 2) {												// run to R4
        					Coordinate target = new Coordinate(mazeSize.x - 3, mazeSize.y - 3);
                    		Arrays.sort(notLosing, new SortByDistance(target, head));
                    		return notLosing[0];
        				} else if (snakeRegion == 3) {												// run to R1
        					Coordinate target = new Coordinate(3, 3);
                    		Arrays.sort(notLosing, new SortByDistance(target, head));
                    		return notLosing[0];
        				} else if (snakeRegion == 4) {												// run to R2
        					Coordinate target = new Coordinate(mazeSize.x - 3, 3);
                    		Arrays.sort(notLosing, new SortByDistance(target, head));
                    		return notLosing[0];
        				}
        			} else {																		// else run to the middle
        				Coordinate target = new Coordinate(mazeSize.x/3, mazeSize.y/3);
                		Arrays.sort(notLosing, new SortByDistance(target, head));
                		return notLosing[0];
        			}
        		}
            	Arrays.sort(notLosing, new SortByDistance(apple, head));
            	return notLosing[0];
            }
        } else {
        	return validMoves[0];
        }
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
