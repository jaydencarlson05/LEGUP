package edu.rpi.legup.model.gameboard.regions;

import edu.rpi.legup.model.gameboard.PuzzleElement;


/**
 * RegionBorder represents a one cell long line that is used to border a cell region.
 * It can either be a horizontal or a vertical border, based on its type
 *
 */
public class RegionBorder extends PuzzleElement<Integer> {
    private final RegionBorderType type;


    /**
     * Constructs a RegionBorder with the specified border type.
     *
     * @param _type the type of the border cell
     */
    public RegionBorder(RegionBorderType _type)
    {
        super();
        type = _type;
        this.setModifiable(false);
    }


    /**
     * Creates a deep copy of this RegionBorder.
     *
     * @return a new RegionBorder with the same properties
     */
    public PuzzleElement<Integer> copy() {
        RegionBorder copy = new RegionBorder(type);
        copy.setIndex(index);
        this.setModifiable(false);
        return copy;
    }


    /**
     * Gets the type of this border.
     *
     * @return the RegionBorderType representing this border
     */
    public RegionBorderType getType() {return type;}
}
