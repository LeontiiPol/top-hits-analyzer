package polovinko.leontii.tophitsanalyzer.service.csv.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import polovinko.leontii.tophitsanalyzer.model.CsvColumnCell;
import polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@ActiveProfiles("test")
class DefaultCsvServiceIntegrationTest {

   @Autowired
   private DefaultCsvService defaultCsvService;

   @ParameterizedTest(name = "{index} {0}")
   @MethodSource("getArguments")
   void readCsvColumn(String testName, SongsCsvColumn column) {
      List<CsvColumnCell> csvColumnCells = defaultCsvService.readCsvColumn(column);

      assertEquals(20, csvColumnCells.size());
   }

   @ParameterizedTest(name = "{index} {0}")
   @MethodSource("getArguments")
   void readCsvColumnFilteredByYear(String testName, SongsCsvColumn column) {
      List<CsvColumnCell> csvColumnCells = defaultCsvService.readCsvColumnFilteredByYear(column, 2001);

      assertEquals(10, csvColumnCells.size());
   }

   private static Stream<Arguments> getArguments() {
      return Stream.of(
          Arguments.of(
              "whenYearColumnPassed_thenParsesCsvAndReturnsCsvColumnCellsWithColumnData",
              SongsCsvColumn.YEAR
          ),
          Arguments.of(
              "whenKeyColumnPassed_thenParsesCsvAndReturnsCsvColumnCellsWithColumnData",
              SongsCsvColumn.KEY
          ),
          Arguments.of(
              "whenPopularityColumnPassed_thenParsesCsvAndReturnsCsvColumnCellsWithColumnData",
              SongsCsvColumn.POPULARITY
          ),
          Arguments.of(
              "whenDanceabilityColumnPassed_thenParsesCsvAndReturnsCsvColumnCellsWithColumnData",
              SongsCsvColumn.DANCEABILITY
          ),
          Arguments.of(
              "whenEnergyColumnPassed_thenParsesCsvAndReturnsCsvColumnCellsWithColumnData",
              SongsCsvColumn.ENERGY
          ),
          Arguments.of(
              "whenDurationMsColumnPassed_thenParsesCsvAndReturnsCsvColumnCellsWithColumnData",
              SongsCsvColumn.DURATION_MS
          )
      );
   }
}
