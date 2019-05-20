package arithmetic;

import bean.BoardChess;
import bean.MinimaxResult;
import bean.Move;
import common.Bag;
import common.Constant;
import game.GameRule;
import utils.BoardUtil;

import java.util.LinkedList;

public class NegaScoutAgent{
        
    static final int INFINITY = 1000000;
    
    private static int mMaxPly = 5;
	
	public static MinimaxResult findMove(BoardChess board) {
		return abNegascoutDecision(board);
	}
	
	public static MinimaxResult abNegascoutDecision(BoardChess board){
        MinimaxResult moveScore = abNegascout(board,mMaxPly,-INFINITY,INFINITY);
    	return moveScore;
    }

    public static MinimaxResult abNegascout(BoardChess board, int depth, int alpha, int beta){
    	// Check if we have done recursing
    	if (depth==0){
            return new MinimaxResult(ReversiEvaluation.currentValue(board),depth,null);
        }
    		
    	int currentScore;
    	int bestScore = -INFINITY;
    	Move bestMove = null;
		// Keep track the test window value
    	int adaptiveBeta = beta;
    	
    	// Generates all possible moves
		LinkedList<Integer> moves = new LinkedList<>();
        GameRule.valid_moves(board,moves);
    	if (moves.isEmpty())
    		return new MinimaxResult(bestScore,depth,null);
    	bestMove = BoardUtil.convertMove(BoardUtil.rightShift(moves.getFirst(), Constant.BITVALUE));
    	
    	// Go through each move
        for (Integer curMove : moves) {
			byte move = BoardUtil.rightShift(curMove, Constant.BITVALUE);
			GameRule.make_move(board, move);
    		// Recurse
			MinimaxResult current = abNegascout(board, depth - 1, -adaptiveBeta, - Math.max(alpha,bestScore));
    		
    		currentScore = - current.getMark();
			GameRule.un_move(board);
    		// Update bestScore
    		if (currentScore>bestScore){
    			// if in 'narrow-mode' then widen and do a regular AB negamax search
    			if (adaptiveBeta == beta || depth <= (mMaxPly-2)){
    				bestScore = currentScore;
					bestMove = BoardUtil.convertMove(move);
    			}else{ // otherwise, we can do a Test
    				current = abNegascout(board, depth - 1, -beta, -currentScore);
    				bestScore = - current.getMark();
    				bestMove = BoardUtil.convertMove(move);
    			}
    			
    			// If we are outside the bounds, the prune: exit immediately
        		if(bestScore>=beta){
        			return new MinimaxResult(bestScore,depth,bestMove);
        		}
        		
        		// Otherwise, update the window location
        		adaptiveBeta = Math.max(alpha, bestScore) + 1;
    		}
    	}
    	return MinimaxResult.builder().depth(depth).mark(bestScore).move(bestMove).build();
    }
}
