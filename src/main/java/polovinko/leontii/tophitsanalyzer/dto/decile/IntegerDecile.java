package polovinko.leontii.tophitsanalyzer.dto.decile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntegerDecile extends BaseDecile {

   private Integer min;
   private Integer max;

   public IntegerDecile(Integer min, Integer max, Long count) {
      this.min = min;
      this.max = max;
      this.count = count;
   }
}
