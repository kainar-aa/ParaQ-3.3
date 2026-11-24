package kz.paraq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties={
  "spring.datasource.url=jdbc:h2:mem:paraqtest;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1",
  "spring.sql.init.mode=always"})
@AutoConfigureMockMvc
class BasicSecurityTest {
  @Autowired MockMvc mvc;
  @Test void healthEndpointIsPublic() throws Exception {
    mvc.perform(get("/health")).andExpect(status().isOk()).andExpect(jsonPath("$.ok").value(true));
  }
  @Test void catalogIsPublic() throws Exception {
    mvc.perform(get("/api/books")).andExpect(status().isOk()).andExpect(jsonPath("$[0].title").exists());
  }
  @Test void adminEndpointsRequireAuthentication() throws Exception {
    mvc.perform(get("/api/admin/stats")).andExpect(status().isUnauthorized());
  }
  @Test void profileEndpointRequiresAuthentication() throws Exception {
    mvc.perform(get("/api/reservations")).andExpect(status().isUnauthorized());
  }
}
