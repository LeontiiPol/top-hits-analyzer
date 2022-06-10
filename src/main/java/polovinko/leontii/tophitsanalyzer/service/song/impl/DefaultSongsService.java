package polovinko.leontii.tophitsanalyzer.service.song.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import polovinko.leontii.tophitsanalyzer.dto.decile.BaseDecile;
import polovinko.leontii.tophitsanalyzer.dto.decile.DoubleDecile;
import polovinko.leontii.tophitsanalyzer.dto.decile.IntegerDecile;
import polovinko.leontii.tophitsanalyzer.exception.TopHitsAnalyzerValidationException;
import polovinko.leontii.tophitsanalyzer.model.CsvColumnCell;
import polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn;
import polovinko.leontii.tophitsanalyzer.service.csv.CsvService;
import polovinko.leontii.tophitsanalyzer.service.song.SongsService;
import java.util.List;
import java.util.Collection;
import java.util.DoubleSummaryStatistics;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultSongsService implements SongsService {

   private final CsvService csvService;

   @Override
   public List<BaseDecile> getDecilesForColumn(SongsCsvColumn column) {
      List<CsvColumnCell> csvColumnCells = csvService.readCsvColumn(column);
      return createDeciles(csvColumnCells, column);
   }

   private List<BaseDecile> createDeciles(List<CsvColumnCell> csvColumnCells, SongsCsvColumn column) {
      return divideColumnCellsIntoGroups(csvColumnCells).stream()
          .map(group -> createDecile(group, column))
          .collect(Collectors.toList());
   }

   private Collection<List<CsvColumnCell>> divideColumnCellsIntoGroups(List<CsvColumnCell> csvColumnCells) {
      AtomicInteger rowsCounter = new AtomicInteger();
      int groupSize = (int) Math.ceil(csvColumnCells.size() / 10.0);
      return csvColumnCells.stream()
          .sorted()
          .collect(Collectors.groupingBy(columnCell -> rowsCounter.getAndIncrement() / groupSize))
          .values();
   }

   private BaseDecile createDecile(List<CsvColumnCell> csvColumnCells, SongsCsvColumn column) {
      DoubleSummaryStatistics statistics = getSongsSummaryStatistics(csvColumnCells);
      if (column.isInteger()) {
         return new IntegerDecile((int) statistics.getMin(), (int) statistics.getMax(), statistics.getCount());
      }
      return new DoubleDecile(statistics.getMin(), statistics.getMax(), statistics.getCount());
   }

   private DoubleSummaryStatistics getSongsSummaryStatistics(List<CsvColumnCell> csvColumnCells) {
      return csvColumnCells.stream()
          .mapToDouble(CsvColumnCell::getData)
          .summaryStatistics();
   }

   @Override
   public List<BaseDecile> getDecilesForColumnFilteredByYear(SongsCsvColumn songsCsvColumn, Integer year) {
      if (year < 2000 || year > 2019) {
         throw new TopHitsAnalyzerValidationException("Year must be in range from 2000 to 2019");
      }
      List<CsvColumnCell> csvColumnCells = csvService.readCsvColumnFilteredByYear(songsCsvColumn, year);
      return createDeciles(csvColumnCells, songsCsvColumn);
   }
}
