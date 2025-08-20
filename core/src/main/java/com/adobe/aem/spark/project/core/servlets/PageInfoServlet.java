package com.adobe.aem.spark.project.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(service = Servlet.class)
@SlingServletResourceTypes(resourceTypes = { "spark/components/page" },
 methods = {HttpConstants.METHOD_GET},
 selectors = { "info" },
 extensions = { "json" })
public class PageInfoServlet extends SlingSafeMethodsServlet {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Reference
    private Externalizer externalizer;

    @Reference
    private SlingSettingsService slingSettingsService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Resource resource = request.getResource();

        ResourceResolver resolver = request.getResourceResolver();

        if (resource == null) {

            response.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
            mapper.writeValue(response.getWriter(), Map.of("error", "No Resource Found"));

            return;
        }

        PageManager pageManager = request.getResourceResolver().adaptTo(PageManager.class);
        Page page = pageManager.getContainingPage(resource);

        if (page == null) {
            response.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
            mapper.writeValue(response.getWriter(),
                    Map.of("error", "No page found for resource " + resource.getPath()));
            return;
        }

        Set<String> runModes = slingSettingsService.getRunModes();
        boolean isAuthor = runModes.contains("author");

         String relativeUrl = page.getPath() + ".html";

            String fullUrl;

            if (isAuthor) {
                fullUrl = externalizer.authorLink(resolver, relativeUrl) + "?wcmmode=disabled";
            } else {
                fullUrl = externalizer.publishLink(resolver, relativeUrl);
            }

        Map<String, Object> result = new HashMap<>();
        result.put("Path", page.getPath() + ".html");
        result.put("title", page.getTitle());
        result.put("lastModifiedDate", page.getLastModified());
        result.put("fullPath", fullUrl);
        // result.put("template", page.getTemplate());

        List<Map<String, Object>> childPages = new ArrayList<>();
        Iterator<Page> children = page.listChildren();

        while (children.hasNext()) {

            Page child = children.next();
           
            Map<String, Object> childInfo = new HashMap<>();
            childInfo.put("path", child.getPath() + ".html");
            childInfo.put("title", child.getTitle());
            childInfo.put("lastModifiedDate", child.getLastModified());
            childInfo.put("fullPath", fullUrl);

            childPages.add(childInfo);

        }
        result.put("ChildPages", childPages);

        mapper.writeValue(response.getWriter(), result);

    }

}
