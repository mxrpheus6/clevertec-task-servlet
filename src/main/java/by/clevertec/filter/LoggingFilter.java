package by.clevertec.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@WebFilter("/*")
public class LoggingFilter implements Filter {
    private static final Logger logger = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String method = httpRequest.getMethod();
        String requestURL = httpRequest.getRequestURL().toString();
        String clientIP = request.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");
        String queryString = httpRequest.getQueryString();
        Date requestTime = new Date();

        logger.info("Request received: ");
        logger.info("  Method: " + method);
        logger.info("  URL: " + requestURL);
        logger.info("  Query: " + (queryString == null ? "N/A" : queryString));
        logger.info("  Client IP: " + clientIP);
        logger.info("  User-Agent: " + userAgent);
        logger.info("  Time: " + requestTime);

        chain.doFilter(request, response);

        logger.info("Response sent to: " + clientIP);
    }

    @Override
    public void destroy() {}
}
