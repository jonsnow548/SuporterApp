package ro.ase.com.suporterapp.shop;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    public static final List<Product> CART_ITEMS = new ArrayList<>();

    //getter
    public static List<Product> getCartItems() {
        return CART_ITEMS;
    }
    public static void addToCart(Product product) {
        CART_ITEMS.add(product);
    }

    public static void removeFromCart(Product product) {
        CART_ITEMS.remove(product);
    }
    public static boolean isInCart(Product product) {
        for (Product item : CART_ITEMS) {
            if (item.getId() == product.getId()) {
                return true;
            }
        }
        return false;
    }
    public static double getTotal() {
        double total = 0;
        for (Product product : CART_ITEMS) {
            total += product.getPrice();
        }
        return total;
    }
    public static void clear() {
        CART_ITEMS.clear();
    }
}
