package kz.paraq;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookMaintenance {
  private final JdbcTemplate db;
  BookMaintenance(JdbcTemplate db){this.db=db;}
  // A pickup day remains valid until the day ends. Expire only the following midnight.
  @Scheduled(cron="0 10 0 * * *",zone="Asia/Almaty")
  @Transactional public void expireUncollected(){
    LocalDate now=LocalDate.now(ZoneId.of("Asia/Almaty"));
    var expired=db.queryForList("SELECT id,book_id FROM reservations WHERE status IN ('RESERVED','READY') AND pickup_day < ?",java.sql.Date.valueOf(now));
    for(var r:expired){
      long rid=((Number)r.get("id")).longValue();long bid=((Number)r.get("book_id")).longValue();
      db.queryForList("SELECT id FROM books WHERE id=? FOR UPDATE",bid);
      db.update("UPDATE reservations SET status='EXPIRED',updated_at=CURRENT_TIMESTAMP WHERE id=? AND status IN ('RESERVED','READY') AND pickup_day < ?",rid,java.sql.Date.valueOf(now));
      db.update("UPDATE waitlist SET status='CANCELLED' WHERE offer_reservation_id=?",rid);
    }
    // All decisions below use one book row lock, also used by reserve/cancel/return.
    var bookIds=db.queryForList("SELECT DISTINCT book_id FROM waitlist WHERE status='WAITING'");
    for(var b:bookIds){
      long bid=((Number)b.get("book_id")).longValue();
      db.queryForList("SELECT id FROM books WHERE id=? FOR UPDATE",bid);
      int copies=db.queryForObject("SELECT copies FROM books WHERE id=?",Integer.class,bid);
      while(copies>db.queryForObject("SELECT COUNT(*) FROM reservations WHERE book_id=? AND status IN ('RESERVED','READY','ISSUED')",Integer.class,bid)){
        var waiting=db.queryForList("""
          SELECT w.id,w.user_id FROM waitlist w JOIN app_users u ON u.id=w.user_id
          WHERE w.book_id=? AND w.status='WAITING' AND u.active=TRUE AND u.deleted_at IS NULL
            AND (u.banned_until IS NULL OR u.banned_until < CURRENT_TIMESTAMP)
          ORDER BY w.id LIMIT 1
          """,bid);
        if(waiting.isEmpty())break;
        long wid=((Number)waiting.get(0).get("id")).longValue(),uid=((Number)waiting.get(0).get("user_id")).longValue();
        Integer active=db.queryForObject("SELECT COUNT(*) FROM reservations WHERE user_id=? AND status IN ('RESERVED','READY','ISSUED')",Integer.class,uid);
        if(active!=null&&active>=3){db.update("UPDATE waitlist SET status='CANCELLED' WHERE id=?",wid);continue;}
        String qr="PQ-"+UUID.randomUUID().toString().substring(0,12).toUpperCase(Locale.ROOT);
        db.update("INSERT INTO reservations(user_id,book_id,pickup_day,qr_code,status) VALUES(?,?,?,?,'RESERVED')",uid,bid,java.sql.Date.valueOf(now.plusDays(1)),qr);
        Long rid=db.queryForObject("SELECT id FROM reservations WHERE qr_code=?",Long.class,qr);
        db.update("UPDATE waitlist SET status='OFFERED',offer_reservation_id=? WHERE id=?",rid,wid);
        db.update("INSERT INTO notifications(user_id,message_kk,message_ru,message_en) VALUES(?,?,?,?)",uid,"Кезегіңіз келді! Кітапты ертең алыңыз.","Ваша очередь подошла! Заберите книгу завтра.","Your turn! Pick up your book tomorrow.");
      }
    }
  }
}
