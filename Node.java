package unalcol.agents.examples.games.reversi.sis20181.UNfail;

import java.util.Iterator;
import java.util.LinkedList;

public class Node {

	protected final int MAX_DEPTH = 2; // que sea impar

	protected int gainByPieces;
	protected double gain;
	protected int depth;
	protected int playerColor;
	protected int selected;
	protected int[][] boardSimulated;
	protected LinkedList<Long> moves;
	protected LinkedList<Node> children;

	public Node(int[][] board, int playerColor, LinkedList<Long> availableMoves, int depth, int gain) {
		int auxGain = 0;
		this.gain = 0.0;
		Board2 aux = null;
		this.selected = 0;
		this.depth = depth;
		this.gainByPieces = gain;
		this.moves = availableMoves;
		this.boardSimulated = board;
		this.playerColor = playerColor;
		LinkedList<Long> moves = null;
		CalculateModule module = new CalculateModule();
		Board2 current = new Board2(this.boardSimulated);
		
		if (this.depth < this.MAX_DEPTH) {
			this.children = new LinkedList<>();
			for (Long space : this.moves) {
				aux = new Board2(this.boardSimulated, space, this.playerColor);
				module.updateList(aux.getBoard().clone());
				moves = module.calculateAvailableMoves(-this.playerColor);
				if (this.MAX_DEPTH - this.depth <= 2) {
					auxGain = this.playerColor == 1 ? 
							module.whitePieces.size()-current.white_count() :
							module.blackPieces.size()-current.black_count();
				}
				this.children.add(new Node(aux.getBoard().clone(), -this.playerColor, moves, this.depth + 1, auxGain));
			}
		} else {
			this.children = null;
		}
	}

//	public double calculateMove() {
//		
//		double max = Double.NEGATIVE_INFINITY, aux = 0, pos = -1;		
//		if (this.depth == 0) {
//			
//			for (int i = 0; i < this.children.size(); i++) {
//				aux = this.children.get(i).calculateMove();
//				if (aux > max) {
//					max = aux;
//					pos = i;
//				}
//			}			
//			return pos;		
//			
//		} else if (this.depth == 1){
//			
//			if (this.children.size() > 0) {
//				for (Node child : this.children) {
//					aux = child.calculateMove();
//					if (aux > max) {
//						max = aux;
//					}
//				}
//				
//				return (double)(this.gain - max)/(double)(this.moves.size());
//				
//			} else {
//				return this.gain;
//			}
//			
//		} else {
//			return this.gain;
//		}
//	}
	
	public void calculateGain() {
		
		double max = Double.NEGATIVE_INFINITY, min = Double.POSITIVE_INFINITY, aux = 0.0;
		
		if (this.children == null) {
			return ;
		}
		
		if (this.depth - 1 == this.MAX_DEPTH) {
			if (this.children.size() > 0) {
				for(int i = 0; i < this.children.size(); i++) {
					if (this.children.get(i).gainByPieces > max) {
						max = ((double)(this.gainByPieces)-(double)(this.children.get(i).gainByPieces));
						max /= (double)(this.children.get(i).moves.size());
						this.selected = i;
					}
				}
				
				this.gain = max;
					
			} else {
				
				this.gain = (double)(this.gainByPieces);
			}
			
			return ;
		}
		
		if ((this.depth & 1) == 1) {
			
			if (this.children.size() > 0) {
				for(int i = 0; i < this.children.size(); i++) {
					if (this.children.get(i).gain > max) {
						max = this.children.get(i).gain;
						this.selected = i;
					}
				}
				this.gain = max;
			} else  {
				this.gain = (double)(this.gainByPieces);
			}
			
		} else {
			
			if (this.children.size() > 0) {
				for(int i = 0; i < this.children.size(); i++) {
					if (this.children.get(i).gain < min) {
						min = this.children.get(i).gain;
						this.selected = i;
					}
				}
				this.gain = min;
			} else  {
				this.gain = (double)(this.gainByPieces);
			}
		}
	}
}