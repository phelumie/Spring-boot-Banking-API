//package com.microfinanceBank.Customer.Config.filters.config;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//import com.microfinanceBank.Customer.Config.filters.filter.CustomXssFilter;
//import com.microfinanceBank.Customer.Config.filters.service.*;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.stereotype.Component;
//
//@Component
//@DependsOn("xssFiltersConfiguration")
//public class XssFilterConfig {
//
//  private final XssFiltersConfiguration xssFiltersConfiguration;
//
//  public XssFilterConfig(XssFiltersConfiguration xssFiltersConfiguration) {
//    this.xssFiltersConfiguration = xssFiltersConfiguration;
//  }
//
//  @Bean
//  public FilterRegistrationBean<CustomXssFilter> xssFilterRegistration(RansackXss ransackXss,
//                                                                       ServletRequestXssFilterManager servletRequestXssFilterManager) {
//    FilterRegistrationBean<CustomXssFilter> registration = new FilterRegistrationBean<>();
//    registration.setFilter(new CustomXssFilter(ransackXss, servletRequestXssFilterManager));
//    Set<String> patters = xssFiltersConfiguration.xssMatches();
//    if (patters.size() > 0) {
//      registration.addUrlPatterns(patters.toArray(new String[0]));
//    } else {
//      registration.setEnabled(false);
//    }
//    return registration;
//  }
//
//  @Bean
//  @ConditionalOnMissingBean
//  public RansackXss ransackXss() {
//    return new DefaultRansackXssImpl();
//  }
//
//  @Bean
//  @ConditionalOnMissingBean
//  public ServletRequestXssFilterManager servletRequestXssManager(
//      List<ServletInputStreamXssFilterProvider> servletInputStreamXssFilterProviders) {
//    return new ServletRequestXssFilterManager(servletInputStreamXssFilterProviders);
//  }
//
//  @Bean("servletInputStreamXssProcessors")
//  @ConditionalOnMissingBean
//  public List<ServletInputStreamXssFilterProvider> servletInputStreamXssProcessors() {
//    ArrayList<ServletInputStreamXssFilterProvider> servletInputStreamXssFilterProviders = new ArrayList<>();
//    servletInputStreamXssFilterProviders.add(new ServletInputStreamXssJsonFilterProvider());
//    return servletInputStreamXssFilterProviders;
//  }
//
//
//}
