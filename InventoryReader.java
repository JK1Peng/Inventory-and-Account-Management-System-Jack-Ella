//Program by Ella Withington 
import java.util.HashMap;
import java.io.*;
import java.util.*;

public class InventoryReader {
    static HashMap<String, String> data = new HashMap<String, String>();
public static void main(String[] args) {
}
public static HashMap<String,String> readFile(String filename){
	System.out.println(filename);
    File dataFile = new File(filename);
    if ( ! dataFile.exists() ) {
        System.out.println("No data file found.");
        System.exit(1);
    }
    try( Scanner scanner = new Scanner(dataFile) ) {
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            int separatorPosition = input.indexOf('<');
            int separatorPosition2 = input.indexOf('>');
            input = input.substring(separatorPosition + 1, separatorPosition2);
            if(input.equals("stockNumber")){
                String value = scanner.nextLine();
                value = value.trim();
                data.put("stockNumber", value);
            }
            if(input.equals("description")){
                String value = scanner.nextLine();
                value = value.trim();
                data.put("description", value);
            }
        }
    }
    catch (IOException e) {
        System.out.println("Error in data file.");
        System.exit(1);
    }
    for (String key : data.keySet()) {
        System.out.print("Key = " + key + ", ");
        System.out.println("Data = " + data.get(key));
        }
    return data;
}
}


