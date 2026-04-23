package com.adobe.aem.spark.project.core.services.impl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

import com.adobe.aem.spark.project.core.services.ApiService;
import com.adobe.aem.spark.project.core.services.configs.ApiConfig;

@Component(service = ApiService.class, immediate = true)
@Designate(ocd = ApiConfig.class)
public class ApiServiceImpl implements ApiService {

    private ApiConfig apiConfig;

    @Activate
    @Modified
    protected void activate(ApiConfig apiConfig){
      this.apiConfig = apiConfig;
    }

    @Override
    public String getAPi() {
        
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

        HttpGet request = new HttpGet(apiConfig.Api());
        
        try(CloseableHttpResponse response = httpClient.execute(request)){
            return EntityUtils.toString(response.getEntity());
        }
    
    } catch (Exception e) {

        return null;
    }
       
    }
    
}
