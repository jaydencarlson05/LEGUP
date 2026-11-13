package edu.rpi.legup.puzzle.rippleeffect.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.DirectRule;
import edu.rpi.legup.model.tree.TreeNode;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectBoard;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCell;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCellType;

public class FinalCellInRegionDirectRule extends DirectRule {

    public FinalCellInRegionDirectRule()
    {
        super(
                "RIPL-BASC-0001",
                "Final Cell in Region",
                "If only one number is missing from the region, fill it in.",
                "edu/rpi/legup/images/rippleeffect/rules/FinalCellInRegion.png"
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
        origCell.setData(newCell.getData());
        for (RippleEffectCell c : origBoard.getRegion(origCell).getCells())
        {
            if (c.getType() != RippleEffectCellType.NUMBER)
            {
                return super.getInvalidUseOfRuleMessage() + ": Region must have only one cell missing";
            }
        }

        DuplicateValueContradictionRule contraRule = new DuplicateValueContradictionRule();
        if (contraRule.checkContradictionAt(origBoard, puzzleElement) == null)
        {
            return super.getInvalidUseOfRuleMessage() + ": Cell value must be unique to the region";
        }
        return null;
    }

    @Override
    public Board getDefaultBoard(TreeNode node) {return null;}
}
