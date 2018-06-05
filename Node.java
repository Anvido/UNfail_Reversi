package unalcol.agents.examples.games.reversi.sis20181.UNfail;

import java.util.Iterator;
import java.util.LinkedList;

public class Node {

	protected final int MAX_DEPTH = 2;

	protected int gain;
	protected int depth;
	protected int playingTurn;
	protected int[][] boardSimulated;
	protected LinkedList<Node> children;

	public Node(int[][] board, int playingTurn, LinkedList<Long> availableMoves, int depth) {
		this.children = new LinkedList<>();
		this.depth = depth;
		this.boardSimulated = board;
		this.gain = 0;
		Board2 aux = null;

		LinkedList<Long> enemyMoves = null;
		CalculateModule module = new CalculateModule();

		if (this.depth < this.MAX_DEPTH) {
			for (Long space : availableMoves) {

				aux = new Board2(this.boardSimulated, space, -this.playingTurn);

				// Aqui toca calcular los movimientos del otro
				module.updateList(aux.getBoard().clone());
				enemyMoves = module.calculateAvailableMoves(-this.playingTurn);

				this.children.add(new Node(aux.getBoard(), -this.playingTurn, enemyMoves, this.depth + 1));
			}
		}
	}

	public void algo() {
		// new Board2(this.boardSimulated, space, -this.playingTurn);
		// int gainByPieces = 0;
		// Board2 aux = null, currentBoard = new Board2(this.boardSimulated);
		// int gainByPieces = (this.playingTurn == 1) ?
		// currentBoard.white_count()-aux.white_count() :
		// currentBoard.black_count()-aux.black_count() ;
	}
}
