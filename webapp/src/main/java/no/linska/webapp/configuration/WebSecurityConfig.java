package no.linska.webapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.user-authentication}")
    private String authenticationQuery;

    @Value("${spring.queries.user-authorization}")
    private String authorizationQuery;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                    .authorizeRequests()
                    .antMatchers("/", "/home","/register","/api/messages/hello").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/process_login")
                    .defaultSuccessUrl("/hello")
                    .failureUrl("/login?error")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .permitAll()

                .and()
                    .logout()
                    .permitAll()
                .and().exceptionHandling()
                        .accessDeniedPage("/error");



    }


    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(authenticationQuery)
                .authoritiesByUsernameQuery(authorizationQuery)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }
}
