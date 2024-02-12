package com.javahabit.d2ccommerceapp.controller;

import com.javahabit.d2ccommerceapp.service.book.IService;
import jakarta.servlet.http.HttpServletRequest;
import org.ff4j.FF4j;
import org.ff4j.core.FlippingExecutionContext;
import org.ff4j.core.FlippingStrategy;
import org.ff4j.strategy.ClientFilterStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class BookController {

    @Autowired
    FF4j ff4j;
    @Qualifier("book-service")
    IService bookService;
    public BookController(IService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public String getBook( Model model, HttpServletRequest request){
        model.addAttribute("books", bookService.apply());

        List roles =  SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(u-> u.getAuthority()).collect(Collectors.toList()) ;
        String userName =   SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("customerName", userName);
        model.addAttribute("userRole", roles.get(0).toString());
        model.addAttribute("state", bookService.getUserState(userName));
        //System.out.println("Show books: " + ff4j.check("show-books"));
        ff4j.isAllowed(ff4j.getFeature("show-copyright"));
        FlippingStrategy flippingStrategy = new ClientFilterStrategy();
        FlippingExecutionContext fex = new FlippingExecutionContext();
        fex.addValue("clientHostName", getClientIp(request));
        fex.addValue("weight", ff4j.getProperty("fiftyPercent"));
        return "book";

    }

    @GetMapping("/login")
    public String login()
    {
        return "/login";
    }

    @GetMapping("")
    public ModelAndView loginBack(ModelMap model)
    {
        return new ModelAndView("redirect:/book", model);
    }

    private String getClientIp(HttpServletRequest request)
    {

        String remoteAddr = "";

        if (request != null)
        {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            System.out.println("X-FORWARDED-FOR : " + remoteAddr);
            if (remoteAddr == null || "".equals(remoteAddr))
            {
                remoteAddr = request.getRemoteAddr();

                System.out.println("Remote Addr : " + remoteAddr);
            }
        }

        return remoteAddr;
    }
}
