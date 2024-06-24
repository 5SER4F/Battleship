package org.ukhanov.field;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.ukhanov.game.GameMaster.cls;
import static org.ukhanov.field.Field.*;

public abstract class AbstractFill {
    private List<Integer> ships;
    private int[][] field;

    private final boolean isManual;

    private static final int SHIP = 1;

    public AbstractFill(boolean isManual) {
        this.isManual = isManual;
    }

    public int[][] fillField() {
        ships = fillShips();
        field = new int[16][16];
        ships.forEach(this::tryPlaceShip);
        removeHalo();
        return field;
    }

    private void tryPlaceShip(int shipSize) {
        boolean direct;
        int row, column;
        boolean isPlaced = false;
        while (!isPlaced) {
            Map<String, Integer> coordinate = shipCoordinate(shipSize);
            direct = coordinate.get("direct") > 0;//0 left to right, 1 top to bottom
            row = coordinate.get(ROW);
            column = coordinate.get(COLUMN);
            if (isCanBePlaced(direct, row, column, shipSize)) {
                placeShip(direct, row, column, shipSize);
                isPlaced = true;
            }
        }
        if (isManual) {
            //TODO console clear
            cls();
            System.out.println(new Field(field));//Отрисовываем поле после ввода нового корабля
        }
    }

    protected abstract Map<String, Integer> shipCoordinate(int shipSize);

    private void placeShip(boolean direct, int row, int column, int shipSize) {
        for (int placedPart = 0; placedPart < shipSize; placedPart++) {
            if (direct) {
                field[row + placedPart][column] = SHIP;
            } else {
                field[row][column + placedPart] = SHIP;
            }
            markHalo();
        }
    }

    private boolean isCanBePlaced(boolean direct, int row, int column, int shipSize) {
//        cls();
        if (direct) {
            if (row + shipSize > FIELD_BORDER) {
                if (isManual) {
                    System.out.printf("Выход за пределы поля строка=%d столбец=%d на количество клеток=%d\n"
                            , FIELD_BORDER, column, Math.abs(FIELD_BORDER - row - shipSize) + 1);
                }
                return false;
            }

        } else {
            if (column + shipSize > FIELD_BORDER) {
                if (isManual) {
                    System.out.printf("Выход за пределы поля строка=%d столбец=%d на количество клеток=%d\n"
                            , row, FIELD_BORDER, Math.abs(FIELD_BORDER - column - shipSize) + 1);
                }
                return false;
            }
        }
        for (int placedPart = 0; placedPart < shipSize; placedPart++) {
            if (direct) {
                if (field[row + placedPart][column] > 0) {
                    if (isManual) {
                        System.out.printf("Пересечение/контакт с кораблем строка=%d столбец=%d\n", row + placedPart,
                                column);
                    }
                    return false;
                }
            } else {
                if (field[row][column + placedPart] > 0) {
                    if (isManual) {
                        System.out.printf("Пересечение/контакт с кораблем строка=%d столбец=%d\n", row, placedPart + column
                                + 1);
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void markHalo() {
        for (int row = 0; row < FIELD_BORDER; row++) {
            for (int column = 0; column < FIELD_BORDER; column++) {
                if (field[row][column] == 0) {
                    for (int k = Math.max(0, row - 1); k <= Math.min(FIELD_BORDER - 1, row + 1); k++) {
                        for (int l = Math.max(0, column - 1); l <= Math.min(FIELD_BORDER - 1, column + 1); l++) {
                            if (field[k][l] == 1) {
                                field[row][column] = 2;
                                break;
                            }
                        }
                        if (field[row][column] == 2) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private void removeHalo() {
        for (int row = 0; row < FIELD_BORDER; row++) {
            for (int column = 0; column < FIELD_BORDER; column++) {
                field[row][column] = field[row][column] == 2 ? 0 : field[row][column];
            }
        }
    }

    private List<Integer> fillShips() {
        List<Integer> ships = new LinkedList<>();
        for (int shipSize = 6; shipSize > 0; shipSize--) {
            for (int shipCount = 7 - shipSize; shipCount > 0; shipCount--) {
                ships.add(shipSize);
            }
        }
        return ships;
    }

}
