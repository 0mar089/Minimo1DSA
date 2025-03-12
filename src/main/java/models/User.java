package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class User {
    String dni;
    Queue<Order> orders;

    public User(String dni) {
        orders = new LinkedList<>();
    }

    public void AddOrder(Order o) {
        orders.add(o);
    }

    public String GetDNI() {
        return dni;
    }

    public List<Order> GetOrders() {
        
    }
}
