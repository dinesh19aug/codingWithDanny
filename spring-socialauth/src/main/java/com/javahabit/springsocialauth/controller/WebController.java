package com.javahabit.springsocialauth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/index")
    public String publicPage() {
        return "public";
    }

    @GetMapping("/admin")
    public String adminPage(@AuthenticationPrincipal OAuth2User principal, Model model) {
        String name = principal.getAttribute("name");
        model.addAttribute("name", name);
        System.out.println("Model attribute 'name': " + model.getAttribute("name"));
        return "admin"; // Assuming you have admin.html in the static folder
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/index";  // Redirect to the public page
    }


}
