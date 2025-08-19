package com.adobe.aem.spark.project.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.Servlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(service = Servlet.class)
@SlingServletResourceTypes(resourceTypes = { "spark/components/page" },
extensions = {"json"},
methods = {HttpConstants.METHOD_GET}
)
public class ChildPagesServlet extends SlingAllMethodsServlet {
    

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Resource resource = request.getResource();
        ResourceResolver resolver = request.getResourceResolver();

        PageManager pageManager = resolver.adaptTo(PageManager.class);
        Page currentPage = pageManager.getContainingPage(resource);

        if (currentPage == null) {
            response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(response.getWriter(), Collections.singletonMap("error", "Invalid page resource"));
            return;
        }

        List<Map<String, String>> childPagesList = new ArrayList<>();
        collectChildPages(currentPage, childPagesList);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("totalCount", childPagesList.size());
        responseMap.put("childPages", childPagesList);

        mapper.writeValue(response.getWriter(), responseMap);
    }

    /**
     * Recursively collect all descendant pages.
     */
    private void collectChildPages(Page page, List<Map<String, String>> list) {
        Iterator<Page> children = page.listChildren();
        while (children.hasNext()) {
            Page child = children.next();
            Map<String, String> childMap = new HashMap<>();
            childMap.put("title", child.getTitle());
            childMap.put("path", child.getPath());
            list.add(childMap);

            // Recursive call to fetch deeper children
            collectChildPages(child, list);
        }
    }
}
