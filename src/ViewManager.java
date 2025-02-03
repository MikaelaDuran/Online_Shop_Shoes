import models.*;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class ViewManager {

    Repository r = new Repository();
    Scanner scanner = new Scanner(System.in);
    CartRepository rc = new CartRepository();

    // Deklarera utanför metoderna för att kunna använda i andra klasser
    private int customerId;
    private int itemId;
    private int orderId;
    private int productId;


        public void Meny(){
            System.out.println("Meny \n1.Produkter \n2.Varukorg \n3.Betala \n4.Logga ut" +
                    "\nVälj ett alternativ:");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: ProductView();
                    break;
                case 2: CartItemsView(customerId);
                    break;
                case 3:
                    System.out.println("BETALA");
                    break;
                case 4:
                    System.out.println("LOGGA UT");
                    break;
            }
        }
        public void LoginView(){

        System.out.println("Välkommen till Online-shoppen för skor!");
        System.out.println("Logga in");
        System.out.print("Användarnamn: ");
        String usernameInput = scanner.nextLine().trim();
        System.out.print("Lösenord: ");
        String passwordInput = scanner.nextLine().trim();

        Customer customer = r.authenticateCustomer(usernameInput, passwordInput);

        if (customer != null) {
            customerId = customer.getId(); // TODO: Spara inloggad kunds ID
            System.out.println("Välkommen " + customer.getFirstName() + "!");
            Meny();
        }
        else {
            System.out.println("Felaktigt användarnamn eller lösenord!");
            LoginView();
        }
    }

        public void ProductView(){
        List<Product> products = r.getProducts();

        if(products.isEmpty()){
            System.out.println("Inga produkter finns tillgängliga!");
            return;
        }
            //Utskrift av tabell
            System.out.println("\n-------------------------------------------------");
            System.out.printf("| %-20s | %-15s | %-10s |\n", "Produkt", "Märke", "Pris (kr)");
            System.out.println("-------------------------------------------------");

            for (Product p : products) {
                System.out.printf("| %-20s | %-15s | %-10d |\n",
                        p.getName(), p.getBrand(), p.getPrice());
            }

            System.out.println("-------------------------------------------------");
            System.out.print("Skriv in en produkt: ");
            String selectedProductName = scanner.nextLine().trim();

            // Söker efter produkten med produkt namn
            Product selectedProduct = null;
            for (Product p : products) {
                if (p.getName().equalsIgnoreCase(selectedProductName)) {
                    selectedProduct = p;
                    break;
                }
            }

            if (selectedProduct != null) {
                // Skickar produktens ID till ItemView
                ItemView(productId = selectedProduct.getId());
            } else {
                System.out.println("Produkt hittades inte!");
            }
        }

        public void ItemView(int productId){

        List<Item> itemList = r.getItemInStock(productId);

            System.out.println("\n-------------------------------------------------------------");
            System.out.printf("| %-20s | %-15s | %-10s |\n", "Storlek", "Färg", "Lager");
            System.out.println("-------------------------------------------------------------");

            for(Item item : itemList){
                System.out.printf("| %-20s | %-15s | %-10d |\n",
                        item.getSize(), item.getColour(),item.getStock());
            }

            System.out.println("-------------------------------------------------------------");
            System.out.print("Välj färg: ");
            String selectedColour = scanner.nextLine().trim();
            System.out.print("Välj storlek: ");
            int selectedSize = scanner.nextInt();

            //Kontrollera att vald item finns (ItemId)
            Item selectedItem = null;
            for (Item item : itemList) {
                if (item.getColour().equalsIgnoreCase(selectedColour) && item.getSize() == selectedSize) {
                    selectedItem = item;
                    break;
                }
            }

            if (selectedItem != null) {
                itemId = selectedItem.getId(); // Id för item
                Orders order = r.checkIfActiveOrderId(customerId);
                rc.AddToCart(customerId, order, productId, itemId);
                Meny();

            } else {
                System.out.println("Den valda item hittades inte!");
                Meny();

            }
        }

    public void CartItemsView(int customerId) {
        List<CartItem> items = rc.getCartItems(customerId);

        if (items.isEmpty()) {
            System.out.println("Inga produkter hittades i varukorgen för kund ID: " + customerId);
            return;
        }

        System.out.println("\n--- Varukorg för CustomerId: " + customerId + " ---");
        System.out.printf("%-8s %-10s %-15s %-10s %-10s %-10s %-8s %-8s\n",
                "OrderID", "Customer", "Product", "Size", "Colour", "Status", "ItemID", "Quantity");
        System.out.println("------------------------------------------------------------------------");

        for (CartItem item : items) {
            System.out.printf("%-8d %-10s %-15s %-10d %-10s %-10s %-8d %-8d\n",
                    item.getOrderId(), item.getCustomer(), item.getProduct(),
                    item.getSize(), item.getColour(), item.getStatus(),
                    item.getItemId(), item.getQuantity());
        }
    }
}

