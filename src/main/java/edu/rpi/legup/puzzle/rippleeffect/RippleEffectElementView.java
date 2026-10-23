package edu.rpi.legup.puzzle.rippleeffect;

import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.ui.boardview.GridElementView;

import java.awt.*;

public class RippleEffectElementView extends GridElementView {

    public RippleEffectElementView(RippleEffectCell cell) {
        super(cell);
    }

    /**
     * Gets the PuzzleElement associated with this view
     *
     * @return PuzzleElement associated with this view
     */
    @Override
    public RippleEffectCell getPuzzleElement() {
        return (RippleEffectCell) super.getPuzzleElement();
    }

    @Override
    public void drawElement(Graphics2D graphics2D) {
        RippleEffectCell cell = getPuzzleElement();

        // Draw the white cell
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(location.x, location.y, size.width, size.height);

        // Draw a black border
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(location.x, location.y, size.width, size.height);

        // Draw the number inside the cell
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(new Font("Arial", Font.BOLD, 14));
        String data = String.valueOf(cell.getData());
        FontMetrics metrics = graphics2D.getFontMetrics();
        int x = location.x + (size.width - metrics.stringWidth(data)) / 2;
        int y = location.y + ((size.height - metrics.getHeight()) / 2) + metrics.getAscent();
        graphics2D.drawString(data, x, y);
    }
}