package polovinko.leontii.tophitsanalyzer.convertor;

import org.springframework.core.convert.converter.Converter;
import polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn;

public class StringToSongsCsvColumnConvertor implements Converter<String, SongsCsvColumn> {

   @Override
   public SongsCsvColumn convert(String value) {
      return SongsCsvColumn.valueOf(value.toUpperCase());
   }
}
