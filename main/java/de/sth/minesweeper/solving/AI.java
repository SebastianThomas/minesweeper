package de.sth.minesweeper.solving;

import de.sth.minesweeper.MineSweeper;
import de.sth.minesweeper.buttons.EmptySweeperButton;
import de.sth.minesweeper.buttons.SweeperButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AI {
    private MineSweeper game;
    private SweeperButton[][] buttons;

    public AI(MineSweeper game) {
        this.game = game;

        this.buttons = this.game.getButtons();
        System.out.println(this.buttons[1][1]);
    }

    public void printSaveCalls(int x, int y) {

    }

    public PossibleReveals getSaveCalls(int x, int y) {
        System.out.println(this.buttons[x][y]);
        // TODO: NullPointerException ???
        if (!(this.buttons[x][y] instanceof EmptySweeperButton))
            throw new UnsupportedOperationException("SweeperButton is not empty!");

        // Get array list with all possible fields around (at the border: less fields), so max capacity: 8
        ArrayList<SweeperButton> possibilities = new ArrayList<>(8);
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (x + i < 0 || y + j < 0 || x + 1 > this.buttons.length - 1 || y + j > this.buttons[0].length)
                    continue;
                possibilities.add(this.buttons[x + i][y + j]);
            }
        }
        // There might not always be 8 fields around, so trim list
        possibilities.trimToSize();

        ArrayList<SweeperButton> empty = new ArrayList<>(this.getEmpty(possibilities));

        int bombFilledFields = getRightClicked(possibilities).size();

        ArrayList<SweeperButton> returnValue = new ArrayList<>(possibilities.size());
        // GET SAVE FREE FIELDS
        // When field is already satisfied
        if (bombFilledFields == this.buttons[x][y].getBombsAround()) {
            returnValue.addAll(empty);

            returnValue.trimToSize();
            return new PossibleReveals(false, returnValue);
        }

        // GET SAVE BOMBS
        // When f.e. 1 field is free and one bomb is right clicked, and the current bomb's number is 2, the position is save
        if (((EmptySweeperButton) this.buttons[x][y]).getBombsAround() - bombFilledFields == empty.size()) {
            returnValue.addAll(empty);
        }
        return new PossibleReveals(true, returnValue);
    }

    public List<SweeperButton> getEmpty(ArrayList<SweeperButton> possibilities) {
        return possibilities.stream().filter(b -> !b.isRevealedOrRightClicked()).collect(Collectors.toList());
    }

    public List<SweeperButton> getRightClicked(ArrayList<SweeperButton> possibilities) {
        return possibilities.stream().filter(SweeperButton::isRightClicked).collect(Collectors.toList());
    }
}
