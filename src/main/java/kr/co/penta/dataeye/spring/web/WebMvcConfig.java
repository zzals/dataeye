package kr.co.penta.dataeye.spring.web;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.ProgressListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.ui.context.ThemeSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.theme.SessionThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import kr.co.penta.dataeye.spring.web.filter.RequestLoggingFilter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.view.tiles-enabled}") private String tilesEnabled;
    @Value("${dataeye.theme}") private String theme;
    @Value("${dataeye.config.menu-access-logging-enable}") private boolean menuAccessLoggingEnable;
    
    @Autowired private Environment env;

    @ConditionalOnProperty(value="spring.view.tiles-enabled")
    @Bean
    public TilesConfigurer tilesConfigurer() {
        final TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(new String[] {"WEB-INF/tiles/tiles.xml"});
        tilesConfigurer.setCheckRefresh(true);

        return tilesConfigurer;
    }
    
    @ConditionalOnProperty(value="spring.view.tiles-enabled")
    @Bean
    public UrlBasedViewResolver urlBasedViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(TilesView.class);
        resolver.setOrder(2);
        return resolver;
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setOrder(3);
        internalResourceViewResolver.setPrefix("/WEB-INF/views/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    @Bean
    public ViewResolver contentNegotiatingViewResolver(
            ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
        List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
        if("true".equals(tilesEnabled)) {
            viewResolvers.add(urlBasedViewResolver());
        }
        viewResolvers.add(internalResourceViewResolver());
        resolver.setViewResolvers(viewResolvers);
        resolver.setOrder(1);
        return resolver;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(180);

        return messageSource;
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");

        return localeChangeInterceptor;
    }

    @Bean(name="localeResolver")
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        //CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(new Locale(env.getProperty("application.portal.default-locale")));
        log.info("set default locale : {}", env.getProperty("application.portal.default-locale"));

        return localeResolver;
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver() {
            @Override
            protected FileUpload prepareFileUpload(String encoding) {
                FileUpload fileUpload = super.prepareFileUpload(encoding);
                ProgressListener progressListener = new ProgressListener() {
                    private long megaBytes = -1;
                    public void update(long pBytesRead, long pContentLength, int pItems) {
                        long mBytes = pBytesRead / 1000000;
                        if (megaBytes == mBytes) {
                            return;
                        }
                        megaBytes = mBytes;
                        System.out.println("We are currently reading item " + pItems);
                        if (pContentLength == -1) {
                            System.out.println("So far, " + pBytesRead + " bytes have been read.");
                        } else {
                            System.out.println("So far, " + pBytesRead + " of " + pContentLength
                                    + " bytes have been read.");
                        }
                    }
                };
                fileUpload.setProgressListener(progressListener);
                return fileUpload;
            }
        };
        
        long maxFileSize = 500000000;
        try {
            maxFileSize = Long.parseLong(env.getProperty("spring.http.multipart.max-file-size"));
        } catch (Exception e) {}
        resolver.setMaxInMemorySize(100000000);
        resolver.setMaxUploadSize(maxFileSize);
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public ThemeSource themeSource() {
        ResourceBundleThemeSource resourceBundleThemeSource = new ResourceBundleThemeSource();
        resourceBundleThemeSource.setBasenamePrefix("theme/theme-");
        resourceBundleThemeSource.setDefaultEncoding("UTF-8");

        return resourceBundleThemeSource;
    }

    @Bean
    public ThemeResolver themeResolver() {
        SessionThemeResolver sessionThemeResolver = new SessionThemeResolver();
        sessionThemeResolver.setDefaultThemeName(theme);

        return sessionThemeResolver;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public ThemeChangeInterceptor themeChangeInterceptor() {
        ThemeChangeInterceptor themeChangeInterceptor = new ThemeChangeInterceptor();
        themeChangeInterceptor.setParamName("theme");

        return themeChangeInterceptor;
    } 

	@Bean
	public FilterRegistrationBean requestLoggingFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new RequestLoggingFilter(menuAccessLoggingEnable));
		filterRegistrationBean.setOrder(-1);
		
		Collection<String> urlPattrens = new ArrayList<>();
		urlPattrens.add("/portal/main");
		urlPattrens.add("/admin/main");
		urlPattrens.add("/system/main");
		urlPattrens.add("/portal/view");
		urlPattrens.add("/admin/view");
		urlPattrens.add("/system/view");
		urlPattrens.add("/board/list");
		filterRegistrationBean.setUrlPatterns(urlPattrens);
		return filterRegistrationBean;
	}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/401").setViewName("redirect:/error/401");
//        registry.addViewController("/403").setViewName("redirect:/error/403");
//        registry.addViewController("/404").setViewName("redirect:/error/404");
//        registry.addViewController("/500").setViewName("redirect:/error/500");
        registry.addViewController("/").setViewName("redirect:/portal/main");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(themeChangeInterceptor());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        super.configureDefaultServletHandling(configurer);
        configurer.enable();
    }

    @Bean
    public Validator getValidator() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/ValidationMessages");
        messageSource.setFallbackToSystemLocale(false);
        
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        
        return factory;
    }
    
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
    	CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
    	loggingFilter.setIncludeClientInfo(true);
    	loggingFilter.setIncludeQueryString(true);
    	loggingFilter.setIncludePayload(true);
    	
    	return loggingFilter;
    }
    
    /*@Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
        registry.enableContentNegotiation(
                new XlsView(),
                new XlsxView(),
                new XlsxStreamingView());
    }
    
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
        .defaultContentType(MediaType.TEXT_HTML)
        .parameterName("type")
        .favorParameter(true)
        .ignoreUnknownPathExtensions(false)
        .ignoreAcceptHeader(false)
        .useJaf(true);
    }*/
}
