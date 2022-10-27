/** 
   The MPiece class is a class for movable pieces.
   
*/
public class MPiece extends Piece {
   
   private int rank;
   
   /**Constructor of a movable piece.
      @param piece the name of the piece
      @param color which player the piece belongs to
   */
   public MPiece(char piece, boolean color) {
      super(piece, color);
      if (piece == 'm' || piece == 'M') {
         this.rank = 10;
      }
      if (piece == 'g' || piece == 'G') {
         this.rank = 9;
      }
      if (piece == 'c' || piece == 'C') {
         this.rank = 8;
      }
      if (piece == 'j' || piece == 'J') {
         this.rank = 7;
      }
      if (piece == 'p' || piece == 'P') {
         this.rank = 6;
      }
      if (piece == 'l' || piece == 'L') {
         this.rank = 5;
      }
      if (piece == 's' || piece == 'S') {
         this.rank = 4;
      }
      if (piece == 'i' || piece == 'I') {
         this.rank = 3;
      }
      if (piece == 'o' || piece == 'O') {
         this.rank = 2;
      }
      if (piece == 'y' || piece == 'Y') {
         this.rank = 1;
      }
   }
   /**Constructor of a movable piece.
      @param piece the name of the piece
      @param color which player the piece belongs to
      @param rank the rank of the piece in int
   */
   public MPiece(char piece, boolean color, int rank) {
      super(piece, color);
      this.rank = rank;
   }
   /**Method to move a piece.
      @param board the game board
      @param move the move that the piece will make
      @return isMoveGood tells if the move is successful
   */
     
   public boolean makeMove(Board board, Move move) {
      boolean isMoveGood = false;
      Cell[][] temp = board.getGrid();
      int sr = move.getSr();
      int sc = move.getSc();
      int dr = move.getDr();
      int dc = move.getDc();
      boolean attackEmpty = false;
      boolean defendEmpty = false;
      Piece attack = temp[sr][sc].getPiece();
      Piece defend = temp[dr][dc].getPiece();
      if (attack == null) {
         attackEmpty = temp[sr][sc].isEmpty();
      }
      if (defend == null) {
         defendEmpty = temp[dr][dc].isEmpty();
      }
      if (!defendEmpty) { 
         isMoveGood = false;
      }
      if (attack == null) { 
         isMoveGood = false;
      }
      if (attack != null && defendEmpty) {
         if (attack instanceof MPiece) {
            temp[move.getDr()][move.getDc()] = temp[move.getSr()][move.getSc()];
            temp[move.getSr()][move.getSc()] = 
               new Cell(move.getSr(), move.getSc(), false);
            board.setGrid(temp);
            isMoveGood = true;
         }
      }
      if (attack != null && defend != null) {
         if (Stratego.getWinner(attack, defend) == 0) {
            temp[move.getDr()][move.getDc()] 
               = temp[move.getSr()][move.getSc()];
            temp[move.getSr()][move.getSc()] 
               = new Cell(move.getSr(), move.getSc(), false);
            board.setGrid(temp);
            isMoveGood = true;
         }
         if (Stratego.getWinner(attack, defend) == 1) {
            temp[move.getSr()][move.getSc()] 
               = new Cell(move.getSr(), move.getSc(), false);
            board.setGrid(temp);
            isMoveGood = true;
         }
         if (Stratego.getWinner(attack, defend) == 2) {
            temp[move.getSr()][move.getSc()] 
               = new Cell(move.getSr(), move.getSc(), false);
            temp[move.getDr()][move.getDc()] 
               = new Cell(move.getDr(), move.getDc(), false);
            isMoveGood = true;
         }
         if (Stratego.getWinner(attack, defend) == 3) { 
            isMoveGood = false;
         }
      }
      return isMoveGood;
   }
   /**Method to check whether the move follows the rules.
      @param move the move that the piece will make
      @return legal whether the move is legal
   */  
   
   public boolean isLegalMove(Move move) {
      boolean legal = true;
      int sr = move.getSr();
      int sc = move.getSc();
      int dr = move.getDr();
      int dc = move.getDc();
      if (rank == 2) {
         if (!move.isHorizontal() && !move.isVertical()) {
            legal = false;
         }
      }
      else {
         if (!move.isHorizontal() && !move.isVertical()) {
            legal = false;
         }
         if (move.isHorizontal()) {
            if ((sc - dc) != 1 && (dc - sc) != 1) {
               legal = false;
            }
         }
         if (move.isVertical()) {
            if ((sr - dr) != 1 && (dr - sr) != 1) {
               legal = false;
            }
         }
      }
      return legal;
   }
   /**Method to return the rank of a piece.
      @return rank of the piece.
   */  
   
   public int getRank() {
      return rank;
   }
}