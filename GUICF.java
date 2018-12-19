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

// Sidenote: used Automator to create Connect Four desktop application on Mac. Follow link below for instructions
// https://superuser.com/questions/360247/run-a-shell-script-on-os-x-without-having-a-terminal-window-appear

package hw4;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*; // must import child class too for ActionListener and ActionEvent

public class GUICF extends CFGame {

	private final JFrame frame = new JFrame("Connect Four");
	private final Container pane = frame.getContentPane(); // will contain panels for board and buttons
	private final JPanel boardPanel = new JPanel(); // will contain the 6x7 board grid
	private final JPanel buttonPanel = new JPanel(); // will contain the buttons used during play; playButton or PlayerArrowButtonArray
	private final JPanel endGamePanel = new JPanel(); // will contain endGameLabel and endGameButton
	private final JPanel newGamePanel = new JPanel(); // will contain onePlayerButton, twoPlayerButton, and simulationButton
	private final JButton playButton = new JButton("Play"); // for the AI vs AI game
	private final JButton endGameButton = new JButton("New Game"); // displayed at end of a game, giving option to play a new game
	private final JButton onePlayerButton = new JButton("One Player Game"); // these three buttons are for the new game menu
	private final JButton twoPlayerButton = new JButton("Two Player Game");
	private final JButton simulationButton = new JButton("AI Simulation");
	private final JButton[] onePlayerArrowButtonArray = new JButton[7]; // have to distinguish between one and two players for ActionListener
	private final JButton[] twoPlayerArrowButtonArray = new JButton[7];
	private final JLabel[][] jlabelArray = new JLabel[7][6]; // contains the 42 JLabels to construct the grid for the game board itself
	private javax.swing.Timer delayTimer;  // delays AI move by 1 second in 1 player game
	private JLabel endGameLabel; // wait to initialize with custom text displaying whoever wins
	private GameBoard this_board; // instance of the private inner class; represents the graphics, not the buttons
	private CFPlayer red, black; // the two general CFPlayers used in any of the 3 game types



	// 1st Constructor
	// Sets up GUI board with the 3 button menu to start a new game 
	public GUICF (boolean input) {

		if (input == true) {

			setupFrame(newGamePanel); // calls the private method setupFrame to reduce repeating code in each constructor

			onePlayerButton.addActionListener(new AIPlayListener());
			twoPlayerButton.addActionListener(new AIPlayListener());
			simulationButton.addActionListener(new AIPlayListener());

			newGamePanel.setLayout(new GridLayout(1,3)); // GridLayout evenly spaces components of JPanel and standardizes sizing
			newGamePanel.add(onePlayerButton);
			newGamePanel.add(twoPlayerButton);
			newGamePanel.add(simulationButton);

			frame.setVisible(true); // frame is only set as visible once every piece of the GUI is in place
		}
	}


	// 2nd Constructor
	// Sets up and starts a human vs. human game, where the red player (the player who goes first) is randomly decided
	public GUICF() {

		buttonPanel.setLayout(new GridLayout(1,7));

		setupFrame(buttonPanel);


		for (int i=0; i<7; i++) {
			twoPlayerArrowButtonArray[i] = new JButton("\u2193");
			twoPlayerArrowButtonArray[i].addActionListener(new AIPlayListener());
			buttonPanel.add(twoPlayerArrowButtonArray[i]);
		}

		frame.setVisible(true);

		CFPlayer human1 = new HumanPlayer();
		CFPlayer human2 = new HumanPlayer();

		if (Math.round(Math.random()) == 0) {
			this.red = human1;
			this.black = human2;
		}
		else {
			this.red = human2;
			this.black = human1;
		}
	}


	// 3rd Constructor
	// Sets up and starts a human vs. AI game, where the red player (the player who goes first) is randomly decided
	public GUICF(CFPlayer ai) {

		buttonPanel.setLayout(new GridLayout(1,7));

		setupFrame(buttonPanel);

		for (int i=0; i<7; i++) {
			onePlayerArrowButtonArray[i] = new JButton("\u2193"); // "\u2193" codes for the downward pointing arrow
			onePlayerArrowButtonArray[i].addActionListener(new AIPlayListener());
			buttonPanel.add(onePlayerArrowButtonArray[i]);
		}

		frame.setVisible(true);

		CFPlayer human = new HumanPlayer();
		if (Math.round(Math.random()) == 0) {
			this.red = ai;
			this.black = human;

			int column = red.nextMove(GUICF.this); // AI only moves after ActionListener has been called for human move...
			playGUI(column); 					   // ...so must code for AI to move first if red player
		}
		else {
			this.red = human;
			this.black = ai;
		}
	}


	// 4th Constructor
	// Sets up and starts an AI vs. AI game, where the red player (the player who goes first) is randomly decided
	public GUICF(CFPlayer ai1, CFPlayer ai2) {

		if (Math.round(Math.random()) == 0) {
			this.red = ai1;
			this.black	= ai2;
		}
		else {
			this.red = ai2;
			this.black	= ai1;
		}

		setupFrame(buttonPanel);

		playButton.addActionListener(new AIPlayListener());
		buttonPanel.setLayout(new GridLayout(1,1));
		buttonPanel.add(playButton);

		frame.setVisible(true);
	}



	// Helper method to avoid repeating code for setting up GUI frame
	private void setupFrame(JPanel input) {
		JPanel panel = input;

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(700, 600);

		// The following code to center the frame on screen is from StackOverflow
		// https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

		pane.add(panel, BorderLayout.NORTH);
		pane.add(boardPanel, BorderLayout.CENTER);

		boardPanel.setLayout(new GridLayout(6,7));
		this_board = new GameBoard();
	}


