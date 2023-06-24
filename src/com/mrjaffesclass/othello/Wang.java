package com.mrjaffesclass.othello;

import java.util.*; 
/**
 * Player object. Students will extend this class
 * and override the getNextMove method
 * 
 * @author Yonghao Wang
 * @version 1.0
 */


public class Wang extends Player
{   
    
  private Position bestMove = null;
  
  public Wang(String str, int color) {
    super (color);
  }
    
  /**
   * Gets the player name
   * @return        Player name
   */
  public String getName() {
    return this.getClass().getSimpleName();
  }
  
  Position getNextMove(Board board) {


    int maxDepth = 6;   
    boolean maximizingPlayer = true;
    
    int bestScore = minimax(board, maxDepth, maxDepth, maximizingPlayer);
    
    return bestMove;
  }
  
  
    public int minimax(Board gameBoard, int currentDepth, int maxDepth, boolean maximizingPlayer) {
       
       
        if (currentDepth == 0) {
           return evaluateBoard(gameBoard);
           }
        
        if (maximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;
            LinkedList<Position> moves = generateMoves(this,gameBoard);

            /*
            System.out.println(showMoves(this,moves)); 
            */
            if (moves.size() == 0){
                if (currentDepth == maxDepth ){
                    bestMove = null;
                }
                return evaluateBoard(gameBoard);
            } 
            else {
            
                for (Position move : moves) {
                    Board newBoard = cloneBoard(gameBoard);

                    //System.out.println("Clone Board before the move \n");
                    //System.out.println(newBoard.toString());

                    newBoard.makeMove(this, move);
                    
                    /* 
                    System.out.println( this.toString()+" move [ "+ move.getRow()+","+move.getCol()+" ], depth = " + currentDepth+ "\n"); 
                    System.out.println(newBoard.toString()); 
                    */

                    int pushupScore = minimax(newBoard, currentDepth - 1, maxDepth, !maximizingPlayer);
                    if (pushupScore > bestScore) {
                        bestScore = pushupScore;

                      if (currentDepth == maxDepth) {
                        bestMove = move;
                      }

                    }
                    
                    /*
                    System.out.println( "cD = "+ currentDepth + ", pS = "+ pushupScore +","+" bS = " + bestScore + ","+ " bM = " + bestMove+"\n"); 
                    if (currentDepth == 1 ){
                        System.out.println( "------ end of resursive call --------\n"); 
                    }
                    if (currentDepth == maxDepth ){
                        System.out.println( "==== end of outer resursive call ====\n"); 
                    }
                    */
                }
                
                return bestScore;
            }
        }
        else {
            
            Player opponentPlayer = new Player(-this.getColor());
            
            int bestScore = Integer.MAX_VALUE;
            LinkedList<Position> moves = generateMoves(opponentPlayer,gameBoard);
            
            /*
            System.out.println(showMoves(opponentPlayer,moves)); 
            */
            
            if (moves.size() == 0){
                return evaluateBoard(gameBoard);
            } 
            else {
            
                for (Position move : moves) {
                    Board newBoard = cloneBoard(gameBoard);

                    newBoard.makeMove(opponentPlayer, move);

                    /*
                    System.out.println( opponentPlayer.toString()+" move [ "+ move.getRow()+","+move.getCol()+" ], depth = " + currentDepth+"\n"); 
                    System.out.println(newBoard.toString()); 
                    */
                    int pushupScore = minimax(newBoard, currentDepth - 1, maxDepth, !maximizingPlayer);
                    if (pushupScore < bestScore) {
                        bestScore = pushupScore;
                      if (currentDepth == maxDepth) {
                        bestMove = move;
                      }
                    }
                    
                    /*
                    System.out.println( "cD = "+ currentDepth + ", pS = "+ pushupScore +","+" bS = " + bestScore + ","+ " bM = " + bestMove+"\n");
                    if (currentDepth == 1 ){
                        System.out.println( "------ reach the leave of resursive call --------\n"); 
                    }
                    if (currentDepth == maxDepth ){
                        System.out.println( "==== end of resursive call iteration ====\n"); 
                    }
                    */
                }
            return bestScore;    
            }
           
        }
    }
    
