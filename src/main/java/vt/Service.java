package vt;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Service {
	public List<Product> readCsv() throws CsvValidationException {
		String csvFile = "csv/sample.csv";
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<Product> products = new ArrayList<Product>();
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			reader.readNext();
			while ((line = reader.readNext()) != null) {
				Product product = new Product();
				product.setItemName(line[0]);
				product.setCode(Long.valueOf(line[1]));
				product.setQuantity(Integer.valueOf(line[2]));
				product.setExpirationDate(LocalDate.parse(line[3], dateFormat));
				Product sameProduct = products.stream().filter(x -> x.getItemName().equals(product.getItemName())).
						filter(x -> x.getCode() == product.getCode())
						.filter(x -> x.getExpirationDate().equals(product.getExpirationDate())).findFirst().orElse(null);
				if (sameProduct != null) {
					int index = products.indexOf(sameProduct);
					sameProduct.setQuantity(sameProduct.getQuantity() + product.getQuantity());
					products.set(index, sameProduct);
				}
				else {
					products.add(product);
				}


			}
			products = products.stream()
			  .sorted((object1, object2) -> object1.getItemName().compareTo(object2.getItemName())).collect(Collectors.toList());

		} catch (IOException e) {
			System.out.println("Reading CSV file failed..." + e);
			e.printStackTrace();
		}
		return products;
	}

	public List<Product> findByQuantity(List<Product> products, int quantity) {
		
		return products.stream().filter(x -> x.getQuantity() < quantity).collect(Collectors.toList());
	}

	public List<Product> findByExpiration(List<Product> products, String date) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	 return products.stream().filter(x -> x.getExpirationDate().isBefore(LocalDate.parse(date, dateFormat))).collect(Collectors.toList());
	}

}
