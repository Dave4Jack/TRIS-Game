import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;



public class TrisGame {
	static char[][] grid;
	static TrisFrame trisFrame;
	static int count;
	static boolean stop;

	public static void main (String arg[]) {
		grid = new char [3][3];
		trisFrame = new TrisFrame("TRIS");
		count = 0;
		stop = true;
	}

	public static boolean insertChar (int x, int y) {
		if(grid[x][y] == Character.MIN_VALUE && stop) {
			grid[x][y] = Turn.charPlayer;
			return true;
		}
		else return false;
	}

	public static boolean checkWinner () {
		if(grid[0][0] == Turn.charPlayer && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) {
			stop = false;
			return true; //check first diagonal
		};
		if(grid[2][0] == Turn.charPlayer && grid[2][0] == grid[1][1] && grid[1][1] == grid[0][2]) {
			stop = false;
			return true; //check second diagonal
		};
		for (int i = 0; i < grid.length; i++){
			if(grid[i][0] == Turn.charPlayer && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) {
				stop = false;
				return true; //check by rows
			};
		};
		for (int j = 0; j < grid.length; j++){
			if(grid[0][j] == Turn.charPlayer && grid[0][j] == grid[1][j] && grid[1][j] == grid[2][j]) {
				stop = false;
				return true; //check by coloumns
			};
		};
		return false; 
	}

	public static boolean checkTie () {
		count += 1;
		if (count == 9) return true;
		else return false;
	}

	public static void resetGame () {
		grid = new char [3][3];
		trisFrame.dispose();
		trisFrame = new TrisFrame("TRIS");
		count = 0;
		stop = true;
		Turn.changeTurn();
	}
}

class TrisFrame extends JFrame {

	JPanel panel;
	JButton[][] trisGrid;
	JLabel victory;
	JLabel tie;
	JButton restart;

	TrisFrame (String s) {
		this.panel = new JPanel(null);
		this.trisGrid = new JButton[3][3]; //the grid of the game
		this.victory = new JLabel ("Victory!");
		this.tie = new JLabel ("Tie!");
		this.restart = new JButton ("Restart?");

		this.panel.setSize(600, 600);
		this.panel.setBackground(Color.darkGray);

		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++) {
				this.trisGrid[i][j] = new JButton();
				this.trisGrid[i][j].setSize(100, 100);
				this.trisGrid[i][j].setLocation(150+100*i, 150+100*j);
				this.panel.add(this.trisGrid[i][j]);
			};
		};

		this.victory.setBounds(275, 100, 80, 40);
		this.victory.setFont(new Font("Arial", Font.BOLD, 15));
		this.victory.setForeground(Color.white);
		this.tie.setBounds(285, 100, 80, 40);
		this.tie.setFont(new Font("Arial", Font.BOLD, 15));
		this.tie.setForeground(Color.white);
		this.restart.setBounds(250, 500, 100, 40);
		this.restart.setFont(new Font("Arial", Font.BOLD, 15));
	
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j< 3; j++) {
				trisGrid[i][j].addActionListener(new ActionListener () { //adding the char once the bottom is pushed
					@Override
					public void actionPerformed(ActionEvent e){
						for (int x = 0; x < 3; x++) {
							for (int y = 0; y < 3; y++) {
								if(e.getSource() == trisGrid[x][y]) {
									if(TrisGame.insertChar(x,y)) {
										trisGrid[x][y].setText(String.valueOf(Turn.charPlayer));
										trisGrid[x][y].setFont(new Font("Arial", Font.BOLD, 80));
										if(TrisGame.checkWinner()) {
											panel.add(victory);
											panel.add(restart);
										};
										if(TrisGame.checkTie()) {
											panel.add(tie);
											panel.add(restart);
										};
										SwingUtilities.updateComponentTreeUI(TrisGame.trisFrame);
										Turn.changeTurn();
									};
								};
							};
						};
					}
				});
			};
		};

		this.restart.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e){
				for (int i = 0; i < 3; i++){
					for (int j = 0; j< 3; j++) {
						remove(panel);
					}
				}
				TrisGame.resetGame();
			}
		});

		this.add(this.panel);
		this.setTitle(s);
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}

class Turn {
	static char charPlayer = 'X';

	public static void changeTurn () {
		if(charPlayer == 'X') charPlayer = 'O'; //alternating Player1 and Player2
		else charPlayer = 'X';
	}
}