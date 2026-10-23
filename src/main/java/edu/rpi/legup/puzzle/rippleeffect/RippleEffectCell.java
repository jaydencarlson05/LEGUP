package edu.rpi.legup.puzzle.rippleeffect;

import edu.rpi.legup.model.gameboard.GridCell;
import edu.rpi.legup.puzzle.starbattle.StarBattleCell;

import java.awt.Point;

public class RippleEffectCell extends GridCell<Integer> {
    public RippleEffectCell(int type, Point location) {
        super(type, location);
    }

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
}