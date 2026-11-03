package edu.rpi.legup.puzzle.rippleeffect.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.ContradictionRule;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectBoard;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCell;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCellType;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectRegion;

public class EqualDigitsInRegionContradictionRule extends ContradictionRule {

    public EqualDigitsInRegionContradictionRule()
    {
        super(
                "RIPL-CONT-0001",
                "Equal Digits in Region",
                "Two equal numbers exist in the same region",
                "edu/rpi/legup/images/rippleeffect/contradictions/DuplicateNumber.png"
        );
    }

    @Override
    public String checkContradictionAt(Board board, PuzzleElement puzzleElement) {
        RippleEffectBoard reBoard = (RippleEffectBoard) board;
        RippleEffectCell cell = (RippleEffectCell) board.getPuzzleElement(puzzleElement);
        if (cell.getType() != RippleEffectCellType.NUMBER)
            return super.getInvalidUseOfRuleMessage() + ": Cell must be a numbered cell";

        RippleEffectRegion region = reBoard.getRegion(cell);
        for (RippleEffectCell c : region.getCells())
        {
            if (c.getData().equals(cell.getData()) && !c.getLocation().equals(cell.getLocation()))
            {
                return null;
            }
        }
        return super.getNoContradictionMessage() + ": This cell does not share a value with another cell in its region";
    }
}
