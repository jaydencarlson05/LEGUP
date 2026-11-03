package edu.rpi.legup.puzzle.rippleeffect.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.DirectRule;
import edu.rpi.legup.model.tree.TreeNode;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectBoard;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCell;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCellType;

public class FinishRegionDirectRule extends DirectRule {

    public FinishRegionDirectRule()
    {
        super(
                "RIPL-BASC-0001",
                "Finish Region",
                "If only one number is missing from the region, fill it in.",
                "edu/rpi/legup/images/rippleeffect/rules/FinishRegionPlaceholder.png"
        );
    }

    @Override
    protected String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) {
        RippleEffectBoard board = (RippleEffectBoard) transition.getBoard();
        RippleEffectCell newCell = (RippleEffectCell) board.getPuzzleElement(puzzleElement);
        System.out.println("Cell value: " + newCell.getData() + ". Cell type: " + newCell.getType());
        if (newCell.getType() != RippleEffectCellType.NUMBER)
            return super.getInvalidUseOfRuleMessage() + ": Cell must be a number";

        RippleEffectBoard origBoard = ((RippleEffectBoard) transition.getParents().getFirst().getBoard()).copy();
        RippleEffectCell origCell = (RippleEffectCell) origBoard.getPuzzleElement(puzzleElement);
        origCell.setData(newCell.getData());
        origBoard.setCell(origCell.getLocation().x, origCell.getLocation().y, origCell);
        for (RippleEffectCell c : origBoard.getRegion(origCell).getCells())
        {
            if (c.getType() != RippleEffectCellType.NUMBER)
            {
                System.out.println("Cell value: " + c.getData() + ". Cell type: " + c.getType() + ". Cell location: " + c.getLocation());
                return super.getInvalidUseOfRuleMessage() + ": Region must have only one cell missing";
            }
        }

        EqualDigitsInRegionContradictionRule contraRule = new EqualDigitsInRegionContradictionRule();
        if (contraRule.checkContradictionAt(origBoard, puzzleElement) == null)
        {
            return super.getInvalidUseOfRuleMessage() + ": Cell value must be unique to the region";
        }
        return null;
    }

    @Override
    public Board getDefaultBoard(TreeNode node) {return null;}
}
