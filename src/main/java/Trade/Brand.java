package Trade;

import java.util.Objects;
import java.util.Scanner;

public class Brand {
    protected String name;

    public Brand(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        Brand ob = (Brand) obj;
        return name.equals(ob.name);
    }

    public static void builder(Depo depo) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Название бренда:");
        String newName = sc.nextLine();
        Brand newBrand = new Brand(newName);

        if (!depo.brandList.contains(newBrand)) {
            depo.brandList.add(newBrand);
            System.out.println("Брэнд " + newBrand + " создан");
        }
    }
}