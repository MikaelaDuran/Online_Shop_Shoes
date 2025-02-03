import models.*;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

    private Properties p = new Properties();


    public Repository() {

        try {
            p.load(new FileInputStream("/Users/mikaela/IdeaProjects/Online_Shop_Shoes/src/Setting.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Customer authenticateCustomer(String usernameInput, String passwordInput) {

        String query = "SELECT * FROM Customer WHERE userName = ? AND password = ?";
        Customer customer = null;

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("username"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, usernameInput);
            stmt.setString(2, passwordInput);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customer = new Customer(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("district"),
                        rs.getString("address"),
                        rs.getString("userName"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Felmeddelande! Gick ej koppla med databasen");
        }

        return customer; // Om ingen användare hittas, returneras null
    }

        public List<Product> getProducts() {
            List<Product> productList = new ArrayList<>();
            String query = "SELECT id, name, brand, price FROM Product;";

            try (Connection con = DriverManager.getConnection(
                    p.getProperty("connection"),
                    p.getProperty("username"),
                    p.getProperty("password"));
                 PreparedStatement stmt = con.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String brand = rs.getString("brand");
                    int price = rs.getInt("price");

                    Product product = new Product(id, name, brand, price);
                    productList.add(product);
                }

            } catch (SQLException e) {
                throw new RuntimeException("Felmeddelande! Kunde inte hämta produkter.", e);
            }

            return productList;
        }


        public List<Item> getItemInStock (int productId) {
        List<Item> itemList = new ArrayList<>();

        String query = "SELECT * FROM Item WHERE productId=? AND stock>0";

            try (Connection con = DriverManager.getConnection(
                    p.getProperty("connection"),
                    p.getProperty("username"),
                    p.getProperty("password"));
                 PreparedStatement stmt = con.prepareStatement(query))
            {

                stmt.setInt(1, productId);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    int productID = rs.getInt("productId");
                    String colour = rs.getString("colour");
                    int size = rs.getInt("size");
                    int stock = rs.getInt("stock");


                    Item item = new Item(id,productID,colour,size,stock);
                    itemList.add(item);

                }

            } catch (SQLException e) {
                throw new RuntimeException("Felmeddelande! Kunde inte hämta items.", e);
            }

            return itemList;
        }


        public Orders checkIfActiveOrderId (int customerId){
            // endast 1 active order per person = LIMIT 1
            String query = "SELECT * FROM Orders WHERE customerId = ? AND orderStatus= 'active' LIMIT 1";


            try (Connection con = DriverManager.getConnection(
                    p.getProperty("connection"),
                    p.getProperty("username"),
                    p.getProperty("password"));
                 PreparedStatement stmt = con.prepareStatement(query))

            {

                stmt.setInt(1,customerId);
                ResultSet rs = stmt.executeQuery();


                if (rs.next()) {
                    Orders order = new Orders();

                    order.setId(rs.getInt("id"));
                    order.setCustomerId(rs.getInt("customerID"));
                    order.setOrderCreateDate(rs.getTimestamp("orderDate").toLocalDateTime());
                    order.setOrderUpdateDate(rs.getTimestamp("updatedOrder").toLocalDateTime());
                    order.setStatus(rs.getString("orderStatus"));

                    return order; // Returnera order om den finns
                }

            } catch (SQLException e) {
                throw new RuntimeException("Felmeddelande! Kunde inte hämta order.", e);
            }

            return null; // Om ingen order hittades, returnera null
        }

}






