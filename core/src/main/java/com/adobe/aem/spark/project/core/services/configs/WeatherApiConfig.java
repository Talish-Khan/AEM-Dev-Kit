package com.adobe.aem.spark.project.core.services.configs;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Weather API Configuration", description = "Configuration for Weather API")
public @interface WeatherApiConfig {

    @AttributeDefinition(name = "API Key", description = "OpenWeatherMap API Key")
    String apiKey() default "";

}
