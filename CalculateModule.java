package unalcol.agents.examples.games.reversi.sis20181.UNfail;

import java.util.LinkedList;

public class CalculateModule {

	protected final int SPACE = 0;
	protected final int WHITE = 1;
	protected final int BLACK = -1;
	protected LinkedList<Long> blackPieces;
	protected LinkedList<Long> whitePieces;
	protected LinkedList<Long> availableMoves;
	protected int[][] board;

	public CalculateModule() {
		this.whitePieces = new LinkedList<>();
		this.blackPieces = new LinkedList<>();
		this.availableMoves = new LinkedList<>();
	}

	public CalculateModule(int size) {
		this.board = new int[size][size];
		this.whitePieces = new LinkedList<>();
		this.blackPieces = new LinkedList<>();
		this.availableMoves = new LinkedList<>();
	}

	public void updateList(int[][] board) {
		int size = this.board.length;
		this.board = board;
		this.clear();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == this.WHITE) {
					this.addWhitePiece(j, i);
				} else {
					this.addBlackPiece(j, i);
				}
			}
		}
	}

	public void clear() {
		this.whitePieces.clear();
		this.blackPieces.clear();
	}

	public void addBlackPiece(int x, int y) {
		this.board[y][x] = this.BLACK;
		this.blackPieces.add(Space.encode(x, y));
	}

	public void addWhitePiece(int x, int y) {
		this.board[y][x] = this.WHITE;
		this.whitePieces.add(Space.encode(x, y));
	}
	
	public void addSpace(int x, int y) {
		this.board[y][x] = this.SPACE;
	}

	public LinkedList<Long> calculateAvailableMoves(int color) {
		LinkedList<Long> pieces = (color == this.WHITE) ? this.blackPieces : this.whitePieces;
		for (Long piece : pieces) {
			this.addAvailableMoves(piece, color);
		}
		return this.availableMoves;
	}

	public boolean valid(int initI, int initJ, int stepI, int stepJ, int color) {
		int size = this.board.length;
		for (int i = initI, j = initJ; i >= 0 && i < size && j >= 0 && j < size; i += stepI, j += stepJ) {
			if (this.board[i][j] == this.SPACE) {
				return false;
			} else if (this.board[i][j] == color) {
				return true;
			}
		}
		return false;
	}

	public void addAvailableMoves(Long piece, int color) {
		int[] coords = Space.decode(piece);
		int x = coords[0], y = coords[1];

		// Revisando adyacentes

		// Arriba
		try {
			if (this.board[y - 1][x] == this.SPACE) {
				addtoList(y, x, 1, 0, color);
			}
		} catch (Exception e) {
		}

		// Diagonal arriba derecha
		try {
			if (this.board[y - 1][x + 1] == this.SPACE) {
				addtoList(y, x, 1, -1, color);
			}
		} catch (Exception e) {
		}

		// Derecha
		try {
			if (this.board[y][x + 1] == this.SPACE) {
				addtoList(y, x, 0, -1, color);
			}
		} catch (Exception e) {
		}

		// Diagonal abajo derecha
		try {
			if (this.board[y + 1][x + 1] == this.SPACE) {
				addtoList(y, x, -1, -1, color);
			}
		} catch (Exception e) {
		}

		// Abajo
		try {
			if (this.board[y + 1][x] == this.SPACE) {
				addtoList(y, x, -1, 0, color);
			}
		} catch (Exception e) {
		}

		// Diagonal abajo izquierda
		try {
			if (this.board[y + 1][x - 1] == this.SPACE) {
				addtoList(y, x, -1, 1, color);
			}
		} catch (Exception e) {
		}

		// Izquierda
		try {
			if (this.board[y][x - 1] == this.SPACE) {
				addtoList(y, x, 0, 1, color);
			}
		} catch (Exception e) {
		}

		// Diagonal arriba izquierda
		try {
			if (this.board[y - 1][x - 1] == this.SPACE) {
				addtoList(y, x, 1, 1, color);
			}
		} catch (Exception e) {
		}
	}

	public void addtoList(int initI, int initJ, int stepI, int stepJ, int color) {

		Long key = null;

		if (this.valid(initI, initJ, stepI, stepJ, color)) {
			key = Space.encode(initJ - stepJ, initI - stepI);
			if (!this.availableMoves.contains(key)) {
				this.availableMoves.add(key);
			}
		}
	}

}
