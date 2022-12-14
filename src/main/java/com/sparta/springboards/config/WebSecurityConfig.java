package com.sparta.springboards.config;

import com.sparta.springboards.jwt.JwtAuthFilter;
import com.sparta.springboards.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    //Spring Security 가 제공하는 적응형 단방향 함수인 bCrypt 를 사용하여 비밀번호를 암호화(Encoder) --> 적응형 단방향이 자동 적용
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    //WebSecurityCustomizer 은 SecurityFilterChain 보다 우선적으로 걸리는 설정
    public WebSecurityCustomizer webSecurityCustomizer() {
        //h2-console 사용 및 resources 접근 허용 설정
        //ignoring(): 이러한 경로도 들어온 것들은 인증 처리하는 것을 무시하겠다
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf().disable();

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //permitAll() 를 사용해서, 이런 URL 들을 인증하지 않고 실행 할 수 있게 함
        http.authorizeRequests().antMatchers("/api/auth/**").permitAll()
                //그 이외의 URL 요청들을 전부 다 authentication(인증 처리)하겠다
                .anyRequest().authenticated()
                // JWT 인증/인가를 사용하기 위한 설정
                // Custom Filter 등록하기
                //addFilterBefore: 어떤 Filter 이전에 추가하겠다 --> 우리가 만든 JwtAuthFilter 를 UsernamePasswordAuthenticationFilter 이전에 실행할 수 있도록
                //1. JwtAuthFilter 를 통해 인증 객체를 만들고 --> 2. context 에 추가 --> 3.인증 완료 --> UsernamePasswordAuthenticationFilter 수행 --> 인증됐으므로 다음 Filter 로 이동 --> Controller 까지도 이동
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //"거부"가 났을 때, 403 Forbidden 페이지(접근 제한 페이지) 이동 설정
        http.exceptionHandling().accessDeniedPage("/api/user/forbidden");

        return http.build();
    }
}
