import models.Order;
import models.Product;
import models.User;

import java.util.*;

public class ProductManagerImpl implements ProductManager {
    private List<Product> productList;
    private Queue<Order> orderQueue;
    private List<User> userList;

    public ProductManagerImpl() {
        productList = new ArrayList<>();
        orderQueue = new LinkedList<>();
        userList = new ArrayList<>();
    }

    @Override
    public void addProduct(String id, String name, double price) {
        productList.add(new Product(id, name, price));
    }

    @Override
    public List<Product> getProductsByPrice() {
        List<Product> orderedProducts = new ArrayList<>();
        orderedProducts.addAll(productList);
        Collections.sort(orderedProducts, Comparator.comparingDouble(Product::getPrice));
        return orderedProducts;
    }

    @Override
    public void addOrder(Order order) {
        orderQueue.add(order);

        for (User user : userList) {
            if(Objects.equals(user.GetDNI(), order.getDNI())){
                user.AddOrder(order);
                break;
            }
        }
    }

    @Override
    public int numOrders() {
        return orderQueue.size();
    }

    // Cuando tu vendes la orden, cuando la das al cliente se suman los productos de venta
    @Override
    public Order deliverOrder() {
        Order order = orderQueue.poll();

        // Hay que recorrer la lista de alimentos  y el producto con nombre = al alimento sacado de la lista se le sube +1 las ventas
        for(int i = 0; i<order.getSizeAlimento(); i++){
            if(order.ContainsAlimento(productList.get(i).getName())){

                // Hemos encontrado que el producto que se ha vendido esta en la lista de productos
                // Ahora tenemos que subir las ventas a ese producto
                productList.get(i).UpSales();

            }
        }
        return order;
    }

    @Override
    public Product getProduct(String c1) {

        for(Product product : productList){
            if(product.getId().equals(c1)){
                return product;
            }
        }
        return null;
    }

    @Override
    public User getUser(String number) {
        return null;
    }
}
