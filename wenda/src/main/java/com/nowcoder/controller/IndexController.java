package com.nowcoder.controller;

import com.nowcoder.model.User;
import com.nowcoder.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by dell on 2017/5/24.
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/","/index"} , method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String index(HttpSession httpSession){
        logger.info("visit home");
        return wendaService.getMessage(2) + "hello nowcoder" + httpSession.getAttribute("msg");
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type" , defaultValue = "1") int type,
                          @RequestParam(value = "key" , required = false) String key){
        return String.format("Profile page of %s / %d , type: %d  key : %s" , groupId , userId , type , key);
    }

    @RequestMapping(path = {"/vm"} , method = {RequestMethod.GET })
    public String template(Model model){
        model.addAttribute("value1" , "this is value1");
        List<String> colors = Arrays.asList(new String[]{"RED" , "GREEN" , "BLUE"});
        model.addAttribute("colors" , colors);
        Map<String,String> map = new HashMap<>();
        for(int i=2 ; i<5 ; ++i){
            map.put(String.valueOf(i) , String.valueOf(i*i));
        }
        model.addAttribute("map" , map);
        model.addAttribute("user" , new User("Alice"));
        return "home";
    }

    @RequestMapping(path = {"/request"} , method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession httpSession,
                          @CookieValue("JSESSIONID") String sessionId){
        StringBuilder sb = new StringBuilder();
        sb.append("cookie is : " + sessionId);
        Enumeration<String> headerNames=request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name + " ; " + request.getHeader(name) + "<br/>");
        }
        if(request.getCookies()!=null){
            for(Cookie cookie : request.getCookies()){
                sb.append("Cookie : " + cookie.getName() + " value : " + cookie.getValue());
            }
        }
        sb.append(request.getMethod() + "<br/>");
        sb.append(request.getQueryString() + "<br/>");
        sb.append(request.getPathInfo() + "<br/>");
        sb.append(request.getRequestURL() + "<br/>");

        response.addHeader("nowcoderId" , "hello");
        response.addCookie(new Cookie("username" , "alice"));
        return sb.toString();
    }

    @RequestMapping(path = {"/redirect/{code}"} , method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession httpSession){
        httpSession.setAttribute("msg" , "jump from redirect");
        RedirectView red = new RedirectView("/" , true);
        if(code==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    @RequestMapping(path = {"/admin"} , method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("not right");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error : " + e.getMessage();
    }

}
