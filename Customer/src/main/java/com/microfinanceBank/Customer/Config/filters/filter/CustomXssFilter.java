package com.microfinanceBank.Customer.Config.filters.filter;


import com.microfinanceBank.Customer.Config.filters.httpwrapper.CaptureRequestWrapper;
import com.microfinanceBank.Customer.Config.filters.service.RansackXss;
import com.microfinanceBank.Customer.Config.filters.service.ServletRequestXssFilterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ConditionalOnReactiveDiscoveryEnabled;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Order(1)
public class CustomXssFilter implements Filter {

  private RansackXss ransackXss;
  private  ServletRequestXssFilterManager servletRequestXssFilterManager;

  public CustomXssFilter() {
  }

  public CustomXssFilter(RansackXss ransackXss,
                         ServletRequestXssFilterManager servletRequestXssFilterManager) {
    this.ransackXss = ransackXss;
    this.servletRequestXssFilterManager = servletRequestXssFilterManager;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {
    filterChain.doFilter(new CaptureRequestWrapper((HttpServletRequest) servletRequest, ransackXss,
            servletRequestXssFilterManager),
        servletResponse);
  }

}
