package org.ukhanov.field;

import java.util.Map;
import java.util.Random;

import static org.ukhanov.field.Field.*;

public class AutoFill extends AbstractFill {
    private final Random random = new Random();

    public AutoFill() {
        super(false);
    }

    @Override
    protected Map<String, Integer> shipCoordinate(int shipSize) {
        return Map.of("direct", random.nextInt(2),
                ROW, random.nextInt(FIELD_BORDER),
                COLUMN, random.nextInt(FIELD_BORDER));
    }
}
