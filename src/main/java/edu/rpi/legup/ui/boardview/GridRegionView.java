package edu.rpi.legup.ui.boardview;

import edu.rpi.legup.model.gameboard.GridRegion;

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

        g.setColor(UIManager.getColor("GridRegion.borderColor"));
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
    }
}
