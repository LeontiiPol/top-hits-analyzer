package polovinko.leontii.tophitsanalyzer.service.csv;

import polovinko.leontii.tophitsanalyzer.model.CsvColumnCell;
import polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn;
import java.util.List;

public interface CsvService {

   List<CsvColumnCell> readCsvColumn(SongsCsvColumn column);

   List<CsvColumnCell> readCsvColumnFilteredByYear(SongsCsvColumn column, Integer year);
}
