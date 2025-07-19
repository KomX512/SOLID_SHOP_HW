package Trade;

import Service.Useful;

import java.util.*;
import java.util.stream.Collectors;

public class Nomenclature {
    public String name;
    private String articul;
    private Brand brand;
    private Measure measure;
    private float weight;
    private float price;

    final Measure DEFAULT_MEASURE = Measure.PCS;
    final float DEFAULT_WEIGHT = 0F;

    public Nomenclature(String name) {
        this.name = name;
        this.measure = DEFAULT_MEASURE;
        this.weight = DEFAULT_WEIGHT;
    }

    public void setArticul(String articul) {
        this.articul = articul;
    }

    public void setBrand(String brand) {
        this.brand = new Brand(brand);
    }

    public void setMeasure(String stringMeasure) {
        if (!stringMeasure.equals("")) {
            this.measure = Measure.valueOf(stringMeasure);
        }
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getArticul() {
        return articul;
    }

    public Brand getBrand() {
        return brand;
    }

    public float getWeight() {
        return weight;
    }

    public float getPrice() {
        return price;
    }

    public Measure getMeasure() {
        return measure;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (brand != null) {
            sb.append(" [" + brand.name + "]");
        }

        if (!articul.equals("")) {
            sb.append(" (" + articul + ")");
        }

        return sb.toString();
    }

    public void fullCardPrint() {
        System.out.println(name);
        System.out.println("Артикул: " + articul);
        System.out.println("Брэнд: " + brand);
        System.out.println("Единица измерения: " + measure);
        System.out.println("Вес единицы: " + weight);
        System.out.println("Цена: " + price);
    }

    public static void printAllGoods(List nomenclatureList) {

        nomenclatureList.forEach((value) -> {
            System.out.println(value + " / " + ((Nomenclature) value).getPrice());
        });

    }

    public static void filterAndPrintGoods(Depo depo) {

        Scanner sc = new Scanner(System.in);

        System.out.println("1. По ключевому слову");
        System.out.println("2. До указанной цены");
        System.out.println("3. Выше указанной цены");
        System.out.println("4. Бренд");

        int ch = Useful.scanInt();

        List conscripts;
        float priceFilter = 0F;
        switch (ch) {
            case 1://По ключевому слову
                System.out.print("Ключевое слово:");
                String keyWord = sc.nextLine();

                conscripts = depo.nomenclatureList.stream()
                        .filter(nomenclature -> ((Nomenclature) nomenclature).getName().toLowerCase().contains(keyWord.toLowerCase()))
                        .toList();

                printAllGoods(conscripts);
                break;
            case 2://Фильтр ниже чем указанная цена
                System.out.print("Цена:");
                priceFilter = Useful.scanFloat();
                final float filterMax = priceFilter;
                conscripts = depo.nomenclatureList.stream()
                        .filter(nomenclature -> ((Nomenclature) nomenclature).getPrice() <= filterMax)
                        .toList();
                printAllGoods(conscripts);
                break;
            case 3://Фильтр выше чем указанная цена
                System.out.print("Цена:");
                priceFilter = Useful.scanFloat();
                final float filterMin = priceFilter;
                conscripts = depo.nomenclatureList.stream()
                        .filter(nomenclature -> ((Nomenclature) nomenclature).getPrice() >= filterMin)
                        .toList();
                printAllGoods(conscripts);
                break;
            case 4: //Фильтр по Брэнду

                for (int i = 0; i < depo.brandList.size(); i++) {
                    System.out.println((i + 1) + ". " + depo.brandList.get(i));
                }
                int chBr = Useful.scanInt();
                if (chBr > 0) {
                    Brand brandFilter = (Brand) depo.brandList.get(ch - 1);
                    conscripts = depo.nomenclatureList.stream()
                            .filter(nomenclature -> ((Nomenclature) nomenclature).getBrand().equals(brandFilter))
                            .toList();
                    printAllGoods(conscripts);
                }
                break;
            default:
                System.out.println("Операция не определена..");
        }
    }

    public static void goodsRating(Depo depo) {

        depo.ratingMap.entrySet().stream()
                .sorted(Map.Entry.<Nomenclature, Integer>comparingByValue().reversed())
                .forEach(System.out::println);
    }

    public static List getBestRating(Depo depo, int quantity) {

        List operationList = depo.ratingMap.entrySet().stream()
                .sorted(Map.Entry.<Nomenclature, Integer>comparingByValue().reversed())
                .map(m -> m.getKey())
                .limit(quantity)
                .collect(Collectors.toList());

        return operationList;
    }

    public static List getPoorRating(Depo depo, int quantity) {

        List operationList = depo.ratingMap.entrySet().stream()
                .sorted(Map.Entry.<Nomenclature, Integer>comparingByValue())
                .map(m -> m.getKey())
                .limit(quantity)
                .collect(Collectors.toList());

        return operationList;
    }

    public static List getRecomendation(Depo depo, int quantity) {

        List bestList = getBestRating(depo, depo.nomenclatureList.size() - quantity);

        List selectedList = (List) depo.nomenclatureList.stream()
                .filter(nom -> !bestList.contains(nom))
                .limit(quantity)
                .toList();

        System.out.println(selectedList);
        return selectedList;
    }

    public static void builder(Depo depo) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Новая номенклатура");
        System.out.print("Наименование:");
        String newName = sc.nextLine();
        System.out.print("Артикул:");
        String articul = sc.nextLine();
        boolean breakFlag = false;
        for (int i = 0; i < depo.nomenclatureList.size(); i++) {
            Nomenclature currentNom = (Nomenclature) depo.nomenclatureList.get(i);
            if (currentNom.name.equals(newName)) {
                System.out.println("Уже есть такое наименование...");
                breakFlag = true;
                break;
            }
            if (currentNom.articul.equals(articul)) {
                System.out.println("Уже есть такой артикул...");
                breakFlag = true;
                break;
            }
        }
        if (breakFlag) {
            System.out.println("Отмена ввода...");
            return;
        }

        Nomenclature novaObj = new Nomenclature(newName);
        novaObj.setArticul(articul);

        System.out.println("Выберите Брэнд:");

        for (int i = 0; i < depo.brandList.size(); i++) {
            System.out.println((i + 1) + ". " + depo.brandList.get(i));
        }
        int chBr = Useful.scanInt();
        if (chBr > 0) {
            Brand brand = (Brand) depo.brandList.get(chBr - 1);
            novaObj.setBrand(brand.name);
        }

        System.out.println("Единица измерения:");
        int measureIndex = 0;
        Measure[] measureArray = new Measure[Measure.values().length];
        for (Measure current : Measure.values()) {
            System.out.println((measureIndex + 1) + ". " + current);
            measureArray[measureIndex] = current;
            measureIndex++;
        }

        int chMs = Useful.scanInt();
        if (chMs > 0) {
            novaObj.setMeasure(measureArray[chMs - 1].toString());
            System.out.println(measureArray[chMs - 1]);
        }
        System.out.println("Введите вес:");

        float newWeight = Useful.scanFloat();
        novaObj.setWeight(newWeight);

        System.out.println("Введите цену:");

        float newPrice = Useful.scanFloat();
        novaObj.setPrice(newPrice);

        System.out.println("Введена новая номенклатура: ");
        novaObj.fullCardPrint();
        depo.nomenclatureList.add(novaObj);

    }

}
