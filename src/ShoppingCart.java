import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ShoppingCart {
    public static void main(String[] args) {

        String cartDirectoryPath;
        if (args.length > 0) {
            cartDirectoryPath = args[0];
        } else {
            cartDirectoryPath = "db";

            File cartDirectory = new File(cartDirectoryPath);
            if (!cartDirectory.exists()) {
                cartDirectory.mkdir();
            }
        }

        ShoppingCartDB cartDatabase = new ShoppingCartDB(cartDirectoryPath);
        List<String> shoppingCart = new ArrayList<>();
        Set<String> uniqueShoppingCart = new HashSet<>();
        String currentUsername = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to your shopping cart");
        System.out.println("To continue, please input 'list', 'add' or 'delete'. To exit, please enter 'stop'");
        System.out.println(">");

        while (true) {
            String input = scanner.nextLine();

            if (input.startsWith("login")) {
                String username = input.substring(6);
                currentUsername = username;
                cartDatabase.loadShoppingCart(username, shoppingCart);
            }

            if (input.equals("save")) {
                if (currentUsername == null) {
                    System.out.println("Please login first");
                } else {
                    cartDatabase.saveShoppingCart(currentUsername, shoppingCart);
                }
            }

            if (input.equals("users")) {
                cartDatabase.listUsers();
            }

            if (input.equals("list")) {
                if (shoppingCart.size() == 0) {
                    System.out.println("Your cart is empty");
                } else {
                    for (int i = 0; i < shoppingCart.size(); i++) {
                        System.out.println((i + 1) + ". " + shoppingCart.get(i));
                    }
                }
            }

            if (input.startsWith("add")) {
                String addItem = input.substring(4);
                String[] multipleItems = addItem.split("[,\\s]+");
                for (String item : multipleItems) {
                    if (!uniqueShoppingCart.contains(item)) {
                        shoppingCart.add(item);
                        uniqueShoppingCart.add(item);
                        System.out.println(item + " is added to cart");
                    } else {
                        System.out.println(item + " is already in your cart");
                    }
                }
            }
            if (input.startsWith("delete")) {
                int deleteItemIndex = Integer.parseInt(input.substring(7));
                if ((deleteItemIndex >= 1) && (deleteItemIndex < shoppingCart.size())) {
                    String deleteItem = shoppingCart.remove(deleteItemIndex - 1);
                    System.out.println(deleteItem + " is removed from cart");
                } else {
                    System.out.println("Incorrect item index");
                }
            }

            if (input.equals("stop")) {
                break;
            }

        }

        scanner.close();

    }

}
