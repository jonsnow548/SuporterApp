package ro.ase.com.suporterapp.shop;

import java.io.Serializable;
import java.util.UUID;

public class Order implements Serializable {
    private String id;
    private String address;
    private double total;
    private String paymentMethod;
    private String phoneNumber;
    private String status; // Stările posibile pot fi, de exemplu, "nou", "trimis", "livrat"

    // Constructor gol necesar pentru Firestore
    public Order() {
        this.id = UUID.randomUUID().toString(); // Generare id unic
        this.status = "nou"; // Setare stare inițială
    }

    // Constructor pentru inițializare fără starea livrării
    public Order(String address, double total, String paymentMethod, String phoneNumber) {
        this(); // Folosește constructorul fără parametri pentru setări implicite
        this.address = address;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.phoneNumber = phoneNumber;
    }

    // Constructor complet
    public Order(String id, String address, double total, String paymentMethod, String phoneNumber, String status) {
        this.id = id;
        this.address = address;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    // Getteri și setteri

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
