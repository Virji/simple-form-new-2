package com.simpleform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class IncomingPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String sender;
    private String recipient;
    private String deliveryAddress;
    private String weight;
    private Double price;
    private boolean isOfficeDelivery;


    public IncomingPackage() {
    }

    public IncomingPackage(String sender, String recipient, String deliveryAddress, String weight, boolean isOfficeDelivery) {
        this.sender = sender;
        this.recipient = recipient;
        this.deliveryAddress = deliveryAddress;
        this.weight = weight;
        this.isOfficeDelivery = isOfficeDelivery;


    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getWeight() {
        return weight;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setOfficeDelivery(boolean officeDelivery) {
        isOfficeDelivery = officeDelivery;
    }

    @Override
    public String toString() {
        return "IncomingPackage{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", weight='" + weight + '\'' +
                ", price=" + price +
                ", isOfficeDelivery=" + isOfficeDelivery +
                '}';
    }

    public double getNumericWeight() {
        try {
            return Double.parseDouble(weight.trim());
        } catch (NumberFormatException e) {
            // Handle the case where the weight is not a valid double
            return 0; // Or any default value or throw a custom exception
        }
    }

    public boolean isOfficeDelivery() {
        return isOfficeDelivery;
    }

    public void calculateAndSetPrice() {
        // Basic price calculation logic
        double basePrice = 5.0; // Base price for handling and processing
        double numericWeight = getNumericWeight(); // Convert weight string to double for comparison
        double weightPrice = numericWeight > 10.00 ? 10.00 : numericWeight; // Use numericWeight for comparison
        double deliveryPrice = isOfficeDelivery ? 5.00 : 15.00; // Cheaper if delivery to office

        this.price = basePrice + weightPrice + deliveryPrice;
    }


}
