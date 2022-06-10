package polovinko.leontii.tophitsanalyzer.dto.decile;

import io.swagger.annotations.ApiModelProperty;

public abstract class BaseDecile {

   @ApiModelProperty(value = "Number of values in decile", example = "200")
   protected Long count;

   public Long getCount() {
      return count;
   }
}
