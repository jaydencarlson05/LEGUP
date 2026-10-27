package edu.rpi.legup.puzzle.rippleeffect;

import edu.rpi.legup.controller.ElementController;
import edu.rpi.legup.model.gameboard.Board;
import edu.rpi.legup.model.gameboard.PuzzleElement;

import java.awt.event.MouseEvent;

public class RippleEffectCellController extends ElementController {

    @Override
    public void changeCell(MouseEvent e, PuzzleElement data) {
        RippleEffectCell cell = (RippleEffectCell) data;
        RippleEffectBoard board = (RippleEffectBoard) this.boardView.getBoard();
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            if (e.isControlDown())
            {
                this.boardView.getSelectionPopupMenu().show(this.boardView, this.boardView.getCanvas().getX() + e.getX(), this.boardView.getCanvas().getY() + e.getY());
            }
            else
            {
                if (cell.getData() < board.getRegion(cell).getSize())
                {
                    cell.setData(cell.getData() + 1);
                }
                else
                {
                    cell.setData(0);
                }
            }
        }
        else if (e.getButton() == MouseEvent.BUTTON3)
        {
            if (cell.getData() <= 0)
            {
                cell.setData(board.getRegion(cell).getSize());
            }
            else
            {
                cell.setData(cell.getData() - 1);
            }
        }
    }
}