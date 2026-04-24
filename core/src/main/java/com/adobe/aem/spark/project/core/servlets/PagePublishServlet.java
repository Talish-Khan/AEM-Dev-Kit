package com.adobe.aem.spark.project.core.servlets;

import java.io.IOException;

import javax.jcr.Session;
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
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.Replicator;

@Component(service = Servlet.class)
@SlingServletPaths(value = "/bin/publish")
public class PagePublishServlet extends SlingAllMethodsServlet {

    private static final Logger log = LoggerFactory.getLogger(PagePublishServlet.class);

    @Reference
    private Replicator replicator;

    @Reference
    SparkUtil sparkUtil;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        String pagePath = request.getParameter("pagePath");

        ResourceResolver resolver = sparkUtil.getServiceReoResourceResolver();
        Session session = resolver.adaptTo(Session.class);

        try {

            replicator.replicate(session, ReplicationActionType.ACTIVATE, pagePath);

            log.info("page published sucessfully at path :" + pagePath);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}
