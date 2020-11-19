package kr.co.penta.dataeye;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;

import kr.co.penta.dataeye.spring.web.SessionListener;

@SpringBootApplication(scanBasePackages="kr.co.penta.dataeye")
@EnableAsync
@EnableScheduling
public class DataeyeApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DataeyeApplication.class, args);
    }

    @Bean
    public HttpSessionListener httpSessionListener(){
      return new SessionListener();
    }
   
      
    @Override
	protected WebApplicationContext run(SpringApplication application) {
//		application.getSources().remove(ErrorPageFilter.class);
    	return super.run(application);
	}

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DataeyeApplication.class);
    }
    
//    @Bean
//    public ServletContextInitializer initializer() {
//        return new ServletContextInitializer() {
//            @Override
//            public void onStartup(ServletContext servletContext) throws ServletException {
//                servletContext.setInitParameter("coherence-web-sessions-enabled", "true");
//                servletContext.setInitParameter("coherence-sessioncollection-class", "com.tangosol.coherence.servlet.SplitHttpSessionCollection");
//                servletContext.setInitParameter("coherence-servletcontext-clustered", "true");
//                servletContext.setInitParameter("coherence-enable-sessioncontext", "true");
//                servletContext.setInitParameter("coherence-scopecontroller-class", "com.tangosol.coherence.servlet.AbstractHttpSessionCollection$GlobalScopeController");
//            }
//        };
//    }
	
}
