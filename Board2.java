package unalcol.agents.examples.games.reversi.sis20181.UNfail;

import unalcol.agents.examples.games.reversi.Board;

public class Board2 extends Board {
	
	public Board2(int size){
		super(size);
	}
	
	public Board2(int[][] board){
		super(board.length);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				values[i][j] = board[i][j];
			}
		}
	}
	
	public Board2(int[][] board, Long space, int color){
		this(board);
		int[] coords = Space.decode(space);
		this.play(coords[1], coords[0], color);
	}
	
	public int[][] getBoard() {
		return this.values;
	}
	
}
