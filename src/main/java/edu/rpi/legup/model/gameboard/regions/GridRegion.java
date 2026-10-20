package edu.rpi.legup.model.gameboard.regions;

import edu.rpi.legup.model.gameboard.GridCell;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.puzzle.starbattle.StarBattleRegion;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * GridRegion represents a collection of cells within a grid. It manages a list of cells and
 * provides methods to add, remove, and retrieve cells from the region.
 *
 * @param <T> the type of cell managed by the GridRegion
 */
public abstract class GridRegion<T extends GridCell<?>> extends PuzzleElement<Integer> {

    protected Set<T> regionCells;
    protected Path2D border;

    /** Region Constructor */
    public GridRegion() {
        this.regionCells = new HashSet<>();
        this.border = new Path2D.Double();
    }

    /**
     * Adds the cell to the region
     *
     * @param cell cell to be added to the region
     */
    public void addCell(T cell) {
        regionCells.add(cell);
        updateBorderPath();
    }

    /**
     * Removes the cell from the region
     *
     * @param cell cell to be removed from the region
     */
    public void removeCell(T cell) {
        regionCells.remove(cell);
        updateBorderPath();
    }

    /**
     * Returns the list of cells in the region
     *
     * @return list of cells in region
     */
    public Set<T> getCells() {
        return regionCells;
    }

    /**
     * Returns the number of cells in the region
     *
     * @return number of cells in the region
     */
    public int getSize() {
        return regionCells.size();
    }

    /**
     * Returns the Path2D of the border of the region
     *
     * @return path of the region border
     */
    public Path2D getBorder()
    {
        return border;
    }

    /**
     * Returns true if the region contains the given cell, false if it does not
     *
     * @param cell Cell to check if this region contains
     * @return true iff region contains cell
     */
    public boolean contains(T cell)
    {
        return getCellLocations().contains(cell.getLocation());
    }

    /**
     * Returns true if the two regions share a cell list
     *
     * @param other Other region to check equivalence against
     * @return true iff this.regionCells.equals(other.regionCells)
     */
    public boolean equals(StarBattleRegion other)
    {
        return getCellLocations().equals(other.getCellLocations());
    }

    public abstract GridRegion<T> copy();

    /**
     * Returns the set of locations of each cell in the region
     *
     * @return the set of locations of each cell in the region
     */
    public Set<Point> getCellLocations()
    {
        Set<Point> cellLocations = new HashSet<>();
        for (GridCell<?> cell : regionCells)
        {
            cellLocations.add(cell.getLocation());
        }
        return cellLocations;
    }


    /**
     * Updates the border around the region of cells
     *
     */
    public void updateBorderPath()
    {
        border.reset();
        Set<Point> cellLocations = getCellLocations();
        for (Point point: cellLocations)
        {
            int x = point.x; int y = point.y;

            // Top edge
            if (!cellLocations.contains(new Point(x, y-1))) {
                border.moveTo(x, y);
                border.lineTo(x + 1, y);
            }

            // Right edge
            if (!cellLocations.contains(new Point(x+1, y))) {
                border.moveTo(x+1, y);
                border.lineTo(x+1, y+1);
            }

            // Bottom edge
            if (!cellLocations.contains(new Point(x, y+1))) {
                border.moveTo(x, y+1);
                border.lineTo(x+1, y+1);
            }

            // Left edge
            if (!cellLocations.contains(new Point(x-1, y))) {
                border.moveTo(x, y);
                border.lineTo(x, y+1);
            }
        }
    }
}
