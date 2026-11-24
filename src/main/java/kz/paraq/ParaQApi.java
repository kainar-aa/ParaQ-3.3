package kz.paraq;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class ParaQApi {
  private final JdbcTemplate db;
  private final PasswordEncoder encoder;
  private static final ZoneId ALMATY=ZoneId.of("Asia/Almaty");
  private static final Pattern LOGIN=Pattern.compile("[A-Za-z0-9_]{3,40}");
  ParaQApi(JdbcTemplate db, PasswordEncoder encoder){this.db=db;this.encoder=encoder;}
  private static void ensure(boolean yes,String error){if(!yes)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,error);}
  private long id(Object o){return ((Number)o).longValue();}
  private LocalDate today(){return LocalDate.now(ALMATY);}
  private List<Map<String,Object>> query(String sql,Object...args){return db.queryForList(sql,args);}
  private Map<String,Object> single(String sql,Object...args){var x=query(sql,args);if(x.isEmpty())throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found");return x.get(0);}
  private Map<String,Object> current(Principal principal){
    if(principal==null||"anonymousUser".equals(principal.getName()))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Login required");
    var rows=query("SELECT id,username,full_name,email,phone,address,role,active,banned_until,deleted_at FROM app_users WHERE username=?",principal.getName());
    if(rows.isEmpty())throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Login required");
    var u=rows.get(0);
    if(!Boolean.TRUE.equals(u.get("active"))||u.get("deleted_at")!=null||
      (u.get("banned_until")!=null && ((java.sql.Timestamp)u.get("banned_until")).toLocalDateTime().isAfter(LocalDateTime.now())))
      throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Account blocked");
    return u;
  }
  private Map<String,Object> requireRole(Principal p,String role){var u=current(p);if(!role.equals(u.get("role")))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Insufficient permission");return u;}
  private Map<String,Object> staff(Principal p){var u=current(p);if(!List.of("ADMIN","LIBRARIAN").contains(u.get("role")))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Staff only");return u;}
  private void audit(Long actor,String action,String subject,long subjectId,String detail){db.update("INSERT INTO audit_log(actor_id,action,subject_type,subject_id,detail) VALUES(?,?,?,?,?)",actor,action,subject,subjectId,detail);}
  private void notify(long uid,String kk,String ru,String en){db.update("INSERT INTO notifications(user_id,message_kk,message_ru,message_en) VALUES(?,?,?,?)",uid,kk,ru,en);}
  private long insert(String sql,Object...args){
    var keys=new GeneratedKeyHolder();
    db.update(con->{PreparedStatement ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);for(int i=0;i<args.length;i++)ps.setObject(i+1,args[i]);return ps;},keys);
    if(keys.getKeys()!=null&&!keys.getKeys().isEmpty()){
      Object generated=keys.getKeys().get("id");
      if(generated==null)generated=keys.getKeys().values().iterator().next();
      return ((Number)generated).longValue();
    }
    throw new IllegalStateException("Unable to generate ID");
  }
  private void lockBook(long bid){single("SELECT id FROM books WHERE id=? FOR UPDATE",bid);}
  private long activeCount(long bid){return id(single("SELECT COUNT(*) AS n FROM reservations WHERE book_id=? AND status IN ('RESERVED','READY','ISSUED')",bid).get("n"));}
  private long openCount(long uid){return id(single("SELECT COUNT(*) AS n FROM reservations WHERE user_id=? AND status IN ('RESERVED','READY','ISSUED')",uid).get("n"));}
  private String qr(){return "PQ-"+UUID.randomUUID().toString().substring(0,12).toUpperCase(Locale.ROOT);}
  private String text(String x,int max){return x==null?"":x.trim().substring(0,Math.min(x.trim().length(),max));}

  @GetMapping("/auth/csrf") public Map<String,String> csrf(HttpServletRequest request){
    var token=(org.springframework.security.web.csrf.CsrfToken)request.getAttribute(org.springframework.security.web.csrf.CsrfToken.class.getName());
    if(token==null)throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"CSRF unavailable");
    return Map.of("token",token.getToken(),"headerName",token.getHeaderName());
  }
  @GetMapping("/auth/me") public Map<String,Object> me(Principal principal){return principal==null||"anonymousUser".equals(principal.getName())?Map.of("role","GUEST"):current(principal);}
  public record Register(String username,String password,String fullName){}
  @PostMapping("/auth/register") @Transactional
  public Map<String,Object> register(@RequestBody Register x){
    ensure(x!=null&&x.username()!=null&&LOGIN.matcher(x.username()).matches(),"Login: 3–40 characters");
    ensure(x.fullName()!=null&&x.fullName().trim().length()>=2&&x.fullName().trim().length()<=160,"Full name: 2–160 characters");
    ensure(x.password()!=null&&x.password().length()>=6&&x.password().length()<=72,"Password: 6–72 characters");
    String u=x.username().toLowerCase(Locale.ROOT);
    ensure(query("SELECT id FROM app_users WHERE username=?",u).isEmpty(),"Username already in use");
    long uid=insert("INSERT INTO app_users(username,full_name,password_hash,role) VALUES(?,?,?,'READER')",u,x.fullName().trim(),encoder.encode(x.password()));
    audit(uid,"REGISTER","USER",uid,"New reader");return Map.of("ok",true);
  }
  public record Profile(String fullName,String email,String phone,String address){}
  @PutMapping("/account/profile") public Map<String,Object> profile(Principal p,@RequestBody Profile x){
    long uid=id(current(p).get("id"));ensure(x!=null&&x.fullName()!=null&&x.fullName().trim().length()>=2&&x.fullName().trim().length()<=160,"Full name required");
    String email=text(x.email(),180),phone=text(x.phone(),30),address=text(x.address(),300);
    ensure(email.isEmpty()||email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"),"Invalid email");
    ensure(phone.isEmpty()||phone.matches("[+0-9 ()-]{7,30}"),"Invalid phone");
    db.update("UPDATE app_users SET full_name=?,email=?,phone=?,address=? WHERE id=?",x.fullName().trim(),email,phone,address,uid);
    audit(uid,"PROFILE_UPDATE","USER",uid,"Profile fields updated");return Map.of("ok",true);
  }
  public record PasswordChange(String oldPassword,String newPassword){}
  @PostMapping("/account/password") public Map<String,Object> password(Principal p,@RequestBody PasswordChange x,HttpServletRequest req){
    long uid=id(current(p).get("id"));ensure(x!=null&&x.oldPassword()!=null&&x.newPassword()!=null&&x.newPassword().length()>=6&&x.newPassword().length()<=72,"Invalid password");
    String old=(String)single("SELECT password_hash FROM app_users WHERE id=?",uid).get("password_hash");
    ensure(encoder.matches(x.oldPassword(),old),"Incorrect current password");ensure(!encoder.matches(x.newPassword(),old),"Choose a different password");
    db.update("UPDATE app_users SET password_hash=? WHERE id=?",encoder.encode(x.newPassword()),uid);
    if(req.getSession(false)!=null)req.getSession(false).invalidate();SecurityContextHolder.clearContext();
    audit(uid,"PASSWORD_CHANGE","USER",uid,"Password changed");return Map.of("ok",true,"relogin",true);
  }

  @GetMapping("/books") public List<Map<String,Object>> books(@RequestParam(defaultValue="") String search,
      @RequestParam(defaultValue="all") String format){
    ensure(search.length()<=120,"Search too long");String like="%"+search.toLowerCase(Locale.ROOT)+"%";
    var out=query("""
      SELECT b.id,b.title,b.title_kk,b.title_ru,b.title_en,b.author,b.genre,b.language,b.description,b.cover_url,b.copies,
      CASE WHEN b.content IS NULL THEN FALSE ELSE TRUE END AS online,
      (b.copies-(SELECT COUNT(*) FROM reservations r WHERE r.book_id=b.id AND r.status IN ('RESERVED','READY','ISSUED'))) AS available,
      COALESCE((SELECT AVG(CAST(v.rating AS DECIMAL(5,2))) FROM reviews v WHERE v.book_id=b.id),0) AS rating
      FROM books b WHERE b.is_public=TRUE AND (LOWER(b.title) LIKE ? OR LOWER(b.author) LIKE ? OR LOWER(b.genre) LIKE ? OR LOWER(COALESCE(b.title_kk,'')) LIKE ? OR LOWER(COALESCE(b.title_ru,'')) LIKE ?)
      ORDER BY b.id DESC
      """,like,like,like,like,like);
    return out.stream().filter(b->"all".equals(format)||("online".equals(format)&&Boolean.TRUE.equals(b.get("online")))||("physical".equals(format)&&id(b.get("copies"))>0)).toList();
  }
  @GetMapping("/books/{bid}") public Map<String,Object> book(@PathVariable long bid){
    return single("""
      SELECT b.id,b.title,b.title_kk,b.title_ru,b.title_en,b.author,b.genre,b.language,b.description,b.cover_url,b.copies,
      CASE WHEN b.content IS NULL THEN FALSE ELSE TRUE END AS online,
      b.content_type,(b.copies-(SELECT COUNT(*) FROM reservations r WHERE r.book_id=b.id AND r.status IN ('RESERVED','READY','ISSUED'))) AS available
      FROM books b WHERE b.id=? AND b.is_public=TRUE
      """,bid);
  }
  @GetMapping("/books/{bid}/reviews") public List<Map<String,Object>> reviews(@PathVariable long bid){
    return query("SELECT u.full_name,r.rating,r.body,r.created_at FROM reviews r JOIN app_users u ON r.user_id=u.id WHERE r.book_id=? ORDER BY r.created_at DESC LIMIT 40",bid);
  }
  public record Review(int rating,String body){}
  @PostMapping("/books/{bid}/reviews") public Map<String,Object> review(@PathVariable long bid,Principal p,@RequestBody Review x){
    long uid=id(current(p).get("id"));book(bid);ensure(x.rating()>=1&&x.rating()<=5,"Rating 1–5");
    String body=text(x.body(),1000);
    if(query("SELECT user_id FROM reviews WHERE user_id=? AND book_id=?",uid,bid).isEmpty())
      db.update("INSERT INTO reviews(user_id,book_id,rating,body) VALUES(?,?,?,?)",uid,bid,x.rating(),body);
    else db.update("UPDATE reviews SET rating=?,body=?,created_at=CURRENT_TIMESTAMP WHERE user_id=? AND book_id=?",x.rating(),body,uid,bid);
    return Map.of("ok",true);
  }
  @GetMapping("/favorites") public List<Map<String,Object>> favorites(Principal p){
    return query("SELECT b.id,b.title,b.title_kk,b.title_ru,b.title_en,b.author,b.cover_url,b.genre FROM favorites f JOIN books b ON f.book_id=b.id WHERE f.user_id=? AND b.is_public=TRUE ORDER BY f.created_at DESC",id(current(p).get("id")));
  }
  @PutMapping("/favorites/{bid}") public Map<String,Object> favorite(@PathVariable long bid,Principal p){
    long uid=id(current(p).get("id"));book(bid);
    if(query("SELECT user_id FROM favorites WHERE user_id=? AND book_id=?",uid,bid).isEmpty())db.update("INSERT INTO favorites(user_id,book_id) VALUES(?,?)",uid,bid);
    return Map.of("ok",true);
  }
  @DeleteMapping("/favorites/{bid}") public Map<String,Object> unfavorite(@PathVariable long bid,Principal p){
    db.update("DELETE FROM favorites WHERE user_id=? AND book_id=?",id(current(p).get("id")),bid);return Map.of("ok",true);
  }
  @GetMapping("/progress") public List<Map<String,Object>> progress(Principal p){
    return query("SELECT b.id,b.title,b.title_kk,b.title_ru,b.title_en,b.author,p.percentage,p.updated_at FROM reading_progress p JOIN books b ON p.book_id=b.id WHERE p.user_id=? ORDER BY p.updated_at DESC",id(current(p).get("id")));
  }
  public record Progress(int percentage){}
  @PutMapping("/books/{bid}/progress") public Map<String,Object> progressSave(@PathVariable long bid,Principal p,@RequestBody Progress x){
    long uid=id(current(p).get("id"));ensure(x.percentage()>=0&&x.percentage()<=100,"Progress 0–100");ensure(Boolean.TRUE.equals(book(bid).get("online")),"Not an electronic book");
    if(query("SELECT user_id FROM reading_progress WHERE user_id=? AND book_id=?",uid,bid).isEmpty())db.update("INSERT INTO reading_progress(user_id,book_id,percentage) VALUES(?,?,?)",uid,bid,x.percentage());
    else db.update("UPDATE reading_progress SET percentage=?,updated_at=CURRENT_TIMESTAMP WHERE user_id=? AND book_id=?",x.percentage(),uid,bid);
    return Map.of("ok",true);
  }
  @GetMapping("/books/{bid}/content") public ResponseEntity<byte[]> content(@PathVariable long bid,Principal p){
    current(p);var b=single("SELECT content,content_type FROM books WHERE id=? AND is_public=TRUE",bid);
    if(b.get("content")==null)throw new ResponseStatusException(HttpStatus.NOT_FOUND,"File not uploaded");
    String ct=(String)b.get("content_type");
    return ResponseEntity.ok().contentType(MediaType.parseMediaType(ct))
      .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=book."+(ct.equals("application/pdf")?"pdf":ct.equals("application/epub+zip")?"epub":"txt"))
      .cacheControl(CacheControl.noStore()).body((byte[])b.get("content"));
  }
  @GetMapping(value="/books/{bid}/epub-text",produces="text/plain;charset=UTF-8") public String epub(@PathVariable long bid,Principal p)throws IOException{
    current(p);var b=single("SELECT content,content_type FROM books WHERE id=? AND is_public=TRUE",bid);
    ensure("application/epub+zip".equals(b.get("content_type")),"Not EPUB");
    byte[] bytes=(byte[])b.get("content");ensure(bytes!=null&&bytes.length<=10_000_000,"File too large");
    SortedMap<String,String> chapters=new TreeMap<>();int total=0;
    try(var z=new ZipInputStream(new ByteArrayInputStream(bytes))){ZipEntry e;
      while((e=z.getNextEntry())!=null){String name=e.getName().toLowerCase(Locale.ROOT);
        if(!e.isDirectory()&&(name.endsWith(".xhtml")||name.endsWith(".html"))){
          byte[] data=z.readNBytes(600_001);ensure(data.length<=600_000,"EPUB chapter too large");total+=data.length;ensure(total<=5_000_000,"EPUB too large");
          String s=new String(data,StandardCharsets.UTF_8).replaceAll("(?is)<script[^>]*>.*?</script>"," ").replaceAll("(?is)<style[^>]*>.*?</style>"," ")
            .replaceAll("(?i)</(?:p|div|h[1-6]|li|br|section)>","\n\n").replaceAll("(?s)<[^>]+>"," ")
            .replace("&nbsp;"," ").replace("&amp;","&").replace("&lt;","<").replace("&gt;",">").replace("&quot;","\"");
          chapters.put(name,s);
        }z.closeEntry();
      }
    }
    ensure(!chapters.isEmpty(),"No readable EPUB chapters");return String.join("\n\n",chapters.values());
  }

  public record Pickup(String pickupDay){}
  @PostMapping("/books/{bid}/reserve") @Transactional
  public Map<String,Object> reserve(@PathVariable long bid,Principal p,@RequestBody Pickup x){
    long uid=id(current(p).get("id"));LocalDate day;
    try{day=LocalDate.parse(x.pickupDay());}catch(Exception e){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Select a valid day");}
    ensure(!day.isBefore(today())&&!day.isAfter(today().plusDays(2)),"Choose today or next two days");
    var u=current(p);ensure(u.get("phone")!=null&&!((String)u.get("phone")).isBlank(),"Add phone number in your profile before reserving");
    lockBook(bid);var b=book(bid);ensure(id(b.get("copies"))>0,"No physical copies");
    ensure(openCount(uid)<3,"Maximum three active loans");
    ensure(query("SELECT id FROM reservations WHERE user_id=? AND book_id=? AND status IN ('RESERVED','READY','ISSUED')",uid,bid).isEmpty(),"Already reserved");
    ensure(activeCount(bid)<id(b.get("copies")),"No available copy; join waitlist");
    String code=qr();long rid=insert("INSERT INTO reservations(user_id,book_id,pickup_day,qr_code,status) VALUES(?,?,?,?,'RESERVED')",uid,bid,java.sql.Date.valueOf(day),code);
    notify(uid,"Бронь қабылданды: "+day,"Бронирование подтверждено: "+day,"Reservation confirmed: "+day);
    return Map.of("id",rid,"qrCode",code,"pickupDay",day.toString(),"status","RESERVED");
  }
  @GetMapping("/reservations") public List<Map<String,Object>> myReservations(Principal p){
    return query("""
      SELECT r.id,r.book_id,r.pickup_day,r.due_day,r.qr_code,r.status,r.renewed,r.created_at,b.title,b.title_kk,b.title_ru,b.title_en,b.author
      FROM reservations r JOIN books b ON b.id=r.book_id WHERE r.user_id=? ORDER BY r.created_at DESC
      """,id(current(p).get("id")));
  }
  @PostMapping("/reservations/{rid}/cancel") @Transactional public Map<String,Object> cancel(@PathVariable long rid,Principal p){
    long uid=id(current(p).get("id"));var r=single("SELECT book_id FROM reservations WHERE id=? AND user_id=?",rid,uid);long bid=id(r.get("book_id"));
    lockBook(bid);r=single("SELECT status FROM reservations WHERE id=? AND user_id=? FOR UPDATE",rid,uid);
    ensure(List.of("RESERVED","READY").contains(r.get("status")),"Reservation cannot be cancelled");
    db.update("UPDATE reservations SET status='CANCELLED',updated_at=CURRENT_TIMESTAMP WHERE id=?",rid);
    db.update("UPDATE waitlist SET status='CANCELLED' WHERE offer_reservation_id=?",rid);offerNext(bid);
    return Map.of("ok",true);
  }
  @PostMapping("/reservations/{rid}/renew") @Transactional public Map<String,Object> renew(@PathVariable long rid,Principal p){
    long uid=id(current(p).get("id"));var pre=single("SELECT book_id FROM reservations WHERE id=? AND user_id=?",rid,uid);lockBook(id(pre.get("book_id")));
    var r=single("SELECT status,due_day,book_id,renewed FROM reservations WHERE id=? AND user_id=? FOR UPDATE",rid,uid);
    ensure("ISSUED".equals(r.get("status"))&&!Boolean.TRUE.equals(r.get("renewed")),"Only one renewal for issued books");
    ensure(query("SELECT id FROM waitlist WHERE book_id=? AND status='WAITING'",r.get("book_id")).isEmpty(),"Other readers are waiting");
    LocalDate due=((java.sql.Date)r.get("due_day")).toLocalDate().plusDays(7);
    db.update("UPDATE reservations SET due_day=?,renewed=TRUE,updated_at=CURRENT_TIMESTAMP WHERE id=?",java.sql.Date.valueOf(due),rid);
    return Map.of("dueDay",due.toString());
  }
  @PostMapping("/books/{bid}/waitlist") @Transactional public Map<String,Object> waitlistJoin(@PathVariable long bid,Principal p){
    long uid=id(current(p).get("id"));lockBook(bid);var b=book(bid);
    ensure(id(b.get("copies"))>0,"No physical copies");ensure(activeCount(bid)>=id(b.get("copies")),"The book is available; reserve it instead");
    ensure(query("SELECT id FROM waitlist WHERE user_id=? AND book_id=? AND status='WAITING'",uid,bid).isEmpty(),"Already in queue");
    ensure(query("SELECT id FROM reservations WHERE user_id=? AND book_id=? AND status IN ('RESERVED','READY','ISSUED')",uid,bid).isEmpty(),"Already reserved");
    db.update("INSERT INTO waitlist(user_id,book_id) VALUES(?,?)",uid,bid);return Map.of("ok",true);
  }
  @GetMapping("/waitlist") public List<Map<String,Object>> myWaitlist(Principal p){
    return query("SELECT w.id,w.status,b.id AS book_id,b.title,b.title_kk,b.title_ru,b.title_en FROM waitlist w JOIN books b ON w.book_id=b.id WHERE w.user_id=? AND w.status IN ('WAITING','OFFERED') ORDER BY w.id",id(current(p).get("id")));
  }
  @DeleteMapping("/waitlist/{wid}") public Map<String,Object> waitlistLeave(@PathVariable long wid,Principal p){
    db.update("UPDATE waitlist SET status='CANCELLED' WHERE id=? AND user_id=? AND status='WAITING'",wid,id(current(p).get("id")));return Map.of("ok",true);
  }
  // Must be invoked while holding the book row lock in the current transaction.
  private void offerNext(long bid){
    var b=single("SELECT copies FROM books WHERE id=?",bid);
    if(activeCount(bid)>=id(b.get("copies")))return;
    var pending=query("""
      SELECT w.id,w.user_id FROM waitlist w JOIN app_users u ON u.id=w.user_id
      WHERE w.book_id=? AND w.status='WAITING' AND u.active=TRUE AND u.deleted_at IS NULL
      AND (u.banned_until IS NULL OR u.banned_until < CURRENT_TIMESTAMP)
      ORDER BY w.id LIMIT 1
      """,bid);
    if(pending.isEmpty())return;
    long wid=id(pending.get(0).get("id")),uid=id(pending.get(0).get("user_id"));
    if(openCount(uid)>=3){db.update("UPDATE waitlist SET status='CANCELLED' WHERE id=?",wid);offerNext(bid);return;}
    String code=qr();LocalDate pickup=today().plusDays(1);
    long rid=insert("INSERT INTO reservations(user_id,book_id,pickup_day,qr_code,status) VALUES(?,?,?,?,'RESERVED')",uid,bid,java.sql.Date.valueOf(pickup),code);
    db.update("UPDATE waitlist SET status='OFFERED',offer_reservation_id=? WHERE id=?",rid,wid);
    notify(uid,"Кезегіңіз келді! Алу күні: "+pickup,"Ваша очередь подошла! Дата: "+pickup,"Your book is ready! Pickup: "+pickup);
  }
  @GetMapping("/notifications") public List<Map<String,Object>> notifications(Principal p){
    return query("SELECT id,message_kk,message_ru,message_en,seen,created_at FROM notifications WHERE user_id=? ORDER BY id DESC LIMIT 80",id(current(p).get("id")));
  }
  @PostMapping("/notifications/read") public Map<String,Object> readNotifications(Principal p){
    db.update("UPDATE notifications SET seen=TRUE WHERE user_id=?",id(current(p).get("id")));return Map.of("ok",true);
  }
  @GetMapping(value="/reservations/{rid}/qr",produces=MediaType.IMAGE_PNG_VALUE) public byte[] qrImage(@PathVariable long rid,Principal p)throws Exception{
    var me=current(p);var r=single("SELECT user_id,qr_code FROM reservations WHERE id=?",rid);
    if(id(r.get("user_id"))!=id(me.get("id"))&&!List.of("ADMIN","LIBRARIAN").contains(me.get("role")))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Not your reservation");
    var code=new MultiFormatWriter().encode((String)r.get("qr_code"),BarcodeFormat.QR_CODE,250,250);
    ByteArrayOutputStream o=new ByteArrayOutputStream();ImageIO.write(MatrixToImageWriter.toBufferedImage(code),"PNG",o);return o.toByteArray();
  }

  // QR verification is staff-only. Scanning NEVER automatically issues or returns a book.
  @PostMapping(value="/staff/qr/decode",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
  public Map<String,String> staffQrDecode(Principal p,@RequestPart("image") MultipartFile image) throws IOException{
    staff(p);
    ensure(image!=null && !image.isEmpty() && image.getSize()<=2_000_000,"Image must be 2 MB or smaller");
    String mime=image.getContentType();
    ensure("image/jpeg".equals(mime)||"image/png".equals(mime),"Choose a JPEG or PNG QR image");
    return Map.of("code",QrScanner.decode(image.getBytes()));
  }
  @GetMapping("/staff/qr/lookup") public Map<String,Object> staffQrLookup(Principal p,@RequestParam String code){
    staff(p);
    ensure(code!=null&&code.trim().matches("(?i)PQ-[0-9a-f]{12}"),"Invalid QR code");
    return single("""
      SELECT r.id,r.qr_code,r.status,r.pickup_day,r.due_day,b.title,u.full_name,u.username
      FROM reservations r JOIN books b ON b.id=r.book_id JOIN app_users u ON u.id=r.user_id
      WHERE r.qr_code=?
      """,code.trim().toUpperCase(Locale.ROOT));
  }

  @GetMapping("/staff/reservations") public List<Map<String,Object>> staffReservations(Principal p){
    staff(p);return query("""
      SELECT r.id,r.pickup_day,r.due_day,r.status,r.qr_code,r.book_id,b.title,u.full_name,u.phone,u.username
      FROM reservations r JOIN books b ON r.book_id=b.id JOIN app_users u ON u.id=r.user_id
      ORDER BY r.created_at DESC LIMIT 300
      """);
  }
  public record StaffAction(String code,String condition){}
  @PostMapping("/staff/issue") @Transactional public Map<String,Object> issue(Principal p,@RequestBody StaffAction x){
    long actor=id(staff(p).get("id"));ensure(x!=null&&x.code()!=null&&x.code().startsWith("PQ-"),"Enter QR code");
    var pre=single("SELECT book_id FROM reservations WHERE qr_code=?",x.code().trim());lockBook(id(pre.get("book_id")));
    var r=single("SELECT id,user_id,status,pickup_day FROM reservations WHERE qr_code=? FOR UPDATE",x.code().trim());
    ensure(List.of("RESERVED","READY").contains(r.get("status")),"Not an active reservation");
    ensure(!((java.sql.Date)r.get("pickup_day")).toLocalDate().isAfter(today()),"Pickup day has not arrived");
    long rid=id(r.get("id"));LocalDate due=today().plusDays(14);
    db.update("UPDATE reservations SET status='ISSUED',due_day=?,updated_at=CURRENT_TIMESTAMP WHERE id=?",java.sql.Date.valueOf(due),rid);
    audit(actor,"ISSUE_BOOK","RESERVATION",rid,"Issued until "+due);
    notify(id(r.get("user_id")),"Кітап берілді. Қайтару: "+due,"Книга выдана. Возврат: "+due,"Book issued. Due: "+due);
    return Map.of("ok",true,"dueDay",due.toString());
  }
  @PostMapping("/staff/return") @Transactional public Map<String,Object> returned(Principal p,@RequestBody StaffAction x){
    long actor=id(staff(p).get("id"));ensure(x!=null&&x.code()!=null,"QR code required");
    String condition=x.condition()==null?"GOOD":x.condition().toUpperCase(Locale.ROOT);
    ensure(List.of("GOOD","DAMAGED","LOST").contains(condition),"Condition must be GOOD/DAMAGED/LOST");
    var pre=single("SELECT book_id FROM reservations WHERE qr_code=?",x.code().trim());long bid=id(pre.get("book_id"));lockBook(bid);
    var r=single("SELECT id,user_id,status FROM reservations WHERE qr_code=? FOR UPDATE",x.code().trim());ensure("ISSUED".equals(r.get("status")),"Book is not issued");
    long rid=id(r.get("id"));db.update("UPDATE reservations SET status='RETURNED',updated_at=CURRENT_TIMESTAMP WHERE id=?",rid);
    if(!"GOOD".equals(condition))db.update("UPDATE books SET copies=CASE WHEN copies>0 THEN copies-1 ELSE 0 END WHERE id=?",bid);
    audit(actor,"RETURN_BOOK","RESERVATION",rid,condition);
    notify(id(r.get("user_id")),"Кітап қайтарылды: "+condition,"Книга возвращена: "+condition,"Book returned: "+condition);
    offerNext(bid);return Map.of("ok",true);
  }

  @GetMapping("/admin/stats") public Map<String,Object> adminStats(Principal p){
    requireRole(p,"ADMIN");return Map.of(
      "books",single("SELECT COUNT(*) AS n FROM books WHERE is_public=TRUE").get("n"),
      "copies",single("SELECT COALESCE(SUM(copies),0) AS n FROM books WHERE is_public=TRUE").get("n"),
      "users",single("SELECT COUNT(*) AS n FROM app_users WHERE role='READER' AND deleted_at IS NULL").get("n"),
      "activeReservations",single("SELECT COUNT(*) AS n FROM reservations WHERE status IN ('RESERVED','READY','ISSUED')").get("n"),
      "staff",single("SELECT COUNT(*) AS n FROM app_users WHERE role='LIBRARIAN' AND deleted_at IS NULL").get("n"),
      "completedLoans",single("SELECT COUNT(*) AS n FROM reservations WHERE status='RETURNED'").get("n")
    );
  }
  @GetMapping("/admin/books") public List<Map<String,Object>> adminBooks(Principal p){
    requireRole(p,"ADMIN");return query("SELECT id,title,title_kk,title_ru,title_en,author,genre,language,description,cover_url,copies,content_type,is_public,CASE WHEN content IS NULL THEN FALSE ELSE TRUE END AS online FROM books ORDER BY id DESC");
  }
  public record BookInput(String title,String titleKk,String titleRu,String titleEn,String author,String genre,String language,String description,String coverUrl,Integer copies,Boolean isPublic){}
  private void validateBook(BookInput x){
    ensure(x!=null&&x.title()!=null&&!x.title().isBlank()&&x.title().length()<=220,"Book title required");
    ensure(x.author()!=null&&!x.author().isBlank()&&x.author().length()<=180,"Author required");
    ensure(x.copies()!=null&&x.copies()>=0&&x.copies()<=100000,"Copies: 0–100000");
    ensure(text(x.genre(),80).length()<=80&&text(x.description(),4000).length()<=4000,"Invalid book information");
    ensure(x.coverUrl()==null||x.coverUrl().isBlank()||x.coverUrl().startsWith("https://"),"Cover must use HTTPS");
  }
  @PostMapping("/admin/books") public Map<String,Object> bookAdd(Principal p,@RequestBody BookInput x){
    long actor=id(requireRole(p,"ADMIN").get("id"));validateBook(x);
    long bid=insert("INSERT INTO books(title,title_kk,title_ru,title_en,author,genre,language,description,cover_url,copies,is_public) VALUES(?,?,?,?,?,?,?,?,?,?,?)",
      x.title().trim(),text(x.titleKk(),220),text(x.titleRu(),220),text(x.titleEn(),220),x.author().trim(),text(x.genre(),80),text(x.language(),6),text(x.description(),4000),text(x.coverUrl(),800),x.copies(),x.isPublic()==null||x.isPublic());
    audit(actor,"ADD_BOOK","BOOK",bid,x.title().trim());return Map.of("id",bid);
  }
  @PutMapping("/admin/books/{bid}") @Transactional public Map<String,Object> bookEdit(Principal p,@PathVariable long bid,@RequestBody BookInput x){
    long actor=id(requireRole(p,"ADMIN").get("id"));validateBook(x);lockBook(bid);
    ensure(x.copies()>=activeCount(bid),"Copies cannot be fewer than active reservations");
    db.update("UPDATE books SET title=?,title_kk=?,title_ru=?,title_en=?,author=?,genre=?,language=?,description=?,cover_url=?,copies=?,is_public=? WHERE id=?",
      x.title().trim(),text(x.titleKk(),220),text(x.titleRu(),220),text(x.titleEn(),220),x.author().trim(),text(x.genre(),80),text(x.language(),6),text(x.description(),4000),text(x.coverUrl(),800),x.copies(),x.isPublic()==null||x.isPublic(),bid);
    audit(actor,"EDIT_BOOK","BOOK",bid,x.title().trim());return Map.of("ok",true);
  }
  @PostMapping(value="/admin/books/{bid}/file",consumes=MediaType.MULTIPART_FORM_DATA_VALUE) public Map<String,Object> uploadBook(Principal p,@PathVariable long bid,@RequestPart("file") MultipartFile file)throws IOException{
    long actor=id(requireRole(p,"ADMIN").get("id"));single("SELECT id FROM books WHERE id=?",bid);
    ensure(!file.isEmpty()&&file.getSize()<=10_000_000,"File limit: 10 MB");
    String name=Objects.toString(file.getOriginalFilename(),"").toLowerCase(Locale.ROOT);
    byte[] bytes=file.getBytes();String mime;
    if(name.endsWith(".pdf")&&bytes.length>4&&new String(bytes,0,4,StandardCharsets.US_ASCII).equals("%PDF"))mime="application/pdf";
    else if(name.endsWith(".epub")&&bytes.length>2&&bytes[0]=='P'&&bytes[1]=='K')mime="application/epub+zip";
    else if(name.endsWith(".txt"))mime="text/plain";
    else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Allowed: .txt, .pdf, .epub");
    // Store bytes inside PostgreSQL, not Render's temporary filesystem.
    db.update("UPDATE books SET content=?,content_type=? WHERE id=?",bytes,mime,bid);
    audit(actor,"UPLOAD_EBOOK","BOOK",bid,mime+" bytes="+bytes.length);
    return Map.of("ok",true,"size",bytes.length,"type",mime);
  }
  @DeleteMapping("/admin/books/{bid}/file") public Map<String,Object> removeFile(Principal p,@PathVariable long bid){
    long actor=id(requireRole(p,"ADMIN").get("id"));db.update("UPDATE books SET content=NULL,content_type=NULL WHERE id=?",bid);audit(actor,"REMOVE_EBOOK","BOOK",bid,"");return Map.of("ok",true);
  }

  @GetMapping("/admin/users") public List<Map<String,Object>> users(Principal p){
    requireRole(p,"ADMIN");return query("""
      SELECT u.id,u.username,u.full_name,u.email,u.phone,u.address,u.active,u.banned_until,u.created_at,u.deleted_at,
        (SELECT COUNT(*) FROM reservations r WHERE r.user_id=u.id AND r.status='ISSUED') AS issued_count
      FROM app_users u WHERE u.role='READER' ORDER BY u.id DESC
      """);
  }
  public record UserState(String action,Integer days){}
  @PostMapping("/admin/users/{uid}/status") public Map<String,Object> userStatus(Principal p,@PathVariable long uid,@RequestBody UserState x){
    long actor=id(requireRole(p,"ADMIN").get("id"));var u=single("SELECT id,role,deleted_at FROM app_users WHERE id=?",uid);
    ensure("READER".equals(u.get("role"))&&u.get("deleted_at")==null,"Reader account required");
    ensure(x!=null&&List.of("ban","unban").contains(x.action()),"Action must be ban/unban");
    if("ban".equals(x.action())){
      if(x.days()!=null&&x.days()>0&&x.days()<=365)db.update("UPDATE app_users SET active=TRUE,banned_until=? WHERE id=?",java.sql.Timestamp.valueOf(LocalDateTime.now().plusDays(x.days())),uid);
      else db.update("UPDATE app_users SET active=FALSE,banned_until=NULL WHERE id=?",uid);
    }else db.update("UPDATE app_users SET active=TRUE,banned_until=NULL WHERE id=?",uid);
    audit(actor,"USER_"+x.action().toUpperCase(Locale.ROOT),"USER",uid,"days="+x.days());return Map.of("ok",true);
  }
  @DeleteMapping("/admin/users/{uid}") @Transactional public Map<String,Object> deleteUser(Principal p,@PathVariable long uid){
    long actor=id(requireRole(p,"ADMIN").get("id"));var u=single("SELECT role,deleted_at FROM app_users WHERE id=?",uid);
    ensure("READER".equals(u.get("role"))&&u.get("deleted_at")==null,"Reader account required");
    ensure(query("SELECT id FROM reservations WHERE user_id=? AND status IN ('RESERVED','READY','ISSUED')",uid).isEmpty(),"Reader still has active loans/reservations");
    db.update("DELETE FROM favorites WHERE user_id=?",uid);db.update("DELETE FROM reading_progress WHERE user_id=?",uid);
    db.update("UPDATE app_users SET active=FALSE,deleted_at=CURRENT_TIMESTAMP,username=?,full_name='Deleted reader',email=NULL,phone=NULL,address=NULL,password_hash=? WHERE id=?",
      "deleted_"+uid,encoder.encode(UUID.randomUUID().toString()),uid);
    audit(actor,"DELETE_USER","USER",uid,"Anonymized and disabled; loan history preserved");return Map.of("ok",true);
  }
  @GetMapping("/admin/staff") public List<Map<String,Object>> listStaff(Principal p){
    requireRole(p,"ADMIN");return query("SELECT id,username,full_name,email,phone,active,created_at FROM app_users WHERE role='LIBRARIAN' AND deleted_at IS NULL ORDER BY id DESC");
  }
  public record StaffCreate(String username,String fullName,String password,String phone){}
  @PostMapping("/admin/staff") public Map<String,Object> createStaff(Principal p,@RequestBody StaffCreate x){
    long actor=id(requireRole(p,"ADMIN").get("id"));ensure(x!=null&&x.username()!=null&&LOGIN.matcher(x.username()).matches(),"Valid login required");
    ensure(x.fullName()!=null&&x.fullName().trim().length()>=2,"Full name required");ensure(x.password()!=null&&x.password().length()>=6&&x.password().length()<=72,"Password must be 6–72 chars");
    String u=x.username().toLowerCase(Locale.ROOT);ensure(query("SELECT id FROM app_users WHERE username=?",u).isEmpty(),"Username used");
    long uid=insert("INSERT INTO app_users(username,full_name,phone,password_hash,role) VALUES(?,?,?,?,'LIBRARIAN')",u,text(x.fullName(),160),text(x.phone(),30),encoder.encode(x.password()));
    audit(actor,"CREATE_STAFF","USER",uid,u);return Map.of("id",uid);
  }
  @PostMapping("/admin/staff/{uid}/status") public Map<String,Object> staffStatus(Principal p,@PathVariable long uid,@RequestBody UserState x){
    long actor=id(requireRole(p,"ADMIN").get("id"));ensure("LIBRARIAN".equals(single("SELECT role FROM app_users WHERE id=?",uid).get("role")),"Only librarians can be updated");
    ensure(x!=null&&List.of("ban","unban").contains(x.action()),"Invalid action");
    db.update("UPDATE app_users SET active=?,banned_until=NULL WHERE id=?","unban".equals(x.action()),uid);
    audit(actor,"STAFF_"+x.action().toUpperCase(Locale.ROOT),"USER",uid,"");return Map.of("ok",true);
  }
  @GetMapping("/admin/logs") public List<Map<String,Object>> logs(Principal p){
    requireRole(p,"ADMIN");return query("SELECT l.id,l.created_at,l.action,l.subject_type,l.subject_id,l.detail,u.username AS actor FROM audit_log l LEFT JOIN app_users u ON u.id=l.actor_id ORDER BY l.id DESC LIMIT 200");
  }
  @GetMapping("/admin/reports") public List<Map<String,Object>> reports(Principal p){
    requireRole(p,"ADMIN");return query("""
      SELECT b.id,b.title,b.author,b.copies,
       (SELECT COUNT(*) FROM reservations r WHERE r.book_id=b.id AND r.status IN ('RESERVED','READY','ISSUED')) AS currently_out,
       (SELECT COUNT(*) FROM reservations r WHERE r.book_id=b.id AND r.status='RETURNED') AS returned,
       (SELECT COUNT(*) FROM waitlist w WHERE w.book_id=b.id AND w.status='WAITING') AS waiting
       FROM books b ORDER BY b.id
      """);
  }
}
