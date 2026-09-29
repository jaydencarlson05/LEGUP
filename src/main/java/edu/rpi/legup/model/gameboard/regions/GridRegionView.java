package edu.rpi.legup.model.gameboard.regions;

import edu.rpi.legup.ui.boardview.ElementView;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class GridRegionView extends ElementView {
    /**
     * GridRegionView Constructor creates a GridRegion view
     *
     * @param region GridRegion which the view uses to draw
     */
    public GridRegionView(GridRegion<?> region) {
        super(region);
    }


    /**
     * Gets the PuzzleElement associated with this view
     *
     * @return PuzzleElement associated with this view
     */
    @Override
    public GridRegion<?> getPuzzleElement() {return (GridRegion<?>) super.getPuzzleElement();}

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
        Path2D border = ((GridRegion<?>) puzzleElement).getBorder();
        AffineTransform scale = AffineTransform.getScaleInstance(xSize, ySize);
        Path2D scaledBorder = (Path2D) border.createTransformedShape(scale);


        g.draw(scaledBorder);
        g.dispose();
        // This needs more work but it'll do for now (add  - (xSize / 2) to location.y and x that
        // don't have it)
        // Also, look into changing width of borders, size of cell = 30 units
    }
}
