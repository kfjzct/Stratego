/** 
   Class to represent a stratego board.
   A board is made up of 10x10 grid of 
   cells. It has the basic operations 
   necessary to view and edit the grid.
*/
public final class Board {

   /** singleton instance */
   private static final Board INSTANCE = new Board();
     
   /**The size of the board.*/
   private static final int SIZE = 10;
   
   /**The 2D-array representing the board.*/
   private Cell[][] grid;
   
   /**The last two moves made on the board by red.*/
   private Move[] l2MovesR;
   
   /**The last two moves made on the board by blue.*/ 
   private Move[] l2MovesB; 

   /**whose turn it is; true for red, false for blue */ 
   private boolean turn; 

   /**Constructor for a board.
      Fills the board with empty cells.
   */
   private Board() {
      this.grid = new Cell[SIZE][SIZE];
      for (int i = 0; i < SIZE; i++) {
         for (int j = 0; j < SIZE; j++) {
            this.grid[i][j] = new Cell(i, j, false);
         }
      }
      // the last two moves made on the board by red
      this.l2MovesB = new Move[2];
      // the last two moves made on the board by blue 
      this.l2MovesR = new Move[2]; 
      this.l2MovesB[0] = new Move(-1, -1, -1, -1); 
      this.l2MovesB[1] = new Move(-1, -1, -1, -1);
      this.l2MovesR[0] = new Move(-1, -1, -1, -1);
      this.l2MovesR[1] = new Move(-1, -1, -1, -1);
   }
   
   /** Method to get the only instance of board in the game.
     @return return the instance
   */
   public static Board getInstance() {
      return INSTANCE;
   }
   
   /**A method to return the size of the board.
      @return the size of the board
   */
   public int size() {
      return SIZE;
   }
   
   /**
      Method to display the board to standard out.
      @param showRed whether the values in red cells are visible
      @param showBlue whether the values in blue cells are visible
   */
   public void display(boolean showRed, boolean showBlue) {
      for (int i = 0; i < SIZE; i++) {
         for (int j = 0; j < SIZE; j++) {
            if (grid[i][j].isLake()) {
               System.out.print("X  ");
            }
            else if (!grid[i][j].isEmpty()) {
               Piece p = this.grid[i][j].getPiece();
               if (showBlue && showRed) {
                  System.out.print(p.getCharacter() + "  ");
               } else if (showRed && p.isRed()) {
                  System.out.print(p.getCharacter() + "  ");
               } else if (showBlue && !p.isRed()) {
                  System.out.print(p.getCharacter() + "  ");
               } else {
                  System.out.print("*  ");
               }
            }
            else { // empty cell  
               System.out.print("-  ");
            }
         }
         System.out.println();
      }
      System.out.println();
   }

   /**
      Method to return the grid for the board.
      @return the Cell[][] representing the board.
   */
   public Cell[][] getGrid() {
      return this.grid;
   }

   /**
      Method to assign the grid for the board.
      @param newBoard the Cell[][] to replace the current board.
   */
   public void setGrid(Cell[][] newBoard) {
      this.grid = newBoard;
   }
   
   /**
   Method to return the array that has the last two moves
   on the board. This is helpful with twoSquareRule.
   @param red is red piece or not
   @return the last two moves as an array of type Move
   */
   public Move[] getL2Moves(boolean red) {
      if (red) {
         return l2MovesR;
      }
      else {
         return l2MovesB;
      }
   }

   /**
   Method to set record last move on the board.
   This is helpful with twoSquareRule.
   @param move the move
   @param red is red piece or not
   */
   public void setLMove(Move move, boolean red) {
      if (red) {
         this.l2MovesR[0] = this.l2MovesR[1];
         this.l2MovesR[1] = move;
      }
      else {
         this.l2MovesB[0] = this.l2MovesB[1];
         this.l2MovesB[1] = move;
      }
   }

   
/**
      Method to check if a move would go out of bounds.
      @param move the movement to check the bounds on.
      @return true if it goes out of bounds. False otherwise.
   */
   public boolean isOutOfBound(Move move) {
      int sr = move.getSr();
      int sc = move.getSc();
      int dr = move.getDr();
      int dc = move.getDc();
      return (sr >= SIZE || sr < 0 || sc >= SIZE || sc < 0 || dr >= SIZE 
         || dr < 0 || dc >= SIZE || dc < 0); 
   }
   
