package org.ukhanov.player;

import org.ukhanov.field.CellState;
import org.ukhanov.field.Field;
import org.ukhanov.field.HitStatus;

import java.util.*;

import static org.ukhanov.field.Field.*;

public class BotPlayer extends AbstractPlayer {

    private final Random random = new Random();


    private List<Integer> ships = fillShips();
    private Map<String, Integer> coordinateOfFirstHit = new HashMap<>();//Кординаты первой пораженной палубы
    private HitStatus lastStrikerStatus;
    private boolean seeking = true;


    public BotPlayer(Field myField, String name) {
        super(myField, name);

    }

    @Override
    public Map<String, Integer> strike() {
        if (seeking) {
            return newStrikeCoordinate();
        }
        return nextShipPartCoordinate();
    }

    @Override
    public void strikeResult(int row, int column, HitStatus hitStatus) {
        if (hitStatus == HitStatus.KILL) {
            seeking = true;
        }
        if (hitStatus == HitStatus.HIT) {
            if (seeking) {
                coordinateOfFirstHit.put(ROW, row);
                coordinateOfFirstHit.put(COLUMN, column);
            }
            seeking = false;
        }
        super.strikeResult(row, column, hitStatus);
    }

    private Map<String, Integer> nextShipPartCoordinate() {
        int rowOfFirstHit = coordinateOfFirstHit.get(ROW);
        int columnOfFirstHit = coordinateOfFirstHit.get(COLUMN);
        //right
        for (int j = columnOfFirstHit; j < FIELD_BORDER; j++) {
            CellState cell = enemyField.getCellState(rowOfFirstHit, j);
            if (cell == CellState.MISS) {
                break;
            }
            if (cell == CellState.EMPTY) {
                return Map.of(ROW, rowOfFirstHit,
                        COLUMN, j);
            }
        }
        //left
        for (int j = columnOfFirstHit; j > 0; j--) {
            CellState cell = enemyField.getCellState(rowOfFirstHit, j);
            if (cell == CellState.MISS) {
                break;
            }
            if (cell == CellState.EMPTY) {
                return Map.of(ROW, rowOfFirstHit,
                        COLUMN, j);
            }
        }
        //bottom
        for (int i = rowOfFirstHit; i < FIELD_BORDER; i++) {
            CellState cell = enemyField.getCellState(i, columnOfFirstHit);
            if (cell == CellState.MISS) {
                break;
            }
            if (cell == CellState.EMPTY) {
                return Map.of(ROW, i,
                        COLUMN, columnOfFirstHit);
            }
        }
        //top
        for (int i = rowOfFirstHit; i > 0; i--) {
            CellState cell = enemyField.getCellState(i, columnOfFirstHit);
            if (cell == CellState.MISS) {
                break;
            }
            if (cell == CellState.EMPTY) {
                return Map.of(ROW, i,
                        COLUMN, columnOfFirstHit);
            }
        }
        //По идее мы не должны сюда попадать
        seeking = true;
        return newStrikeCoordinate();
    }

    private Map<String, Integer> newStrikeCoordinate() {
        while (true) {
            int row = random.nextInt(FIELD_BORDER);
            int column = random.nextInt(FIELD_BORDER);
            if (enemyField.canStrike(row, column)) {
                return Map.of(ROW, row,
                        COLUMN, column);
            }
        }
    }

    private static List<Integer> fillShips() {
        List<Integer> ships = new LinkedList<>();
        for (int shipSize = 6; shipSize > 0; shipSize--) {
            for (int shipCount = 7 - shipSize; shipCount > 0; shipCount--) {
                ships.add(shipSize);
            }
        }
        return ships;
    }

}
