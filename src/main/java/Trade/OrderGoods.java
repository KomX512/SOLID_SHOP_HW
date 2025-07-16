package Trade;

public class OrderGoods {
    private Nomenclature nomenclature;
    private float quantity;
    private float price;
    private float summ;

    public OrderGoods(Nomenclature nomenclature, float quantity, float price) {
        this.nomenclature = nomenclature;
        this.quantity = quantity;
        this.price = price;
        this.summ = getSumm(quantity, price);
    }

    public void changePrice(float newPrice){
        this.price = newPrice;
        this.summ = getSumm(this.quantity, newPrice);
    }

    public void addQuantity(float addingQuantity){
        this.quantity = this.quantity + addingQuantity;
        this.summ = getSumm(this.quantity , this.price);
    }

    public void changeQuantity(float newQuantity){
        this.quantity = newQuantity;
        this.summ = getSumm(newQuantity, this.price);
    }

    public Nomenclature getNomenclature() {
        return nomenclature;
    }

    public float getSumm(float quantity, float price) {
        return quantity * price;
    }

    public float getQuantity(){
        return quantity;
    }

    @Override
    public String toString(){
        final String SPACE = "                                                    ";
        StringBuilder sb = new StringBuilder();
        sb.append((nomenclature.toString() + SPACE).substring(0, 50) );
        sb.append(" | ");
        sb.append(quantity);
        sb.append(" | ");
        sb.append(nomenclature.getMeasure());
        sb.append(" | ");
        sb.append(price);
        sb.append(" | ");
        sb.append(summ);
        return sb.toString();
    }
}
