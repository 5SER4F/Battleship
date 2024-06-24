package org.ukhanov.player;

import org.ukhanov.field.HitStatus;

import java.util.Map;

public interface Player {
    Map<String, Integer> strike();

    void strikeResult(int row, int column, HitStatus hitStatus);

    HitStatus takeHit(Map<String, Integer> strikeCoordinate);
}
