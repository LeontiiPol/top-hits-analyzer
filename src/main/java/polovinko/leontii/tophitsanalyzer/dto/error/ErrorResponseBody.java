package polovinko.leontii.tophitsanalyzer.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseBody {

   @ApiModelProperty(value = "Error message", example = "Year must be in range from 2000 to 2019")
   private String message;
   @ApiModelProperty(value = "Time when error occurred in yyyy-MM-dd HH:mm:ss format", example = "2022-04-29 14:22:32")
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime happenedAt;
}
