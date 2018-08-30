package com.severstal.sqp.config;

/**
 * BCrypt password encoder.
 *
 * @author Ivan Efanov <in.efanov@stalcom.com>
 * @package com.severstal.sqp.config
 */

import java.util.Locale;
import java.util.Stack;

import com.severstal.sqp.entity.Complexity;
import com.severstal.sqp.entity.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.severstal.sqp.entity.Question;
import com.severstal.sqp.entity.User;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages_ru");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("utf-8");
        messageSource.setCacheSeconds(5);
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        lci.setParamName("ru.page.default.text");
        lci.setParamName("ru.page.default.title");
        lci.setParamName("ru.button.default.text");
        lci.setParamName("ru.page.text");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Bean(name = "currentEmployeeContainer")
    @Scope(
            value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Stack<User> currentEmployeeContainer()
    {
        return new Stack<User>();
    }

    @Bean(name = "currentTestQuestionContainer")
    @Scope(
            value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Stack<Question> currentTestQuestionContainer()
    {
        return new Stack<Question>();
    }

    @Bean(name = "currentTestResultContainer")
    @Scope(
            value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Stack<Test> currentTestResult()
    {
        return new Stack<Test>();
    }


    @Bean(name = "currentComplexityContainer")
    @Scope(
            value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Stack<Complexity> currentComplexity()
    {
        return new Stack<Complexity>();
    }

    @Bean(name = "currentCountOfTriesContainer")
    @Scope(
            value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Stack<Integer> currentCountOfTries()
    {
        return new Stack<Integer>();
    }

    @Bean(name = "currentResultPoints")
    @Scope(
            value = WebApplicationContext.SCOPE_SESSION,
            proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Stack<Double> currentResultPoints()
    {
        return new Stack<Double>();
    }
}
