package edu.rpi.legup.puzzle.starbattle;

import edu.rpi.legup.model.gameboard.GridBoard;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class StarBattleBoard extends GridBoard {

    private int size;
    private int puzzleNum;
    private Set<StarBattleRegion> regions;

    /**
     * Constructs a StarBattleBoard with the specified size and puzzle number.
     *
     * @param size the dimension of the square board
     * @param num the puzzle identifier number
     */
    public StarBattleBoard(int size, int num) {
        super(size, size);
        this.size = size;
        this.puzzleNum = num;
        this.regions = new HashSet<>();
        for (int i = 0; i < size; i++) {
            regions.add(new StarBattleRegion());
        }
    }

    /**
     * Retrieves the StarBattleCell at the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the StarBattleCell at the specified position
     */
    @Override
    public StarBattleCell getCell(int x, int y) {
        return (StarBattleCell) super.getCell(x, y);
    }

    /*
    public StarBattleCell getCell(int groupIndex, int x, int y) {
        return getCell(x + (groupIndex % groupSize) * groupSize, y + (groupIndex / groupSize) * groupSize);
    }*/

    /**
     * Gets the size of the board.
     *
     * @return the board dimension
     */
    public int getSize() {
        return size;
    }

    /**
     * Retrieves all cells in the specified row.
     *
     * @param rowNum the row index
     * @return a set of cells in the row
     */
    public Set<StarBattleCell> getRow(int rowNum) {
        Set<StarBattleCell> row = new HashSet<>();
        for (int i = 0; i < size; i++) {
            row.add(getCell(i, rowNum));
        }
        return row;
    }

    /**
     * Gets the puzzle number associated with this board.
     *
     * @return the puzzle number
     */
    public int getPuzzleNumber() {
        return puzzleNum;
    }

    /**
     * Retrieves all cells in the specified column.
     *
     * @param colNum the column index
     * @return a set of cells in the column
     */
    public Set<StarBattleCell> getCol(int colNum) {
        Set<StarBattleCell> column = new HashSet<>();
        for (int i = 0; i < size; i++) {
            column.add(getCell(colNum, i));
        }
        return column;
    }


    /**
     * Gets the region associated with the given cell.
     *
     * @param cell the StarBattleCell
     * @return the region the cell belongs to
     */
    public StarBattleRegion getRegion(StarBattleCell cell)
    {
        for (StarBattleRegion r : regions)
        {
            if (r.contains(cell)) {return r;}
        }
        return null;
    }

    public void addRegion(StarBattleRegion region) {regions.add(region);}


    public Set<StarBattleRegion> getRegions() {return regions;}


    /**
     * Counts the number of stars in the specified column.
     *
     * @param columnIndex the column index
     * @return the number of stars in the column
     */
    public int columnStars(int columnIndex) {
        int stars = 0;
        if (columnIndex < size) {
            for (StarBattleCell c : this.getCol(columnIndex)) {
                if (c.getType() == StarBattleCellType.STAR) {
                    ++stars;
                }
            }
        }
        return stars;
    }

    /**
     * Counts the number of stars in the specified row.
     *
     * @param rowIndex the row index
     * @return the number of stars in the row
     */
    public int rowStars(int rowIndex) {
        int stars = 0;
        if (rowIndex < size) {
            for (StarBattleCell c : this.getRow(rowIndex)) {
                if (c.getType() == StarBattleCellType.STAR) {
                    ++stars;
                }
            }
        }
        return stars;
    }

    /**
     * Creates a deep copy of this StarBattleBoard.
     *
     * @return a new independent copy of the board
     */
    public @NotNull StarBattleBoard copy() {
        StarBattleBoard copy = new StarBattleBoard(size, puzzleNum);
        for (StarBattleRegion region : regions)
        {
            StarBattleRegion regionCopy = region.copy();
            copy.addRegion(regionCopy);
            for (StarBattleCell cell : regionCopy.getCells()) {
                copy.setCell(cell.getLocation().x, cell.getLocation().y, cell);
            }
        }
        for (PuzzleElement<?> e : modifiedData) {
            copy.getPuzzleElement(e).setModifiable(false);
        }
        return copy;
    }
}
