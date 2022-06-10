package polovinko.leontii.tophitsanalyzer.service.song.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import polovinko.leontii.tophitsanalyzer.dto.decile.BaseDecile;
import polovinko.leontii.tophitsanalyzer.dto.decile.DoubleDecile;
import polovinko.leontii.tophitsanalyzer.dto.decile.IntegerDecile;
import polovinko.leontii.tophitsanalyzer.exception.TopHitsAnalyzerValidationException;
import polovinko.leontii.tophitsanalyzer.model.CsvColumnCell;
import polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn;
import polovinko.leontii.tophitsanalyzer.service.csv.CsvService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class DefaultSongsServiceUnitTest {

   @Mock
   private CsvService csvService;
   @InjectMocks
   private DefaultSongsService defaultSongsService;
   private List<CsvColumnCell> csvColumnCells;

   @BeforeEach
   void setUp() {
      csvColumnCells = DoubleStream.iterate(1.0, x -> x += 1)
          .limit(20)
          .mapToObj(CsvColumnCell::new)
          .collect(Collectors.toList());
   }

   @Test
   void getDecilesForColumn_whenMethodInvokedOnCsvColumnThatContainsDoubleData_thenDoubleDecilesReturned() {
      when(csvService.readCsvColumn(SongsCsvColumn.DANCEABILITY)).thenReturn(csvColumnCells);

      List<BaseDecile> deciles = defaultSongsService.getDecilesForColumn(SongsCsvColumn.DANCEABILITY);

      assertEquals(10, deciles.size());
      assertAll(getAssertionsToCheckDoubleDeciles(deciles, csvColumnCells));
   }

   @Test
   void getDecilesForColumn_whenMethodInvokedOnCsvColumnThatContainsIntegerData_thenIntegerDecilesReturned() {
      when(csvService.readCsvColumn(SongsCsvColumn.DURATION_MS)).thenReturn(csvColumnCells);

      List<BaseDecile> deciles = defaultSongsService.getDecilesForColumn(SongsCsvColumn.DURATION_MS);

      assertEquals(10, deciles.size());
      assertAll(getAssertionsToCheckIntegerDeciles(deciles, csvColumnCells));
   }

   @Test
   void getDecilesForColumn_whenCsvColumnCellsAreNotSorted_thenSortCellsAndReturnDeciles() {
      List<CsvColumnCell> shuffledCsvColumnCells = new ArrayList<>(csvColumnCells);
      Collections.shuffle(shuffledCsvColumnCells);
      when(csvService.readCsvColumn(SongsCsvColumn.DANCEABILITY)).thenReturn(shuffledCsvColumnCells);

      List<BaseDecile> deciles = defaultSongsService.getDecilesForColumn(SongsCsvColumn.DANCEABILITY);

      assertEquals(10, deciles.size());
      assertAll(getAssertionsToCheckDoubleDeciles(deciles, csvColumnCells));
   }

   @Test
   void getDecilesForColumnFilteredByYear_whenMethodInvoked_thenDecilesOfFilteredColumnReturned() {
      when(csvService.readCsvColumnFilteredByYear(SongsCsvColumn.DANCEABILITY, 2005)).thenReturn(csvColumnCells);

      List<BaseDecile> deciles =
          defaultSongsService.getDecilesForColumnFilteredByYear(SongsCsvColumn.DANCEABILITY, 2005);

      assertEquals(10, deciles.size());
      assertAll(getAssertionsToCheckDoubleDeciles(deciles, csvColumnCells));
   }

   @ParameterizedTest(name = "{index} {0}")
   @MethodSource("getArguments")
   void getDecilesForColumnFilteredByYear(String testName, Integer year, String exceptionMessage) {
      TopHitsAnalyzerValidationException exception =
          assertThrows(
              TopHitsAnalyzerValidationException.class,
              () -> defaultSongsService.getDecilesForColumnFilteredByYear(SongsCsvColumn.DANCEABILITY, year)
          );
      assertEquals(exceptionMessage, exception.getMessage());
   }

   private Executable[] getAssertionsToCheckDoubleDeciles(List<BaseDecile> deciles, List<CsvColumnCell> csvColumnCells) {
      List<Executable> assertions = new ArrayList<>();
      for (int i = 0; i < deciles.size(); i++) {
         DoubleDecile doubleDecile = (DoubleDecile) deciles.get(i);
         Double actualMin = doubleDecile.getMin();
         Double actualMax = doubleDecile.getMax();
         Long actualCount = deciles.get(i).getCount();
         Double expectedMin = csvColumnCells.get(2 * i).getData();
         Double expectedMax = csvColumnCells.get(2 * i + 1).getData();

         assertions.add(() -> assertEquals(expectedMin, actualMin));
         assertions.add(() -> assertEquals(expectedMax, actualMax));
         assertions.add(() -> assertEquals(2, actualCount));
      }
      return assertions.toArray(Executable[]::new);
   }

   private Executable[] getAssertionsToCheckIntegerDeciles(List<BaseDecile> deciles, List<CsvColumnCell> csvColumnCells) {
      List<Executable> assertions = new ArrayList<>();
      for (int i = 0; i < deciles.size(); i++) {
         IntegerDecile integerDecile = (IntegerDecile) deciles.get(i);
         Integer actualMin = integerDecile.getMin();
         Integer actualMax = integerDecile.getMax();
         Long actualCount = deciles.get(i).getCount();
         Integer expectedMin = csvColumnCells.get(2 * i).getData().intValue();
         Integer expectedMax = csvColumnCells.get(2 * i + 1).getData().intValue();

         assertions.add(() -> assertEquals(expectedMin, actualMin));
         assertions.add(() -> assertEquals(expectedMax, actualMax));
         assertions.add(() -> assertEquals(2, actualCount));
      }
      return assertions.toArray(Executable[]::new);
   }

   private static Stream<Arguments> getArguments() {
      return Stream.of(
          Arguments.of(
              "whenYearLessThan2000_thenThrowsException",
              1999,
              "Year must be in range from 2000 to 2019"
          ),
          Arguments.of(
              "whenYearMoreThan2019_thenThrowsException",
              2022,
              "Year must be in range from 2000 to 2019"
          )
      );
   }
}
