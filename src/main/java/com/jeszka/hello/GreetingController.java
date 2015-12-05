package com.jeszka.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @CrossOrigin(origins = "http://localhost:9000")
    @RequestMapping("/greeting")
    public @ResponseBody DeferredResult<Greeting> greeting(@RequestParam(required=false, defaultValue="World") String name) {
        System.out.println("+++ in greeting +++");
        DeferredResult<Greeting> deferredResult = new DeferredResult<>();

        CompletableFuture.supplyAsync(() -> execute(name))
             .whenCompleteAsync((result, throwable) -> deferredResult.setResult(result));
        System.out.println("--- in greeting ---");
        return deferredResult;
    }

    private Greeting execute(String name) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Greeting ready");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

}