
import Service.FileOperations;
import Service.Menu;
import Trade.Brand;
import Trade.Nomenclature;
import Trade.Order;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        final int END_MARKER = 999;

        //Действия по меню
        final int NEW_ORDER = 11;
        final int ORDER_BY_NUMBER = 12;
        final int ORDERS_BY_DATE = 13;

        final int ALL_GOODS = 21;
        final int FILTER_GOODS = 22;
        final int GOODS_RATING = 23;
        final int NEW_NOM = 24;
        final int ADD_BRAND = 25;
        //---------------------------------
        List nomenclatureList = new ArrayList();
        List ordersList = new ArrayList();
        Map ratingMap = new TreeMap();

        fillDefaultNomenclature(nomenclatureList);
        List brandList = fillBrandList(nomenclatureList);

        boolean repeat = true;
        while (repeat) {

            int choice = Menu.MainMenu();
            if (choice == END_MARKER){
                repeat = false;
                System.out.println("Выход из программы...");
                break;
            }

            switch (choice) {
                case NEW_ORDER:
                    Order.buildNewOrder(nomenclatureList, ordersList, ratingMap);
                    break;
                case ORDER_BY_NUMBER:
                    Order findedOrder = Order.findOrderByNumber(ordersList);
                    if (findedOrder != null){
                        Order.orderOperations(findedOrder);
                    }
                    break;
                case ORDERS_BY_DATE:
                    System.out.println("Under Construction");
                    break;
                case ALL_GOODS:
                    Nomenclature.printAllGoods(nomenclatureList);
                    break;
                case FILTER_GOODS:
                    Nomenclature.filterAndPrintGoods(nomenclatureList, brandList);
                    break;
                case GOODS_RATING:
                    Nomenclature.goodsRating(ratingMap);
                    break;
                case NEW_NOM:
                    Nomenclature.builder(nomenclatureList, brandList);
                    break;
                case ADD_BRAND:
                    Brand.builder(brandList);
                    break;
                default:
                    System.out.println("Не верно выбрана операция");

            }
            choice = 0;
        }
    }

    private static List fillBrandList(List nomenclatureList) {
        List brandList = new ArrayList();
        Nomenclature current;
        for (int i = 0; i < nomenclatureList.size(); i++) {
            current = (Nomenclature) nomenclatureList.get(i);
            System.out.println(nomenclatureList.get(i));
            if (!brandList.contains(current.getBrand())) {
                brandList.add(current.getBrand());
            }
        }
        return brandList;
    }

    private static void fillDefaultNomenclature(List nomenclatureList) {

        final String FILE_NAME = "data.csv";
        new FileOperations().defaultNomenclatureCSV(FILE_NAME, nomenclatureList);

    }
}