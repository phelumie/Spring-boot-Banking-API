package com.microfinanceBank.Customer.Config.filters.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Use this annotation on your controller methods/actions where you wish to filter Cross-site
 * scripting. It will remove all xss from request parameter.</p>
 *
 * @author Bhushan Uniyal.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface XssFilter {

}