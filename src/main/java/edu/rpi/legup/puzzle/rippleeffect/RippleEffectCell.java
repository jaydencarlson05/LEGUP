package edu.rpi.legup.puzzle.rippleeffect;

import edu.rpi.legup.model.gameboard.GridCell;
import edu.rpi.legup.puzzle.starbattle.StarBattleCell;

import java.awt.Point;

public class RippleEffectCell extends GridCell<Integer> {

    /**
     * Constructs a new RippleEffectCell with the given data and location
     *
     * @param value value for the data of the cell to be set to
     * @param location location for the cell to be set to
     */
    public RippleEffectCell(int value, Point location) {
        super(value, location);
    }


    /**
     * Performs a deep copy of the RippleEffectCell
     *
     * @return an independent deep copy of this cell
     */
    @Override
    public RippleEffectCell copy()
    {
        RippleEffectCell copy = new RippleEffectCell(data, (Point) location.clone());
        copy.setIndex(index);
        copy.setModifiable(isModifiable);
        copy.setGiven(isGiven);
        copy.setGoal(isGoal);
        return copy;
    }

    /**
     * Gets the RippleEffectCellType of the cell
     *
     * @return UNKNOWN if data == 0, NUMBER otherwise
     */
    public RippleEffectCellType getType()
    {
        if (data == 0) return RippleEffectCellType.UNKNOWN;
        return RippleEffectCellType.NUMBER;
    }

    /**
     * Returns true if the cell is known, false otherwise
     *
     * @return getType() != RippleEffectCellType.UNKNOWN
     */
    @Override
    public boolean isKnown()
    {
        return getType() != RippleEffectCellType.UNKNOWN;
    }
}