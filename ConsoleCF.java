/*
 Author: Chase Bakkeby
 Course: PIC 20A
 Professor: Ernest Ryu
 TA: Andrew Krieger
 Date: 11/14/18
 Assignment: Connect Four GUI
*/


package hw4;


import java.util.Scanner;


public class ConsoleCF extends CFGame {

	public final CFPlayer red, black;


	// Constructor #1
	// Sets up a human vs. human game, where the red player is the player who goes first
	public ConsoleCF() {
		CFPlayer human1 = new HumanPlayer(); // creates an instance of the private inner class HumanPlayer which extends CFPlayer
		CFPlayer human2 = new HumanPlayer();

		this.red = human1;
		this.black = human2;
	}


	// Constructor #2
	// Sets up a human vs. AI game, where the red player (the player who goes first) is randomly decided
	public ConsoleCF(CFPlayer ai) {
		CFPlayer human = new HumanPlayer();

		if (Math.round(Math.random()) == 0) {
			this.red = ai;
			this.black = human;
		}
		else {
			this.red = human;
			this.black = ai;
		}
	}


	// Constructor #3
	// Sets up an AI vs. AI game, where the red player (the player who goes first) is randomly decided
	public ConsoleCF(CFPlayer ai1, CFPlayer ai2) {
		if (Math.round(Math.random()) == 0) {
			this.red = ai1;
			this.black = ai2;
		}
		else {
			this.red = ai2;
			this.black = ai1;
		}
	}


	// Method which plays the game until the game is over
	public void playOut () {
		while (!this.isGameOver()){
			this.play(red.nextMove(this)); // red always goes first
			if (this.isGameOver()) return; // checks if the game is over after every move
			this.play(black.nextMove(this));
		}
	}

	// Method which returns either "draw", "Human Player", or the AI’s name given by CFPlayer’s getName method
	// This is one reason why using the CFPlayer interface is useful
	public String getWinner () {
		if (this.winner() == -1) {
			return black.getName();
		}
		else if (this.winner() == 1) {
			return red.getName();
		}
		else return "draw";
	}

	// The private inner class implements CFPlayer
	// Prints the state of the board to the command line and asks the user for the next move
	// If the provided move is invalid, say so and ask again for a valid move
	// HumanPlayer’s getName implementation returns "Human Player"
	private class HumanPlayer implements CFPlayer {

		@Override
		public int nextMove(CFGame g) {
			System.out.println();
			g.printBoard();
			System.out.print("Please enter the column number you would like to play (1-7): ");
			Scanner reader = new Scanner(System.in);
        	int humanPlay = reader.nextInt();
        	while (!( (humanPlay >= 1) && (humanPlay <= 7) && (g.getState()[humanPlay-1][5] == 0) )) {
        		System.out.print("Please enter a valid column: ");
        		humanPlay = reader.nextInt(); // lets the user enter any number [1,7] using 1-based indexing
        	}
        	return humanPlay;
		}

		@Override
		public String getName() {
			return "Human Player";
		}
	}
}
