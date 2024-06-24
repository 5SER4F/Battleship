package org.ukhanov.field;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ManualFill extends AbstractFill {
    private final Scanner scanner = new Scanner(System.in);

    public ManualFill() {
        super(true);
    }

    @Override
    protected Map<String, Integer> shipCoordinate(int shipSize) {
        System.out.println("Расположите корабль размером=" + shipSize);
        Map<String, Integer> coordinate = new HashMap<>();
        System.out.println("Введите ориентацию корабля \n" +
                "1.слева направо\n" +
                "2.сверху вниз");
        coordinate.put("direct", scanner.nextInt() - 1);
        System.out.println("Введите координаты первой палубы коробля");
        System.out.println("Введите координату строки 1-16");
        coordinate.put("row", scanner.nextInt() - 1);

        System.out.println("Введите координату столбца A-P");
        coordinate.put("column", scanner.next().charAt(0) - 'A');
        return coordinate;
    }
}
