package com.adobe.aem.spark.project.core.models;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;

import com.day.cq.wcm.api.Page;


@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ChildPagesListModel {
    

    @ScriptVariable
    private Page currentPage ;

    private List<Page> childPages;

    @PostConstruct
    protected void init() {
        childPages = new ArrayList<>();

        if (currentPage != null) {
            Iterator<Page> children = currentPage.listChildren();

            while (children.hasNext()) {
                childPages.add(children.next());
            }
        }
    }

    public List<Page> getChildPages() {
        return childPages;
    }

}
