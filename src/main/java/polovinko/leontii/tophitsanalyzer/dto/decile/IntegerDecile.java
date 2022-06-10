package polovinko.leontii.tophitsanalyzer.dto.decile;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntegerDecile extends BaseDecile {

   @ApiModelProperty(value = "Min value in decile", example = "100")
   private Integer min;
   @ApiModelProperty(value = "Max value in decile", example = "150")
   private Integer max;

   public IntegerDecile(Integer min, Integer max, Long count) {
      this.min = min;
      this.max = max;
      this.count = count;
   }
}
