package  com.example.app; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
@RestController
public class LedgerSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(LedgerSystemApplication.class, args);
  }

}