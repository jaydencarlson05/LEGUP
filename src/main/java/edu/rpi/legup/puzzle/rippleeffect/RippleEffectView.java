package edu.rpi.legup.puzzle.rippleeffect;

import edu.rpi.legup.controller.BoardController;
import edu.rpi.legup.model.gameboard.PuzzleElement;
import edu.rpi.legup.ui.boardview.GridBoardView;
import edu.rpi.legup.ui.boardview.GridRegionView;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class RippleEffectView extends GridBoardView {
    private final Set<GridRegionView> regionViews;

    public RippleEffectView(RippleEffectBoard board) {
        super(new BoardController(), new RippleEffectCellController(), board.getDimension());
        super.setBoard(board);
        for (PuzzleElement<?> puzzleElement : board.getPuzzleElements()) {
            RippleEffectCell cell = (RippleEffectCell) puzzleElement;
            Point loc = cell.getLocation();
            RippleEffectElementView elementView = new RippleEffectElementView(cell);
            elementView.setIndex(cell.getIndex());
            elementView.setSize(elementSize);
            elementView.setLocation(new Point(loc.x * elementSize.width, loc.y * elementSize.height));
            elementViews.add(elementView);
        }

        regionViews = new HashSet<>();
        int count = 0;
        for (RippleEffectRegion region : board.getRegions())
        {
            GridRegionView regionView = new GridRegionView(region);
            regionView.setIndex(count++);
            regionView.setSize(elementSize);
            regionView.setLocation(new Point(0, 0));
            regionViews.add(regionView);
        }
    }

    @Override
    public void drawBoard(@NotNull Graphics2D graphics2D) {
        super.drawBoard(graphics2D);
        for (GridRegionView regionView : regionViews) {
            regionView.draw(graphics2D);
        }
    }
}