package learn.springboot.oauth2webclient;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
@Slf4j
public class RequestInterceptor extends HandlerInterceptorAdapter implements WebMvcConfigurer {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        if (log.isDebugEnabled()) {
            logRequest(request);
            logResponse(response);
        }
        super.afterCompletion(request, response, handler, ex);
    }

    private void logResponse(HttpServletResponse response) {
        log.debug("============================response begin==========================================");
        log.debug("Status code  : {}", response.getStatus());
        log.debug("Status text  : {}", response.getHeaderNames());
        response.getHeaderNames().stream().peek(
            headerName -> log.debug("Response Header : {}", headerName + ":" + response.getHeader(headerName)));
        log.debug("=======================response end=================================================");
    }

    private void logRequest(HttpServletRequest request) {
        log.debug("===========================request begin================================================");
        log.debug("URI         : {}", request.getRequestURI());
        log.debug("Method      : {}", request.getMethod());
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.debug("Headers     : {}", headerName + ":" + request.getHeader(headerName));
        }
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            log.debug("Parameter: {}", parameterName + ":" + request.getParameter(parameterName));
        }
        log.debug("==========================request end================================================");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this);
    }
}
