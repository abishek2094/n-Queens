package Github;

/*
 *	A DFS implementation of the n-Queens problem.
 */

public class RecursiveDFSImplementation {

	
	// A class variable representing the board matrix on which the Queens are placed.
	static byte[][] board = null;
		
	// A class variable representing the board size and hence the number of queens to be placed.
	static int n = 0;
	
	// A class variable that represents the current number of queens placed on the board 
	static int noOfPlacedQueens = 0;
	
	
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

	public static boolean dfsPlaceQueens(int row) {
	/*
	 *	The actual function that is recursively called with the row number to place the n-Queens
	 *	in a DFS manner.
	 */
		int i,j;
		try {
			
			if(noOfPlacedQueens == n)
				return true;
			   
			i = row;
			j = 0;
			while(j < n) {
				if(isPlacedQueenSafe(i, j) == true) {
					board[i][j] = 1;    
					noOfPlacedQueens++;
					
					if(dfsPlaceQueens(i+1) == true)
						return true;
					board[i][j] = 0;
					noOfPlacedQueens--;
				}
				j++;
			}
			return false;   
		} catch(Exception e) {
			System.out.println("Failed to execute : dfsPlaceQueens :- "+e.getMessage());
			System.exit(1);
			return false;
		}
	}
	
	public static void dfs() throws Exception {
	/*
	 *  A function that implements the DFS method to place the n-Queens by recursively placing the queens
	 *  until all queens have been placed or no such arrangement is possible.
	 */
		if(dfsPlaceQueens(0) == true) {
			printBoard();
			System.out.println("Successfully placed the queens !");
		}	
		else
			System.out.println("Failed to place the queens !");
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
	
	public static void main(String[] args) {
	/*
	 *	The main function that first initializes the random input and finally calls dfs method.
	 */	
		try {
			initializeRandomInput();
			dfs();
			
		} catch (Exception e) {
			System.out.println("Failed to execute main()");
		}
	}
	
	
}