   /**
      Method to check if a move lands in a lake on 
      the current board.
      @param row the row
      @param col the column
      @return true if it goes out of bounds. False otherwise.
   */
   public boolean isLake(int row, int col) {
      return grid[row][col].isLake();
   }
   
   /**
      Method to check if a cell is inbound.
      @param i the row
      @param j the column
      @return true if it goes out of bounds. False otherwise.
   */
   public boolean inBound(int i, int j) {
      boolean inBd;
      if (i >= 0 && i <= 9 && j >= 0 && j <= 9) {
         inBd = true;
      }
      else {
         inBd = false;
      }
      return inBd;
   }
   
   /**
   Method to get the count of movable pieces on the board
   for a certain player.
   @param red red player or not
   @return the count
   */
   public int getCountMovables(boolean red) {
      int count = 0;
      for (int i = 0; i < grid.length; i++) {
         for (int j = 0; j < grid[0].length; j++) {
            if (red) {
               if (!grid[i][j].isLake() && grid[i][j].getPiece() != null 
                  && grid[i][j].getPiece().getCharacter() != 'b' 
                  && grid[i][j].getPiece().getCharacter() != 'f'
                  && grid[i][j].getPiece().getCharacter() != 'B' 
                  && grid[i][j].getPiece().getCharacter() != 'F'
                  && Character.isUpperCase(grid[i][j].getPiece()
                  .getCharacter())) {
                  count = count + 1;
               } 
            }
            if (!red) {
               if (!grid[i][j].isLake() && grid[i][j].getPiece() != null 
                  && grid[i][j].getPiece().getCharacter() != 'b' 
                  && grid[i][j].getPiece().getCharacter() != 'f'
                  && grid[i][j].getPiece().getCharacter() != 'B' 
                  && grid[i][j].getPiece().getCharacter() != 'F'
                  && Character.isLowerCase(grid[i][j].getPiece()
                  .getCharacter())) {
                  count = count + 1;
               }
            }
         }
      }  
      return count; 
   }
   
   /**
   Method to get movable pieces on the board
   for a certain player.
   @param red red player or not
   @return an array of cells containing movable pieces
   */
   public Cell[] getMovables(boolean red) {
      int count = 0;
      Cell[] result = new Cell[getCountMovables(red)];
      for (int i = 0; i < grid.length; i++) {
         for (int j = 0; j < grid[0].length; j++) {
            if (red) {
               if (!grid[i][j].isLake() && grid[i][j].getPiece() != null 
                  && grid[i][j].getPiece().getCharacter() != 'b' 
                  && grid[i][j].getPiece().getCharacter() != 'f'
                  && grid[i][j].getPiece().getCharacter() != 'B' 
                  && grid[i][j].getPiece().getCharacter() != 'F'
                  && Character.isUpperCase(grid[i][j].getPiece()
                  .getCharacter())) {
                  result[count] = grid[i][j];
                  count = count + 1;
               } 
            }
            if (!red) {
               if (!grid[i][j].isLake() && grid[i][j].getPiece() != null 
                  && grid[i][j].getPiece().getCharacter() != 'b' 
                  && grid[i][j].getPiece().getCharacter() != 'f'
                  && grid[i][j].getPiece().getCharacter() != 'B' 
                  && grid[i][j].getPiece().getCharacter() != 'F'
                  && Character.isLowerCase(grid[i][j].getPiece()
                  .getCharacter())) {
                  result[count] = grid[i][j];
                  count = count + 1;
               }
            }
         }
      }
      return result;
   }
   
