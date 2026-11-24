package kz.paraq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ParaQApplication {
  public static void main(String[] args) { SpringApplication.run(ParaQApplication.class,args); }
}
