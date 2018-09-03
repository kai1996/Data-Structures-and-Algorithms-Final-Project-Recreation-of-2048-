// B. Ghimire, K. Pandey

import java.util.Vector;

public class Board {
	
	protected Tile[][] board; //board is an array of tiles
	public int score;
	// constructors
	public Board(){
		board = new Tile[4][4];		
		
		for (int row =0; row < 4; row++)
			for (int col =0; col < 4; col++)
				board[row][col] = new Tile(0); // create empty tiles	
	}
	
	public Board(Tile[][] updateBoard){
		board = updateBoard;
	}
	
	/* ----------------------------- methods --------------------------------*/
	
		
	
	public Tile getTile(int row, int col){
	// get the tile at [row, col]
		return board[row][col];		
	}
	
	public void putTile(int row, int col, int val, boolean com){
	// put a tile with val at [row, col]
		board[row][col] = new Tile(val, com);	
	}
	
	public Vector<int[]> getEmptySlots(){
		Vector<int[]> emptySlots = new Vector<int[]>();		
		
		for (int row =0; row < 4; row++){
			for (int col = 0; col < 4; col++){
				int[] currPos = {row, col};
				Tile curr = getTile(row, col);
				if (curr.getVal() == 0) emptySlots.add(currPos);
			}			
		}		
		return emptySlots;
		
	}
	
	
	public String toString(){
		Tile temp;
		String boardString = "";
		for (int row =0; row < 4; row++){
			for (int col = 0; col < 4; col++){
				temp = board[row][col];
				boardString += temp.toString();
			}
			boardString += "\n";
		}
		return boardString;
	}
	
	
	/* -------------------  ops ------------------ */
	
	protected void transpose(){
		// transpose the board
		Tile temp;
		for (int row =0; row < 4; row++){
			for (int col = row +1; col < 4; col++){
				temp = board[row][col];
				board[row][col] = board[col][row];
				board[col][row] = temp;
			}
		}			
	}
	
	protected void reverse(){
		// reverse all rows
		Tile temp;
		for (int row =0; row < 4; row++){ // reverse each row
			int j = 3; // length -1			
			for (int col =0; col < j; col++, j--){
				temp = board[row][col];
				board[row][col] = board[row][j];
				board[row][j] =temp;
			}
		}		
	}
	
	protected void rotateL(){
		// rotate the board -90		
		reverse();
		transpose();
	}
	
	protected void rotateR(){
		// rotate the board +90
		transpose();
		reverse();
	}
	
	public void slideRight(){
		slide();
	}
	
	public void slideLeft(){
		rotateL();
		rotateL();
		slide();
		rotateR();
		rotateR();
	}
	
	public void slideDown(){
		rotateL();
		slide();
		rotateR();
	}
	
	public void slideUp(){
		rotateR();
		slide();
		rotateL();
	}
		
	protected boolean hasMoves(){
		boolean hasMoves = false;
		for (int row = 0; row < 4; row ++){
			for (int col = 0; col < 3; col++){
				Tile curr = getTile(row, col);
				Tile next = getTile(row, col+1);
				
				int currVal = curr.getVal();
				int nextVal = next.getVal();
				
				// if next tile is 0, curr tile can move to that spot
				if (currVal !=0 && nextVal == 0) return true;
				
				if (currVal != 0 && nextVal !=0){
					// check if tiles can combine
					if (currVal == nextVal&& 
							(next.canCombine() && curr.canCombine())){
						hasMoves = hasMoves || true;
					}
				}
			}
		}
		
		return hasMoves;
	}
	
	protected void slide(){
		// perform all possible slides
		while (hasMoves()) slideOnce();
	}
	
	protected void slideOnce(){
		
		// perform 1 slide
		for (int row =0; row < 4; row++){	
			for (int col = 2; col >= 0; col--){
				Tile curr = getTile(row, col);
				Tile next = getTile(row, col+1);
				
				int currVal = curr.getVal();
				int nextVal = next.getVal();
				
				if (currVal == nextVal&& 
						(next.canCombine() && curr.canCombine())){				
					putTile(row, col, 0, false);					
					putTile(row, col+1, nextVal*2, true);
					if(currVal!=0){
						score+=15;
					}
				}
				
				if (nextVal == 0){					
					putTile(row, col, 0, false);
					putTile(row, col+1, currVal, false);
				}
					
			}
		}
	}
	
	
	public int getScore(){
		return score;
		}
	
	public boolean isGameOver(){
		clearCombine();
		boolean gameOver = true;
		for (int i = 0; i < 4; i++){
			if (hasMoves()) gameOver = gameOver && false;
			rotateR();
		}
		return gameOver;
	}
	
	public boolean has2048(){
		boolean won = false;
		for (int row = 0; row < 4; row ++)
			for (int col = 0; col < 4; col++)
				won = won || getTile(row, col).getVal() >= 2048;
		
		return won;
	}		
		
		
	protected void clearCombine(){
		for (int row = 0; row < 4; row ++){
			for (int col = 0; col < 4; col++){
				Tile curr = getTile(row, col);
				if (curr.getVal() != 0) 
					putTile(row, col, curr.getVal(), false);				
			}
		}
	}
	
	
	public static void main(String[] args){
		Board myBoard = new Board();
		// random tiles on the board
		for (int row =0; row < 4; row++)
			for (int col = 0; col < 4; col++)
				myBoard.putTile(row, col, 1, false);
				
		
		System.out.print(myBoard + "\n");
		myBoard.slideRight();
		System.out.print(myBoard + "\n");
		
		myBoard.clearCombine();
		myBoard.slideLeft();
		System.out.print(myBoard + "\n");
		
		myBoard.clearCombine();
		myBoard.slideRight();
		System.out.print(myBoard + "\n");
		
		myBoard.clearCombine();
		myBoard.slideDown();
		System.out.print(myBoard + "\n");
				
	}
}
