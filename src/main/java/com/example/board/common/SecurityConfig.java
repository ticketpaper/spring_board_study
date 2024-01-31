package com.example.board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 해당 어노테이션은 spring security 설정을 커스텀하기 위함
//    WebSecurityConfigurerAdapter를 상속하는 방식은 지원종료 됐음
@EnableGlobalMethodSecurity(prePostEnabled = true)
// pre : 사전, post : 사후, 사전/사후에 인중/권한 검사
public class SecurityConfig {

    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
//                csrf보안공격에 대한 설정은 하지 않겠다라는 의미
                .csrf().disable()
//                특정 url에 대해서는 보안을 인증처리 하지않고 특정 url대해서는 인증처리 하겠다는 설
                .authorizeRequests()
//                인증 미적용 url 패턴
                .antMatchers("/", "/author/create", "/author/login-page")
                .permitAll()
//                그외 요청은 모두 인증 필요
                .anyRequest().authenticated()
                .and()
//                만약에 세션을 사용하지 않으면 아래 내용 설정
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin()
                .loginPage("/author/login-page")
//               스프링 내장 메서드를 사용하기위해 /doLogin url 사용
                .loginProcessingUrl("/doLogin")
                .usernameParameter("email")
                .passwordParameter("pw")
                .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
//                spring security의 doLogout기능 그대로 사용
                .logoutUrl("/doLogout")
                .and()
                .build();
    }
}
