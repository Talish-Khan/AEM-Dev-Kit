package com.adobe.aem.spark.project.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(service = Servlet.class)
@SlingServletResourceTypes(resourceTypes = { "spark/components/page" },
methods = {HttpConstants.METHOD_GET},
selectors = {"info"},
extensions = {"json"})
public class PageInfoServlet extends SlingSafeMethodsServlet {


    private static final ObjectMapper mapper = new ObjectMapper();


    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
            
      response.setContentType("text/json");

       Resource resource = request.getResource();

       if (resource == null) {
        
            response.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
            mapper.writeValue(response.getWriter(), Map.of("error","No Resource Found"));
       
            return;
        }    

        PageManager pageManager = request.getResourceResolver().adaptTo(PageManager.class);
        Page page = pageManager.getContainingPage(resource);
        
        if (page == null) {
            response.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
            mapper.writeValue(response.getWriter(), Map.of("error", "No page found for resource " + resource.getPath()));
            return;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("Path", page.getPath() + ".html");
        result.put("title", page.getTitle());
        result.put("lastModifiedDate", page.getLastModified());
        // result.put("template", page.getTemplate());

        List<Map<String, Object>> childPages = new ArrayList<>();
        Iterator<Page> children = page.listChildren();

        while (children.hasNext() ) {
             
            Page child = children.next();
             Map<String, Object> childInfo = new HashMap<>();
             childInfo.put("path", child.getPath() + ".html") ;
             childInfo.put("title", child.getTitle());
             childInfo.put("lastModifiedDate", child.getLastModified());
            
             childPages.add(childInfo);

        }
        result.put("ChildPages", childPages);

        mapper.writeValue(response.getWriter(), result);

    }


}
