package com.adobe.aem.spark.project.core.services.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Site Config Service", description = "Configuration for Site")
public @interface SiteConfigConfigurations {
    
    @AttributeDefinition(name = "Site Name", description = "Enter the Name for Site")
    String siteName() default "";

    @AttributeDefinition(name = "Email Address", description = "Enter Site Email Address")
    String email() default "";

    @AttributeDefinition(name = "Items Per Page", description = "Enter Items Per Page")
    int itemsPerPage() ;
    

}

