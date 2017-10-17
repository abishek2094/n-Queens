package Github;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 *	A solution to the n-Queens problem using Simulated Annealing.
 */

public class SimmulatedAnnealing {
	
	static Random ran = new Random();
	
	// A class variable representing the board matrix on which the Queens are placed.
	static byte[][] board = null;
	
	// A class variable representing the board size and hence the number of queens to be placed.
	static int n = 0;
	
	// A class variable that keeps track of the random queen positions.
	static int[][] queenPositions = null;
	
	// A class variable that specifies the current number of attacks between the randomly placed queens.
	static int currentNoOfAttacks = -1;
	
	// A class variable that specifies the number of attacks between the randomly placed queens after making the random move.
	static int newNoOfAttacks = -1;
	
	// A class variable that represents a random queen chosen to make a random move.
	static int chosenQueen = -1;
	
	// A class variable that is used to track the current row of the chosen queen.
	static int currentRowIndex = -1;
	
	// A class variable that is used to track the current column of the chosen queen.
	static int currentColIndex = -1;
	
	// A class variable to keep track of the running time.
	static long currentTime;
	
	public static double acceptanceFunction(int currentEnergy, int newEnergy, double temperature) {
		/*
		 *	The function produces the value that is used to measure whether to accept a move that produces
		 *	a worse state than the current state. 
		 *	The value that this function produces depends on how worse the new state is compared to the current
		 *	state.
		 *	The value produced by the function decreases with time.
		 */
		double result = 0.0;
		try {
			result = ((Math.exp(currentEnergy/temperature)) / (Math.exp(newEnergy/temperature)));
			return result;
		} catch(Exception e) {
			System.out.println("Failed to execute : acceptanceFunction := "+e.getMessage());
			System.exit(1);
			return -1.0;
		}
	}
	
	public static double randomDouble() {
		/*
		 *	A function that generates a random double between 0 and 1.
		 */
		return (ran.nextInt(10000)/10000.0);
	}

	public static void initializeQueens() {
		/*
		 *	A function that initializes the n queens onto the diagonal of the board.
		 */
		
		int count = 0;
		try {
			queenPositions = new int[n][2];
			while(count < n) {
				board[count][count] = 1;
				queenPositions[count][0] = count;
				queenPositions[count][1] = count;
				count++;
			}
				
		} catch(Exception e) {
			System.out.println("Failed to execute : initializeQueens := "+e.getMessage());
			System.exit(0);
		}
	}
	
	public static boolean makeARandomMove() {
		/*
		 *	A function that chooses a random queen and makes a random single move with that queen.
		 */
		int lizard = 0, rowIndex = 0, colIndex = 0, i = 0;
		List<Integer> queenNumber = new ArrayList<Integer>();
		List<Byte> moves = null;
		try {
			for(i = 0; i < n; i++)
				queenNumber.add(new Integer(i));
			Collections.shuffle(queenNumber);
			Collections.shuffle(queenNumber);
			for(i = 0; i < n ; i++) {	
				lizard  = queenNumber.remove(0);
				moves = new ArrayList<Byte>();
				moves.add(new Byte((byte)0));
				moves.add(new Byte((byte)1));
				moves.add(new Byte((byte)2));
				moves.add(new Byte((byte)3));
				moves.add(new Byte((byte)4));
				moves.add(new Byte((byte)5));
				moves.add(new Byte((byte)6));
				moves.add(new Byte((byte)7));
				Collections.shuffle(moves);
				Collections.shuffle(moves);
				for(int j = 0; j < 8; j++) {
					int pos;
					rowIndex = queenPositions[lizard][0];
					colIndex = queenPositions[lizard][1];
					pos = moves.remove(0);
					switch(pos) {
					case 0:rowIndex--;colIndex--;break;
					case 1:rowIndex--;break;
					case 2:rowIndex--;colIndex++;break;
					case 3:colIndex++;break;
					case 4:rowIndex++;colIndex++;break;
					case 5:rowIndex++;break;
					case 6:rowIndex++;colIndex--;break;
					case 7:colIndex--;break;
					}
					if(rowIndex < 0)
						rowIndex = n - 1;
					else if(rowIndex >= n)
						rowIndex = 0;
					if(colIndex < 0)
						colIndex = n - 1;
					else if(colIndex >= n)
						colIndex = 0;
					if(board[rowIndex][colIndex] == 1 || board[rowIndex][colIndex] == 2)
						continue;
					currentRowIndex = queenPositions[lizard][0];
					currentColIndex = queenPositions[lizard][1];
					currentNoOfAttacks = computeNoOfAttacks();
					board[rowIndex][colIndex] = 1;
					board[queenPositions[lizard][0]][queenPositions[lizard][1]] = 0;
					queenPositions[lizard][0] = rowIndex;
					queenPositions[lizard][1] = colIndex;
					chosenQueen = lizard;
					newNoOfAttacks = computeNoOfAttacks();
					return true;
				}
			}
			return false;
		} catch(Exception e) {
			System.out.println("Failed to execute : makeARandomMove := "+e.getMessage());
			System.exit(0);
			return false;
		}
	}
	
