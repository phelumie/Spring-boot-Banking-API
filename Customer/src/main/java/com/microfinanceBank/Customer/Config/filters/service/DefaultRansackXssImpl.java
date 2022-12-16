package com.microfinanceBank.Customer.Config.filters.service;

import java.util.regex.Pattern;

import static com.microfinanceBank.Customer.Config.filters.util.ConstantUtil.FILTER_PATTERNS;
import static org.apache.commons.lang.StringUtils.EMPTY;

/**
 * Default implementation of {@link RansackXss}, You can override this implementation, create your
 * custom  implementation of RansackXss and mask as a bean
 *
 * @author Bhushan Uniyal.
 */
public class DefaultRansackXssImpl implements RansackXss {

  @Override
  public String ransack(String value) {
    if (value != null) {
      for (Pattern pattern : FILTER_PATTERNS) {
        value = pattern.matcher(value).replaceAll(EMPTY);
      }
    }
    return value;
  }

}
