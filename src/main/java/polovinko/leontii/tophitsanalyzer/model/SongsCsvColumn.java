package polovinko.leontii.tophitsanalyzer.model;

public enum SongsCsvColumn {

   YEAR(4, true),
   KEY(8, true),
   POPULARITY(5, true),
   DANCEABILITY(6, false),
   ENERGY(7, false),
   DURATION_MS(2, true);

   private final Integer position;
   private final boolean isInteger;

   SongsCsvColumn(Integer position, boolean isInteger) {
      this.position = position;
      this.isInteger = isInteger;
   }

   public Integer getPosition() {
      return position;
   }

   public boolean isInteger() {
      return isInteger;
   }
}
