package polovinko.leontii.tophitsanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SongsDecile {

   private Number min;
   private Number max;
   private Long count;
}
