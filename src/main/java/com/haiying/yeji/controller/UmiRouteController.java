package com.haiying.yeji.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UmiRouteController {
    @Autowired
    HttpSession httpSession;

    //umirc.ts -> routes -> path
    @GetMapping({"/login", "/back"})
    public String route() {
        return "index";
    }
}
