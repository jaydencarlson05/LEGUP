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

public class FinishRegionCaseRule extends CaseRule {

    public FinishRegionCaseRule() {
        super(
                "RIPL-CASE-0001",
                "Finish Region",
                "Finish the region in up to nine ways",
                "edu/rpi/legup/images/rippleeffect/cases/FinishRegion.png"
        );
    }

    @Override
    public Board getApplicableLocationsBoard(Board board) {
        RippleEffectBoard reBoard = (RippleEffectBoard) board;
        reBoard.setCaseRule(this);
        reBoard.setModifiable(false);
        for (PuzzleElement element : reBoard.getPuzzleElements())
        {
            if (((RippleEffectCell) element).getType() == RippleEffectCellType.UNKNOWN)
                reBoard.addCasePickableElement(element);
        }
        return reBoard;
    }

    /**
     * Gets the permutations of the list of elements and stores them in result
     *
     * @param elements List of integers to permute
     * @param current Current list for recursion - should be given as a new list
     * @param result List of permutations for output
     */
    private void getPermutations(List<Integer> elements, List<Integer> current, List<List<Integer>> result)
    {
        if (current.size() == elements.size())
        {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int element : elements)
        {
            if (current.contains(element)) continue;

            current.add(element);
            getPermutations(elements, current, result);
            current.removeLast();
        }
    }

    @Override
    public ArrayList<Board> getCasesFrom(Board board, PuzzleElement puzzleElement) {
        ArrayList<Board> cases = new ArrayList<>();
        if (puzzleElement == null)
            return cases;

        RippleEffectBoard reBoard = (RippleEffectBoard) board;
        RippleEffectCell cell = (RippleEffectCell) puzzleElement;
        RippleEffectRegion region = reBoard.getRegion(cell);

        // Get the unknown cells in the region and the values they could contain
        Set<Integer> missingNumsSet = new HashSet<>();
        List<RippleEffectCell> unknownCells = new ArrayList<>();
        for (int i = 1; i <= region.getSize(); ++i)
            missingNumsSet.add(i);
        for (RippleEffectCell c : region.getCells())
        {
            if (c.getType() == RippleEffectCellType.UNKNOWN)
                unknownCells.add(c);
            else
                missingNumsSet.remove(c.getData());
        }

        // Check for too many cases


        // Create a new board for each permutation of values
        List<Integer> missingNums = new ArrayList<>(missingNumsSet);
        List<List<Integer>> permutations = new ArrayList<>();
        getPermutations(missingNums, new ArrayList<>(), permutations);
        for (List<Integer> permutation : permutations)
        {
            RippleEffectBoard newBoard = reBoard.copy();
            for (int i = 0; i < permutation.size(); ++i)
                newBoard.getCell(unknownCells.get(i).getLocation()).setData(permutation.get(i));
            cases.add(newBoard);
        }
        return cases;
    }

    @Override
    public String checkRuleRawAt(TreeTransition transition, PuzzleElement puzzleElement) {
        return null;
    }
}
