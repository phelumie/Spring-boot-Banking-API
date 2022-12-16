package com.microfinanceBank.Customer.Config.filters.service;

import com.microfinanceBank.Customer.Config.filters.httpwrapper.CaptureRequestWrapper;

import javax.servlet.ServletInputStream;

/**
 * ServletInputStreamXssFilterProvider to get ServletInputStream based on supported content type
 *
 * @author Bhushan Uniyal
 * */
public interface ServletInputStreamXssFilterProvider {

   ServletInputStream getInputStream(CaptureRequestWrapper captureRequestWrapper,
                                     RansackXss ransackXss);

  boolean isSupportedContentType(String contentType);

}
