import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoppingCartDB {

    private String cartDirectory;

    public ShoppingCartDB(String cartDirectory) {
        this.cartDirectory = cartDirectory;
    }

    public void loadShoppingCart(String username, List<String> shoppingCart) {
        String cartFilePath = cartDirectory + File.separator + username + ".db";
        List<String> loadedItems = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(cartFilePath))) {
            while (fileScanner.hasNextLine()) {
                String item = fileScanner.nextLine();
                loadedItems.add(item);
            }
        } catch (FileNotFoundException e) {
            System.out.println(username + ", your cart is empty");
            shoppingCart.clear();
            return;
        }

        shoppingCart.clear();
        shoppingCart.addAll(loadedItems);

        if (loadedItems.isEmpty()) {
            System.out.println(username + ", your cart is empty");
        } else {
            System.out.println(username + ", your cart contains the following items:");
            for (int i = 0; i < loadedItems.size(); i++) {
                System.out.println((i + 1) + ". " + loadedItems.get(i));
            }
        }

    }

    public void saveShoppingCart(String username, List<String> shoppingCart) {
        String cartFilePath = cartDirectory + File.separator + username + ".db";
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(cartFilePath))) {
            for (String item : shoppingCart) {
                printWriter.println(item);
            }
            System.out.println("Your cart has been saved");
        } catch (IOException e) {
            System.err.println("Failed to save cart");
        }
    }

    public void listUsers() {
        List<String> users = new ArrayList<>();
        File userDatabase = new File(cartDirectory);
        File[] files = userDatabase.listFiles();
        try {
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".db")) {
                        users.add(file.getName().replace(".db", ""));
                    }
                }
            }
            if (!users.isEmpty()) {
                System.out.println("The following users are registered");
                for (int i = 0; i < users.size(); i++) {
                    System.out.println((i + 1) + ". " + users.get(i));
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load users");
        }
    }

}