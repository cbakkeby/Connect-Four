/*
 Author: Chase Bakkeby
 Course: PIC 20A
 Professor: Ernest Ryu
 TA: Andrew Krieger
 Date: 11/14/18
 Assignment: Connect Four GUI
*/


package hw4;


public interface CFPlayer {

  int nextMove(CFGame g);

  String getName();
  
}
