package edu.rpi.legup.puzzle.rippleeffect.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.DirectRule;
import edu.rpi.legup.model.tree.TreeNode;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectBoard;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCell;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCellType;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectRegion;

import java.awt.*;

// This rule works but is very strong and overwrites "final cell in region". It may be better to replace it
public class OnlyUnblockedPositionDirectRule extends DirectRule {

    public OnlyUnblockedPositionDirectRule()
    {
        super(
                "RIPL-BASC-0003",
                "Only Unblocked Position",
                "Only one possible position for a value in a region is not covered by other cells in the region, row, or column.",
                "edu/rpi/legup/images/rippleeffect/rules/OnlyUnblockedPosition.png"
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
        RippleEffectRegion region = origBoard.getRegion(origCell);

        DuplicateValueContradictionRule duplicateValueRule = new DuplicateValueContradictionRule();
        InsufficientGapContradictionRule insufficientGapRule = new InsufficientGapContradictionRule();

        // Check that every position in the region but the given contradicts one of the above rules
        for (RippleEffectCell c : region.getCells())
        {
            if (c.getType() == RippleEffectCellType.UNKNOWN)
            {
                int origData = c.getData();
                c.setData(newCell.getData());
                if (c.getLocation().equals(origCell.getLocation()) &&
                    (duplicateValueRule.checkContradictionAt(origBoard, c) == null
                    || insufficientGapRule.checkContradictionAt(origBoard, c) == null))
                {
                    c.setData(origData);
                    return super.getInvalidUseOfRuleMessage() + ": Cell value is invalid";
                }
                else if (!c.getLocation().equals(origCell.getLocation()) &&
                        duplicateValueRule.checkContradictionAt(origBoard, c) != null
                        && insufficientGapRule.checkContradictionAt(origBoard, c) != null)
                {
                    c.setData(origData);
                    return super.getInvalidUseOfRuleMessage() + ": Some other position is allowed";
                }
                c.setData(origData);
            }
        }
        return null;
    }

    @Override
    public Board getDefaultBoard(TreeNode node) {return null;}
}
