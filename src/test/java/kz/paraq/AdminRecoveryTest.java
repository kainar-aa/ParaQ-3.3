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

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:adminRecoveryTest;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1",
    "PARAQ_ADMIN_USERNAME=", "PARAQ_ADMIN_PASSWORD=", "PARAQ_ADMIN_RESET=false"
})
@AutoConfigureMockMvc
class AdminRecoveryTest {
  @Autowired SeedData seed;
  @Autowired JdbcTemplate db;
  @Autowired PasswordEncoder encoder;
  @Autowired MockMvc mvc;

  @Test @Transactional
  void createsAndRecoversAdminWithoutRemovingExistingReaders() throws Exception {
    db.update("INSERT INTO app_users(username,full_name,password_hash,role) VALUES(?,?,?,'READER')",
      "reader_recovery_test", "Test Reader", encoder.encode("ReaderPass2026!"));

    seed.admin="admin_recovery_test";
    seed.password="FirstAdminPassword2026!";
    seed.resetAdmin=false;
    seed.run();
    long adminId=db.queryForObject("SELECT id FROM app_users WHERE username='admin_recovery_test'",Long.class);

    seed.password="SecondAdminPassword2026!";
    seed.resetAdmin=true;
    seed.run();
    long sameAdminId=db.queryForObject("SELECT id FROM app_users WHERE username='admin_recovery_test'",Long.class);
    assertEquals(adminId,sameAdminId);
    assertEquals(1,db.queryForObject("SELECT COUNT(*) FROM app_users WHERE username='reader_recovery_test'",Integer.class));
    String hashed=db.queryForObject("SELECT password_hash FROM app_users WHERE id=?",String.class,adminId);
    assertTrue(encoder.matches("SecondAdminPassword2026!",hashed));
    assertFalse(encoder.matches("FirstAdminPassword2026!",hashed));
    mvc.perform(post("/api/auth/login").param("username","admin_recovery_test")
        .param("password","SecondAdminPassword2026!").with(csrf()))
        .andExpect(status().isOk());
  }
}
