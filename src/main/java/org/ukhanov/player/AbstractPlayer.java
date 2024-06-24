package org.ukhanov.player;

import org.ukhanov.field.*;

import java.util.Map;

import static org.ukhanov.field.Field.COLUMN;
import static org.ukhanov.field.Field.ROW;

public abstract class AbstractPlayer implements Player {

    protected final SelfField myField;
    protected final EnemyField enemyField = new Field();
    protected final String name;


    public AbstractPlayer(Field myField, String name) {
        this.myField = myField;
        this.name = name;
    }

    @Override
    public abstract Map<String, Integer> strike();

    @Override
    public void strikeResult(int row, int column, HitStatus hitStatus) {
        CellState strikerStatus = hitStatus == HitStatus.HIT || hitStatus == HitStatus.KILL ? CellState.HIT
                : CellState.MISS;
        enemyField.strikeResult(row, column, strikerStatus);
    }

    @Override
    public HitStatus takeHit(Map<String, Integer> strikeCoordinate) {
        return myField.takeHit(strikeCoordinate.get(ROW), strikeCoordinate.get(COLUMN));
    }

}
