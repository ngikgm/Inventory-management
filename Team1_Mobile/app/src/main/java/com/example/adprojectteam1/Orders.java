package com.example.adprojectteam1;

public class Orders {


    private int ordersId;
    private int proposed_quantity;
    private int actual_delivered_quantity_by_clerk;
    private int actual_received_quantity_by_representative;
    private String order_status;
    private String start_order_date;
    private String delivered_order_date;
    private  Staff staff_obj;
    private  Item item_obj;


    public int getActual_delivered_quantity_by_clerk() {
        return actual_delivered_quantity_by_clerk;
    }

    public void setActual_delivered_quantity_by_clerk(int actual_delivered_quantity_by_clerk) {
        this.actual_delivered_quantity_by_clerk = actual_delivered_quantity_by_clerk;
    }


    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }

    public int getProposed_quantity() {
        return proposed_quantity;
    }

    public void setProposed_quantity(int proposed_quantity) {
        this.proposed_quantity = proposed_quantity;
    }

    public int getActual_received_quantity_by_representative() {
        return actual_received_quantity_by_representative;
    }

    public void setActual_received_quantity_by_representative(int actual_received_quantity_by_representative) {
        this.actual_received_quantity_by_representative = actual_received_quantity_by_representative;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getStart_order_date() {
        return start_order_date;
    }

    public void setStart_order_date(String start_order_date) {
        this.start_order_date = start_order_date;
    }

    public String getDelivered_order_date() {
        return delivered_order_date;
    }

    public void setDelivered_order_date(String delivered_order_date) {
        this.delivered_order_date = delivered_order_date;
    }

    public Staff getStaff_obj() {
        return staff_obj;
    }

    public void setStaff_obj(Staff staff_obj) {
        this.staff_obj = staff_obj;
    }

    public Item getItem_obj() {
        return item_obj;
    }

    public void setItem_obj(Item item_obj) {
        this.item_obj = item_obj;
    }

    public Orders() { }
    public Orders(int ordersId, int actual_delivered_quantity, String delivered_order_date) {
        this.ordersId = ordersId;
        this.actual_received_quantity_by_representative = actual_delivered_quantity;
        this.delivered_order_date = delivered_order_date;
    }

    public Orders(int ordersId, int proposed_quantity, Staff staff_obj, Item item_obj) {
        this.ordersId = ordersId;
        this.proposed_quantity = proposed_quantity;
        this.staff_obj = staff_obj;
        this.item_obj = item_obj;
    }
    public Orders(int ordersId, int proposed_quantity, Staff staff_obj, Item item_obj,String delivered_order_date) {
        this.ordersId = ordersId;
        this.proposed_quantity = proposed_quantity;
        this.staff_obj = staff_obj;
        this.item_obj = item_obj;
        this.delivered_order_date = delivered_order_date;
    }

    public Orders(int ordersId, int actual_delivered_quantity_by_clerk, int actual_received_quantity_by_representative, String delivered_order_date, Item item_obj) {
        this.ordersId = ordersId;
        this.actual_delivered_quantity_by_clerk = actual_delivered_quantity_by_clerk;
        this.actual_received_quantity_by_representative = actual_received_quantity_by_representative;
        this.delivered_order_date = delivered_order_date;
        this.item_obj = item_obj;
    }
}
