/*
 Name: Chase Bakkeby
 ID: 604831840
 Course: PIC 20A
 Prof: Ernest Ryu
 TA: Andrew Krieger
 Collaborators: Andrew Krieger
 Date: 11/14/18
 Assignment: Connect Four GUI
*/


package hw4;


public class ChaseAI implements CFPlayer {

	// First, checks if there are any winning moves for the player who's turn it is
		// If so, immediately returns the winning move
	// Then, checks if there are winning moves for the opponent, aka defensive moves
		// If there are no winning moves, returns the negative of a defensive move
		// If there are no defensive moves, returns -8 since the value cannot be achieved any other way
	// The returned value is then handled by the overridden method nextMove
	private int winningMoveChecker(int[][] array, int color) { // array and color are passed in to generalize this method for re-running
		int[][] gameBoardArray = array;						   // when considering opponent's next move
		int chaseColor = color;
		int initialChainColor = 0; // for every possible winning combination considered,
		int secondInChain = 0;	   // the values of the four gamepieces are compared to see if they're the same color
		int thirdInChain = 0;
		int fourthInChain = 0;
		int underBlankInChain = 0; // must check if there is a gamepiece already in the column, directly beneath where ChaseAI wants to play
		int chaseWinningMove = -8; // initialized by default to the invalid move -8 (since values [-7,7] can be returned from ChaseAI's nextMove)
		int chaseDefensiveMove = -8;

		// Not only are there 4 angles to connect four, 
			// there are also 4 potential positions to play the winning gamepiece
			// Meaning that there are 16 possible situations to consider!
		// First, check if anyone has 3 in a row
		for (int i=0; i<7; i++) {
		  for (int j=0; j<6; j++) {
		  	initialChainColor = gameBoardArray[i][j];


		  	// Checks for winning moves if the beginning of the chain is blank
		  	if (initialChainColor == 0) {


		  		// Checks for a horizontal winning move
	  		    if (i <= 3) {
		  	      secondInChain = gameBoardArray[i+1][j];
		  	      thirdInChain = gameBoardArray[i+2][j];
		  	      fourthInChain = gameBoardArray[i+3][j];
		  	      if (j == 0) { underBlankInChain = 1; }
		  	      else { underBlankInChain = gameBoardArray[i][j-1]; }

		  	      if ((secondInChain != 0) && (secondInChain == thirdInChain) && (secondInChain == fourthInChain) && (underBlankInChain != 0)) {
		  	      	if (chaseColor == secondInChain) {
			  	    	chaseWinningMove = i;
			  	    	// System.out.println("winningMoveHorizontal0 " + chaseWinningMove);
			  	    	return chaseWinningMove+1;
			  	  	}
			  	  	else {
			  	  	  	chaseDefensiveMove = i;
			  	  	}
		  	      }
		  	    }


		  	    // Checks for an upwardly-diagonal winning move
		  	   	if ((i <= 3) && (j <= 2)) {
			  	  secondInChain = gameBoardArray[i+1][j+1];
			  	  thirdInChain = gameBoardArray[i+2][j+2];
			  	  fourthInChain = gameBoardArray[i+3][j+3];
			  	  if (j == 0) { underBlankInChain = 1; }
		  	      else { underBlankInChain = gameBoardArray[i][j-1]; }

			  	  if ((secondInChain != 0) && (secondInChain == thirdInChain) && (secondInChain == fourthInChain) && (underBlankInChain != 0)) {
	  	    		if (chaseColor == secondInChain) {
			  	    	chaseWinningMove = i;
			  	    	// System.out.println("winningMoveUpDiag0 " + chaseWinningMove);
			  	    	return chaseWinningMove+1;
			  	  	}
			  	  	else {
			  	  	  	chaseDefensiveMove = i;
			  	  	}
		  	      }
		  	    }


		  	    // Checks for a downwardly-diagonal winning move
		  	    if ((i <= 3) && (j >= 3)) {
			  	  secondInChain = gameBoardArray[i+1][j-1];
			  	  thirdInChain = gameBoardArray[i+2][j-2];
			  	  fourthInChain = gameBoardArray[i+3][j-3];
			  	  underBlankInChain = gameBoardArray[i][j-1];

			  	  if ((secondInChain != 0) && (secondInChain == thirdInChain) && (secondInChain == fourthInChain) && (underBlankInChain != 0)) {
	  	    		if (chaseColor == secondInChain) {
			  	    	chaseWinningMove = i;
			  	    	// System.out.println("winningMoveDownDag0 " + chaseWinningMove);
			  	    	return chaseWinningMove+1;
			  	  	}
			  	  	else {
			  	  	  	chaseDefensiveMove = i;
			  	  	}
		  	      }
		  	    }
		  	}



		  	// Checks for winning moves if the beginning of the chain is either red or black
		  	else if (initialChainColor != 0) {


		  	  // Checks for a horizontal winning move
	  		  if (i <= 3) {
		  	    secondInChain = gameBoardArray[i+1][j];
		  	    thirdInChain = gameBoardArray[i+2][j];
		  	    fourthInChain = gameBoardArray[i+3][j];

		  	    if ((initialChainColor == secondInChain) && (initialChainColor == thirdInChain) && (fourthInChain == 0)) {
		  	    	if (j == 0) { underBlankInChain = 1; }
		  	    	else { underBlankInChain = gameBoardArray[i+3][j-1]; }
		  	    	if (underBlankInChain != 0) {
		  	    		if (chaseColor == initialChainColor) {
				  	    	chaseWinningMove = i+3;
				  	    	// System.out.println("winningMoveHorizontal1 " + chaseWinningMove);
				  	    	return chaseWinningMove+1;
				  	  	}
				  	  	else {
				  	  	  	chaseDefensiveMove = i+3;
				  	  	}
		  	    	}
		  	    }
		  	    else if ((initialChainColor == secondInChain) && (initialChainColor == fourthInChain) && (thirdInChain == 0)) {
		  	    	if (j == 0) { underBlankInChain = 1; }
		  	    	else { underBlankInChain = gameBoardArray[i+2][j-1]; }
		  	    	if (underBlankInChain != 0) {
		  	    		if (chaseColor == initialChainColor) {
				  	    	chaseWinningMove = i+2;
				  	    	// System.out.println("winningMoveHorizontal2 " + chaseWinningMove);
				  	    	return chaseWinningMove+1;
				  	  	}
				  	  	else {
				  	  	  	chaseDefensiveMove = i+2;
				  	  	}
		  	    	}
		  	    }
		  	    else if ((initialChainColor == thirdInChain) && (initialChainColor == fourthInChain) && (secondInChain == 0)) {
		  	    	if (j == 0) { underBlankInChain = 1; }
		  	    	else { underBlankInChain = gameBoardArray[i+1][j-1]; }
		  	    	if (underBlankInChain != 0) {
		  	    		if (chaseColor == initialChainColor) {
				  	    	chaseWinningMove = i+1;
				  	    	// System.out.println("winningMoveHorizontal3 " + chaseWinningMove);
				  	    	return chaseWinningMove+1;
				  	  	}
				  	  	else {
				  	  	  	chaseDefensiveMove = i+1;
				  	  	}
		  	    	}
		  	    }
		  	  }


		  	  // Checks for a vertical winning move
		  	  if (j <= 2) {
			  	  secondInChain = gameBoardArray[i][j+1];
			  	  thirdInChain = gameBoardArray[i][j+2];
			  	  fourthInChain = gameBoardArray[i][j+3];
			  	  if ((initialChainColor == secondInChain) && (secondInChain == thirdInChain) && (fourthInChain == 0)) {
			  	    if (chaseColor == initialChainColor) {
			  	    	chaseWinningMove = i;
			  	    	// System.out.println("winningMoveVertical " + chaseWinningMove);
			  	    	return chaseWinningMove+1;
			  	    }
			  	    else {
			  	    	chaseDefensiveMove = i;
			  	    }
			  	  }
			  }


			  // Checks for an upwardly-diagonal winning move
		  	  if ((i <= 3) && (j <= 2)) {
			  	  secondInChain = gameBoardArray[i+1][j+1];
			  	  thirdInChain = gameBoardArray[i+2][j+2];
			  	  fourthInChain = gameBoardArray[i+3][j+3];

			  	  if ((initialChainColor == secondInChain) && (initialChainColor == thirdInChain) && (fourthInChain == 0)) {
		  	    	if (j+3 == 0) { underBlankInChain = 1; }
		  	    	else { underBlankInChain = gameBoardArray[i+3][j+2]; }
		  	    	if (underBlankInChain != 0) {
		  	    		if (chaseColor == initialChainColor) {
				  	    	chaseWinningMove = i+3;
				  	    	// System.out.println("winningMoveUpDiag1 " + chaseWinningMove);
				  	    	return chaseWinningMove+1;
				  	  	}
				  	  	else {
				  	  	  	chaseDefensiveMove = i+3;
				  	  	}
		  	    	}
		  	      }
		  	      else if ((initialChainColor == secondInChain) && (initialChainColor == fourthInChain) && (thirdInChain == 0)) {
		  	    	if (j+2 == 0) { underBlankInChain = 1; }
		  	    	else { underBlankInChain = gameBoardArray[i+2][j+1]; }
		  	    	if (underBlankInChain != 0) {
		  	    		if (chaseColor == initialChainColor) {
				  	    	chaseWinningMove = i+2;
				  	    	// System.out.println("winningMoveUpDiag2 " + chaseWinningMove);
				  	    	return chaseWinningMove+1;
				  	  	}
				  	  	else {
				  	  	  	chaseDefensiveMove = i+2;
				  	  	}
		  	    	}
		  	      }
		  	      else if ((initialChainColor == thirdInChain) && (initialChainColor == fourthInChain) && (secondInChain == 0)) {
		  	    	if (j+1 == 0) { underBlankInChain = 1; }
		  	    	else { underBlankInChain = gameBoardArray[i+1][j]; }
		  	    	if (underBlankInChain != 0) {
		  	    		if (chaseColor == initialChainColor) {
				  	    	chaseWinningMove = i+1;
				  	    	// System.out.println("winningMoveUpDiag3 " + chaseWinningMove);
				  	    	return chaseWinningMove+1;
				  	  	}
				  	  	else {
				  	  	  	chaseDefensiveMove = i+1;
				  	  	}
		  	    	}
		  	      }
			  }


			  // Checks for a downwardly-diagonal winning move
		  	  if ((i <= 3) && (j >= 3)) {
			  	  secondInChain = gameBoardArray[i+1][j-1];
			  	  thirdInChain = gameBoardArray[i+2][j-2];
			  	  fourthInChain = gameBoardArray[i+3][j-3];

			  	  if ((initialChainColor == secondInChain) && (initialChainColor == thirdInChain) && (fourthInChain == 0)) {
		  	    	if (j-3 == 0) { underBlankInChain = 1; }
		  	    	else { underBlankInChain = gameBoardArray[i+3][j-4]; }
		  	    	if (underBlankInChain != 0) {
		  	    		if (chaseColor == initialChainColor) {
				  	    	chaseWinningMove = i+3;
				  	    	// System.out.println("winningMoveDownDag1 " + chaseWinningMove);
				  	    	return chaseWinningMove+1;
				  	  	}
				  	  	else {
				  	  	  	chaseDefensiveMove = i+3;
				  	  	}
		  	    	}
		  	      }
		  	      else if ((initialChainColor == secondInChain) && (initialChainColor == fourthInChain) && (thirdInChain == 0)) {
		  	    	if (j-2 == 0) { underBlankInChain = 1; }
		  	    	else { underBlankInChain = gameBoardArray[i+2][j-3]; }
		  	    	if (underBlankInChain != 0) {
		  	    		if (chaseColor == initialChainColor) {
				  	    	chaseWinningMove = i+2;
				  	    	// System.out.println("winningMoveDownDag2 " + chaseWinningMove);
				  	    	return chaseWinningMove+1;
				  	  	}
				  	  	else {
				  	  	  	chaseDefensiveMove = i+2;
				  	  	}
		  	    	}
		  	      }
		  	      else if ((initialChainColor == thirdInChain) && (initialChainColor == fourthInChain) && (secondInChain == 0)) {
		  	    	if (j-1 == 0) { underBlankInChain = 1; }
		  	    	else { underBlankInChain = gameBoardArray[i+1][j-2]; }
		  	    	if (underBlankInChain != 0) {
		  	    		if (chaseColor == initialChainColor) {
				  	    	chaseWinningMove = i+1;
				  	    	// System.out.println("winningMoveDownDag3 " + chaseWinningMove);
				  	    	return chaseWinningMove+1;
				  	  	}
				  	  	else {
				  	  	  	chaseDefensiveMove = i+1;
				  	  	}
		  	    	}
		  	      }
			  }
		  	}
		  }
		}

		// If no winning moves have been returned, AND there is a valid defensive move to play, return it
		if (chaseDefensiveMove != -8) {
			return (chaseDefensiveMove+1)*(-1);
		}
		// Otherwise, return the invalid -8
		else {
	  	    return chaseDefensiveMove;
		}
	}


