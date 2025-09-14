package com.adobe.aem.spark.project.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.spark.project.core.testcontext.AppAemContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class TeamMemberListModelTest {


    private final AemContext context = AppAemContext.newAemContext();

    private Resource resource;

    @BeforeEach
    void setup() {
        context.create().resource("/content/team-member",
            "name", "Talish",
            "role", "Developer",
            "link", "https://example.com/talish",
            "imgPath", "/content/dam/team/talish.jpg"
        );
        resource = context.resourceResolver().getResource("/content/team-member");
        context.currentResource(resource);
    }

    @Test
    void testGetImgPath() {
        TeamMemberListModel teamMemberListModel = resource.adaptTo(TeamMemberListModel.class);
        assertEquals("/content/dam/team/talish.jpg", teamMemberListModel.getImgPath());
    }

    @Test
    void testGetLink() {
        TeamMemberListModel teamMemberListModel = resource.adaptTo(TeamMemberListModel.class);
        assertEquals("https://example.com/talish", teamMemberListModel.getLink());
    }

    @Test
    void testGetName() {
        TeamMemberListModel teamMemberListModel = resource.adaptTo(TeamMemberListModel.class);
        assertEquals("Talish", teamMemberListModel.getName());
    }

    @Test
    void testGetRole() {
        TeamMemberListModel teamMemberListModel = resource.adaptTo(TeamMemberListModel.class);
          assertEquals("Developer", teamMemberListModel.getRole());
    }
}
