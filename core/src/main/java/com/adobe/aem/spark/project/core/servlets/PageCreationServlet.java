package com.adobe.aem.spark.project.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.spark.project.core.services.SparkUtil;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Component(service = Servlet.class)
@SlingServletPaths(value = { "/bin/createPage" })
public class PageCreationServlet extends SlingAllMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(PageCreationServlet.class);

    @Reference
    SparkUtil sparkUtil;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        String pagePath = request.getParameter("pagePath");
        String templatePath = request.getParameter("templatePath");


        // ResourceResolver resolver = request.getResourceResolver();
        ResourceResolver resolver = sparkUtil.getServiceReoResourceResolver();
        PageManager pageManager = resolver.adaptTo(PageManager.class);

        try {
            // Extract parent path & page name
            String parentPath = pagePath.substring(0, pagePath.lastIndexOf("/"));
            String pageName = pagePath.substring(pagePath.lastIndexOf("/") + 1);

            // Create page
            Page page = pageManager.create(parentPath, pageName, templatePath, pageName);

            if (page != null) {
                resolver.commit();

                log.info("Page Created at path : " + page.getPath());

            } else {

                log.info("page Creation failed at path" + page.getPath());
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
