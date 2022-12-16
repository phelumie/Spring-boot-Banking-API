//package com.microfinanceBank.Customer.Config;
//
//
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
////@WebFilter(urlPatterns = "/**")
//@Order(1)
//public class XssFilter implements Filter {
//
//    public XssFilter() {
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        filterChain.doFilter(new XssRequestWrapper((HttpServletRequest) servletRequest),servletResponse);
//
//    }
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//}
