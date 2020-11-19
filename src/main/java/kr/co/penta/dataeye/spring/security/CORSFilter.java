package kr.co.penta.dataeye.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CORSFilter implements Filter {
    static Logger logger = LoggerFactory.getLogger(CORSFilter.class);

    private static final String HEADER_ORIGIN = "Origin";
    private static final String REQUEST_AUTH_PATH = "/login";

    private String[] allowOrigins;

    public CORSFilter() {
    }

    public CORSFilter(String allowOrigin) {
        this.allowOrigins = allowOrigin.split(",");
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String origin = httpServletRequest.getHeader(HEADER_ORIGIN);
        
        if (validateOrigin(origin)) {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET");
            httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization");
        } else {
            logger.info("Origin : {}", origin);    
        }

        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("isSSO") == null) {
            session.setAttribute("isSSO", "N");
        }

        chain.doFilter(request, httpServletResponse);
    }

    private boolean validateOrigin(final String origin) {
        if (origin != null) {
            for (int i = 0; i < this.allowOrigins.length; i++) {
                if (allowOrigins[i].equals(origin)) {
                    return true;
                }
            }
        }
        return false;
    }
}
