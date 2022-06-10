package polovinko.leontii.tophitsanalyzer.convertor;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn;
import java.util.stream.Stream;

class StringToSongsCsvColumnConvertorUnitTest {

   private StringToSongsCsvColumnConvertor convertor;

   @BeforeEach
   void setUp() {
      convertor = new StringToSongsCsvColumnConvertor();
   }

   @Test
   void convert_whenIncorrectSongsCsvColumnNamePassed_thenThrowsException(){
      assertThrows(IllegalArgumentException.class, () -> convertor.convert("invalid_column_name"));
   }

   @ParameterizedTest(name = "{index} {0}")
   @MethodSource("getArguments")
   void convert(String testName, String stringToConvert, SongsCsvColumn result){
      SongsCsvColumn songsCsvColumn = convertor.convert(stringToConvert);

      assertSame(result, songsCsvColumn);
   }

   private static Stream<Arguments> getArguments() {
      return Stream.of(
          Arguments.of(
              "whenLowerCaseColumnNamePassed_thenStringConvertedToSongsCsvColumn",
              "year",
              SongsCsvColumn.YEAR
          ),
          Arguments.of(
              "whenUpperCaseColumnNamePassed_thenStringConvertedToSongsCsvColumn",
              "KEY",
              SongsCsvColumn.KEY
          ),
          Arguments.of(
              "whenMixedCaseColumnNamePassed_thenStringConvertedToSongsCsvColumn",
              "DuRation_Ms",
              SongsCsvColumn.DURATION_MS
          )
      );
   }
}
