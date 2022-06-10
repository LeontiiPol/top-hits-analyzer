package polovinko.leontii.tophitsanalyzer.dto.decile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoubleDecile extends BaseDecile {

   @ApiModelProperty(value = "Min value in decile", example = "100.5")
   private Double min;
   @ApiModelProperty(value = "Max value in decile", example = "150.1")
   private Double max;

   public DoubleDecile(Double min, Double max, Long count) {
      this.min = min;
      this.max = max;
      this.count = count;
   }
}
