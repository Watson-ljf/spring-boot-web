/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.watson.core.config;

import com.google.common.collect.Lists;
import com.watson.core.auth.EtpCache;
import com.watson.core.auth.UserRealm;
import com.watson.core.filter.CustomJacksonMessageConverter;
import com.watson.core.filter.LoggingFilter;
import com.watson.core.filter.wrapper.WrapperRequestFilter;
import com.watson.core.interceptor.ExecuteTimeInterceptor;
import com.wf.captcha.servlet.CaptchaServlet;
import com.wf.etp.authz.ApiInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.persistence.Basic;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射swagger静态资源
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        // 业务静态资源
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        // 系统静态资源映射
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册接口耗时统计拦截器
        registry.addInterceptor(applicationContext.getBean(ExecuteTimeInterceptor.class)).addPathPatterns("/**");
        // 注册token拦截器配置
        registry.addInterceptor(applicationContext.getBean(ApiInterceptor.class)).addPathPatterns("/api/**").excludePathPatterns("/api/login");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new CustomJacksonMessageConverter());
    }

    @Bean
    public FilterRegistrationBean requestFilterRegistrationBean() {
        FilterRegistrationBean<WrapperRequestFilter> registration = new FilterRegistrationBean<>();
        WrapperRequestFilter filter = new WrapperRequestFilter();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setName("requestFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean encodeFilterRegistrationBean() {
        FilterRegistrationBean<CharacterEncodingFilter> registration = new FilterRegistrationBean<>();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        registration.setFilter(characterEncodingFilter);
        registration.addUrlPatterns("/*");
        registration.setName("encodeFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean
    public FilterRegistrationBean loggingFilterRegistrationBean() {
        FilterRegistrationBean<LoggingFilter> registration = new FilterRegistrationBean<>();
        LoggingFilter loggingFilter = new LoggingFilter();
        registration.setFilter(loggingFilter);
        registration.addUrlPatterns("/*");
        registration.setName("loggingFilter");
        registration.setOrder(3);
        return registration;
    }

    @Bean()
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new CaptchaServlet(), "/image/captcha");
    }

    // token拦截器Bean
    @Bean
    public ApiInterceptor ApiInterceptorBean() {
        ApiInterceptor apiInterceptor = new ApiInterceptor();
        apiInterceptor.setUserRealm(applicationContext.getBean(UserRealm.class));
        apiInterceptor.setCache(applicationContext.getBean(EtpCache.class));
        return apiInterceptor;
    }

    // 兼容put请求
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilterBean() {
        return new HiddenHttpMethodFilter();
    }
}
