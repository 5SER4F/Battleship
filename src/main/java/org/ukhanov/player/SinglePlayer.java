package org.ukhanov.player;

import org.ukhanov.field.Field;

import java.util.Map;
import java.util.Scanner;

import static org.ukhanov.field.Field.FIELD_BORDER;

public class SinglePlayer extends AbstractPlayer implements Player {
    private final Scanner scanner = new Scanner(System.in);

    public SinglePlayer(Field myField, String name) {
        super(myField, name);
    }


    @Override
    public Map<String, Integer> strike() {
        int row, column;
        while (true) {
            System.out.println("Введите координаты удара\n введите строку 1-16");
            row = scanner.nextInt() - 1;
            System.out.println("Введите координаты столбца A-P");
            column = scanner.next().charAt(0) - 'A';
            if (enemyField.canStrike(row, column)) {
                break;
            }
        }
        return Map.of("row", row,
                "column", column);
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        String[] myFieldAsString = myField.toString().split("\n");
        String[] enemyFieldAsString = enemyField.toString().split("\n");
        toString.append("Ваше поле")
                .append(" ".repeat(FIELD_BORDER))
                .append("Поле противника\n");
        for (int i = 0; i < myFieldAsString.length; i++) {
            toString.append(myFieldAsString[i])
                    .append("   ")
                    .append(enemyFieldAsString[i])
                    .append("\n");
        }
        return toString.toString();
    }
}
