package arithmetic.search;

import arithmetic.evaluation.ReversiEvaluation;
import bean.BoardChess;
import bean.MinimaxResult;
import bean.Move;
import bean.WeightIndividual;
import game.GameRule;
import utils.BoardUtil;

import java.util.LinkedList;

public class NegaScoutAgent{
        
    static final int INFINITY = 10000000;
    
    private static int mMaxPly = 10;

	private static final ReversiEvaluation evaluation = new ReversiEvaluation(WeightIndividual.DEFAULT);

	public static MinimaxResult findMove(BoardChess board) {
		return abNegascoutDecision(board);
	}
	
	public static MinimaxResult abNegascoutDecision(BoardChess board){
		MinimaxResult moveScore = null;
		try {
			moveScore = abNegascout(board,mMaxPly,-INFINITY,INFINITY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moveScore;
    }

    public static MinimaxResult abNegascout(BoardChess board, int depth, int alpha, int beta){
    	// Check if we have done recursing
    	if (depth==0){
            return MinimaxResult.builder().mark(evaluation.currentValue(board)).depth(depth).build();
        }
    		
    	int currentScore;
    	int bestScore = -INFINITY;
    	Move bestMove = null;
		// Keep track the test window value
    	int adaptiveBeta = beta;
    	
    	// Generates all possible moves
		LinkedList<Byte> moves = new LinkedList<>();
        GameRule.valid_moves(board,moves);
    	if (moves.isEmpty())
    		return abNegascout(board, depth, -beta,-alpha).inverseMark();
//    	bestMove = BoardUtil.convertMove(BoardUtil.rightShift(moves.getFirst(), Constant.BITVALUE));
    	bestMove = BoardUtil.convertMove(moves.getFirst());

    	// Go through each move
        for (Byte move : moves) {
//			byte move = BoardUtil.rightShift(curMove, Constant.BITVALUE);
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
        			return MinimaxResult.builder().mark(bestScore).depth(depth).move(bestMove).build();
        		}
        		
        		// Otherwise, update the window location
        		adaptiveBeta = Math.max(alpha, bestScore) + 1;
    		}
    	}
    	return MinimaxResult.builder().depth(depth).mark(bestScore).move(bestMove).build();
    }
}
