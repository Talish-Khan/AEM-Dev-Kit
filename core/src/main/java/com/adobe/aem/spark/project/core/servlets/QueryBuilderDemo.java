package com.adobe.aem.spark.project.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(service = Servlet.class)
@SlingServletResourceTypes(resourceTypes = { "spark/components/page" },
selectors = {"query"},
extensions = {"json"})
public class QueryBuilderDemo extends SlingSafeMethodsServlet {

    @Reference
    private QueryBuilder queryBuilder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Session session = null;

        try {

            session = request.getResourceResolver().adaptTo(Session.class);
            // Build Query Parameters
            Map<String, String> map = new HashMap<>();
            map.put("path", "/content"); // search under /content
            map.put("type", "cq:Page");  // get only cq:Page nodes
            map.put("p.limit", "5");
            map.put("p.offset", "0");    // limit results

            // Create query
             Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);

             SearchResult result = query.getResult();
          long totalMatches = result.getTotalMatches(); // total count of results
            List<Hit> hits = result.getHits();

            // Prepare JSON structure
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("totalCount", totalMatches);

            List<Map<String, String>> pages = new ArrayList<>();
            for (Hit hit : hits) {
                Map<String, String> pageData = new HashMap<>();
                pageData.put("path", hit.getPath());
                pages.add(pageData);
            }
            jsonResponse.put("pages", pages);

            // Write JSON using Jackson
            objectMapper.writeValue(response.getWriter(), jsonResponse);


        } catch (Exception e) {
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    
    }
    

}
