package edu.rpi.legup.puzzle.rippleeffect.rules;

import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.rules.CaseRule;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectBoard;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCell;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectCellType;
import edu.rpi.legup.puzzle.rippleeffect.RippleEffectRegion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CellValueCaseRule extends CaseRule {

    public CellValueCaseRule() {
        super(
                "RIPL-CASE-0002",
                "Cell Value",
                "A cell must have a numerical value within the size of its region",
                "edu/rpi/legup/images/rippleeffect/cases/CellValue.png"
        );
    }

    @Override
    public Board getApplicableLocationsBoard(Board board) {
        RippleEffectBoard reBoard = (RippleEffectBoard) board.copy();
        reBoard.setCaseRule(this);
        reBoard.setModifiable(false);
        for (PuzzleElement element : reBoard.getPuzzleElements())
        {
            if (((RippleEffectCell) element).getType() == RippleEffectCellType.UNKNOWN)
                reBoard.addCasePickableElement(element);
        }
        return reBoard;
    }

    @Override
    public ArrayList<Board> getCasesFrom(Board board, PuzzleElement puzzleElement) {
        ArrayList<Board> cases = new ArrayList<>();
        if (puzzleElement == null)
            return cases;

        RippleEffectBoard reBoard = (RippleEffectBoard) board;
        RippleEffectCell cell = (RippleEffectCell) puzzleElement;
        RippleEffectRegion region = reBoard.getRegion(cell);

        // Make a new board for each missing value in the region
        DuplicateValueContradictionRule rule = new DuplicateValueContradictionRule();
        for (int i = 1; i <= region.getSize(); ++i)
        {
            RippleEffectBoard newBoard = reBoard.copy();
            RippleEffectCell c = newBoard.getCell(cell.getLocation());
            c.setData(i);
            newBoard.addModifiedData(c);
            if (rule.checkContradictionAt(newBoard, c) != null)
            {
                cases.add(newBoard);
            }
        }

        return cases;
    }

    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) {
        return null;
    }
}
