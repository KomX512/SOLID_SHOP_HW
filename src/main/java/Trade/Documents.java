package Trade;

import Service.Useful;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public abstract class Documents {

    protected int number;
    protected Date date;
    protected String comment;

    int getNumber(){
        return number;
    }

    public static Documents findByNumber(List docsList) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Номер документа: ");
        int orderNumber = Useful.scanInt();

        List conscripts = docsList.stream()
                .filter(obj -> ((Order) obj).getNumber() == orderNumber)
                .toList();
        if (conscripts.size() == 0) {
            return null;
        }

        return (Order) conscripts.get(0);
    }
}
