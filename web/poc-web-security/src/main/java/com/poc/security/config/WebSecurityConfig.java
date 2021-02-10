package com.poc.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.poc.security.jwt.AuthenticationJwtEntryPoint;
import com.poc.security.jwt.AuthenticationWebSecurityFilter;
import com.poc.support.jwt.JwtParser;
import com.poc.support.rest.logger.RestLoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean(name = "jwtParser")
    public JwtParser jwtParser() {
        return new JwtParser(jwtSecret, jwtExpirationMs);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

   /* @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }*/

    @Bean
    public AuthenticationJwtEntryPoint authenticationJwtEntryPoint() {
        return new AuthenticationJwtEntryPoint();
    }

    @Bean
    public AuthenticationWebSecurityFilter authenticationWebSecurityFilter() {
        return new AuthenticationWebSecurityFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * (@see https://spring.io/guides/tutorials/spring-security-and-angular-js/) for CRSF
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // enabling cors, dimana CorsConfigurationSource nya adalah bean.
        http.cors();

        // enabling crsf
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers ("/api/v1/auth/**","/live");

        // no need this filter
        //.and()
        //.addFilterAfter(new CrsfTokenExchangeFilter(), CsrfFilter.class); // Csrf filter in which we will add the cookie

        http.exceptionHandling().authenticationEntryPoint(authenticationJwtEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()// force no session created
                .authorizeRequests().antMatchers("/api/v1/auth/**").permitAll().and()
                .authorizeRequests().antMatchers("/live").permitAll()
                .anyRequest().authenticated();

        // register our filter here, we chain it to before user and password auth fllter check.
        //http.addFilterBefore(authenticationWebSecurityFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(authenticationWebSecurityFilter(), CsrfFilter.class);
    }

    /**
     * Wajib untuk CORS,
     * jika tidak mau jadikan bean gunankan http.cors().configurationSource(corsConfigurationSource());
     * { @see https://docs.spring.io/spring-security/site/docs/4.2.x/reference/html/cors.html}
     * Enabled CORS global config
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200","http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.addAllowedHeader("*");
        configuration.setMaxAge(3600L); // pre-flight max age
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // adding request logger interceptor, buat logging saja
    @Bean
    public WebMvcConfigurer loggerInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new RestLoggerInterceptor(objectMapper()));
            }
        };
    }
}