    public Board cloneBoard(Board gameBoard) {

        Player opponentPlayer = new Player(-this.getColor());
        Board newBoard = new Board();

        for (int row = 0; row < Constants.SIZE; row++) {
          for (int col = 0; col < Constants.SIZE; col++) {
           
            Position pos = new Position(row, col);
            if(gameBoard.getSquare(pos).getStatus() == this.getColor()) {
                newBoard.setSquare(this, pos);
            }
            else if (gameBoard.getSquare(pos).getStatus() == opponentPlayer.getColor()) {
                newBoard.setSquare(opponentPlayer, pos);
            }
            else{
                ; // leave square empty
            }
            
          }
        }
        return newBoard;
    }
  
    public LinkedList<Position> generateMoves(Player player, Board gameBoard) {
       
    LinkedList<Position> moves = new LinkedList<Position>();
            
    for (int row = 0; row < Constants.SIZE; row++) {
      for (int col = 0; col < Constants.SIZE; col++) {
        Position pos = new Position(row, col);
        if (gameBoard.isLegalMove(player, pos)) {
          moves.add(pos);
        }
      }
    }
        
    return moves;
            
    }
    
    public int evaluateBoard(Board gameBoard) {

      int blackPieces = 0;
      int whitePiecess = 0;

      // Get the score
      int black = gameBoard.countSquares(Constants.BLACK);
      int white = gameBoard.countSquares(Constants.WHITE);
      
      blackPieces += black;
      whitePiecess += white;

      int cornerBonus = 10;
      
      Position pos1 = new Position(0,Constants.SIZE-1);
      Position pos2 = new Position(Constants.SIZE-1,0);
      Position pos3 = new Position(Constants.SIZE-1,Constants.SIZE-1);
      Position pos4 = new Position(0,0);
      
      //Square sqr = new Square(Constants.EMPTY);
      //sqr =  gameBoard.getSquare(pos).getStatus()
      
      if (gameBoard.getSquare(pos1).getStatus() == (Constants.BLACK)) {
          blackPieces += cornerBonus;
      }
      if (gameBoard.getSquare(pos2).getStatus() == (Constants.BLACK)) {
          blackPieces += cornerBonus;
      }
      if (gameBoard.getSquare(pos3).getStatus() == (Constants.BLACK)) {
          blackPieces += cornerBonus;
      }
      if (gameBoard.getSquare(pos4).getStatus() == (Constants.BLACK)) {
          blackPieces += cornerBonus;
      }
      if (gameBoard.getSquare(pos1).getStatus() == (Constants.WHITE)) {
          whitePiecess += cornerBonus;
      }
      if (gameBoard.getSquare(pos2).getStatus() == (Constants.WHITE)) {
          whitePiecess += cornerBonus;
      }
      if (gameBoard.getSquare(pos3).getStatus() == (Constants.WHITE)) {
          whitePiecess += cornerBonus;
      }
      if (gameBoard.getSquare(pos4).getStatus() == (Constants.WHITE)) {
          whitePiecess += cornerBonus;
      }
      
      if (this.getColor() == Constants.BLACK) {
        return blackPieces - whitePiecess;
      } 
      else {
        return whitePiecess - blackPieces;
      }
  }
  
  
  public String showMoves(Player player, LinkedList<Position> moves) {
    StringBuilder sb = new StringBuilder(player.toString() + " has the following legal moves\n");
    
    if (moves.size() == 0) {
       sb.append("oops! ...actually no legal moves for "+player.toString() +"\n");  
    }
    for (int i = 0; i < moves.size(); i++) {
       sb.append(i +" = [ "+ moves.get(i).getRow()+","+moves.get(i).getCol()+" ]\n");
    }
    return sb.toString();
  }

}
