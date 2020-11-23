package kr.co.penta.dataeye.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import kr.co.penta.dataeye.spring.config.properties.SecuritySettings;
import kr.co.penta.dataeye.spring.security.CORSFilter;
import kr.co.penta.dataeye.spring.security.DataeyeUserDetailsService;
import kr.co.penta.dataeye.spring.security.authentication.AjaxAwareAuthenticationEntryPoint;
import kr.co.penta.dataeye.spring.security.authentication.DataeyeWebAuthenticationDetailsSource;
import kr.co.penta.dataeye.spring.security.handler.LoginFailureHandler;
import kr.co.penta.dataeye.spring.security.handler.LoginSuccessHandler;
import kr.co.penta.dataeye.spring.security.handler.LogoutSuccessHandler;

@EnableWebSecurity(debug=false)
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecuritySettings securitySettings;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private AjaxAwareAuthenticationEntryPoint ajaxAwareAuthenticationEntryPoint;

    @Autowired
    private DataeyeWebAuthenticationDetailsSource authenticationDetailsSource;

    @Bean
    public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService() {
        return new DataeyeUserDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    	characterEncodingFilter.setEncoding("UTF-8");
    	characterEncodingFilter.setForceEncoding(true);
    	
    	http.addFilterBefore(characterEncodingFilter, CsrfFilter.class);
        http.addFilterBefore(new CORSFilter(securitySettings.getAllowOrigin()), ChannelProcessingFilter.class);

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/webjars/**", "/assets/**", "/stylesheets/**", "/images/**", "/javascripts/**", "/sso/**", "/down/**").permitAll() // resources
                .antMatchers("/login/**", "/login", "/public/**").permitAll() //login pate test
                .antMatchers("/externalif/**").permitAll() //login pate test
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/system/**").hasAnyRole("SYSTEM")
                .antMatchers("/ers/**", "/customizing/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(securitySettings.getLoginPage())
                .loginProcessingUrl(securitySettings.getLoginProcessingUrl())
                .usernameParameter(securitySettings.getUsernameParameter())
                .passwordParameter(securitySettings.getPasswordParameter())
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
//                .authenticationDetailsSource(authenticationDetailsSource)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
//                .logoutSuccessHandler(logoutSuccessHandler)
                .logoutUrl(securitySettings.getLogoutUrl())    //내부적으로 logout 처리
                .logoutSuccessUrl(securitySettings.getLogoutUrl())
                .permitAll()
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl(securitySettings.getExpiredUrl())
                .maxSessionsPreventsLogin(false)
                .and();

        http.exceptionHandling().authenticationEntryPoint(ajaxAwareAuthenticationEntryPoint).accessDeniedPage("/403");

        http.headers().frameOptions().disable();
    }

//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        AuthenticationProvider authenticationProvider = context.getBean(securitySettings.getProvider(), AuthenticationProvider.class);
//        auth.authenticationProvider(authenticationProvider); //.userDetailsService(userDetailsService);
//        //.inMemoryAuthentication()
//        //.withUser("user").password("password").roles("USER");
//    }
}
