package com.adobe.aem.spark.project.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.adobe.aem.spark.project.core.services.SiteConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(service = Servlet.class)
@SlingServletPaths(value = { "/bin/siteDetails" })
public class SiteDetailServlet extends SlingSafeMethodsServlet{


    @Reference
     private transient SiteConfigService siteConfigService;

     private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
    
             response.setContentType("application/json");

            Map<String, Object> predicateMap = new HashMap<>();
            predicateMap.put("siteName", siteConfigService.getSiteName());
            predicateMap.put("email", siteConfigService.getEmail());
            predicateMap.put("itemsPerPage", siteConfigService.getItemPerPage());

            mapper.writeValue(response.getWriter(), predicateMap);

    }
    
}
