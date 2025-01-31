import models.Customer;
import models.Item;
import models.Product;

import java.util.List;
import java.util.Scanner;

public class ViewManager {

    Repository r = new Repository();
    Scanner scanner = new Scanner(System.in);
    ViewManager() {

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
            System.out.println("Välkommen " + customer.getFirstName() + "!");
            ProductView();
        }
        else {
            System.out.println("Felaktigt användarnamn eller lösenord!");
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
                ItemView(selectedProduct.getId());
            } else {
                System.out.println("Produkt hittades inte!");
            }
        }

        public void ItemView(int productId){

        List<Item> itemList = r.getItem(productId);

            System.out.println("\n-------------------------------------------------------------");
            System.out.printf("| %-20s | %-15s | %-10s |\n", "Storlek", "Färg", "Lager");
            System.out.println("-------------------------------------------------------------");

            for(Item item : itemList){
                System.out.printf("| %-20s | %-15s | %-10d |\n",
                        item.getSize(), item.getColour(),item.getStock());
            }

            System.out.println("-------------------------------------------------------------");
            System.out.print("Välj storlek: ");
            String selectedSize = scanner.nextLine().trim();
            System.out.print("Välj färg: ");
            String selectedColour = scanner.nextLine().trim();
            System.out.println("Du valde " + productView.selectedProductName);

        }
   }

