package polovinko.leontii.tophitsanalyzer.service.csv.impl;

import static polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn.YEAR;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import polovinko.leontii.tophitsanalyzer.exception.CsvParsingException;
import polovinko.leontii.tophitsanalyzer.model.CsvColumnCell;
import polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn;
import polovinko.leontii.tophitsanalyzer.service.csv.CsvService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DefaultCsvService implements CsvService {

   private final ResourceLoader resourceLoader;
   private final String csvFilePath;

   public DefaultCsvService(ResourceLoader resourceLoader, @Value("${csv.paths.songs}") String csvFilePath) {
      this.resourceLoader = resourceLoader;
      this.csvFilePath = csvFilePath;
   }

   @Override
   public List<CsvColumnCell> readCsvColumn(SongsCsvColumn column) {
      Object[] csvColumn = Arrays.stream(getCsvRows())
          .map(Object[].class::cast)
          .map(row -> row[column.getPosition()])
          .toArray();
      return mapRawCsvColumnToModelList(csvColumn);
   }

   private Object[] getCsvRows() {
      Resource resource = resourceLoader.getResource(csvFilePath);
      try (InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
           Reader reader = new BufferedReader(inputStreamReader);
           CSVReader csvReader = new CSVReader(reader)) {
         return StreamSupport.stream(csvReader.spliterator(), false)
             .skip(1)
             .toArray();
      } catch (Exception ex) {
         throw new CsvParsingException(ex.getMessage());
      }
   }

   private List<CsvColumnCell> mapRawCsvColumnToModelList(Object[] csvColumn) {
      return Arrays.stream(csvColumn)
          .map(Object::toString)
          .map(Double::parseDouble)
          .map(CsvColumnCell::new)
          .collect(Collectors.toList());
   }

   @Override
   public List<CsvColumnCell> readCsvColumnFilteredByYear(SongsCsvColumn column, Integer year) {
      Object[] csvColumn = Arrays.stream(getCsvRows())
          .map(Object[].class::cast)
          .filter(row -> row[YEAR.getPosition()].equals(year.toString()))
          .map(row -> row[column.getPosition()])
          .toArray();
      return mapRawCsvColumnToModelList(csvColumn);
   }
}
