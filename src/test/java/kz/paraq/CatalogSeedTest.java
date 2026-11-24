package kz.paraq;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(properties={"spring.datasource.url=jdbc:h2:mem:catalog33;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1", "spring.sql.init.mode=always"})
class CatalogSeedTest {
 @Autowired JdbcTemplate db;
 @Test void hasSixPublicBooksTwoOfEachType(){
   assertThat(db.queryForObject("SELECT COUNT(*) FROM books WHERE is_public=TRUE",Integer.class)).isEqualTo(6);
   assertThat(db.queryForObject("SELECT COUNT(*) FROM books WHERE is_public=TRUE AND copies>0 AND content IS NULL",Integer.class)).isEqualTo(2);
   assertThat(db.queryForObject("SELECT COUNT(*) FROM books WHERE is_public=TRUE AND copies=0 AND content IS NOT NULL",Integer.class)).isEqualTo(2);
   assertThat(db.queryForObject("SELECT COUNT(*) FROM books WHERE is_public=TRUE AND copies>0 AND content IS NOT NULL",Integer.class)).isEqualTo(2);
   assertThat(db.queryForObject("SELECT COUNT(*) FROM books WHERE is_public=TRUE AND content IS NOT NULL AND OCTET_LENGTH(content)>400",Integer.class)).isEqualTo(4);
 }
}
