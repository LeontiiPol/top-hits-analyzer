package polovinko.leontii.tophitsanalyzer.service.song;

import polovinko.leontii.tophitsanalyzer.dto.decile.BaseDecile;
import polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn;
import java.util.List;

public interface SongsService {

   List<BaseDecile> getDecilesForColumn(SongsCsvColumn column);

   List<BaseDecile> getDecilesForColumnFilteredByYear(SongsCsvColumn column, Integer year);
}
