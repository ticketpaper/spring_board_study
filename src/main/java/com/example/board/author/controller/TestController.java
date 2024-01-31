package com.example.board.author.controller;


import com.example.board.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
// Slf4j 어노테이션을 통해 쉽게 logback 로그 라이브러리 사용 가능
@Slf4j
public class TestController {
    //    Slf4j 어노테이션 사용하지않고 직접 라이브러리 import하여 로거 생성 가능
//    private static final Logger log = LoggerFactory.getLogger(LogTestController.class);

    @Autowired
    private AuthorService authorService;

    @GetMapping("/log/test1")
    public String testMethod1() {
        log.debug("디버그 로그");
        log.info("인포 로그");
        log.error("에러 로그");
        return "OK";

    }

    @GetMapping("/exception/test1/{id}")
    public String exceptionTestMethod(@PathVariable Long id) {
        authorService.findById(id);
        return "OK";

    }

    @GetMapping("userinfo/test")
    public String urserInfoTest(HttpServletRequest httpServletRequest) {
//        로그인 유저정보 얻는 방식
//        방법1. session에 attribute를 통해 접근
        String email1 = httpServletRequest.getSession().getAttribute("email").toString();
        System.out.println("email1 = " + email1);
//        방법2. session에서 Authentication 객체를 통해 접근
        SecurityContext securityContext = (SecurityContext)httpServletRequest.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String email2 = securityContext.getAuthentication().getName();
        System.out.println("email2 = " + email2);
//        방법3. SecurityContextHolder 에서 Authentication객체를 접 -> 일반적
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email3 = authentication.getName();
        System.out.println("email3 = " + email3);
        return null;
    }
}
