package kz.paraq;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SeedData implements CommandLineRunner {
  private final JdbcTemplate db;private final PasswordEncoder enc;
  @Value("${PARAQ_ADMIN_USERNAME:}") String admin;
  @Value("${PARAQ_ADMIN_PASSWORD:}") String password;
  @Value("${PARAQ_ADMIN_RESET:false}") boolean resetAdmin;
  SeedData(JdbcTemplate db,PasswordEncoder enc){this.db=db;this.enc=enc;}
  @Override @Transactional public void run(String...args){
    setupAdmin();
    seedBooks();
  }
  private record BookSeed(String title,String kk,String ru,String en,String author,String genre,String language,String desc,int copies,String resource){}
  private BookSeed b(String title,String kk,String ru,String en,String author,String genre,String lang,String desc,int copies,String resource){
    return new BookSeed(title,kk,ru,en,author,genre,lang,desc,copies,resource);
  }
  private byte[] bundled(String resource){
    if(resource==null)return null;
    try(InputStream in=SeedData.class.getResourceAsStream("/library-texts/"+resource)){
      if(in==null)throw new IllegalStateException("Missing bundled text: "+resource);
      byte[] bytes=in.readAllBytes();
      if(bytes.length<400)throw new IllegalStateException("Reader text is too short: "+resource);
      return bytes;
    }catch(IOException ex){throw new IllegalStateException("Cannot read bundled text: "+resource,ex);}
  }
  private void seedBooks(){
    // Six-book curated showcase: two print, two digital, two hybrid.
    // Never delete book records: historic reservations, favorites and reviews keep their FKs.
    List<BookSeed> books=List.of(
      b("Абай жолы","Абай жолы","Путь Абая","Abai's Path","Мұхтар Әуезов","Novel","kk","Физикалық кітап; электронды мәтін берілмейді",2,null),
      b("Romeo and Juliet","Ромео мен Джульетта","Ромео и Джульетта","Romeo and Juliet","William Shakespeare","Classic","en","Драмалық шығарманың физикалық даналары",2,null),
      b("Таңғы кітапхана","Таңғы кітапхана","Утренняя библиотека","The Morning Library","ParaQ оқу жинағы","ShortStory","kk","ParaQ жобасына арналған авторлық қысқа әңгіме",0,"tangy-kitaphana.txt"),
      b("История старого маяка","Ескі шамшырақ тарихы","История старого маяка","The Old Lighthouse","ParaQ оқу жинағы","ShortStory","ru","Оқуға болатын түпнұсқа орысша қысқа әңгіме",0,"stary-mayak.txt"),
      b("Күзгі хат","Күзгі хат","Осеннее письмо","Autumn Letter","ParaQ оқу жинағы","ShortStory","kk","Қағаз және электронды нұсқасы бар қысқа әңгіме",2,"kuzgi-khat.txt"),
      b("Жаңбырлы қала","Жаңбырлы қала","Дождливый город","Rainy City","ParaQ оқу жинағы","ShortStory","kk","Қағаз және электронды нұсқасы бар қысқа әңгіме",2,"jangbyrly-qala.txt")
    );
    List<Long> selected=new java.util.ArrayList<>();
    for(BookSeed x:books){
      var existing=db.queryForList("SELECT id,content FROM books WHERE title=? ORDER BY id LIMIT 1",x.title());
      long bookId;
      if(existing.isEmpty()){
        db.update("INSERT INTO books(title,title_kk,title_ru,title_en,author,genre,language,description,copies,content,content_type) VALUES(?,?,?,?,?,?,?,?,?,?,?)",
          x.title(),x.kk(),x.ru(),x.en(),x.author(),x.genre(),x.language(),x.desc(),x.copies(),bundled(x.resource()),x.resource()==null?null:"text/plain");
        bookId=db.queryForObject("SELECT id FROM books WHERE title=? ORDER BY id LIMIT 1",Long.class,x.title());
      }else{
        bookId=((Number)existing.get(0).get("id")).longValue();
        // Repair missing electronic files without replacing an existing uploaded text.
        if(x.resource()!=null && existing.get(0).get("content")==null)
          db.update("UPDATE books SET content=?,content_type='text/plain' WHERE id=? AND content IS NULL",bundled(x.resource()),bookId);
      }
      selected.add(bookId);
    }

    // Apply this reduction ONCE per database. The original 14+ records remain in
    // books, but are hidden from the public catalog; active loans remain intact.
    String migration="curated_six_v3_3";
    Integer applied=db.queryForObject("SELECT COUNT(*) FROM paraq_migrations WHERE migration_key=?",Integer.class,migration);
    if(applied!=null&&applied==0){
      String placeholders=String.join(",",java.util.Collections.nCopies(selected.size(),"?"));
      db.update("UPDATE books SET is_public=TRUE WHERE id IN ("+placeholders+")",selected.toArray());
      db.update("UPDATE books SET is_public=FALSE WHERE id NOT IN ("+placeholders+")",selected.toArray());
      db.update("INSERT INTO paraq_migrations(migration_key) VALUES(?)",migration);
      System.out.println("[ParaQ] Curated 6-book catalog enabled. Other books archived, NOT deleted.");
    }else{
      System.out.println("[ParaQ] Six-book collection already installed; admin catalog choices kept.");
    }
  }
  /**
   * Console-only administrator setup. No public HTTP endpoint can create/reset an administrator.
   * PARAQ_ADMIN_RESET must be explicitly enabled for an existing administrator.
   * This updates only the admin account; books, readers and loans are never deleted.
   */
  private void setupAdmin(){
    String username=admin==null?"":admin.trim().toLowerCase(Locale.ROOT);
    String suppliedPassword=password==null?"":password;
    List<Map<String,Object>> admins=db.queryForList("SELECT id,username FROM app_users WHERE role='ADMIN' ORDER BY id");
    boolean exists=!admins.isEmpty();

    if(exists && !resetAdmin){
      // Never change an existing administrator just because deployment variables changed.
      return;
    }
    if(username.isBlank() || suppliedPassword.isBlank()){
      if(resetAdmin)throw new IllegalStateException(
        "Admin reset requested without credentials. Run ADMIN_SETUP_WINDOWS.bat to set a private login and password.");
      System.out.println("[ParaQ] No admin created yet. Run ADMIN_SETUP_WINDOWS.bat to create one (existing data is preserved).");
      return;
    }
    if(!username.matches("[a-z0-9_]{3,40}"))throw new IllegalStateException(
      "Admin username must be 3-40 Latin letters, digits or underscores.");
    if(suppliedPassword.length()<6 || suppliedPassword.length()>72)throw new IllegalStateException(
      "Admin password must have 6-72 characters.");

    if(!exists){
      Integer occupied=db.queryForObject("SELECT COUNT(*) FROM app_users WHERE username=?",Integer.class,username);
      if(occupied!=null && occupied>0)throw new IllegalStateException(
        "The chosen admin login already belongs to another account. Choose a different login.");
      db.update("INSERT INTO app_users(username,full_name,password_hash,role,active) VALUES(?,?,?,'ADMIN',TRUE)",
        username,"ParaQ Administrator",enc.encode(suppliedPassword));
      System.out.println("[ParaQ] Administrator created. Login: "+username+" (password is never logged).");
      return;
    }

    // An explicit one-time recovery may also rename a pre-existing admin login.
    // Prefer the matching administrator; otherwise reset the oldest administrator.
    Map<String,Object> target=admins.stream()
      .filter(a->username.equals(a.get("username"))).findFirst().orElse(admins.get(0));
    long id=((Number)target.get("id")).longValue();
    Integer occupied=db.queryForObject("SELECT COUNT(*) FROM app_users WHERE username=? AND id<>?",Integer.class,username,id);
    if(occupied!=null && occupied>0)throw new IllegalStateException(
      "The chosen admin login is already used by another account. Choose a different login.");
    db.update("""
      UPDATE app_users SET username=?,password_hash=?,active=TRUE,banned_until=NULL,deleted_at=NULL
      WHERE id=? AND role='ADMIN'
      """,username,enc.encode(suppliedPassword),id);
    System.out.println("[ParaQ] Administrator credentials reset. Login: "+username+" (password is never logged).");
  }
}
