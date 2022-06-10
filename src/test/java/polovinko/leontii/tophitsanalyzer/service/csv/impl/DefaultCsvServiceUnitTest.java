package polovinko.leontii.tophitsanalyzer.service.csv.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.ResourceLoader;
import polovinko.leontii.tophitsanalyzer.exception.CsvParsingException;
import polovinko.leontii.tophitsanalyzer.model.CsvColumnCell;
import polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class DefaultCsvServiceUnitTest {

   private final static String FILE_LOCATION = "filePath";
   private final static String CSV_DATA = "artist,song,duration_ms,explicit,year,popularity,danceability,energy,key\n" +
       "artist1,song1,1,False,2010,1,0.05,0.05,0\n" +
       "artist2,song2,2,True,2011,5,0.1,0.1,1";

   @Mock
   private ResourceLoader resourceLoader;
   @Mock
   private FileUrlResource resource;
   private DefaultCsvService defaultCsvService;

   @BeforeEach
   void setUp() throws IOException {
      when(resourceLoader.getResource(FILE_LOCATION)).thenReturn(resource);
      when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(CSV_DATA.getBytes(StandardCharsets.UTF_8)));
      defaultCsvService = new DefaultCsvService(resourceLoader, FILE_LOCATION);
   }

   @Test
   void readCsvColumn_whenExceptionThrownWhileCsvParsing_thenWrapsThrownException() throws IOException {
      when(resource.getInputStream()).thenThrow(RuntimeException.class);

      assertThrows(CsvParsingException.class, () -> defaultCsvService.readCsvColumn(SongsCsvColumn.KEY));
   }

   @ParameterizedTest(name = "{index} {0}")
   @MethodSource("getArguments")
   void readCsvColumn(String testName, SongsCsvColumn column, Double firstRowData, Double secondRowData) {
      List<CsvColumnCell> csvColumnCells = defaultCsvService.readCsvColumn(column);

      assertEquals(2, csvColumnCells.size());
      assertEquals(firstRowData, csvColumnCells.get(0).getData());
      assertEquals(secondRowData, csvColumnCells.get(1).getData());
   }

   @ParameterizedTest(name = "{index} {0}")
   @MethodSource("getArguments")
   void readCsvColumnFilteredByYear(String testName, SongsCsvColumn column, Double firstRowData) {
      List<CsvColumnCell> csvColumnCells = defaultCsvService.readCsvColumnFilteredByYear(column, 2010);

      assertEquals(1, csvColumnCells.size());
      assertEquals(firstRowData, csvColumnCells.get(0).getData());
   }

   private static Stream<Arguments> getArguments() {
      return Stream.of(
          Arguments.of(
              "whenYearColumnPassed_thenParsesCsvAndReturnsCsvColumnCellsWithColumnData",
              SongsCsvColumn.YEAR,
              2010d,
              2011d
          ),
          Arguments.of(
              "whenKeyColumnPassed_thenParsesCsvAndReturnsCsvColumnCellsWithColumnData",
              SongsCsvColumn.KEY,
              0d,
              1d
          ),
          Arguments.of(
              "whenPopularityColumnPassed_thenParsesCsvAndReturnsCsvColumnCellsWithColumnData",
              SongsCsvColumn.POPULARITY,
              1d,
              5d
          ),
          Arguments.of(
              "whenDanceabilityColumnPassed_thenParsesCsvAndReturnsCsvColumnCellsWithColumnData",
              SongsCsvColumn.DANCEABILITY,
              0.05,
              0.1
          ),
          Arguments.of(
              "whenEnergyColumnPassed_thenParsesCsvAndReturnsCsvColumnCellsWithColumnData",
              SongsCsvColumn.ENERGY,
              0.05,
              0.1
          ),
          Arguments.of(
              "whenDurationMsColumnPassed_thenParsesCsvAndReturnsCsvColumnCellsWithColumnData",
              SongsCsvColumn.DURATION_MS,
              1d,
              2d
          )
      );
   }
}
