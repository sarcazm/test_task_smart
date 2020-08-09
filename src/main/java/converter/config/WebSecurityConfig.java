package converter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /*
   1) идентификация - процесс распознавания субъекта по его идентификатору (определяем имя, логин, номера телефона и т.д,)
   2) аутентификация - процесс провекри на подлинность (пользователя мы проверякем по его паролю)
   3) авторизация - процесс предоставления прав на доступ к какому-либо ресурсу
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //выдаём доступ к ресурсам по пути ("/", "/registration") всем неавторизованным пользователям - permitAll()
                .antMatchers("/registration").permitAll()
                //anyRequest().authenticated() - все остальные ресурсы будут доступны после идентификации и аутентификации
                .anyRequest().authenticated()
                //and() - аналог оператора логического and (возвращает объект билдера spring security)
                .and()
                //настройка входа в наше веб-приложение  - formLogin()
                .formLogin()
                //loginPage("/login") - указыцваем путь ресурса для осуществления входа в наше веб-приложение
                .loginPage("/login")
                .defaultSuccessUrl("/main")
                //.permitAll() - указываем, что вход в наше веб-приложение будет доступен всем без идентификации и аутентификации
                .permitAll()
                //and() - аналог оператора логического and (возвращает объект билдера spring security)
                .and()
                //настраиваем форму выхода (logout) пользователя из нашего веб-прилоежния
                .logout()
                //.permitAll() - указываем, что выход(logout) из наше веб-приложение будет доступен всем без идентификации и аутентификации
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select username, password, active from usr where username=?")
                .authoritiesByUsernameQuery("select username, 'ROLE_USER' from usr where username=?");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
