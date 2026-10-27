package edu.rpi.legup.puzzle.rippleeffect;

import edu.rpi.legup.model.gameboard.GridBoard;
import edu.rpi.legup.model.gameboard.GridRegion;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.max;


public class RippleEffectBoard extends GridBoard {
    private final Set<RippleEffectRegion> regions;

    /**
     * Constructs a new RippleEffectBoard with the given width and height
     *
     * @param width width of board
     * @param height height of board
     */
    public RippleEffectBoard(int width, int height)
    {
        super(width, height);
        this.regions = new HashSet<>();
    }

    /**
     * Constructs a new RippleEffectBoard with dimensions size x size
     *
     * @param size length and width of board
     */
    public RippleEffectBoard(int size)
    {
        super(size);
        this.regions = new HashSet<>();
    }

    /**
     * Gets the RippleEffectCell at the given coordinates
     *
     * @param x x location of the cell
     * @param y y location of the cell
     * @return the RippleEffectCell at position (x, y)
     */
    @Override
    public RippleEffectCell getCell(int x, int y) {return (RippleEffectCell) super.getCell(x, y);}

    /**
     * Gets the RippleEffectCell at the given location
     *
     * @param location Point with (x, y) coordinates to retrieve from
     * @return RippleEffectCell at the given location
     */
    @Override
    public RippleEffectCell getCell(Point location) {return (RippleEffectCell) super.getCell(location);}

    /**
     * Returns the set of GridRegions in this board
     *
     * @return the set of GridRegions in this board
     */
    public Set<RippleEffectRegion> getRegions() {return regions;}

    /**
     * Returns the RippleEffectRegion that contains the given cell
     *
     * @param cell cell to get the containing region of
     */
    public RippleEffectRegion getRegion(RippleEffectCell cell)
    {
        for (RippleEffectRegion region : regions)
        {
            if (region.contains(cell)) {return region;}
        }
        return null;
    }


    /**
     * Adds a region to the board and updates max region size
     *
     * @param region RippleEffectRegion to add to the board
     */
    public void addRegion(RippleEffectRegion region) {regions.add(region);}

    /**
     * Returns the cell count of the largest region on the board
     *
     * @return the cell count of the largest region on the board
     */
    public int getMaxRegionSize()
    {
        int maxRegionSize = 0;
        for (RippleEffectRegion region : regions)
        {
            maxRegionSize = max(maxRegionSize, region.getSize());
        }
        return maxRegionSize;
    }

    @Override
    public @NotNull RippleEffectBoard copy()
    {
        RippleEffectBoard copy = new RippleEffectBoard(getWidth(), getHeight());
        for (RippleEffectRegion region : regions)
        {
            copy.addRegion(region.copy());
        }
        for (int x = 0; x < this.dimension.width; x++) {
            for (int y = 0; y < this.dimension.height; y++) {
                copy.setCell(x, y, getCell(x, y).copy());
            }
        }
        for (PuzzleElement e : modifiedData) {
            copy.getPuzzleElement(e).setModifiable(false);
        }
        return copy;
    }
}