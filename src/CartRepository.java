import models.CartItem;
import models.Orders;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CartRepository {

    private Properties p = new Properties();

    public CartRepository() {
        try {
            p.load(new FileInputStream("/Users/mikaela/IdeaProjects/Online_Shop_Shoes/src/Setting.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    //ADD TO CART - STORED PROCEDURE
    public int AddToCart(int customerId, Orders order, int produktId, int itemId) {
        Integer orderId = (order != null) ? order.getId() : null;
        int newOrderId = 0;

        String query = "{ CALL AddToCart(?, ?, ?, ?) }";

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("username"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall(query)) {

            stmt.setInt(1, customerId);

            if (orderId != null) {
                stmt.setInt(2, orderId);
            } else {
                stmt.setNull(2, Types.INTEGER);
            }

            stmt.setInt(3, produktId);
            stmt.setInt(4, itemId);
            stmt.execute();



        } catch (SQLException e) {

            System.out.println("SQLException: " + e.getMessage() + " " + e.getErrorCode());
            System.out.println("Kunde inte lägga till i varukorgen!");

        }

        return newOrderId;
    }

    public List<CartItem> getCartItems(int customerId) {
        String query = "SELECT * FROM CartView WHERE CustomerId = ?"; //Skapat en view i databasen
        List<CartItem> cartItems = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("username"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem(
                        rs.getInt("OrderID"),
                        rs.getInt("CustomerId"),
                        rs.getString("Customer"),
                        rs.getString("Product"),
                        rs.getInt("Size"),
                        rs.getString("Colour"),
                        rs.getString("Status"),
                        rs.getInt("ItemID"),
                        rs.getInt("Quantity")
                );
                cartItems.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Kunde inte hämta varukorgen!");
        }

        return cartItems;
    }
}

