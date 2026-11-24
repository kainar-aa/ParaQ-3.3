package kz.paraq;

import java.util.Locale;
import java.time.LocalDateTime;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {
  @Bean PasswordEncoder encoder(){ return new BCryptPasswordEncoder(12); }
  @Bean UserDetailsService users(JdbcTemplate db){
    return username->{
      var rows=db.queryForList("SELECT username,password_hash,role,active,banned_until,deleted_at FROM app_users WHERE username=?",username.toLowerCase(Locale.ROOT));
      if(rows.isEmpty()) throw new UsernameNotFoundException("Account not found");
      var r=rows.get(0);
      if(!Boolean.TRUE.equals(r.get("active"))||r.get("deleted_at")!=null||
         (r.get("banned_until")!=null && ((java.sql.Timestamp)r.get("banned_until")).toLocalDateTime().isAfter(LocalDateTime.now())))
        throw new DisabledException("Account blocked");
      return User.withUsername((String)r.get("username")).password((String)r.get("password_hash"))
        .roles((String)r.get("role")).build();
    };
  }
  @Bean SecurityFilterChain security(HttpSecurity http) throws Exception {
    http.csrf(c->c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
      .authorizeHttpRequests(a->a
        .requestMatchers("/","/index.html","/app.js","/style.css","/favicon.ico","/favicon.png","/apple-touch-icon.png","/logo.png","/logo-256.png","/icon-192.png","/health","/error").permitAll()
        .requestMatchers(HttpMethod.GET,"/api/auth/csrf","/api/auth/me","/api/books","/api/books/*","/api/books/*/reviews").permitAll()
        .requestMatchers(HttpMethod.POST,"/api/auth/register").permitAll()
        .requestMatchers("/api/admin/**").hasRole("ADMIN")
        .requestMatchers("/api/staff/**").hasAnyRole("ADMIN","LIBRARIAN")
        .anyRequest().authenticated())
      .formLogin(f->f.loginProcessingUrl("/api/auth/login")
        .successHandler((q,s,x)->{s.setContentType(MediaType.APPLICATION_JSON_VALUE);s.getWriter().write("{\"ok\":true}");})
        .failureHandler((q,s,x)->{s.setStatus(401);s.setCharacterEncoding("UTF-8");s.setContentType(MediaType.APPLICATION_JSON_VALUE);s.getWriter().write("{\"error\":\"Invalid username or password\"}");}))
      .logout(l->l.logoutUrl("/api/auth/logout")
        .logoutSuccessHandler((q,s,x)->{s.setContentType(MediaType.APPLICATION_JSON_VALUE);s.getWriter().write("{\"ok\":true}");}))
      .exceptionHandling(e->e
        .authenticationEntryPoint((q,s,x)->{s.setStatus(401);s.setContentType("application/json");s.getWriter().write("{\"error\":\"Login required\"}");})
        .accessDeniedHandler((q,s,x)->{s.setStatus(403);s.setContentType("application/json");s.getWriter().write("{\"error\":\"Access denied\"}");}));
    return http.build();
  }
}
