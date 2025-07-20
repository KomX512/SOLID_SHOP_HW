package Trade;

import Service.Useful;

import java.util.Date;

public class OrderTrackString {
    private Date msgDate;
    private OrderStatus status;
    private String msg;

    public OrderTrackString(OrderStatus status, String msg) {

        this.msgDate = new Date();
        this.status = status;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "[" + Useful.dateToString(msgDate, true) + "] " + status + " " + msg;
    }
}