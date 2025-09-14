package com.adobe.aem.spark.project.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.spark.project.core.testcontext.AppAemContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class TeamMemberModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @BeforeEach
    void setup() {
        // Create parent resource
        context.create().page("/content/team");

        // Create child resources under /content/team/teamList
        context.create().resource("/content/team/teamList/member1",
            "name", "Talish",
            "role", "Developer",
            "link", "https://example.com/talish",
            "imgPath", "/content/dam/team/talish.jpg"
        );

        context.create().resource("/content/team/teamList/member2",
            "name", "Aarav",
            "role", "Designer",
            "link", "https://example.com/aarav",
            "imgPath", "/content/dam/team/aarav.jpg"
        );

        // Set current resource to parent
        context.currentResource("/content/team");
    }


    @Test
    void testGetTeamList() {

        TeamMemberModel model =  context.currentResource().adaptTo(TeamMemberModel.class);
         assertNotNull(model, "Model should not be null");

        assertNotNull(model.getTeamList(), "Team list should be injected");
        assertEquals(2, model.getTeamList().size(), "Team list should contain 2 members");

    }
}
