package com.adobe.aem.spark.project.core.models;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TeamMemberModel {
    
    @Inject
    private List<TeamMemberListModel> teamList ;

    public List<TeamMemberListModel> getTeamList() {
        return teamList;
    }

}
