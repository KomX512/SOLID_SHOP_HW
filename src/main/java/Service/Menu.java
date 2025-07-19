package Service;

import java.util.Iterator;
import java.util.Scanner;

public class Menu {

    public static final int END_MARKER = 999;
    public static final String END_REPLY = "end";

    public static int MainMenu() {

        Scanner sc = new Scanner(System.in);
        int secondChoise;
        System.out.println("Основное меню:");
        System.out.println("1. Заказы...");
        System.out.println("2. Товары...");

        String choise = sc.nextLine();

        try {
            if (choise.toLowerCase().equals(END_REPLY)) {
                return END_MARKER;
            }
            secondChoise = 0;
            int intChoise = Integer.parseInt(choise);
            if (intChoise == 1) {
                secondChoise = ordersMenu();
            } else if (intChoise == 2) {
                secondChoise = goodsMenu();
            }

            if (secondChoise == END_MARKER) {
                return END_MARKER;
            }

            return intChoise * 10 + secondChoise;

        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    private static int goodsMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Меню товары:");
        System.out.println("1. Доступные товары");
        System.out.println("2. Доступные товары (по фильтру)...");
        System.out.println("3. Рейтинг товаров");
        System.out.println("4. Добавить новую номенклатуру");
        String choise = sc.nextLine();
        try {
            if (choise.toLowerCase().equals(END_MARKER)) {
                return 999;
            }
            return Integer.parseInt(choise);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    private static int ordersMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Меню заказы:");
        System.out.println("1. Создать заказ");
        System.out.println("2. Выбрать заказ по номеру...");

        String choise = sc.nextLine();
        try {
            if (choise.toLowerCase().equals(END_MARKER)) {
                return 999;
            }
            return Integer.parseInt(choise);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
