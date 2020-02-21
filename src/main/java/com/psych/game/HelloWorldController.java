package com.psych.game;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev-test")
public class HelloWorldController {

    @GetMapping("/")
    public String hello(){
        return "My love, I will hold you when you are down, I will see you attractive even in your worst look, my love, I will be there for you at your worst moment. I will be by your side when the world denies you, will always be by your side, by you I will be!\n" +
                "\n -For My Lovely Lady, Anandita";
    }
}
