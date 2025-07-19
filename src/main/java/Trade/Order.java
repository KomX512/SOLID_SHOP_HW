package Trade;

import Service.Useful;

import java.util.List;
import java.util.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Order {
    private static final AtomicInteger COUNTER = new AtomicInteger(1);
    protected int number;
    protected Date date;
    protected OrderStatus status;
    protected String address;
    protected float weigh;
    protected float summ;
    protected List orderGoods;
    protected String comment;
    protected List orderTrack;

    public Order() {

        this.date = new Date();
        this.number = COUNTER.getAndIncrement();
        this.status = OrderStatus.NEW;
        this.weigh = 0;
        this.summ = 0;
        this.orderGoods = new ArrayList();
        this.orderTrack = new ArrayList();
        this.comment = "";
    }

    public int getNumber() {
        return number;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        this.setNewTrack("Новый статус!");
    }

    public String getAddress() {
        return address;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public List getOrderGoods() {
        return orderGoods;
    }

    public List getOrderTrack() {
        return orderTrack;
    }

    public void setNewTrack(String msg) {
        OrderTrackString newTrackString = new OrderTrackString(this.status, msg);
        this.orderTrack.add(newTrackString);
    }

    public void addOrderString(Nomenclature nomenclature, float quantity) {

        if (orderGoods != null) {
            for (int i = 0; i < orderGoods.size(); i++) {
                OrderGoods current = (OrderGoods) orderGoods.get(i);
                if (current.getNomenclature().equals(nomenclature)) {
                    current.addQuantity(quantity);
                    return;
                }
            }
        }
        this.orderGoods.add(new OrderGoods(nomenclature, quantity, nomenclature.getPrice()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public boolean equals(Object obj) {
        Order ob = (Order) obj;
        return number == ob.number;
    }

    @Override
    public String toString() {
        return "Заказ № " + number + " от " + Useful.dateToString(date, false);
    }

    public void printOrder() {

        System.out.println(this.toString());
        System.out.println("Статус: " + this.getStatus());
        System.out.println("Адрес доставки: " + this.getAddress());
        System.out.println("--------------------------------------------");
        System.out.println("          Наименование [Бренд](Артикул)            |Кол-во|Ед.Изм|Цена|Сумма|");
        System.out.println("--------------------------------------------");
        orderGoods.forEach((value) -> {
            System.out.println(value);
        });
        System.out.println("--------------------------------------------");
        System.out.println("Сумма: " + summ);
        System.out.println("Вес: " + weigh);
        if (!comment.equals("")) {
            System.out.println("Комментарий: " + comment);
        }
    }

    public static Order findOrderByNumber(List ordersList) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Номер заказа: ");
        int orderNumber = Useful.scanInt();

        List conscripts = ordersList.stream()
                .filter(obj -> ((Order) obj).getNumber() == orderNumber)
                .toList();
        if (conscripts.size() == 0) {
            return null;
        }

        return (Order) conscripts.get(0);
    }

    public static void addGoods(Order obj, Depo depo) {
        final String NUMBER_PALLET = "0123456789.";

        Scanner sc = new Scanner(System.in);
        System.out.println("Выберите товары");
        System.out.println("Позиция [ПРОБЕЛ] количество");
        System.out.println("Для завешения введите пустую строку");
        for (int i = 0; i < depo.nomenclatureList.size(); i++) {
            System.out.println((i + 1) + ". " + depo.nomenclatureList.get(i));
        }

        while (true) {
            String processStr = sc.nextLine();
            if (processStr.equals("")) {
                break;
            }
            StringBuilder sb = new StringBuilder();
            int nextStart = 0;
            for (int i = 0; i < processStr.length(); i++) {
                char ch = processStr.charAt(i);
                if (NUMBER_PALLET.indexOf(ch) > 0) {
                    sb.append(ch);
                    continue;
                }
                nextStart = i;
                break;
            }
            nextStart++;
            if (nextStart >= processStr.length()) {
                break;
            }
            int position = Integer.parseInt(sb.toString());
            sb = new StringBuilder();
            for (int i = nextStart; i < processStr.length(); i++) {
                char ch = processStr.charAt(i);
                if (NUMBER_PALLET.indexOf(ch) > 0) {
                    sb.append(ch);
                }
            }

            float quantityScaned = Float.parseFloat(sb.toString());
            try {
                obj.addOrderString((Nomenclature) depo.nomenclatureList.get(position - 1), quantityScaned);
            } catch (Exception ex) {
                System.out.println("Ошибка");
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void buildNewOrder(Depo depo, Order donorOrder) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Адрес доставки");
        String address = sc.nextLine();
        if (address.equals("")) {
            System.out.println("Отмена создания...");
            return;
        }

        Order newOrder = new Order();
        newOrder.setAddress(address);
        if (donorOrder == null) {
            addGoods(newOrder, depo);
        } else {
            List currentList = donorOrder.getOrderGoods();
            for (int i = 0; i < currentList.size(); i++) {
                OrderGoods currentPosition = (OrderGoods) currentList.get(i);
                newOrder.addOrderString(currentPosition.getNomenclature(), currentPosition.getQuantity());
            }
        }

        for (int i = 0; i < newOrder.orderGoods.size(); i++) {
            OrderGoods current = (OrderGoods) newOrder.orderGoods.get(i);
            newOrder.summ = newOrder.summ + current.getSumm(current.getQuantity(), current.getNomenclature().getPrice());
            newOrder.weigh = newOrder.weigh + current.getNomenclature().getWeight() * current.getQuantity();
        }

        newOrder.setNewTrack("Создан заказ");
        newOrder.printOrder();
        depo.ordersList.add(newOrder);

    }

    public void copyOrder(Depo depo) {
        System.out.println("Копируем заказ " + this);
        buildNewOrder(depo, this);
    }

    public static void orderOperations(Order currentOrder, Depo depo) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Смотреть заказ");
        System.out.println("2. Изменить адрес доставки");
        System.out.println("3. Изменить статус");
        System.out.println("4. Возврат заказа");
        System.out.println("5. Повторить заказ");
        System.out.println("6. Трэк-лист заказа");

        int ch = Useful.scanInt();
        switch (ch) {
            case 1:
                currentOrder.printOrder();
                break;
            case 2:
                if (currentOrder.getStatus() == OrderStatus.IN_WORK || currentOrder.getStatus() == OrderStatus.NEW) {
                    System.out.println("Новый адрес");
                    String newAdress = sc.nextLine();
                    currentOrder.setAddress(newAdress);
                    currentOrder.setNewTrack("Замена адрес доставки");
                } else {
                    System.out.println("Уже нельзя заменить адрес...");
                }
                break;
            case 3:
                if (currentOrder.getStatus() == OrderStatus.COMPLETED || currentOrder.getStatus() == OrderStatus.CANCELED) {
                    System.out.println("У заверешнных и отмененных заказов нельзя менять статус...");
                    break;
                }
                changeStatus(currentOrder);
                if (currentOrder.getStatus() == OrderStatus.COMPLETED) {
                    currentOrder.ratintOperations(depo, OrderStatus.COMPLETED);
                }
                break;
            case 4:
                if (currentOrder.getStatus() != OrderStatus.CANCELED) {
                    currentOrder.setStatus(OrderStatus.CANCELED);
                    currentOrder.setNewTrack("Отмена заказа из меню отмены");
                    currentOrder.ratintOperations(depo, OrderStatus.CANCELED);
                }

                break;
            case 5:
                currentOrder.copyOrder(depo);
                break;
            case 6:
                currentOrder.printOrderTrackList();
                break;
            default:
                System.out.println("Неизвествная операция");
        }
    }

    public void ratintOperations(Depo depo, OrderStatus orderStatus) {
        final int СOMPLED_RATING = 1;
        final int CANCELED_RATING = -1;

        List currentOrderGoods = this.getOrderGoods();

        for (int i = 0; i < currentOrderGoods.size(); i++) {
            OrderGoods current = (OrderGoods) currentOrderGoods.get(i);
            int mode = (orderStatus == OrderStatus.CANCELED ? CANCELED_RATING : СOMPLED_RATING);
            int value = 0;
            if (depo.ratingMap.containsKey(current.getNomenclature())) {
                value = depo.ratingMap.get(current.getNomenclature()) + mode * (int) current.getQuantity();
            } else {
                value = mode * (int) current.getQuantity();
            }
            depo.ratingMap.put(current.getNomenclature(), value);
        }
    }

    public void printOrderTrackList() {
        orderTrack.forEach((value) -> {
            System.out.println(value);
        });
    }

    private static void changeStatus(Order currentOrder) {

        System.out.println(currentOrder);
        System.out.println("1. " + OrderStatus.IN_WORK);
        System.out.println("2. " + OrderStatus.ON_SHIPMENT);
        System.out.println("3. " + OrderStatus.ON_THE_WAY);
        System.out.println("4. " + OrderStatus.ON_DELIVERY);
        System.out.println("5. " + OrderStatus.COMPLETED);
        int ch = Useful.scanInt();
        switch (ch) {
            case 1:
                currentOrder.setStatus(OrderStatus.IN_WORK);
                break;
            case 2:
                currentOrder.setStatus(OrderStatus.ON_SHIPMENT);
                break;
            case 3:
                currentOrder.setStatus(OrderStatus.ON_THE_WAY);
                break;
            case 4:
                currentOrder.setStatus(OrderStatus.ON_DELIVERY);
                break;
            case 5:
                currentOrder.setStatus(OrderStatus.COMPLETED);

                break;
        }
    }
}
