package dev.ned.application.websocket;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TestService {
    @MessageMapping("/news")
    @SendTo("/topic/news")
    public Greeting greeting(@Payload HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println(message.toString());
        return new Greeting("Malik", 25.6);
    }

}

class HelloMessage {

    private String name;
    int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public HelloMessage() {}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HelloMessage{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class Greeting {

    private String content;
    double broj;

    public Greeting() {}

    public Greeting(String content, double broj) {
        this.content = content;
        this.broj = broj;
    }

    public double getBroj() {
        return broj;
    }

    public void setBroj(double broj) {
        this.broj = broj;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public String getContent() {
        return content;
    }
}