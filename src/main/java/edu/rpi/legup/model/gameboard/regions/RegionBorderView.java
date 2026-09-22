package edu.rpi.legup.model.gameboard.regions;

import edu.rpi.legup.ui.boardview.ElementView;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class RegionBorderView extends ElementView {
    private final RegionBorderType type;

    /**
     * Constructs a StarBattleBorderView for the given border element.
     *
     * @param border the StarBattleBorder associated with this view
     */
    public RegionBorderView(RegionBorder border) {
        super(border);
        type = border.getType();
    }

    /**
     * Gets the PuzzleElement associated with this view
     *
     * @return PuzzleElement associated with this view
     */
    @Override
    public RegionBorder getPuzzleElement() {
        return (RegionBorder) super.getPuzzleElement();
    }

    /**
     * Draws the border view, including case and hover overlays if applicable.
     *
     * @param graphics2D the Graphics2D context used for rendering
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        drawElement(graphics2D);
        if (isShowCasePicker() && isCaseRulePickable()) {
            drawCase(graphics2D);
            if (isHover()) {
                drawHover(graphics2D);
            }
        }
    }

    /**
     * Draws the visual representation of the border based on its type.
     *
     * @param graphics2D the Graphics2D context used for rendering
     */
    @Override
    public void drawElement(Graphics2D graphics2D) {
        Graphics2D g = (Graphics2D) graphics2D.create();
        float xSize = size.width;
        float ySize = size.height;

        g.setColor(UIManager.getColor("StarBattle.borderColor"));
        g.setStroke(
                new BasicStroke(
                        UIManager.getInt("StarBattle.regionBorderWidth"),
                        BasicStroke.CAP_SQUARE,
                        BasicStroke.JOIN_MITER));
        if (type == RegionBorderType.HORIZONTAL) { // minimize ySize / height
            g.draw(new Line2D.Double(location.x, location.y, location.x + xSize, location.y));
        } else if (type == RegionBorderType.VERTICAL) { // minimize xSize / width
            g.draw(new Line2D.Double(location.x, location.y, location.x, location.y + ySize));
        }
        g.dispose();
        // This needs more work but it'll do for now (add  - (xSize / 2) to location.y and x that
        // don't have it)
        // Also, look into changing width of borders, size of cell = 30 units
    }
}
