package polovinko.leontii.tophitsanalyzer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CsvColumnCell implements Comparable<CsvColumnCell> {

   private Double data;

   @Override
   public int compareTo(CsvColumnCell o) {
      return data.compareTo(o.getData());
   }
}
