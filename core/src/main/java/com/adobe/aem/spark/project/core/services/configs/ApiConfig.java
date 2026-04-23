package com.adobe.aem.spark.project.core.services.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Api Configuration")
public @interface ApiConfig {

    @AttributeDefinition(name = "Provide APi URL" , type = AttributeType.STRING
    )
    String Api();
    
}
