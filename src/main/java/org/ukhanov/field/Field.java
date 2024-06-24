package org.ukhanov.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field implements SelfField, EnemyField {
    public static final int FIELD_BORDER = 16;
    public static final String ROW = "row";
    public static final String COLUMN = "column";
    private CellState[][] field;

    public static final List<Character> CHARACTERS = fillChars();

    public Field() {
        field = fillWithEmpty();
    }

    public Field(int[][] fieldAsInt) {
        field = fillWithEmpty();
        for (int row = 0; row < FIELD_BORDER; row++) {
            for (int column = 0; column < FIELD_BORDER; column++) {
                field[row][column] = fieldAsInt[row][column] == 1 ? CellState.SHIP : CellState.EMPTY;
            }
        }
    }

    //    @Override
    public CellState getCellState(int row, int column) {
        return field[row][column];
    }

    @Override
    public boolean canStrike(int row, int column) {
        if (row < 0 || row > 15 || column < 0 || column > 15) {
            System.out.println("Координаты вне границ поля");
            return false;
        }
        CellState cellState = field[row][column];
        if (cellState == CellState.HIT || cellState == CellState.MISS) {
            System.out.println("Нельзя повторно стрелять в клетку");
            return false;
        }

        return true;
    }

    @Override
    public void strikeResult(int row, int column, CellState strikeStatus) {
        field[row][column] = strikeStatus;
    }

    @Override
    public HitStatus takeHit(int row, int column) {
        if (field[row][column] == CellState.SHIP) {
            field[row][column] = CellState.HIT;
            return isKill(row, column) ? HitStatus.KILL : HitStatus.HIT;
        }
        field[row][column] = CellState.MISS;
        return HitStatus.MISS;
    }

    @Override
    public String toString() {
        StringBuilder fieldAsString = new StringBuilder();
        fieldAsString.append("  ");
        CHARACTERS.forEach(fieldAsString::append);
        fieldAsString.append("\n");
        for (int row = 0; row < FIELD_BORDER; row++) {
            fieldAsString.append(row + 1);
            if (row < 9) {
                fieldAsString.append(" ");
            }
            for (int column = 0; column < FIELD_BORDER; column++) {
                fieldAsString.append(field[row][column].getLabel());
            }
            fieldAsString.append("\n");
        }
        return fieldAsString.toString();
    }

    private boolean isKill(int row, int column) {
        //Проверяю вверх
        boolean top, bottom, left, right;
        top = bottom = left = right = true;
        for (int i = row; i < FIELD_BORDER; i++) {
            if (field[i][column] == CellState.EMPTY || field[i][column] == CellState.MISS) {
                break;
            }
            if (field[i][column] == CellState.SHIP) {
                bottom = false;
                break;
            }
        }
        for (int i = row; i > 0; i--) {
            if (field[i][column] == CellState.EMPTY || field[i][column] == CellState.MISS) {
                break;
            }
            if (field[i][column] == CellState.SHIP) {
                top = false;
                break;
            }
        }

        for (int j = column; j < FIELD_BORDER; j++) {
            if (field[row][j] == CellState.EMPTY || field[row][j] == CellState.MISS) {
                break;
            }
            if (field[row][j] == CellState.SHIP) {
                right = false;
                break;
            }
        }

        for (int j = column; j > 0; j--) {
            if (field[row][j] == CellState.EMPTY || field[row][j] == CellState.MISS) {
                break;
            }
            if (field[row][j] == CellState.SHIP) {
                left = false;
                break;
            }
        }
        return top && bottom && left && right;
    }


    private CellState[][] fillWithEmpty() {
        CellState[][] field = new CellState[16][16];
        Arrays.stream(field)
                .forEach(line -> Arrays.fill(line, CellState.EMPTY));
        return field;
    }

    private static List<Character> fillChars() {
        List<Character> characters = new ArrayList<>();
        for (int i = 'A'; i <= 'P'; i++) {
            characters.add((char) i);
        }
        return List.copyOf(characters);
    }
}
