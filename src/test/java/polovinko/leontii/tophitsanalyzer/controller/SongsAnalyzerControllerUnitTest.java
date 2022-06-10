package polovinko.leontii.tophitsanalyzer.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn;
import polovinko.leontii.tophitsanalyzer.service.song.SongsService;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class SongsAnalyzerControllerUnitTest {

   @Mock
   private SongsService songsService;
   @InjectMocks
   private SongsAnalyzerController controller;

   @Test
   void getSongsDeciles_whenYearPassed_thenSongsFilteredByYear() {
      controller.getSongsDeciles(SongsCsvColumn.KEY, Optional.of(2019));

      verify(songsService).getDecilesForColumnFilteredByYear(SongsCsvColumn.KEY, 2019);
      verify(songsService, never()).getDecilesForColumn(SongsCsvColumn.KEY);
   }

   @Test
   void getSongsDeciles_whenYearNotPassed_thenAllSongsProcessed() {
      controller.getSongsDeciles(SongsCsvColumn.KEY, Optional.empty());

      verify(songsService).getDecilesForColumn(SongsCsvColumn.KEY);
      verify(songsService, never()).getDecilesForColumnFilteredByYear(same(SongsCsvColumn.KEY), anyInt());
   }
}
