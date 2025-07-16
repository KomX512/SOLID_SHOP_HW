package Service;

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

}
