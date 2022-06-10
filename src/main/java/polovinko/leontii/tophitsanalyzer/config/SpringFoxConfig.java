package polovinko.leontii.tophitsanalyzer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import polovinko.leontii.tophitsanalyzer.dto.decile.BaseDecile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

   private final String appDescription;
   private final String developerName;
   private final String developerEmail;
   private final String developerCvUrl;

   public SpringFoxConfig(@Value("${swagger.app.description}") String appDescription,
                          @Value("${swagger.developer.name}") String developerName,
                          @Value("${swagger.developer.email}") String developerEmail,
                          @Value("${swagger.developer.cv}") String developerCvUrl) {
      this.appDescription = appDescription;
      this.developerName = developerName;
      this.developerEmail = developerEmail;
      this.developerCvUrl = developerCvUrl;
   }

   @Bean
   public Docket createRestApi() {
      return new Docket(DocumentationType.SWAGGER_2)
          .useDefaultResponseMessages(false)
          .apiInfo(apiInfo())
          .select()
          .apis(RequestHandlerSelectors.basePackage("polovinko.leontii.tophitsanalyzer"))
          .paths(PathSelectors.any())
          .build()
          .ignoredParameterTypes(BaseDecile.class);
   }

   private ApiInfo apiInfo() {
      return new ApiInfoBuilder()
          .title("Top-hits-analyzer API Documentation")
          .description(appDescription)
          .contact(new Contact(developerName, developerCvUrl, developerEmail))
          .version("1.0.0")
          .build();
   }
}
