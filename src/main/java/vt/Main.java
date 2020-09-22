package vt;


import java.util.List;
import java.util.Scanner;
import com.opencsv.exceptions.CsvValidationException;

public class Main {

	public static void main(String[] args) throws CsvValidationException {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		Service service = new Service();
		boolean exit = false;
		List<Product> products = service.readCsv();
		System.out.println("Welcome. This application will help you manage your products.");
		System.out.println("If you want to see full product list type in \"list\"");
		System.out.println("If you want to see products with quantity less that particular number - type in \"remainder\" and minimum quantity(example: remainder 10)");
		System.out.println("If you want to see products that will expire before particular date - type in \"expiry\" and date(example: expiry 2019-01-21)");
		System.out.println("To exit application type in \"exit\"");
		while(!exit) {			
		String input[] = scanner.nextLine().split(" ");
		if(input[0].equalsIgnoreCase("list")) {
			for (Product product : products) {
				System.out.println("Product name: " + product.getItemName() + " Product code: " + product.getCode() + " Quantity left: " + 
			product.getQuantity() + " Expiration date: " + product.getExpirationDate());
				System.out.println("----------------------------------------------------------------------------------");
			}
			}
		else if(input[0].equalsIgnoreCase("remainder") && (input.length > 1)) {
			if (input[1].matches("-?\\d+")) {
			System.out.println("Products that you have left less than " + input[1] + ":");
			for (Product product : service.findByQuantity(products, Integer.valueOf(input[1]))) {
				System.out.println("Product name: " + product.getItemName() + " Product code: " + product.getCode() + " Quantity left: " + 
			product.getQuantity() + " Expiration date: " + product.getExpirationDate());
				System.out.println("----------------------------------------------------------------------------------");
			}
			}
			else { System.out.println("Invalid number format. Try again."); }
		}
		else if(input[0].equalsIgnoreCase("expiry") && (input.length > 1)) {
			if ((input[1].matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) &&
			(Integer.valueOf(input[1].split("-")[1]) < 13) && (Integer.valueOf(input[1].split("-")[1]) > 0) &&
				(Integer.valueOf(input[1].split("-")[2]) < 31) && (Integer.valueOf(input[1].split("-")[2]) > 0)) {
			System.out.println("Products that expire before " + input[1] + ":");
			for (Product product : service.findByExpiration(products, input[1])) {
				System.out.println("Product name: " + product.getItemName() + " Products code: " + product.getCode() + " Quantity left: " + 
			product.getQuantity() + " Expiration date: " + product.getExpirationDate());
				System.out.println("----------------------------------------------------------------------------------");
			}
			}
			else { System.out.println("Invalid date format."); }
		}
		else if(input[0].equalsIgnoreCase("exit")) {
			exit = true;
			scanner.close();
			continue;
		}
		else {
			System.out.println("Invalid command. Please try again.");
		}
		}

	}

}