   /**
   Method to check if the flag of a certain player
   is still on the board.
   @param red red player or not
   @return true if the flag of the player is still on 
   the board, false otherwise.
   */
   public boolean hasFlag(boolean red) {
      boolean hF = false;
      for (int i = 0; i < grid.length; i++) {
         for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j].getPiece() != null) {
               if (red) {
                  if (grid[i][j].getPiece().getCharacter() == 'F') {
                     hF = true;
                  }
               }
               if (!red) {
                  if (grid[i][j].getPiece().getCharacter() == 'f') {
                     hF = true;
                  }
               }
            }
         }
      }
      return hF; 
   }
   
   /**
   I made this method on my own to check if a cell. 
   is able to be moved into.
   @param cell the cell that the method checks
   @param red whether the cell is red or not
   @return open tells whether the cell is open
   */
   public boolean openCell(Cell cell, boolean red) {
      boolean open = false;
      if (cell.isEmpty()) {
         open = true;
      }
      if (cell.getPiece() != null) {
         if (red) {
            if (Character.isLowerCase(cell.getPiece().getCharacter())) {
               open = true;
            }
         }
         if (!red) {
            if (Character.isUpperCase(cell.getPiece().getCharacter())) {
               open = true;
            }
         }
      }
      return open;
   }
   
   /**
   Method to check the piece contained by cell is 
   trapped or not (trapped means cannot move!).
   @param cell the cell
   @return true if the the piece contained by cell is 
   trapped, false otherwise.
   */
   public boolean isTrapped(Cell cell) {
      boolean iT = true;
      boolean top = false;
      boolean right = false;
      boolean left = false;
      boolean bottom = false;
      int i = cell.getRow();
      int j = cell.getColumn();
      if (!cell.isLake() && cell.getPiece() != null 
         && cell.getPiece().getCharacter() != 'b' 
         && cell.getPiece().getCharacter() != 'f'
         && cell.getPiece().getCharacter() != 'B' 
         && cell.getPiece().getCharacter() != 'F'
         && Character.isUpperCase(cell.getPiece()
         .getCharacter())) {
         if (inBound(i - 1, j)) {
            if (openCell(grid[i - 1][j], true)) {
               top = true;
            }
         }
         if (inBound(i + 1, j)) {
            if (openCell(grid[i + 1][j], true)) {
               bottom = true;
            }
         }
         if (inBound(i, j - 1)) {
            if (openCell(grid[i][j - 1], true)) {
               left = true;
            }
         }
         if (inBound(i, j + 1)) {
            if (openCell(grid[i][j + 1], true)) {
               right = true;
            }
         }
      }
      if (!cell.isLake() && cell.getPiece() != null 
         && cell.getPiece().getCharacter() != 'b' 
         && cell.getPiece().getCharacter() != 'f'
         && cell.getPiece().getCharacter() != 'B' 
         && cell.getPiece().getCharacter() != 'F'
         && Character.isLowerCase(cell.getPiece()
         .getCharacter())) {
         if (inBound(i - 1, j)) {
            if (openCell(grid[i - 1][j], false)) {
               top = true;
            }
         }
         if (inBound(i + 1, j)) {
            if (openCell(grid[i + 1][j], false)) {
               bottom = true;
            }
         }
         if (inBound(i, j - 1)) {
            if (openCell(grid[i][j - 1], false)) {
               left = true;
            }
         }
         if (inBound(i, j + 1)) {
            if (openCell(grid[i][j + 1], false)) {
               right = true;
            }
         }
      }
      
      if (top || bottom || left || right) {
         iT = false;
      }
      return iT; 
   }
   
   /** return turn. 
    * 
    * @return turn
    */
   public boolean getTurn() {
      return turn;
   }
   
   /** set turn. 
    * 
    * @param b the turn
   */
   public void setTurn(boolean b) {
      this.turn = b;
   }

}
