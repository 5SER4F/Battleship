package org.ukhanov.game;

import org.ukhanov.field.AutoFill;
import org.ukhanov.field.Field;
import org.ukhanov.field.HitStatus;
import org.ukhanov.field.ManualFill;
import org.ukhanov.player.BotPlayer;
import org.ukhanov.player.Player;
import org.ukhanov.player.SinglePlayer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static org.ukhanov.field.Field.COLUMN;
import static org.ukhanov.field.Field.ROW;

public class GameMaster {
    public static final int ALL_SHIPS = 56;
    private final Scanner scanner = new Scanner(System.in);
    private final Map<String, String> menu = menu();

    private final Random random = new Random(System.nanoTime());

    private Player human;

    private Player bot;

    private int humanShips;
    private int botShips;

    public void startGame() {
        System.out.println(menu.get("Name"));
        String playerName = scanner.nextLine();
        System.out.println("Player name = " + playerName);

        System.out.println(menu.get("Player mode"));
        int playerMode = scanner.nextInt();
        System.out.println("Game mode = " + playerMode);
        if (playerMode == 1) {
            //Создаем класс для 1 игрока
        } else {
            //Создаем класс для 2 игроков
        }

        System.out.println(menu.get("How place ship"));
        int placeMode = scanner.nextInt();
        System.out.println("Способ расстановки =" + placeMode);
        int[][] humanField;
        if (placeMode == 1) {
            humanField = new AutoFill().fillField();
        } else {
            humanField = new ManualFill().fillField();
        }
        human = new SinglePlayer(new Field(humanField), playerName);
        bot = new BotPlayer(new Field(new AutoFill().fillField()), "Bot");

        boolean isHumanTurn = random.nextBoolean();// true - игрок
        System.out.println("Первым ходит - " +
                (isHumanTurn ? "вы" : "оппонет"));

        System.out.println("Ваше поле");
        System.out.println(human);
        humanShips = botShips = ALL_SHIPS;
        while (true) {
            System.out.println("Ход делает:" + (isHumanTurn ? "вы" : "оппонет"));
            if (isHumanTurn) {
                if (turn(human, bot)) {
                    isHumanTurn = false;
                } else {
                    botShips--;
                }
            } else {
                if (turn(bot, human)) {
                    isHumanTurn = true;
                } else {
                    humanShips--;
                }
            }
            cls();
            System.out.println(human);
            if (isEndGame()) {
                if (humanShips == 0) {
                    System.out.println("Вы проиграли");
                }
                if (botShips == 0) {
                    System.out.println("Вы победили");
                }
                break;
            }
        }
    }

    private boolean turn(Player attacker, Player defending) {
        Map<String, Integer> strikeCoordinate = attacker.strike();
        HitStatus strikeResult = defending.takeHit(strikeCoordinate);
        attacker.strikeResult(strikeCoordinate.get(ROW), strikeCoordinate.get(COLUMN), strikeResult);
        System.out.println("Этот выстрел был:" + strikeResult);
        return strikeResult == HitStatus.MISS;
    }

    private boolean isEndGame() {
        return humanShips == 0 || botShips == 0;
    }

    public static void cls() {
//        for (int i = 0; i < 15; i++) {
//            System.out.println();
//        }
//        System.out.print("\033[H\033[2J");
//        System.out.print("\033[H\033[J");
//        System.out.flush();
        try {
            Runtime.getRuntime().exec("cls");//Не получается очистить cmd кодом из java
        } catch (IOException e) {
            for (int i = 0; i < 15; i++) {
                System.out.println();
            }
            System.out.println("Консоль не была очищена");
        }
    }

    private Map<String, String> menu() {
        Map<String, String> menu = new HashMap<>();
        menu.put("Name", "Введите имя.");
        menu.put("Player mode", "Для выбора режима игры введите его номер \n" +
                "1. Одиночная\n" +
                "2. С напарником(не реализовано)");
        menu.put("How place ship", "Для выбора способа расстановки кораблей введите его номер \n" +
                "1. Автоматическая\n" +
                "2. Ручная");
        return menu;
    }
}
