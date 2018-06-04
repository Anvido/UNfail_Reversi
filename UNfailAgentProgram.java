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
	protected int[][] board;
	protected LinkedList<Long> blackPieces;
	protected LinkedList<Long> whitePieces;
	protected HashMap<Long, Integer> myAvailableMoves;
	protected HashMap<Long, Integer> enemyAvailableMoves;

	public UNfailAgentProgram(String color, int id) {
		this.id = id;
		this.color = color.equalsIgnoreCase("white") ? this.WHITE : this.BLACK;
		this.size = 0;
		this.board = null;
		this.whitePieces = new LinkedList<>();
		this.blackPieces = new LinkedList<>();
		this.myAvailableMoves = new HashMap<>();
		this.enemyAvailableMoves = new HashMap<>();
	}

	public void initBoard() {
		this.board = new int[this.size][this.size];
		int n2 = this.size / 2 - 1;
		this.board[n2][n2] = this.WHITE;
		this.board[n2][n2 + 1] = this.BLACK;
		this.board[n2 + 1][n2 + 1] = this.WHITE;
		this.board[n2 + 1][n2] = this.BLACK;
		
		this.whitePieces.add(Space.encode(n2, n2));
		this.blackPieces.add(Space.encode(n2+1, n2));
		this.whitePieces.add(Space.encode(n2+1, n2+1));
		this.blackPieces.add(Space.encode(n2, n2+1));
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
		this.whitePieces.clear();
		this.blackPieces.clear();
		this.myAvailableMoves.clear();
		this.enemyAvailableMoves.clear();

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
	
	public int enemy(int color){
		return color == this.WHITE? this.BLACK : this.WHITE;
	}
	
	public void addToMap(int initI, int initJ, int stepI, int stepJ){
		
		int colorToUse = 0;
		HashMap<Long, Integer> auxMap = null;
		
		
		if(this.board[initI][initJ] == this.color){
			
			// buscando la ganacia del enemigo (rompe con su color)
			colorToUse = enemy(this.color);
			auxMap = enemyAvailableMoves;
			
		}else if(this.board[initI][initJ] == enemy(this.color)){
			
			// buscando mi ganacia (rompe con mi color)
			colorToUse = this.color;
			auxMap = myAvailableMoves;
		}
		
		int gain = gain(initI, initJ, stepI, stepJ, colorToUse);
		if (gain == 0) return ;
		
		Long key = Space.encode(initJ-stepJ, initI-stepI);
		int actualGain = (auxMap.containsKey(key) ? auxMap.get(key) : 0);
		auxMap.put(key, gain + actualGain);
	}

	public int gain(int initI, int initJ, int stepI, int stepJ, int color) {
		int gain = 0;
		
		for (int i = initI, j = initJ; i >= 0 && i < this.size && j >= 0 && j < this.size; i+=stepI, j+=stepJ){
			if (this.board[i][j] == this.SPACE) {
				return 0;
			} else if(this.board[i][j] == color) {
				return gain;
			} else {
				gain++;
			}
		}
		return 0;
	}
	
	public void addAvailableMoves(Long piece) {
		int[] coords = Space.decode(piece);
		int x = coords[0], y = coords[1];
		
		// Revisando adyacentes
		
		//Arriba
		try{
			if (this.board[y - 1][x] == this.SPACE) {
				addToMap(y, x, 1, 0);
			}
		}catch(Exception e){ }
		
		// Diagonal arriba derecha
		try{
			if (this.board[y - 1][x + 1] == this.SPACE) {
				addToMap(y, x, 1, -1);
			}
		}catch(Exception e){ }
		
		// Derecha
		try{
			if (this.board[y][x + 1] == this.SPACE) {
				addToMap(y, x, 0, -1);
			}
		}catch(Exception e){ }
		
		// Diagonal abajo derecha
		try{
			if (this.board[y + 1][x + 1] == this.SPACE) {
				addToMap(y, x, -1, -1);
			}
		}catch(Exception e){ }
		
		// Abajo
		try{
			if (this.board[y + 1][x] == this.SPACE) {
				addToMap(y, x, -1, 0);
			}
		}catch(Exception e){ }
		
		// Diagonal abajo izquierda
		try{
			if (this.board[y + 1][x - 1] == this.SPACE) {
				addToMap(y, x, -1, 1);
			}
		}catch(Exception e){ }
		
		
		// Izquierda
		try{
			if (this.board[y][x - 1] == this.SPACE) {
				addToMap(y, x, 0, 1);
			}
		}catch(Exception e){ }
		
		// Diagonal arriba izquierda
		try{
			if (this.board[y - 1][x - 1] == this.SPACE) {
				addToMap(y, x, 1, 1);
			}
		}catch(Exception e){ }
	}
	
	public void calculateAvailableMoves() {
		LinkedList<Long> enemyPieces, myPieces;

		if (this.color == this.WHITE) {
			myPieces = this.whitePieces;
			enemyPieces = this.blackPieces;
		} else {
			myPieces = this.blackPieces;
			enemyPieces = this.whitePieces;
		}
		
		for (Long piece : enemyPieces) {
			this.addAvailableMoves(piece);
		}
	}
	
	public int[] getOptimalMove(int color) {
		int max = 0;
		Long pos = null;
		HashMap<Long, Integer> toUse = (color == this.color) ? this.myAvailableMoves : this.enemyAvailableMoves;
		
		for(Entry<Long, Integer> entry : toUse.entrySet()){
			if(entry.getValue() > max){
				max = entry.getValue();
				pos = entry.getKey();
			}
		}
		
		return (pos == null) ? null : Space.decode(pos);
	}

	@Override
	public Action compute(Percept p) {
		int[] move = null;
		int turn = ((String) p.getAttribute("play")).equalsIgnoreCase("white") ? this.WHITE : this.BLACK;
		
		System.out.println(this.id+": current turn: " + ((turn == this.WHITE) ? "white" : "black"));
				
		
		if (turn == this.color) {
			
			System.out.println(this.id+": My turn");
			if (this.size == 0) {
				this.size = Integer.parseInt((String) p.getAttribute("size"));		
				this.board = new int[this.size][this.size];
			}
			this.updateBoard(p);
			this.printBoard();
			this.calculateAvailableMoves();
			move = this.getOptimalMove(this.color);	
			if (move == null) {
				System.out.println("No tengo movimientos... Pasar");
				return new Action(Reversi.PASS);
			} else {
				System.out.println(this.id+": "+move[0] + ":" + move[1] + ":" + ((this.color == this.WHITE) ? "white" : "black"));
				return new Action(move[1] + ":" + move[0] + ":" + ((this.color == this.WHITE) ? "white" : "black"));	
			}
		} else {
			System.out.println(this.id+": Enemy turn");
			System.out.println(this.id+": Stealing turn");
			return new Action(Reversi.PASS);
		}
	}

	@Override
	public void init() {
		this.size = 0;
		this.board = null;
		this.whitePieces = new LinkedList<>();
		this.blackPieces = new LinkedList<>();
		this.myAvailableMoves = new HashMap<>();
		this.enemyAvailableMoves = new HashMap<>();
	}

}
