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


public interface CFPlayer {

  int nextMove(CFGame g);

  String getName();
  
}