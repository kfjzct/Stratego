/** 
   The rule class keeps track of 
   a couple of past two moves and
   does the necessary checks.
*/
public class Stratego {

   /**
      Method to check whether moving a piece would would go into another piece.
      @param board the board to perform the check on.
      @param move the move to be checked
      @return whether the move is allowed.
   */
   public static boolean crossesAnotherPiece(Board board, Move move) {
      boolean cAP = false;
      int sr = move.getSr();
      int sc = move.getSc();
      int dr = move.getDr();
      int dc = move.getDc();
      Cell[][] temp = board.getGrid();
      if (dr == sr) {
         if (dc > sc) {
            for (int i = sc + 1; i < dc; i++) {
               if (temp[sr][i].getPiece() != null) {
                  cAP = true;
               }
            }
         }
         else {
            for (int i = dc + 1; i < sc; i++) {
               if (temp[sr][i].getPiece() != null) {
                  cAP = true;
               }
            }
         }
      }
      if (dc == sc) {
         if (dr > sr) {
            for (int i = sr + 1; i < dr; i++) {
               if (temp[i][sc].getPiece() != null) {
                  cAP = true;
               }
            }
         }
         else {
            for (int i = dr + 1; i < sr; i++) {
               if (temp[i][sc].getPiece() != null) {
                  cAP = true;
               }
            }
         }
      }
      return cAP; 
   }


   /** method to check if two square rule is respected.
      @param move the movement to check
      @param board the board
      @return whether the rules are met.
   */
   public static boolean twoSquareRule(Board board, Move move) {
      boolean redTurn = board.getTurn();
      boolean tSR = true;
      Move[] l2M = board.getL2Moves(redTurn);
      if (l2M[0].isOpposite(l2M[1]) && move.equals(l2M[0])) {
         tSR = false;
      }
      return tSR;
   }
    
   /** Method to check whether the game is over.
      @param board the board to check on.
      @return an int representing the game state
      0 for game is not over
      1 for red wins
      2 for blue wins
   */
   public static int isGameOver(Board board) {
      boolean redF = false;
      boolean blueF = false;
      boolean redTrapped = true;
      boolean blueTrapped = true;
      int result = 0;
      Cell[][] a = board.getGrid(); 
      Cell[] r = board.getMovables(true); 
      Cell[] b = board.getMovables(false); 
      if (board.hasFlag(true)) {
         redF = true;
      }
      if (board.hasFlag(false)) {
         blueF = true;
      }
      for (int k = 0; k < r.length; k++) {
         if (!board.isTrapped(r[k])) {
            redTrapped = false;
         }    
      }
      for (int l = 0; l < b.length; l++) {
         if (!board.isTrapped(b[l])) {
            blueTrapped = false;
         }
      }
      if (!redF && blueF) {
         result = 2;
      }
      if (redF && !blueF) {
         result = 1;
      }
      if (redF && blueF) {
         if (redTrapped && !blueTrapped) {
            result = 2;
         }
         if (!redTrapped && blueTrapped) {
            result = 1;
         }
      }
      return result;
   }

   /**Method to check whether a move would cross a 
    * lake or land in a lake. HINT: there is a isLake()
    * method in the Board class that can be use here!
      @param board the board
      @param move the movement to check
      @return whether the move would cross a lake
   */
   public static boolean crossesLake(Board board, Move move) {
      int dr = move.getDr();
      int dc = move.getDc();
      int sr = move.getSr();
      int sc = move.getSc();
      boolean result = false;
      Cell[][] tp = board.getGrid();
      if (board.isLake(dr, dc)) {
         result = true;
      }
      if (move.isHorizontal()) {
         if (move.isHorizontalRight()) {
            for (int i = sc + 1; i < dc; i++) {
               if (tp[sr][i].isLake()) {
                  result = true;
                  break;
               }
            }
         }
         else {
            for (int i = dc + 1; i < sc; i++) {
               if (tp[sr][i].isLake()) {
                  result = true;
                  break;
               }
            }
         }
      }
      else {
         if (move.isVerticalDown()) {
            for (int i = sr + 1; i < dr; i++) {
               if (tp[i][sc].isLake()) {
                  result = true;
               }
            }
         }
         else {
            for (int i = dr + 1; i < sr; i++) {
               if (tp[i][sc].isLake()) {
                  result = true;
               }
            }
         }
      }
      return result;
   }
   
   /** Method to get the winner of an attack.
      @param att the piece attacking
      @param def the piece defending
      @return an int representing the winner of the attack. See below.
      0 return means attacker wins
      1 return means defender wins
      2 means both lose
      3 invalid, something is wrong!
   */
   public static int getWinner(Piece att, Piece def) {
      int result = 2;
      if (att instanceof ImPiece) {
         result = 3;
      }
      if (att instanceof MPiece) {
         if (att.isRed() && def.isRed()) {
            result = 3;
         }
         else if (!att.isRed() && !def.isRed()) {
            result = 3;
         }
         else if (def instanceof ImPiece) {
            if (((MPiece) att).getRank() != 3 && !((ImPiece) def).isFlag()) {
               result = 1;
            }
            else if (((MPiece) att).getRank() 
               == 3 && !((ImPiece) def).isFlag()) {
               result = 0;
            }
            else if (((ImPiece) def).isFlag()) {
               result = 0;
            }
         }
         else if (def instanceof MPiece) {
            if (((MPiece) att).getRank() 
               == 1 && ((MPiece) def).getRank() == 10) {
               result = 0;
            }
            else if (((MPiece) att).getRank() == ((MPiece) def).getRank()) {
               result = 2;
            }
            else if (((MPiece) att).getRank() > ((MPiece) def).getRank()) {
               result = 0;
            }
            else if (((MPiece) att).getRank() < ((MPiece) def).getRank()) {
               result = 1;
            }
         }
      }
      return result; 
   }
}
