import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDB {

    private String cartDirectory;

    public ShoppingCartDB(String cartDirectory) {
        this.cartDirectory = cartDirectory;
    }

        public List<String> loadShoppingCart(String username){
            List<String> shoppingCart = new ArrayList<>();
            String filePath = cartDirectory + File.separator + username + ".db";

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
                String line;
                while ((line = reader.readLine()) != null){
                    shoppingCart.add(line);
                }
            } catch (IOException e){
                    System.out.println("Error found in loading shopping cart");
                }
                return shoppingCart;

                br.close();

            }

    public void saveShoppingCart (String username, List<String> shoppingCart){
        String filePath = cartDirectory + File.separator + username + ".db";
        try (FileWriter writer = new FileWriter(filePath)){
            for (String item: shoppingCart){
                writer.write(item + "\n");
            }
        } catch (IOException e){
            System.out.println("Error found in saving shopping cart");
        }
        fw.flush();
        fw.close();
    }

    public List<String> listUsers(){
        List<String> users = new ArrayList<>();
        File directory = new File(cartDirectory);

        if (directory.exists() && directory.isDirectory()){
            for (File file: directory.listFiles()){
                if (file.isFile()){
                    users.add(file.getName());
                }
            }

        }

        return users;
    }

    }


