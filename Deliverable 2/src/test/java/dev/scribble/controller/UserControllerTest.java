package dev.scribble.controller;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String expectedFirst = "John";
    private final String expectedLast = "Smith";

    // Generate random email and username to prevent conflicts caused by uniqueness constraints.
    private final String randomEmail = java.util.UUID.randomUUID() + "@test.com";
    private final String randomUsername = java.util.UUID.randomUUID().toString();


    @Test
    void createUser() {
        // Define a JSON string for the request body. In reality this will be a JWT, but for testing the
        // UserController unit a JSON string suffices.
        String newUserJson = """
                {
                    "firstName": "%s",
                    "lastName": "%s",
                    "email": "%s",
                    "username": "%s",
                    "passwordHash": "$argon2i$v=19$m=65536,t=2,p=4$c29tZXNhbHQ$FgNukdXUKj/gS6Ur+fgQ6laMZLTrvSKo"
                }
                """.formatted(expectedFirst, expectedLast, randomEmail, randomUsername);

        // Perform a POST request to the '/user/add' endpoint using mockMvc for testing
        // We could use the andExpect stream instead but this keeps to the JUnit assertions we learned
        String result = assertDoesNotThrow(() -> mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/add")
                        // Set the 'Content-Type' header to json and send the data
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andReturn().getResponse().getContentAsString());

        // Test the results
        assertDoesNotThrow(() -> testResponseHelper(result));
    }

    private void testResponseHelper(String response) throws JSONException {
        // Convert the string to JSON
        JSONObject r = new JSONObject(response);

        // Parse and test the data
        assertEquals(expectedFirst, r.getString("firstName"), "TESTING: user creation (firstName)");
        assertEquals(expectedLast, r.getString("lastName"), "TESTING: user creation (firstName)");
        assertEquals(randomEmail, r.getString("email"), "TESTING: user creation (firstName)");
    }
}
