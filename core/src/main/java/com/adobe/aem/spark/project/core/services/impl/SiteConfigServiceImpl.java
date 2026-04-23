package com.adobe.aem.spark.project.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

import com.adobe.aem.spark.project.core.services.SiteConfigService;
import com.adobe.aem.spark.project.core.services.configs.SiteConfigConfigurations;


@Component(service = SiteConfigService.class, immediate = true ) 
@Designate(ocd = SiteConfigConfigurations.class)
public class SiteConfigServiceImpl implements SiteConfigService {

    private volatile SiteConfigConfigurations config;

    @Activate
    @Modified
    protected void activate(SiteConfigConfigurations config){
       this.config = config;
    }


    @Override
    public String getSiteName() {
        return config.siteName();
    }

    @Override
    public String getEmail() {
       return config.email();
    
    }

    @Override
    public int getItemPerPage() {
       return config.itemsPerPage();
    }
    
}
