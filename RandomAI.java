/*
 Author: Chase Bakkeby
 Course: PIC 20A
 Professor: Ernest Ryu
 TA: Andrew Krieger
 Date: 11/14/18
 Assignment: Connect Four GUI
*/


package hw4;


import java.util.Random;

public class RandomAI implements CFPlayer {

	// Returns, but does not itself play, a random legal column (0-6)
	public int nextMove(CFGame g) {
		int[][] gameBoardArray = g.getState();
	// First, makes note of which columns have available space to be played
		int[] possibleMoves = new int[7];
		int counter = 0;
		for (int i=0; i<7; i++) {
			if (gameBoardArray[i][5] == 0) {
				possibleMoves[counter] = i;
				counter++;
			}
		}

	// Then, randomly returns one of the previously noted legal columns
		long seed = System.currentTimeMillis();
		Random randInt = new Random(seed);
		int aiIndex = randInt.nextInt(counter);
		int aiMove = possibleMoves[aiIndex];

		return aiMove+1;
	}

	// Returns "Random Player"
	@Override
	public String getName() {
		return "Random Player";
	}
}
