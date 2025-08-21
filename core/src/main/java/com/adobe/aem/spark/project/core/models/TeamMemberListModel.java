package com.adobe.aem.spark.project.core.models;



import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(adaptables = Resource.class , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TeamMemberListModel {
    
    @ValueMapValue
    private String name;

    public String getName() {
        return name;
    }

    @ValueMapValue
    private String role;

    public String getRole() {
        return role;
    }

    @ValueMapValue
    private String link ;

    public String getLink() {
        return link;
    }

    @ValueMapValue
    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }


}