	public static int computeNoOfAttacks() {
		/*
		 *	A function that computes number of attacking pairs of queens given the current board configuration. 
		 */
		int i, j, k;
		int noOfAttacks = 0;
		boolean rowLizard = false, colLizard = false, leftDiagonalLizard = false, rightDiagonalLizard = false;
		try {
			for(i = 0; i < n; i++) {
				rowLizard = false;
				colLizard = false;
				for(j = 0; j < n; j++) {
					if(board[i][j] == 1)
						if(rowLizard == true)
							noOfAttacks++;
						else
							rowLizard = true;
					if(board[j][i] == 1)
						if(colLizard == true)
							noOfAttacks++;
						else
							colLizard = true;
				}
			}
			for(k = 0; k < n; k++) {
				i = k;
				j = n - 1;
				rightDiagonalLizard = false;
				while(i >= 0 && j >= 0) {
					if(board[i][j] == 1)
						if(rightDiagonalLizard == true)
							noOfAttacks++;
						else
							rightDiagonalLizard = true;
					i--;
					j--;
				}
				i = k;
				j = 0;
				leftDiagonalLizard = false;
				while(i >= 0 && j < n) {
					if(board[i][j] == 1)
						if(leftDiagonalLizard == true)
							noOfAttacks++;
						else
							leftDiagonalLizard = true;
					i--;
					j++;
				}
			}
			for(k = 1; k < n; k++) {
				j = k;
				i = n - 1;
				leftDiagonalLizard = false;
				while(i >= 0 && j < n) {
					if(board[i][j] == 1)
						if(leftDiagonalLizard == true)
							noOfAttacks++;
						else
							leftDiagonalLizard = true;
					i--;
					j++;
				}	
			}
			for(k = n-2; k >= 0; k--) {
				j = k;
				i = n - 1;
				rightDiagonalLizard = false;
				while(i >= 0 && j >= 0) {
					if(board[i][j] == 1)
						if(rightDiagonalLizard == true)
							noOfAttacks++;
						else
							rightDiagonalLizard = true;
					i--;
					j--;
				}
			}
			return noOfAttacks;
		} catch (Exception e) {
			System.out.println("Failed to execute : computeNoOfAttacks := "+e.getMessage());
			System.exit(0);
			return -1;
		}
	}
	
	public static void simulatedAnnealing() {
		/*
		 *	The function that actually performs the simmulated annealing method by first initializing the
		 *	n queens and then making a random move on a randomly chosen queen.
		 *	This function is designed to exit if it cannot find a solution after 295s.
		 */
		double temperature = 0.0;
		int t = 0;			
		try {
			initializeQueens();
			if(computeNoOfAttacks() == 0) {
				System.out.println("Successfully placed Queens");
				printBoard();
				System.exit(0);
			}
			while(System.currentTimeMillis() - currentTime <= 295000) {
				temperature = (double)1/(Math.log(t++)+1);
				if(makeARandomMove() == false) {
					if(computeNoOfAttacks() == 0){
						System.out.println("Successfully placed Queens");
						printBoard();
						System.exit(0);
					}
					else {
						System.out.println("Failed to place Queens");
						System.exit(0);
					}
				}
				if(newNoOfAttacks == 0) {
					System.out.println("Successfully placed Queens");
					printBoard();
					System.exit(0);
				} else if(newNoOfAttacks > currentNoOfAttacks) {
					if(acceptanceFunction(currentNoOfAttacks, newNoOfAttacks, temperature) < randomDouble()) {
						board[queenPositions[chosenQueen][0]][queenPositions[chosenQueen][1]] = 0;
						queenPositions[chosenQueen][0] = currentRowIndex;
						queenPositions[chosenQueen][1] = currentColIndex;
						board[queenPositions[chosenQueen][0]][queenPositions[chosenQueen][1]] = 1;
					}
				}	
			}
			if(computeNoOfAttacks() == 0) {
				System.out.println("Successfully placed Queens");
			}
			else
				System.out.println("Failed to place Queens");
		} catch(Exception e) {
			System.out.println("Failed to Execute : simulatedAnnealing :- "+e.getMessage());
			System.exit(0);
		}
	}
	
	public static void initializeRandomInput() {
		/*
		 *	A function that generates a problem of random size - n. Here, n <= 10
		 */	
			n = (int)(Math.random() * 20);
			board = new byte[n][n];
			for(int i = 0; i < n; i++)
				for(int j = 0; j < n; j++)
					board[i][j] = 0;
		}
	
	public static void printBoard() {
		/*
		 *	A function that prints the current state of the board.
		 */
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) 
				System.out.print(board[i][j]);
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		/*
		 *	The main function that first initializes the random input and finally calls simulatedAnnealing method.
		 */	
		try {
			initializeRandomInput();
			currentTime = System.currentTimeMillis();
			
			simulatedAnnealing(); 
			
		} catch (Exception e) {
			System.out.println("Failed to execute main()");
		}
	}
	
}
