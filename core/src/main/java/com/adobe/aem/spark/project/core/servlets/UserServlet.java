package com.adobe.aem.spark.project.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.aem.spark.project.core.services.UserService;

@Component(service = Servlet.class)
@SlingServletPaths(value = { "/bin/users"})
public class UserServlet extends SlingSafeMethodsServlet {

    @Reference
    private UserService userService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        
                response.setContentType("application/json");
                String user = userService.getUsers();
                response.getWriter().write(user);
    }


    
}
