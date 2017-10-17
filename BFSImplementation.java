package Github;

import java.util.ArrayList;
import java.util.List;


/*
 *	A BFS implementation of the n-Queens problem.
 */


public class BFSImplementation {
	// A class variable representing the board matrix on which the Queens are placed.
	static byte[][] board = null;
	
	// A class variable representing the board size and hence the number of queens to be placed.
	static int n = 0;
	
	// A class variable representing the queue where positions of queens are populated and
	// extracted in a FIFO manner to implement BFS
	static List<String> queue = new ArrayList<String>();
	
	public static boolean isPlacedQueenSafe(int row, int col) {
	/*
	 *	A function that returns true if a Queen represented by row and col is safe from attack by the already placed queens.
	 *	As we are placing the queens one row at a time, it is sufficient to check the left top diagonal, right top diagonal
	 *	and vertically upwards.
	 *		Given i = row and j = col,
	 *			- Left top diagonal is represented by     -> board[i--][j--]
	 *			- Right top diagonal is represented by    -> board[i--][j++]]
	 *			- Vertically upward can be represented by -> board[i--][j]    
	 */
		try {
			int	leftTopDiagonalRow = row;
			int leftTopDiagonalColumn = col;
			int	rightTopDiagonalRow = row;
			int rightTopDiagonalColumn = col;
			int	columnCheckRow = row;
			for(int i = 0; i < n; i++) {
				leftTopDiagonalRow--;
				leftTopDiagonalColumn--;
				rightTopDiagonalRow--;
				rightTopDiagonalColumn++;
				columnCheckRow--;
				if (leftTopDiagonalRow >= 0 && leftTopDiagonalColumn >= 0) {
	                if(board[leftTopDiagonalRow][leftTopDiagonalColumn] == 1)
	                    return false;
				}
				if(rightTopDiagonalRow >= 0 && rightTopDiagonalColumn < n) {
	                if(board[rightTopDiagonalRow][rightTopDiagonalColumn] == 1)
	                    return false;
				}
				
				if(columnCheckRow >= 0) {
	                if(board[columnCheckRow][col] == 1)
	                    return false;
				}
			}
			return true;
		} catch(Exception e) {
			System.out.println("Failed to execute : isPlacedQueenSafe :- "+e.getMessage());
			System.exit(1);
			return false;
		}
	}
	
	public static void initializeQueue() {
	/*
	 *	A function that initializes the queue with all possible single queen positions in the first row so
	 *	as to implement the BFS procedure.
	 *	For Example, a 3-Queens problem would have the initialization -> {(0,0), (0,1), (0,2)}.
	 */
		try {
			for(int i=0; i<n; i++)
				queue.add(0+","+i);
			System.out.println(queue);
		} catch(Exception e) {
			System.out.println("Failed to execute : initializeQueue :- "+e.getMessage());
		}
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
	
	public static void placeNextQueen() {
	/*
	 *	A function that extracts the first element in the queue, then sets up the board based on the queen
	 *	positions as defined by the extracted element and finally finds all possible locations of the next
	 *	queen (in the next row) and adds these to the end of the queue.
	 */
		String element = null;
		String indices[] = null;
		int index[][] = null;
		int len;
		int rowCounter = 0;
		int currentlyPlaced = 0;
		
		try {
			element = queue.get(0);
			queue.remove(element);
			
			indices = element.split(";");
			
			len = indices.length;
			index = new int[len][2];
			
			for(int i = 0; i < len; i++) {
				String temp[] = indices[i].split(",");
				rowCounter = index[i][0] = Integer.parseInt(temp[0]);
				rowCounter++;
				index[i][1] = Integer.parseInt(temp[1]);
				board[(index[i][0])][(index[i][1])] = 1;
				currentlyPlaced++;
				if(currentlyPlaced == n) {
					System.out.println("Successfully placed Lizards");
					printBoard();
					System.exit(0);
				}
			}
			
			if(rowCounter >= n) {
				return;
			}
			
			int col = 0;
			while(col < n) {
				if(isPlacedQueenSafe(rowCounter, col) == true) {
					String addToQueue = (element+";"+rowCounter+","+col);
					if(len + 1 == n) {
						board[rowCounter][col] = 1;
						System.out.println("Successfully placed Lizards");
						printBoard();
						System.exit(0);
					}
					queue.add(addToQueue);
			
				}
				col++;
			}
			
			
			for(int i = 0; i < len; i++)
				board[(index[i][0])][(index[i][1])] = 0;
			
		} catch(Exception e) {
			System.out.println("Failed to execute : placeNextQueen :- "+e.getMessage());
			System.exit(1);
		}
	}
	
	public static void bfs() throws Exception {
	/*
	`*  A function that implements the BFS method to place the n-Queens by iteratively placing the queens
	 *  until all queens have been placed or the queue is empty
	 */
		while(queue.size() != 0) {	
			placeNextQueen();
		}
		System.out.println("No solution exists");
	}
	
	public static void initializeRandomInput() {
	/*
	 *	A function that generates a problem of random size - n. Here, n <= 10
	 */

		n = (int)(Math.random() * 10);		
		board = new byte[n][n];
		for(int i = 0; i < n; i++) 
			for(int j = 0; j < n; j++)
					board[i][j] = 0;
	}
	
	public static void main(String[] args) {
	/*
	 *	The main function that first initializes the random input, then initializes the queue and
	 *	finally calls bfs method.
	 */
		try {
			initializeRandomInput();
			initializeQueue();
			bfs();
			
		} catch (Exception e) {
			System.out.println("Failed to execute main()");
		}
	}

}
