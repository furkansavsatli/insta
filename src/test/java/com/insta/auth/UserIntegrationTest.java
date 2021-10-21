package com.insta.auth;

import com.insta.controller.ImageController;
import com.insta.model.AppUser;
import com.insta.model.AppUserRole;
import com.insta.security.JwtTokenProvider;
import com.insta.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
public class UserIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserService userService;
    @MockBean
    ImageController imageController;

    @BeforeEach
    public void setUp() {

        AppUser admin = new AppUser();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setAppUserRoles(new ArrayList<>(Collections.singletonList(AppUserRole.ROLE_CLIENT)));

        userService.signup(admin);
    }

    @Test
    public void testAuth() throws Exception {
        String token = jwtTokenProvider.createToken("admin", Collections.singletonList(AppUserRole.ROLE_CLIENT));
        Assertions.assertNotNull(token);
        mockMvc.perform(get("/image")).andExpect(status().isForbidden());
        mockMvc.perform(delete("/image").requestAttr("content", 1).header("Authorization", token)).andExpect(status().isBadRequest());
        mockMvc.perform(get("/tweet").header("Authorization", token)).andExpect(status().isNotFound());
        mockMvc.perform(get("/image").header("Authorization", token)).andExpect(status().isOk());
    }
}
