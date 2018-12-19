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
import hw4.CFPlayer;
import hw4.RandomAI;
import hw4.ChaseAI;
import hw4.ConsoleCF;
import hw4.GUICF;


public class Test {
  public static void main(String[] args) {
    new GUICF(true);

    // System.out.println();
    // System.out.println("CONNECT FOUR");
    // System.out.println();
    // System.out.print("Enter '1' for GUI, '2' for AI Comparison, '3' for Console Game: ");
    // Scanner reader = new Scanner (System.in);
    // int gameMode = reader.nextInt();
    // System.out.println();
    
    // if (gameMode == 1) {
    //   new GUICF(new ChaseAI());
    // } 

    // else if (gameMode == 2) {
    //   CFPlayer ai1 = new ChaseAI();
    //   CFPlayer ai2 = new RandomAI();
    //   int n = 10000;
    //   int winCount = 0;
    //   for (int i =0; i < n ; i++) {
    //     ConsoleCF game = new ConsoleCF(ai1, ai2);
    //     game.playOut();
    //     if(game.getWinner() == ai1.getName())
    //       winCount++;
    //   }
    //   System.out.print(ai1.getName() + " wins "); 
    //   System.out.print(((double) winCount)/((double) n)*100 + "%"); 
    //   System.out.println(" of the time.");
    //   System.out.println();
    // } 

    // else {
    //   ConsoleCF game = new ConsoleCF(new ChaseAI());
    //   game.playOut();
    //   System.out.println();
    //   game.printBoard();
    //   System.out.println(game.getWinner() + " has won.");
    //   System.out.println();
    // } 
  }
}
