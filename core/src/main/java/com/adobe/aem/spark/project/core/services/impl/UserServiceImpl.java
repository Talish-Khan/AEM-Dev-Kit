package com.adobe.aem.spark.project.core.services.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpResponse;

import com.adobe.aem.spark.project.core.services.UserService;
import com.adobe.aem.spark.project.core.services.configs.UserServiceConfig;

@Component(service = UserService.class, immediate = true)
@Designate(ocd = UserServiceConfig.class)
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserServiceConfig userServiceConfig;

    @Activate
    @Modified
    protected void activate(UserServiceConfig userServiceConfig){
        this.userServiceConfig = userServiceConfig;
    }

    // private static final String API_URL = "https://jsonplaceholder.typicode.com/users";

    @Override
    public String getUsers() {

        JSONArray filteredUsers = new JSONArray();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(userServiceConfig.API_URL());

            HttpResponse response = httpClient.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {

                HttpEntity entity = response.getEntity();

                if (entity != null) {

                    String result = EntityUtils.toString(entity);

                    JSONArray users = new JSONArray(result);

                    for (int i = 0; i < users.length(); i++) {

                        JSONObject user = users.getJSONObject(i);

                        JSONObject obj = new JSONObject();
                        obj.put("name", user.getString("name"));
                        obj.put("email", user.getString("email"));

                        filteredUsers.put(obj);
                    }
                }

            } else {
                LOG.error("API returned non-200 status: {}", statusCode);
            }

        } catch (IOException e) {
            LOG.error("IO Exception while calling API", e);
        } catch (Exception e) {
            LOG.error("Unexpected error", e);
        }

        return filteredUsers.toString();
    }
}
