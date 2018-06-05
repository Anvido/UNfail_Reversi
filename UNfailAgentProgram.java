/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unalcol.agents.examples.games.reversi.sis20181.UNfail;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

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
	protected int id;
	protected int size;
	protected int color;
	protected CalculateModule state;

	public UNfailAgentProgram(String color, int id) {
		this.id = id;
		this.color = color.equalsIgnoreCase("white") ? this.WHITE : this.BLACK;
		this.size = 0;
		this.state = null;
	}

	public void initBoard(int size) {
		int n2 = size / 2 - 1;
		this.state.addWhitePiece(n2, n2);
		this.state.addBlackPiece(n2 + 1, n2);
		this.state.addWhitePiece(n2 + 1, n2 + 1);
		this.state.addBlackPiece(n2, n2 + 1);
	}

	public void printBoard(int[][] board) {
		for (int[] row : board) {
			for (int i : row) {
				System.out.print((i > this.SPACE) ? "B" : (i < this.SPACE) ? "R" : "-");
			}
			System.out.println();
		}
	}

	public void updateBoard(Percept p) {
		this.state.clear();
		
		// Inicializo el tablero
		
		String space = null;
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				space = (String) p.getAttribute(i + ":" + j);

				switch (space) {
					case "white":
						this.state.addWhitePiece(j, i);
						break;
	
					case "black":
						this.state.addBlackPiece(j, i);
						break;
						
					default:
						this.state.addSpace(j,i);
						break;
				}
			}
		}
	}

	// public int enemy(int color){
	// return color == this.WHITE? this.BLACK : this.WHITE;
	// }

	public int[] getOptimalMove() {

		Long pos = null;
		LinkedList<Long> myAvailableMoves = this.state.calculateAvailableMoves(this.color);

		// Node desicion = new Node(this.state.board.clone(), this.color,
		// myAvailableMoves, 0);

		return (myAvailableMoves.size() == 0) ? null : Space.decode(myAvailableMoves.get(0));
	}

	@Override
	public Action compute(Percept p) {
		int[] move = null;
		int turn = ((String) p.getAttribute("play")).equalsIgnoreCase("white") ? this.WHITE : this.BLACK;

		System.out.println(this.id + ": current turn: " + ((turn == this.WHITE) ? "white" : "black"));

		if (turn == this.color) {

			System.out.println(this.id + ": My turn");
			if (this.size == 0) {
				this.size = Integer.parseInt((String) p.getAttribute("size"));
				this.state = new CalculateModule(size);
			}
			this.updateBoard(p);
			this.printBoard(this.state.board);
			move = this.getOptimalMove();
			if (move == null) {
				System.out.println("No tengo movimientos... Pasar");
				return new Action(Reversi.PASS);
			} else {
				System.out.println(this.id + ": " + move[0] + ":" + move[1] + ":"
						+ ((this.color == this.WHITE) ? "white" : "black"));
				return new Action(move[1] + ":" + move[0] + ":" + ((this.color == this.WHITE) ? "white" : "black"));
			}
		} else {
			System.out.println(this.id + ": Enemy turn");
			System.out.println(this.id + ": Stealing turn");
			return new Action(Reversi.PASS);
		}
	}

	@Override
	public void init() {
		this.size = 0;
	}

}
