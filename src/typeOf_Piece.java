
//start typeOf_Piece.java
public class typeOf_Piece {

	private int color;
	public static final int COLOR_WHITE = 0;
	public static final int COLOR_BLACK = 1;

	private int type;
	public static final int TYPE_ROOK = 1;
	public static final int TYPE_KNIGHT = 2;
	public static final int TYPE_BISHOP = 3;
	public static final int TYPE_QUEEN = 4;
	public static final int TYPE_KING = 5;
	public static final int TYPE_PAWN = 6;

	//Chess is played on a square board of
	//eight rows (called ranks and denoted with numbers 1 to 8)
	//and eight columns (called files and denoted with letters a to h) of squares.
	private int row;

	public static final int ROW_1 = 0;
	public static final int ROW_2 = 1;
	public static final int ROW_3 = 2;
	public static final int ROW_4 = 3;
	public static final int ROW_5 = 4;
	public static final int ROW_6 = 5;
	public static final int ROW_7 = 6;
	public static final int ROW_8 = 7;
	public static final int ROW_9 = 8;
		public static final int ROW_10 = 9;
	public static final int ROW_11 = 10;

	private int column;

	public static final int COLUMN_A = 0;
	public static final int COLUMN_B = 1;
	public static final int COLUMN_C = 2;
	public static final int COLUMN_D = 3;
	public static final int COLUMN_E = 4;
	public static final int COLUMN_F = 5;
	public static final int COLUMN_G = 6;
	public static final int COLUMN_H = 7;
	public static final int COLUMN_I = 8;
        public static final int COLUMN_J = 9;
	public static final int COLUMN_K = 10;

	private boolean isCaptured = false;

	public typeOf_Piece(int color, int type, int row, int column) {
		this.row = row;
		this.column = column;
		this.color = color;
		this.type = type;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getColor() {
		return this.color;
	}

	@Override
	public String toString() {
		String strColor = (this.color==COLOR_WHITE?"white":"black");

		String strType = "unknown";
		switch (this.type) {
			case TYPE_BISHOP: strType = "B";break;
			case TYPE_KING: strType = "K";break;
			case TYPE_KNIGHT: strType = "N";break;
			case TYPE_PAWN: strType = "P";break;
			case TYPE_QUEEN: strType = "Q";break;
			case TYPE_ROOK: strType = "R";break;
		}

		String strRow = getRowString(this.row);
		String strColumn = getColumnString(this.column);

		return strColor+" "+strType+" "+strRow+"/"+strColumn;
	}

	public int getType() {
		return this.type;
	}

	public  String getRowString(int row){
		String strRow = "unknown";
		switch (row) {
			case ROW_1: strRow = "1";break;
			case ROW_2: strRow = "2";break;
			case ROW_3: strRow = "3";break;
			case ROW_4: strRow = "4";break;
			case ROW_5: strRow = "5";break;
			case ROW_6: strRow = "6";break;
			case ROW_7: strRow = "7";break;
			case ROW_8: strRow = "8";break;
			case ROW_9: strRow = "9";break;
			case ROW_10: strRow = "10";break;
			case ROW_11: strRow = "11";break;
		}
		return strRow;
	}

	public  String getColumnString(int column){
		String strColumn = "unknown";
		switch (column) {
			case COLUMN_A: strColumn = "A";break;
			case COLUMN_B: strColumn = "B";break;
			case COLUMN_C: strColumn = "C";break;
			case COLUMN_D: strColumn = "D";break;
			case COLUMN_E: strColumn = "E";break;
			case COLUMN_F: strColumn = "F";break;
			case COLUMN_G: strColumn = "G";break;
			case COLUMN_H: strColumn = "H";break;
			case COLUMN_I: strColumn = "I";break;
			case COLUMN_J: strColumn = "J";break;
			case COLUMN_K: strColumn = "K";break;
		}
		return strColumn;
	}

	public void isCaptured(boolean isCaptured) {
		this.isCaptured = isCaptured;
	}

	public boolean isCaptured() {
		return this.isCaptured;
	}

}
//end typeOf_Piece.java