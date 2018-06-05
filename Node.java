package unalcol.agents.examples.games.reversi.sis20181.UNfail;

import java.util.Iterator;
import java.util.LinkedList;

public class Node {

	protected final int MAX_DEPTH = 1;

	protected int gain;
	protected int depth;
	protected int playerColor;
	protected int[][] boardSimulated;
	protected LinkedList<Long> moves;
	protected LinkedList<Node> children;

	public Node(int[][] board, int playerColor, LinkedList<Long> availableMoves, int depth, int gain) {
		int auxGain = 0;
		this.gain = gain;
		Board2 aux = null;
		this.depth = depth;
		this.moves = availableMoves;
		this.boardSimulated = board;
		this.playerColor = playerColor;
		LinkedList<Long> enemyMoves = null;
		CalculateModule module = new CalculateModule();
		Board2 current = new Board2(this.boardSimulated);
		
		if (this.depth < this.MAX_DEPTH) {
			this.children = new LinkedList<>();
			for (Long space : this.moves) {
				aux = new Board2(this.boardSimulated, space, this.playerColor);
				module.updateList(aux.getBoard().clone());
				enemyMoves = module.calculateAvailableMoves(-this.playerColor);
				auxGain = this.playerColor == 1 ? 
					aux.white_count()-current.white_count() :
					aux.black_count()-current.black_count();
				this.children.add(new Node(aux.getBoard().clone(), -this.playerColor, enemyMoves, this.depth + 1, auxGain));
			}
		} else {
			this.children = null;
		}
	}

	public int calculateMove() {
		
		int min = Integer.MAX_VALUE, aux = 0, pos = -1;		
		if (this.depth == 0) {
			
			for (int i = 0; i < this.children.size(); i++) {
				aux = this.children.get(i).calculateMove();
				if (aux < min) {
					min = aux;
					pos = i;
				}
			}
			
			return pos;		
			
		} else {
			
			return this.moves.size();
			
		}
	}
}