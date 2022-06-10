package polovinko.leontii.tophitsanalyzer.dto.decile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoubleDecile extends BaseDecile {

   private Double min;
   private Double max;

   public DoubleDecile(Double min, Double max, Long count) {
      this.min = min;
      this.max = max;
      this.count = count;
   }
}
