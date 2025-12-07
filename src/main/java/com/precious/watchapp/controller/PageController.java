package com.precious.watchapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

 

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/men")
    public String men() {
        return "amen";
    }

    @GetMapping("/women")
    public String women() {
        return "awomen";
    }

    @GetMapping("/collection")
    public String collection() {
        return "collection";
    }

    @GetMapping("/anew")
    public String accessories() {
        return "anew"; // your accessories page
    }

    @GetMapping("/login")
    public String login() {
        return "alogin";
    }
@GetMapping("/address")
public String address() {
    return "address";
}

@GetMapping("/anavebar")
public String anavebar() {
    return "anavebar";
}

@GetMapping("/cartsaved")
public String cartsaved() {
    return "cartsaved";
}

@GetMapping("admin")
public String admin(){
    return "admin";
}


}
