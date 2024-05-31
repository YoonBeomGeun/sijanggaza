package sijang.sijanggaza;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //스프링 환경 설정 파일이라는 의미
@EnableWebSecurity //모든 요청 URL이 스프링 시큐리티의 제어를 받음
@EnableMethodSecurity(prePostEnabled = true) //@PreAuthorize 애너테이션을 사용하기 위해 반드시 필요한 설정
public class SecurityConfig {
    @Bean //스프링에 의해 관리됨, 세부 설정(인증되지 않은 모든 페이지의 요청 허락)
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/orders/**")).authenticated()
                        .anyRequest().permitAll())
                .csrf((csrf) -> csrf
                        /*.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))*/ // /h2-console/로 시작하는 모든 URL은 CSRF 검증 X
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/**"))) // api 테스트를 위해 수정
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                            XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))) //URL 요청 시 X-Frame-Options 헤더를 DENY 대신 SAMEORIGIN으로 설정
                .formLogin((formLogin) -> formLogin //스프링 시큐리티의 로그인 설정을 담당
                        .loginPage("/user/login") //로그인 페이지 URL
                        .defaultSuccessUrl("/")) //로그인 성공 시
                .logout((logout) -> logout //로그아웃 설정
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true))
        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean //스프링 시큐리티의 인증 처리
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
