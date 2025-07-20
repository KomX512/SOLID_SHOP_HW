package Service;

import java.util.Date;
import java.util.Scanner;

public class Useful {

    //ввод float чисел
    public static float scanFloat() {
        Scanner sc = new Scanner(System.in);
        float result = 0F;
        try {
            result = Float.parseFloat(sc.nextLine());
        } catch (Exception ex) {
            System.out.println("Ошибка ввода");
            System.out.println(ex.getMessage());
        }

        return result;
    }

    // ввод int чисел
    public static int scanInt() {
        Scanner sc = new Scanner(System.in);
        int result = 0;
        try {
            result = Integer.parseInt(sc.nextLine());
        } catch (Exception ex) {
            System.out.println("Ошибка ввода");
            System.out.println(ex.getMessage());
        }

        return result;
    }

    public static String dateToString(Date date, boolean wihtTime) {
        StringBuilder sb = new StringBuilder();
        sb.append(twoDigitsStr(date.getDate()));
        sb.append(".");
        sb.append(twoDigitsStr(date.getMonth()));
        sb.append(".");
        sb.append(date.getYear() + 1900);
        if (wihtTime) {
            sb.append(" ");
            sb.append(twoDigitsStr(date.getHours()));
            sb.append(":");
            sb.append(twoDigitsStr(date.getMinutes()));
            sb.append(":");
            sb.append(twoDigitsStr(date.getSeconds()));
        }
        return sb.toString();
    }

    private static String twoDigitsStr(int source) {
        String result = "0" + String.valueOf(source);
        return result.substring(result.length() - 2, result.length());
    }
}
