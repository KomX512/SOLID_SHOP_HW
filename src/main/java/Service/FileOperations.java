package Service;

import Trade.Nomenclature;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileOperations {

    public static void defaultNomenclatureCSV(String fileName, List nomenclatureList) {

        try (Reader reader = Files.newBufferedReader(Paths.get(fileName));) {

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);

            for (CSVRecord record : records) {

                if (!record.get(0).equals("")) {
                    Nomenclature newItem = new Nomenclature(record.get(0));
                    newItem.setArticul(record.get(1)); //Артикул
                    newItem.setBrand(record.get(2)); //Брэнд
                    newItem.setMeasure(record.get(3)); //Единица измерения
                    newItem.setWeight(Float.parseFloat(record.get(4)));  //Вес
                    newItem.setPrice(Float.parseFloat(record.get(5)));  //Цена

                    nomenclatureList.add(newItem);
                }
            }

            reader.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
