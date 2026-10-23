package edu.rpi.legup.puzzle.rippleeffect;

import edu.rpi.legup.model.gameboard.GridRegion;

public class RippleEffectRegion extends GridRegion<RippleEffectCell> {
    /**
     * Returns a deep copy of the RippleEffectRegion
     *
     * @return a deep copy of the RippleEffectRegion
     */
    @Override
    public RippleEffectRegion copy() {
        RippleEffectRegion copy = new RippleEffectRegion();
        for (RippleEffectCell cell : getCells())
        {
            copy.addCell(cell.copy());
        }
        return copy;
    }
}
