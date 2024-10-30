package org.example.laptopthachthat.User;

import javafx.scene.image.Image;

public class Product {


        private int productID;
        private boolean stock;
        private Image image;
        private String productName;
        private String description;
        private int quality;
        private double price;


        public Product(int productID, boolean stock, Image image, String productName, String description, int quality, double price) {
            this.productID = productID;
            this.stock = stock;
            this.image = image;
            this.productName = productName;
            this.description = description;
            this.quality = quality;
            this.price = price;
        }

    }

