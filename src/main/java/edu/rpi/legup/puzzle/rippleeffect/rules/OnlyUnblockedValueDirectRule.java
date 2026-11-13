package edu.rpi.legup.puzzle.rippleeffect.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.DirectRule;
import edu.rpi.legup.model.tree.TreeNode;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectBoard;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCell;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCellType;

public class OnlyUnblockedValueDirectRule extends DirectRule {

    public OnlyUnblockedValueDirectRule()
    {
        super(
                "RIPL-BASC-0002",
                "Only Unblocked Value",
                "Only one possible value for a cell is not covered by other cells in the region, row, or column.",
                "edu/rpi/legup/images/rippleeffect/rules/OnlyUnblockedValue.png"
        );
    }

    @Override
    protected String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) {
        RippleEffectBoard board = (RippleEffectBoard) transition.getBoard();
        RippleEffectCell newCell = (RippleEffectCell) board.getPuzzleElement(puzzleElement);
        if (newCell.getType() != RippleEffectCellType.NUMBER)
            return super.getInvalidUseOfRuleMessage() + ": Cell must be a number";

        RippleEffectBoard origBoard = ((RippleEffectBoard) transition.getParents().getFirst().getBoard()).copy();
        RippleEffectCell origCell = (RippleEffectCell) origBoard.getPuzzleElement(puzzleElement);

        DuplicateValueContradictionRule duplicateValueRule = new DuplicateValueContradictionRule();
        InsufficientGapContradictionRule insufficientGapRule = new InsufficientGapContradictionRule();

        // Check that every value but the given value contradicts one of the above rules
        for (int i = 1; i <= origBoard.getRegion(origCell).getSize(); ++i)
        {
            origCell.setData(i);
            if (i == newCell.getData() &&
               (duplicateValueRule.checkContradictionAt(origBoard, origCell) == null
                || insufficientGapRule.checkContradictionAt(origBoard, origCell) == null))
            {
                return super.getInvalidUseOfRuleMessage() + ": Cell value is invalid";
            }
            else if (i != newCell.getData() &&
                    duplicateValueRule.checkContradictionAt(origBoard, origCell) != null
                    && insufficientGapRule.checkContradictionAt(origBoard, origCell) != null)
            {
                return super.getInvalidUseOfRuleMessage() + ": Some other value is allowed";
            }
        }
        return null;
    }

    @Override
    public Board getDefaultBoard(TreeNode node) {return null;}
}
