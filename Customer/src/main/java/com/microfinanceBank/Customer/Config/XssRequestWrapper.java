//package com.microfinanceBank.Customer.Config;
//
//import org.keycloak.common.util.HtmlUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//
//public class XssRequestWrapper extends HttpServletRequestWrapper {
//
//    public XssRequestWrapper(HttpServletRequest request) {
//        super(request);
//    }
//
//    @Override
//    public String[] getParameterValues(String parameter){
//        System.out.println("parameter "+parameter);
//        String[] values=super.getParameterValues(parameter);
//
//        if (values==null)
//            return new String[0];
//
//        int count=values.length;
//        String[] encodedValues=new String[count];
//
//        for (int i=0;i<count;i++)
//            encodedValues[i]=stripXSS(values[i]);
//
//        return encodedValues;
//    }
//
//    @Override
//    public String getParameter(String parameter){
//        System.out.println("getparameter "+parameter);
//        String value=super.getParameter(parameter);
//        return stripXSS(value);
//    }
//
//    @Override
//    public String getHeader(String name){
//        String value=super.getHeader(name);
//        if(value !=null)
//            return stripXSS(value);
//
//        return new String();
//    }
//    private String stripXSS(String value) {
//        System.out.println("stripXss "+value);
//        return HtmlUtils.escapeAttribute(value);
//    }
//
//}
