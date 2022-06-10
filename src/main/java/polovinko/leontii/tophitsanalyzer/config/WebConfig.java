package polovinko.leontii.tophitsanalyzer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import polovinko.leontii.tophitsanalyzer.convertor.StringToSongsCsvColumnConvertor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

   @Override
   public void addFormatters(FormatterRegistry registry) {
      registry.addConverter(new StringToSongsCsvColumnConvertor());
   }
}
