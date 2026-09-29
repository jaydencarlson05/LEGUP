package edu.rpi.legup.model.gameboard.regions;

import edu.rpi.legup.model.gameboard.GridCell;
import edu.rpi.legup.model.gameboard.PuzzleElement;

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

    protected List<T> regionCells;
    protected Path2D border;

    /** Region Constructor */
    public GridRegion() {
        this.regionCells = new ArrayList<>();
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
    public List<T> getCells() {
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
     * Updates the border around the region of cells
     *
     */
    public void updateBorderPath()
    {
        border.reset();
        Set<Point> cellLocations = new HashSet<>();
        for (GridCell<?> cell : regionCells)
        {
            cellLocations.add(cell.getLocation());
        }
        for (Point point: cellLocations)
        {
            int x = point.x; int y = point.y;
            // Top edge
            if (!cellLocations.contains(new Point(x, y-1))) {
                border.moveTo(x-.5, y-.5);
                border.lineTo(x + 1, y);
            }

            // Right edge
            if (!cellLocations.contains(new Point(x+1, y))) {
                border.moveTo(x+.5, y-.5);
                border.lineTo(x, y+1);
            }

            // Bottom edge
            if (!cellLocations.contains(new Point(x, y-1))) {
                border.moveTo(x-.5, y+.5);
                border.lineTo(x+1, y);
            }

            // Left edge
            if (!cellLocations.contains(new Point(x-1, y))) {
                border.moveTo(x-.5, y-.5);
                border.lineTo(x, y+1);
            }
        }
    }
}
