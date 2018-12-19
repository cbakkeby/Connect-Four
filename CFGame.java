/*
 Author: Chase Bakkeby
 Course: PIC 20A
 Professor: Ernest Ryu
 TA: Andrew Krieger
 Date: 11/14/18
 Assignment: Connect Four GUI
*/


package hw4;


// CFGame provides the bare-bone game logic but doesn’t play the game itself.
// By having ConsoleCF and GUICF inherit CFGame, you avoid duplicating the code that implements the basic Connect Four game logic,
  // while allowing them to play the Connect Four game in a different manner.

public class CFGame {

  // stateArray[i][j]= 0 means the i,j slot is empty
  // stateArray[i][j]= 1 means the i,j slot has red
  // stateArray[i][j]=-1 means the i,j slot has black
  
  private final int[][] stateArray; // final means that stateArray cannot be changed to refer to another object, but its can be modified
  private boolean isRedTurn; // Red always goes first
  private int turnCount = 0;
  private int winnerVariable = 0;


  // Default constructor
  // Initializes the 7x6 grid so that each slot is empty
  public CFGame() {
    stateArray = new int[7][6];
    for (int i=0; i<7; i++)
      for (int j=0; j<6; j++)
        stateArray[i][j] = 0;
  }


  // Prints the board state to the console
  public void printBoard() {
    for (int j=5; j>=0; j--) {
      for (int i=0; i<7; i++) {
        if (stateArray[i][j] == 1) System.out.print(" r");
        else if (stateArray[i][j] == -1) System.out.print(" b");
        else System.out.print(" -");
      }
      System.out.println();
    }
    System.out.println(" 1 2 3 4 5 6 7");
    System.out.println();
  }
  

  // Returns the current state of the game board (stored in a private method) via a public method
  public int[][] getState() {
    int[][] ret_arr = new int[7][6];
    for (int i=0; i<7; i++) {
      for (int j=0; j<6; j++) {
        ret_arr[i][j] = stateArray[i][j];
      }
    }
    return ret_arr;
  }
  

  // Determines if it is red or black's turn
  public boolean isRedTurn() {
    if (turnCount%2 == 0) { return true; }
    else { return false; }
  }
  

  // Plays the specified column
  // If column cannot be played because it is full or because it is an invalid column, return false. Otherwise, return true.
  // The columns are counted with 1-based indexing, i.e., the columns range from 1 to 7.
  public boolean play(int c) {
    int column  = c;
    column--; // returns to zero-based indexing for iterating through array
    if ((column >= 0) && (column <= 6) && (!isGameOver())) {
      for (int j = 0; j < 6; j++) {
        if (stateArray[column][j] == 0) {
          if (isRedTurn()) { stateArray[column][j] = 1; }
          else { stateArray[column][j] = -1; }
          turnCount++;
          return true;
        }
      }
    }
    return false;
  }
  

  // Returns true if the game is over because there is a winner, or because there are no more possible moves. Otherwise returns false.
  public boolean isGameOver() {

    int initialChainColor = 0;
    int secondInChain = 0;
    int thirdInChain = 0;
    int fourthInChain = 0;

    // First, check if anyone has 4 in a row
    for (int i=0; i<7; i++) {
      for (int j=0; j<6; j++) {
        initialChainColor = stateArray[i][j];

        if (initialChainColor != 0) {

          // Checks for a horizontal connect four
          if (i <= 3) {
            secondInChain = stateArray[i+1][j];
            thirdInChain = stateArray[i+2][j];
            fourthInChain = stateArray[i+3][j];
            if ((initialChainColor == secondInChain) && (secondInChain == thirdInChain) && (thirdInChain == fourthInChain)) {
              winnerVariable = initialChainColor;
              return true;
            }
          }

          // Checks for a vertical connect four
          if (j <= 2) {
            secondInChain = stateArray[i][j+1];
            thirdInChain = stateArray[i][j+2];
            fourthInChain = stateArray[i][j+3];
            if ((initialChainColor == secondInChain) && (secondInChain == thirdInChain) && (thirdInChain == fourthInChain)) {
              winnerVariable = initialChainColor;
              return true;
            }
          }

          // Checks for an upwardly-diagonal connect four
          if ((i <= 3) && (j <= 2)) {
            secondInChain = stateArray[i+1][j+1];
            thirdInChain = stateArray[i+2][j+2];
            fourthInChain = stateArray[i+3][j+3];
            if ((initialChainColor == secondInChain) && (secondInChain == thirdInChain) && (thirdInChain == fourthInChain)) {
              winnerVariable = initialChainColor;
              return true;
            }
          }

          // Checks for a downwardly-diagonal connect four
          if ((i <= 3) && (j >= 3)) {
            secondInChain = stateArray[i+1][j-1];
            thirdInChain = stateArray[i+2][j-2];
            fourthInChain = stateArray[i+3][j-3];
            if ((initialChainColor == secondInChain) && (secondInChain == thirdInChain) && (thirdInChain == fourthInChain)) {
              winnerVariable = initialChainColor;
              return true;
            }
          }
        }
      }
    }

    // 7 columns, 6 rows, makes 42 slots on the board. Once completed 42 successful turns, game is over
    if (turnCount == 42) { 
      winnerVariable = 0;
      return true; 
    }

    else { return false; }
  }
  

  // This method should be called when isGameOver() returns true.
  // Returns 1 if red is the winner, -1 if black is the winner, and 0 if the game is a draw.
  // Simply returns the content of the private variable via a public method
  public int winner() {
    return winnerVariable;
  }

}
