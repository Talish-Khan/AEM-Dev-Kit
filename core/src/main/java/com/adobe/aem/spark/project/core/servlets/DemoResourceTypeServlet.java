package com.adobe.aem.spark.project.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import com.day.cq.commons.jcr.JcrConstants;

@Component(service = Servlet.class)
@SlingServletResourceTypes(resourceTypes = { "spark/components/page" },
selectors = {"weather"},
extensions = {"json"},
methods = {HttpConstants.METHOD_GET}
)
public class DemoResourceTypeServlet extends SlingSafeMethodsServlet{

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        final Resource resource=request.getResource();

        response.setContentType("text/plain");

        response.getWriter().write("Title Of the Page :"+ resource.getValueMap().get(JcrConstants.JCR_TITLE));;
    }
    
}
