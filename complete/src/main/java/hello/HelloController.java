package hello;

import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/RandomNumber")
    public int RandomNumber()
    {
        Random rand = new Random();
        int x = rand.nextInt(100);
        return x;
    }
}
