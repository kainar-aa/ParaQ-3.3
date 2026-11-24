package kz.paraq;

import java.time.LocalDate;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties={
  "spring.datasource.url=jdbc:h2:mem:reservationNoQrTest;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1",
  "spring.sql.init.mode=always", "PARAQ_ADMIN_USERNAME=", "PARAQ_ADMIN_PASSWORD=", "PARAQ_ADMIN_RESET=false"
})
class ReservationWithoutQrTest {
  @Autowired JdbcTemplate db;
  @Autowired ParaQApi api;

  @Test @Transactional void issueAndReturnUsingReservationIdWithNoQrField(){
    Integer qrColumns=db.queryForObject(
      "SELECT COUNT(*) FROM information_schema.columns WHERE LOWER(table_name)='reservations' AND LOWER(column_name)='qr_code'",
      Integer.class);
    assertEquals(0,qrColumns);

    db.update("INSERT INTO app_users(username,full_name,password_hash,role) VALUES(?,?,?,'LIBRARIAN')",
      "test_noqr_librarian", "Test Librarian", "unused");
    long uid=db.queryForObject("SELECT id FROM app_users WHERE username=?",Long.class,"test_noqr_librarian");
    db.update("INSERT INTO books(title,author,copies) VALUES('NoQr Test Book','NoQr Test Author',1)");
    long bid=db.queryForObject("SELECT id FROM books WHERE title='NoQr Test Book'",Long.class);
    db.update("INSERT INTO reservations(user_id,book_id,pickup_day,status) VALUES(?,?,?,'RESERVED')",
      uid,bid,java.sql.Date.valueOf(LocalDate.now(ZoneId.of("Asia/Almaty"))));
    long rid=db.queryForObject("SELECT id FROM reservations WHERE user_id=? AND book_id=?",Long.class,uid,bid);
    var staff=(java.security.Principal)()->"test_noqr_librarian";
    api.issue(staff,new ParaQApi.StaffAction(rid,null));
    assertEquals("ISSUED",db.queryForObject("SELECT status FROM reservations WHERE id=?",String.class,rid));
    api.returned(staff,new ParaQApi.StaffAction(rid,"GOOD"));
    assertEquals("RETURNED",db.queryForObject("SELECT status FROM reservations WHERE id=?",String.class,rid));
  }
}
