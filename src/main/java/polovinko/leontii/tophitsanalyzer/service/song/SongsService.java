package polovinko.leontii.tophitsanalyzer.service.song;

import polovinko.leontii.tophitsanalyzer.dto.SongsDecile;
import polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn;
import java.util.List;

public interface SongsService {

   List<SongsDecile> getDecilesForColumn(SongsCsvColumn column);

   List<SongsDecile> getDecilesForColumnFilteredByYear(SongsCsvColumn column, Integer year);
}
