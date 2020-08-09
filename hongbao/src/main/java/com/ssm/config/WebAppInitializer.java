package com.ssm.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Spring IoC 环境配置
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        //配置Ioc资源
        return new Class<?>[]{RootConfig.class};
    }

    /**
     * DispatcherServlet 环境配置
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    /**
     * 拦截请求配置
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"*.do"};
    }

    @Override
    protected void customizeRegistration(Dynamic registration) {
        //配置上传文件路径
        String filepath = "e:/WebProject/hongbao/file";

        //5MB
        Long singleMax = (long) Math.pow(2, 20) * 5;

        //10MB
        Long totalMax = (long) Math.pow(2, 20) * 10;

        //配置上传文件配置
        registration.setMultipartConfig(new MultipartConfigElement(filepath, singleMax, totalMax, 0));
    }
}
