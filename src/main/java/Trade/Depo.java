package Trade;

import java.util.*;

public class Depo {
    private static Depo depo = null;

    public List nomenclatureList = new ArrayList();
    public List brandList = new ArrayList<>();
    public List ordersList = new ArrayList();
    public HashMap<Nomenclature, Integer> ratingMap = new HashMap<>();

    private Depo() {
    }

    public static Depo getInstance() {
        if (depo == null) depo = new Depo();
        return depo;
    }
}