	// If c is a column that can be played, play the column and return true, otherwise return false and do not update the state of the game
	// Internal game logic inherited from CFGame and the displayed board represented in this_board must be updated.
	private boolean playGUI(int c) {
		int column = c;

		for (int row=0; row<6; row++) {
			if (this.getState()[column-1][row] == 0) {
				if (this.isRedTurn()) {
					this_board.paint(column-1, row--, 1 /*red*/);
				}
				else {
					this_board.paint(column-1, row--, -1 /*black*/);
				}
				this.play(column);
				this.printBoard();

				// If the game is over, replace JButton with Winner JLabel and New Game JButton
				if (this.isGameOver()) {
					pane.remove(buttonPanel);
					if (this.winner() == 0) {
						endGameLabel = new JLabel("The game has ended in a draw.");
					}
					else {
						if (this.winner() == 1) {
							endGameLabel = new JLabel("Red (" + red.getName() + ") has won the game!");
						}
						else {
							endGameLabel = new JLabel("Black (" + black.getName() + ") has won the game!");
						}
					}

					endGameLabel.setHorizontalAlignment(endGameLabel.CENTER);
					endGameButton.addActionListener(new AIPlayListener());
					endGamePanel.setLayout(new GridLayout(1,2));
					endGamePanel.add(endGameLabel);
					endGamePanel.add(endGameButton);

					pane.add(endGamePanel, BorderLayout.NORTH);
					pane.revalidate(); // GUI doesn't immediately recognize the change in JPanel, so must manually repaint
					pane.repaint();
				}
				return true;
			}
		}

		return false;
	}


	// private inner class implements ActionListener interface to respond to button clicks in GUI
	private class AIPlayListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ev) {
			int column = -1;

		// These first three deal with button clicks in the newGamePanel, and call the respective constructors
			if (ev.getSource() == onePlayerButton) {
				frame.dispose();
				CFPlayer ai = new ChaseAI(); 
				GUICF onePlayerGame = new GUICF(ai);
			}

			else if (ev.getSource() == twoPlayerButton) {
				frame.dispose();
				GUICF twoPlayerGame = new GUICF();
			}

			else if (ev.getSource() == simulationButton) {
				frame.dispose();
				CFPlayer ai1 = new ChaseAI(); 
				CFPlayer ai2 = new RandomAI(); 
				GUICF simulationGame = new GUICF(ai1, ai2);
			}

		// This is if the user clicks to play a new game after one has finished
			else if (ev.getSource() == endGameButton) {
				frame.dispose();
				GUICF newGame = new GUICF(true);
			}

		// This is if the user clicks the Play button to make moves during the AI vs AI game
			else if (ev.getSource() == playButton) {
				if (GUICF.this.isRedTurn()) {
					column = red.nextMove(GUICF.this);
				}
				else {
					column = black.nextMove(GUICF.this);
				}
				playGUI(column);
				return;
			}

		// This is for after the user clicks an arrow button, making a move in the 1 Player game
		// This is called after the user's move is made, pausing AI move from displaying and disabling the arrow buttons for 1 second 	
			else if (ev.getSource() == delayTimer) {
				if (!GUICF.this.isGameOver()) {
					if (GUICF.this.isRedTurn()) {
						column = red.nextMove(GUICF.this);
					}
					else {
						column = black.nextMove(GUICF.this);
					}
					if (column != -1) {
						playGUI(column);
					}

					for (int i=0; i<7; i++) {
						onePlayerArrowButtonArray[i].setEnabled(true);
					}
				}
			}

		// This is if the user clicks an arrow button
		// delayTimer is only called in 1 Person game
			else {
				for (int i=0; i<7; i++) {
					if (ev.getSource() == onePlayerArrowButtonArray[i]) {
						column = i+1;
						playGUI(column);
						// Forced delay between printing of Human Player's move and AI's move
						// In the meantime, UI will be completely responsive
						// Temporarily disables the 7 arrow buttons
						for (int j=0; j<7; j++) { onePlayerArrowButtonArray[j].setEnabled(false); }
						delayTimer = new javax.swing.Timer(1000, new AIPlayListener());
						delayTimer.setRepeats(false);
						delayTimer.start();
					}

					else if (ev.getSource() == twoPlayerArrowButtonArray[i]) {
						column = i+1;
						playGUI(column);
					}
				}

				
			}
		}
	}


	// private inner class represents the graphics for the 6 × 7 board, but not the buttons.
	private class GameBoard extends JPanel { 
		
		// Constructor initializes empty board
		private GameBoard() {
			for (int j=5; j>=0; j--) {
      			for (int i=0; i<7; i++) {
      				jlabelArray[i][j] = new JLabel();
      				jlabelArray[i][j].setOpaque(true); // setOpaque ensures that the background color shows up
					jlabelArray[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					jlabelArray[i][j].setBackground(Color.WHITE);
					boardPanel.add(jlabelArray[i][j]);
				}
			}
		}

		// Method paints specified coordinate red or black
		private void paint(int column, int row, int color) {
			if (color == 1) {
				jlabelArray[column][row].setBackground(Color.RED);
			}
			else if (color == -1) {
				jlabelArray[column][row].setBackground(Color.BLACK);
			}
		}
	}


	// I re-created the private inner class HumanPlayer within GUICF so that I could easily call the getName() method
	private class HumanPlayer implements CFPlayer {
		@Override
		public int nextMove(CFGame g) {
			return -1; // if statement in AIPlayListener ensures that this move will never be passed into playGUI
		}

		@Override
		public String getName() {
			return "Human Player";
		}
	}
}