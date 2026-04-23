package com.adobe.aem.spark.project.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.spark.project.core.services.ApiService;

@Component(service = Servlet.class)
@SlingServletPaths(value = "/bin/api")
public class ApiServlet extends SlingAllMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(ApiServlet.class);

    @Reference
    ApiService apiService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

            log.info("Do get method executed");

            response.setContentType("application/json");

            String api = apiService.getAPi();

            response.getWriter().write(api);

    }


    
}
