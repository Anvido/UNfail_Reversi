/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.games.reversi.sis20181.UNfail;

import java.util.Iterator;
import java.util.LinkedList;

import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.examples.games.reversi.Reversi;

/**
 *
 * @author Jonatan
 */
public class UNfailAgentProgram implements AgentProgram {

	protected final int SPACE = 0;
	protected final int WHITE = 1;
	protected final int BLACK = -1;

	protected int color;
	protected int size;
	protected int[][] board;
	protected LinkedList<Long> availableMoves;
	protected LinkedList<Long> blackPieces;
	protected LinkedList<Long> whitePieces;

	public UNfailAgentProgram(String color) {
		this.color = color.equalsIgnoreCase("white") ? this.WHITE : this.BLACK;
		this.size = 0;
		this.board = null;
		this.availableMoves = new LinkedList<>();
		this.whitePieces = new LinkedList<>();
		this.blackPieces = new LinkedList<>();
	}

	public void initBoard() {
		this.board = new int[this.size][this.size];
		int n2 = this.size / 2 - 1;
		this.board[n2][n2] = this.WHITE;
		this.board[n2][n2 + 1] = this.BLACK;
		this.board[n2 + 1][n2 + 1] = this.WHITE;
		this.board[n2 + 1][n2] = this.BLACK;
	}

	public void printBoard() {
		for (int[] row : board) {
			for (int i : row) {
				System.out.print((i > this.SPACE) ? "B" : (i < this.SPACE) ? "R" : "-");
			}
			System.out.println();
		}
	}

	public void updateBoard(Percept p) {
		whitePieces.clear();
		blackPieces.clear();

		String space = null;
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				space = (String) p.getAttribute(i + ":" + j);

				switch (space) {
				case "space":
					this.board[i][j] = this.SPACE;
					break;

				case "white":
					this.board[i][j] = this.WHITE;
					this.whitePieces.add(Space.encode(j, i));
					break;

				case "black":
					this.board[i][j] = this.BLACK;
					this.blackPieces.add(Space.encode(j, i));
					break;

				default:
					System.out.println("¿Que espacio es?. Espero no salir");
					break;
				}
			}
		}
	}

	public void visitSpaces() {
		
	}
	
	public void addAvailableMoves(Long piece) {
		
		int[] coords = Space.decode(piece);
		int x = coords[0], y = coords[1];
		
		// Revisando adyacentes
		
		//Arriba
		try{
			if (this.board[y - 1][x] == this.SPACE) {
				
			}
		}catch(Exception e){ }
		
		// Diagonal arriba derecha
		try{
			if (this.board[y - 1][x + 1] == this.SPACE) {
				
			}
		}catch(Exception e){ }
		
		// Derecha
		try{
			if (this.board[y][x + 1] == this.SPACE) {
				
			}
		}catch(Exception e){ }
		
		// Diagonal abajo derecha
		try{
			if (this.board[y + 1][x + 1] == this.SPACE) {
				
			}
		}catch(Exception e){ }
		
		// Abajo
		try{
			if (this.board[y + 1][x] == this.SPACE) {
				
			}
		}catch(Exception e){ }
		
		// Diagonal abajo izquierda
		try{
			if (this.board[y + 1][x - 1] == this.SPACE) {
				
			}
		}catch(Exception e){ }
		
		
		// Izquierda
		try{
			if (this.board[y][x - 1] == this.SPACE) {
				
			}
		}catch(Exception e){ }
		
		// Diagonal arriba izquierda
		try{
			if (this.board[y - 1][x - 1] == this.SPACE) {
				
			}
		}catch(Exception e){ }
		
		
		
				
	}
	
//	public void calculateAvailableMoves() {
//		LinkedList<Long> enemyPieces, myPieces;
//
//		if (this.color == this.WHITE) {
//			myPieces = this.whitePieces;
//			enemyPieces = this.blackPieces;
//		} else {
//			myPieces = this.blackPieces;
//			enemyPieces = this.whitePieces;
//		}
//		
//		for (Long piece : enemyPieces) {
//			this.addAvailableMoves(piece, myPieces);
//		}
//
//	}

	@Override
	public Action compute(Percept p) {
		int turno = ((String) p.getAttribute("play")).equalsIgnoreCase("white") ? this.WHITE : this.BLACK;
		// String tiempo = (String) p.getAttribute(this.color + "_time");

		if (turno == this.color) {
			if (this.size == 0) {
				this.size = Integer.parseInt((String) p.getAttribute("size"));
				System.out.println(this.size);
				this.initBoard();
				this.printBoard();
			} else {
				// TODO: UNfailAgentProgram
				this.updateBoard(p);

			}
			int i = (int) (8 * Math.random());
			int j = (int) (8 * Math.random());
			return new Action(i + ":" + j + ":" + color);
		}
		System.out.println("Stealing turn");
		return new Action(Reversi.PASS);
	}

	@Override
	public void init() {
		this.size = 0;
		this.board = null;
		this.availableMoves = new LinkedList<>();
		this.whitePieces = new LinkedList<>();
		this.blackPieces = new LinkedList<>();
	}

}
