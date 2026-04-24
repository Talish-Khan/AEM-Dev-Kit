package com.adobe.aem.spark.project.core.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = SparkUtil.class)
public class SparkUtil {
    
    @Reference
    private ResourceResolverFactory resolverFactory;

    private static final String SUBUSER_NAME = "talish" ;

    public ResourceResolver getServiceReoResourceResolver(){

        ResourceResolver resolver = null;

        try {
            
            Map<String , Object> param = new HashMap<>();
            param.put(ResourceResolverFactory.SUBSERVICE, SUBUSER_NAME);

             resolver = resolverFactory.getServiceResourceResolver(param);


        } catch (Exception e) {
            // TODO: handle exception
        }

        return resolver;

    }


}