	// Returns, but does not itself play, a legal column that your AI wants to play
	// Your AI must be good enough to beat RandomAI 80% of the time.
		// ChaseAI beats RandomAI 99.99% of the time
	@Override
	public int nextMove(CFGame g) {

		int chaseColor, opponentColor;

		// Determines which color ChaseAI will play, using bool function from CFGame.java
		if (g.isRedTurn()) { 
			chaseColor = 1; 
			opponentColor = -1;
		}
		else { 
			chaseColor = -1; 
			opponentColor = 1;
		}

		int[][] gameBoardArray = g.getState();
		int chaseMove = winningMoveChecker(gameBoardArray, chaseColor); // calls the private helper method to find possible winning / defensive move
		
		if (chaseMove >= 0) { // if there is a winning move, play it
			return chaseMove;
		}
		else if (chaseMove > -8) { // otherwise if there is a defensive move, play it
			chaseMove *= -1;
			return chaseMove;
		}
		else { 

			// Before making a last-resort play, checks special scenario
			for (int i=0; i<3; i++) {
				int firstInSequence = gameBoardArray[i][0];
				int secondInSequence = gameBoardArray[i+1][0];
				int thirdInSequence = gameBoardArray[i+2][0];
				int fourthInSequence = gameBoardArray[i+3][0];
				int fifthInSequence = gameBoardArray[i+4][0];
				if ( (firstInSequence == 0) && (fifthInSequence == 0) && (thirdInSequence != 0) ) {
					if ( (secondInSequence == thirdInSequence) && (fourthInSequence == 0) ) {
						chaseMove = (i+3) +1;

						int[][] copyOfGameBoardArray = gameBoardArray.clone(); // .clone() creates a new object which is a copy of that called
		  				copyOfGameBoardArray[i+3][0] = chaseColor;

		  				int doubleCheck = winningMoveChecker(copyOfGameBoardArray, opponentColor);

		  				if (doubleCheck < 0) { return chaseMove; }
		  				else { copyOfGameBoardArray[i+3][0] = 0; }
					}
					else if ( (fourthInSequence == thirdInSequence) && (secondInSequence == 0) ) {
						chaseMove = (i+1) +1;
						
						int[][] copyOfGameBoardArray = gameBoardArray.clone(); 
		  				copyOfGameBoardArray[i+1][0] = chaseColor;

		  				int doubleCheck = winningMoveChecker(copyOfGameBoardArray, opponentColor);

		  				if (doubleCheck < 0) { return chaseMove; }
		  				else { copyOfGameBoardArray[i+1][0] = 0; }
					}

				}
			}


			// otherwise, play the first available column in the following array
			   // but check whether or not it will setup opponent for a winning move
			   // if so, try playing the next available column in the order of the following array
			int[] guessOrder = {3,2,4,1,5,0,6}; // starts in the middle and works it way out
			for (int i=0; i<7; i++) {
				int col = guessOrder[i];
		  		for (int j=0; j<6; j++) {

		  			if (gameBoardArray[col][j] == 0) {
		  				chaseMove = col+1;

		  				int[][] copyOfGameBoardArray = gameBoardArray.clone(); 
		  				copyOfGameBoardArray[col][j] = chaseColor;

		  				int doubleCheck = winningMoveChecker(copyOfGameBoardArray, opponentColor);
		  				if (doubleCheck < 0) {
		  					return chaseMove;
		  				}
		  				else {
		  					copyOfGameBoardArray[col][j] = 0;
		  					break; // calling break exits the for loop and moves on to the next column
		  				}
		  			}
		  		}
		  	}
	  	    return chaseMove;
		}
	}


	// Returns "Chase's AI"
	@Override
	public String getName() {
		return "Chase's AI";
	}
}