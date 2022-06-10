package polovinko.leontii.tophitsanalyzer.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import polovinko.leontii.tophitsanalyzer.dto.decile.BaseDecile;
import polovinko.leontii.tophitsanalyzer.dto.decile.DoubleDecile;
import polovinko.leontii.tophitsanalyzer.dto.decile.IntegerDecile;
import polovinko.leontii.tophitsanalyzer.dto.error.ErrorResponseBody;
import polovinko.leontii.tophitsanalyzer.model.SongsCsvColumn;
import polovinko.leontii.tophitsanalyzer.service.song.SongsService;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/songs")
public class SongsAnalyzerController {

   private final SongsService songsService;

   @ApiOperation(
       value = "Returns deciles of a csv file's column. ",
       notes = "Depending on column data type, deciles can contain either integer or double data.")
   @ApiResponses({
       @ApiResponse(code = 200, message = "Successful", response = IntegerDecile[].class),
       @ApiResponse(code = 200, message = "Successful", response = DoubleDecile[].class),
       @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponseBody.class),
       @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponseBody.class)
   })
   @GetMapping(value = "/deciles", produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<List<BaseDecile>> getSongsDeciles(
       @ApiParam(
           example = "key",
           value = "Csv file's column name. Could be in any case.")
       @RequestParam("colname") SongsCsvColumn column,
       @ApiParam(
           example = "2005",
           value = "To filter songs by year")
       @RequestParam(value = "year", required = false) Optional<Integer> year) {

      if (year.isPresent()) {
         return ResponseEntity.ok()
             .body(songsService.getDecilesForColumnFilteredByYear(column, year.get()));
      }
      return ResponseEntity.ok()
          .body(songsService.getDecilesForColumn(column));
   }
}
