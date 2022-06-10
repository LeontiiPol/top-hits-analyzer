package polovinko.leontii.tophitsanalyzer.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SongsAnalyzerControllerIntegrationTest {

   @Autowired
   private MockMvc mockMvc;
   private MockHttpServletRequestBuilder request;

   @BeforeEach
   void setUp() {
      request = MockMvcRequestBuilders.get("/api/songs/deciles");
   }

   @Test
   void getSongsDeciles_whenMethodInvoked_thenDecilesReturned() throws Exception {
      request.param("colname", "duration_ms");

      mockMvc.perform(request)
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(10)))
          .andExpectAll(getResultMatchersToAssertDecilesJson());
   }

   @Test
   void getSongsDeciles_whenYearPassed_thenDecilesOfFilteredSongsReturned() throws Exception {
      request.param("colname", "duration_ms");
      request.param("year", "2011");

      mockMvc.perform(request)
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))
          .andExpect(jsonPath("$[0].max", is(9)))
          .andExpect(jsonPath("$[0].min", is(9)))
          .andExpect(jsonPath("$[0].count", is(1)));
   }

   @ParameterizedTest(name = "{index} {0}")
   @MethodSource("getArguments")
   void getSongsDeciles(String testName, String columnName, String year, String errorMessage) throws Exception {
      request.param("colname", columnName);
      request.param("year", year);
      String dateAndTimeRegexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";

      mockMvc.perform(request)
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message", is(errorMessage)))
          .andExpect(jsonPath("$.happenedAt", matchesPattern(dateAndTimeRegexp)));
   }

   private ResultMatcher[] getResultMatchersToAssertDecilesJson() {
      List<ResultMatcher> resultMatchers = new ArrayList<>();
      for (int i = 0; i < 10; i++) {
         resultMatchers.add(jsonPath("$[" + i + "].min", is(2 * i)));
         resultMatchers.add(jsonPath("$[" + i + "].max", is(2 * i + 1)));
         resultMatchers.add(jsonPath("$[" + i + "].count", is(2)));
      }
      return resultMatchers.toArray(ResultMatcher[]::new);
   }

   private static Stream<Arguments> getArguments() {
      return Stream.of(
          Arguments.of(
              "whenYearLessThan2000_thenErrorMessageReturned",
              "duration_ms",
              "1999",
              "Year must be in range from 2000 to 2019"
          ),
          Arguments.of(
              "whenYearMoreThan2019_thenErrorMessageReturned",
              "duration_ms",
              "2020",
              "Year must be in range from 2000 to 2019"
          ),
          Arguments.of(
              "whenYearInvalid_thenErrorMessageReturned",
              "duration_ms",
              "asd12s",
              "Invalid request parameters"
          ),
          Arguments.of(
              "whenColumnNameInvalid_thenErrorMessageReturned",
              "invalid_colname",
              "2005",
              "Invalid request parameters"
          ),
          Arguments.of(
              "whenRequestWithoutParameters_thenErrorMessageReturned",
              null,
              null,
              "Request must contain 'colname' parameter"
          )
      );
   }
}
