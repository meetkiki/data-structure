package arithmetic.search;

import arithmetic.evaluation.ReversiEvaluation;
import bean.BoardChess;
import bean.MinimaxResult;
import bean.Move;
import bean.WeightIndividual;
import game.GameRule;
import utils.BoardUtil;

import java.util.LinkedList;

import static common.Constant.MAX;
import static common.Constant.MIN;

public class NegaScoutAgent extends SearchAlgorithm {


    private static int mMaxPly = 10;

    private static final ReversiEvaluation evaluation = new ReversiEvaluation(WeightIndividual.DEFAULT);


    public MinimaxResult search(BoardChess board, int depth) {
        try {
            return abNegascout(board, mMaxPly, MIN, MAX);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("negaScoutAgent error.", e);
        }
    }

    public static MinimaxResult abNegascout(BoardChess board, int depth, float alpha, float beta) {
        // Check if we have done recursing
        if (depth == 0) {
            return MinimaxResult.builder().mark(evaluation.currentValue(board)).depth(depth).build();
        }

        float currentScore;
        float bestScore = MIN;
        Move bestMove = null;
        // Keep track the test window value
        float adaptiveBeta = beta;

        // Generates all possible moves
        LinkedList<Byte> moves = new LinkedList<>();
        GameRule.validMoves(board, moves);
        if (moves.isEmpty())
            return abNegascout(board, depth, -beta, -alpha).inverseMark();
//    	bestMove = BoardUtil.convertMove(BoardUtil.rightShift(moves.getFirst(), Constant.BITVALUE));
        bestMove = BoardUtil.convertMove(moves.getFirst());

        // Go through each move
        for (Byte move : moves) {
//			byte move = BoardUtil.rightShift(curMove, Constant.BITVALUE);
            GameRule.makeMove(board, move);
            // Recurse
            MinimaxResult current = abNegascout(board, depth - 1, -adaptiveBeta, -Math.max(alpha, bestScore));

            currentScore = -current.getMark();
            GameRule.unMove(board);
            // Update bestScore
            if (currentScore > bestScore) {
                // if in 'narrow-mode' then widen and do a regular AB negamax search
                if (adaptiveBeta == beta || depth <= (mMaxPly - 2)) {
                    bestScore = currentScore;
                    bestMove = BoardUtil.convertMove(move);
                } else { // otherwise, we can do a Test
                    current = abNegascout(board, depth - 1, -beta, -currentScore);
                    bestScore = -current.getMark();
                    bestMove = BoardUtil.convertMove(move);
                }

                // If we are outside the bounds, the prune: exit immediately
                if (bestScore >= beta) {
                    return MinimaxResult.builder().mark(bestScore).depth(depth).move(bestMove).build();
                }

                // Otherwise, update the window location
                adaptiveBeta = Math.max(alpha, bestScore) + 1;
            }
        }
        return MinimaxResult.builder().depth(depth).mark(bestScore).move(bestMove).build();
    }
}
