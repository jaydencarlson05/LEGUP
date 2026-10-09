package edu.rpi.legup.puzzle.starbattle;

import edu.rpi.legup.controller.BoardController;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.model.gameboard.regions.*;
import edu.rpi.legup.ui.boardview.ElementView;
import edu.rpi.legup.ui.boardview.GridBoardView;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class StarBattleView extends GridBoardView {

    /** Image used to render star cells. */
    static Image STAR;
    private ArrayList<GridRegionView> regionViews;

    static {
        try {
            STAR =
                    ImageIO.read(
                            ClassLoader.getSystemClassLoader()
                                    .getResource("edu/rpi/legup/images/starbattle/star.gif"));
        } catch (IOException e) {
            // pass
        }
    }

    /**
     * Constructs a StarBattleView for the given board. Initializes element views and dynamically
     * builds border views based on region boundaries.
     *
     * @param board the StarBattleBoard to visualize
     */
    public StarBattleView(StarBattleBoard board) {
        super(new BoardController(), new StarBattleController(), board.getDimension());
        regionViews = new ArrayList<>();
        for (PuzzleElement puzzleElement : board.getPuzzleElements()) {
            StarBattleCell cell = (StarBattleCell) puzzleElement;
            Point loc = cell.getLocation();
            StarBattleElementView elementView = new StarBattleElementView(cell);
            elementView.setIndex(cell.getIndex());
            elementView.setSize(elementSize);
            elementView.setLocation(
                    new Point(loc.x * elementSize.width, loc.y * elementSize.height));
            elementViews.add(elementView);
        }
        System.out.println("About to add regions:\n");
        int count = 0;
        for (StarBattleRegion region : board.getRegions())
        {
            GridRegionView regionView = new GridRegionView(region);
            regionView.setIndex(count++);
            regionView.setSize(elementSize);
            regionView.setLocation(new Point(0, 0));
            System.out.println("Region added.\n");
            regionViews.add(regionView);
        }
    }

    // Direction means which side of the cell in question should have a border on it
    // Numpad rules. 2 is down, 4 is left, 6 is right, 8 is up (based on visuals not coordinates,
    // since y is from up to down)
    /**
     * Computes the screen location for drawing a border relative to a given cell.
     *
     * <p>The direction follows numpad conventions:
     *
     * <ul>
     *   <li>2 = bottom
     *   <li>4 = left
     *   <li>6 = right
     *   <li>8 = top
     * </ul>
     *
     * @param one the reference cell
     * @param direction the direction to offset from the cell
     * @param elementSize the size of each grid element
     * @return the computed Point for the border location
     */
    public Point endCell(StarBattleCell one, int direction, Dimension elementSize) {
        Point temp =
                new Point(
                        one.getLocation().x * elementSize.width,
                        one.getLocation().y * elementSize.height); // dump this
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("direction is{}\n", direction);
        }
        if (direction == 2) { // top border
            temp.y += elementSize.height * 15 / 16; // multiply  so it doesn't go off screen
        }
        if (direction == 4) { // left border
            temp.x += elementSize.width / 16; // divide  so it doesn't go off screen
        }
        if (direction == 6) { // right border
            temp.x += elementSize.width * 15 / 16; // multiply  so it doesn't go off screen
        }
        if (direction == 8) { // bottom border
            temp.y += elementSize.height / 16; // multiply  so it doesn't go off screen
        }
        // default of 5 shouldn't change
        // these changes to the x or y coordinate are necessary to properly load them in and not cut
        // them off
        // on the edge of the board
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("point is now {},{}\n", temp.x, temp.y);
        }

        return temp;
    }

    // Important function to be used in the future that will resize the board to allow
    // extra rows and columns for the borders to be given extra space and add text the tops and
    // bottoms of the board. I bet they could put all the text on one "tile" because even though
    // it'll spill over into other ones, only one tiles needs to draw text, and we won't have to
    // worry about each tile having a different portion of the message.
    // Original function from SkyscrapersView.java
    /*
    @Override
    protected Dimension getProperSize() {
        Dimension boardViewSize = new Dimension();
        boardViewSize.width = (gridSize.width + 2) * elementSize.width;
        boardViewSize.height = (gridSize.height + 2) * elementSize.height;
        return boardViewSize;
    }
     */

    @Override
    public void drawBoard(Graphics2D graphics2D) {
        super.drawBoard(graphics2D);
        for (GridRegionView regionView : regionViews) {
            regionView.draw(graphics2D);
        }
        // testing how to draw things off the board
        // StarBattleCell test = new StarBattleCell(0, new Point(-10,-10), -1, 30);
    }
}
