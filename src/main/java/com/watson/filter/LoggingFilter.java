package com.watson.filter;

import ch.qos.logback.classic.Level;
import com.watson.utils.IpUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class LoggingFilter implements Filter {
    private static Logger logger= LoggerFactory.getLogger(LoggingFilter.class);

    private Set<String> errorLevelUriSet=new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        errorLevelUriSet.add("/test/bootstarp");
        errorLevelUriSet.add("/favicon.ico");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
        setLoggingLevel(req);
        StringBuilder sb=new StringBuilder("\n");
        sb.append("requestIPAddress:").append(IpUtils.getRemoteHost(req)).append("\n");
        sb.append("url:").append(req.getRequestURL()).append("\n");
        sb.append("method:").append(req.getMethod()).append("\n");
        sb.append("header:").append("\n");
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append("{\"").append(name).append("\":\"").append(req.getHeader(name)).append("\"}").append("\n");
        }
        String body= IOUtils.toString(request.getInputStream());
        if(!StringUtils.isEmpty(body))
            sb.append("body:").append(body).append("\n");
        sb.append("params:").append("\n");
        for(String name:request.getParameterMap().keySet())
            sb.append(name).append("=").append(request.getParameter(name)).append("\n");
        logger.warn(sb.toString());
        chain.doFilter(request,response);
        recoverLoggingLevel(req);
    }

    private void setLoggingLevel(HttpServletRequest request) {
        if(errorLevelUriSet.contains(request.getRequestURI()))
            ThreadLevelFilter.setLevel(Level.ERROR);
    }
    private void recoverLoggingLevel(HttpServletRequest request) {
        if(errorLevelUriSet.contains(request.getRequestURI()))
            ThreadLevelFilter.setLevel(null);
    }



    @Override
    public void destroy() {

    }
}
