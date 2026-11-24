package kz.paraq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties={
  "spring.datasource.url=jdbc:h2:mem:passwordSixTest;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1",
  "PARAQ_ADMIN_USERNAME=", "PARAQ_ADMIN_PASSWORD=", "PARAQ_ADMIN_RESET=false"
})
@AutoConfigureMockMvc
class SixCharacterPasswordTest {
  @Autowired MockMvc mvc;
  @Autowired JdbcTemplate db;
  @Autowired PasswordEncoder encoder;

  @Test @Transactional
  void minimumPasswordSixIsAcceptedForRegistrationAndLogin() throws Exception {
    mvc.perform(post("/api/auth/register").with(csrf())
      .contentType("application/json")
      .content("{\"username\":\"six_pass_test\",\"password\":\"abc123\",\"fullName\":\"Student Example\"}"))
      .andExpect(status().isOk());
    String hash=db.queryForObject("SELECT password_hash FROM app_users WHERE username=?",String.class,"six_pass_test");
    assertTrue(encoder.matches("abc123",hash));
    mvc.perform(post("/api/auth/login").with(csrf())
      .param("username","six_pass_test").param("password","abc123"))
      .andExpect(status().isOk());
  }
}
