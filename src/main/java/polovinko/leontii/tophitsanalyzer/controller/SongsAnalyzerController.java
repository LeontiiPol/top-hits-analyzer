package polovinko.leontii.tophitsanalyzer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import polovinko.leontii.tophitsanalyzer.dto.SongsDecile;
import polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn;
import polovinko.leontii.tophitsanalyzer.service.song.SongsService;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs")
@Validated
public class SongsAnalyzerController {

   private final SongsService songsService;

   @GetMapping("/deciles")
   public ResponseEntity<List<SongsDecile>> getSongsDeciles(@RequestParam("colname") SongsCsvColumn column,
                                                            @RequestParam("year") Optional<Integer> year) {
      if (year.isPresent()) {
         return ResponseEntity.ok()
             .body(songsService.getDecilesForColumnFilteredByYear(column, year.get()));
      }
      return ResponseEntity.ok()
          .body(songsService.getDecilesForColumn(column));
   }
}
