package io.github.mezk.controller;

import java.io.IOException;
import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;

@RestController
public class TestController {

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public void send() {
        ListenableFuture<ResponseEntity<String>> resp =
            new AsyncRestTemplate().getForEntity("http://localhost:8081/receive", String.class);
        resp.addCallback(c -> System.out.println(c.getBody()), f -> System.out.println("Failure"));
        System.out.println("further operations");
    }


    @RequestMapping(value = "/receive", method = RequestMethod.GET)
    public String receive() throws InterruptedException, IOException {
        Thread.sleep(5000L);
        System.out.println("invokation of receive");

        final Random r = new Random();
        if (r.nextBoolean()) {
            return "Success";
        } else {
            throw new IOException();
        }
    }
}
