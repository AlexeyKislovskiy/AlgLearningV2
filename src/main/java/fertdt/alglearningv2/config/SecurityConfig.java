package fertdt.alglearningv2.config;


import fertdt.alglearningv2.service.CuberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CuberService cuberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(cuberService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/AlgLearning/login", "/AlgLearning/register", "/AlgLearning/login/attempt").permitAll()
                .antMatchers("/img/**", "/css/**", "/js/**").permitAll()
                .antMatchers("/AlgLearning/moderator", "/AlgLearning/api/algorithm/**").hasRole("MODERATOR")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/AlgLearning/login")
                .loginProcessingUrl("/AlgLearning/login")
                .usernameParameter("nickname")
                .passwordParameter("password")
                .defaultSuccessUrl("/AlgLearning/algorithms", true)
                .failureUrl("/AlgLearning/login")
                .and()
                .logout()
                .logoutUrl("/AlgLearning/logout")
                .and()
                .csrf().disable();
    }
}

