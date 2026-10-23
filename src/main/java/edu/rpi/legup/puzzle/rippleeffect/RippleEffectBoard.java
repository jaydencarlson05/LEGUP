package edu.rpi.legup.puzzle.rippleeffect;

import edu.rpi.legup.model.gameboard.GridBoard;

import java.util.HashSet;
import java.util.Set;

public class RippleEffectBoard extends GridBoard {
    private Set<RippleEffectRegion> regions;

    public RippleEffectBoard(int width, int height)
    {
        super(width, height);
        this.regions = new HashSet<>();
    }

    public Set<RippleEffectRegion> getRegions() {return regions;}

    public void addRegion(RippleEffectRegion region) {regions.add(region);}
}