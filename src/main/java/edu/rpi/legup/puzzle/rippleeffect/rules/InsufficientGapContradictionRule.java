package edu.rpi.legup.puzzle.rippleeffect.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.ContradictionRule;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectBoard;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCell;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCellType;

import java.awt.*;

public class InsufficientGapContradictionRule extends ContradictionRule {

    public InsufficientGapContradictionRule()
    {
        super(
                "RIPL-CONT-0002",
                "Insufficient Gap",
                "Two cells of value x are within x cells of each other",
                "edu/rpi/legup/images/rippleeffect/contradictions/InsufficientGap.png"
        );
    }
    @Override
    public String checkContradictionAt(Board board, PuzzleElement puzzleElement) {
        RippleEffectBoard rbBoard = (RippleEffectBoard) board;
        RippleEffectCell cell = (RippleEffectCell) rbBoard.getPuzzleElement(puzzleElement);
        if (cell.getType() != RippleEffectCellType.NUMBER)
            return super.getInvalidUseOfRuleMessage() + ": Cell must be a numbered cell";

        int val = cell.getData();
        int x = cell.getLocation().x, y = cell.getLocation().y;
        for (int i = -val; i <= val; ++i)
        {
            if (i == 0) {continue;}
            RippleEffectCell other = rbBoard.getCell(x + i, y);
            if (other != null && other.getData() == val)
                return null;
            other = rbBoard.getCell(x, y+i);
            if (other != null && other.getData() == val)
                return null;
        }

        return super.getNoContradictionMessage() + ": This cell has sufficient gap from other known, equal cells";
    }
}
