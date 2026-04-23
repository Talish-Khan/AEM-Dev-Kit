package com.adobe.aem.spark.project.core.services.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "User Service Configuration" , description = "User Service Configuration")
public @interface UserServiceConfig {

    @AttributeDefinition(name = "API URL" , description = "Provide API URL")
    String API_URL ();
    
}
