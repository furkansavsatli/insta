package com.insta.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insta.controller.ImageController;
import com.insta.controller.UserController;
import com.insta.model.AppUser;
import com.insta.model.AppUserRole;
import com.insta.model.Image;
import com.insta.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@WithMockUser(username = "admin", authorities = {"ROLE_CLIENT"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImageControllerTest {

    @MockBean
    UserController userController;
    @Autowired
    ImageController imageController;
    @Autowired
    UserService userService;
    MockMvc mockMvc;

    @BeforeAll
    public void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        AppUser admin = new AppUser();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setAppUserRoles(new ArrayList<>(Collections.singletonList(AppUserRole.ROLE_CLIENT)));

        userService.signup(admin);
    }

    @Test
    public void testImageWrongFormat() throws Exception {

        MockMultipartFile jsonFile = new MockMultipartFile("file", "", "application/json", "{\"json\": \"someValue\"}".getBytes());
        mockMvc.perform(multipart("/image").file(jsonFile).accept(MediaType.ALL))
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    public void testLoadListImage() throws Exception {

        MockMultipartFile imageFile = new MockMultipartFile("file", "", "image/jpeg", "{\"json\": \"someValue\"}".getBytes());
        mockMvc.perform(multipart("/image").file(imageFile).accept(MediaType.ALL))
                .andExpect(status().isOk()).andReturn();

        String contentAsString = mockMvc.perform(get("/image").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<Image> list = Arrays.asList((new ObjectMapper()).readValue(contentAsString, Image[].class));
        Assertions.assertEquals(1, list.size());

        String contentAsString2 = mockMvc.perform(get("/image/{imageId}", list.get(0).getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Image image = (new ObjectMapper()).readValue(contentAsString2, Image.class);
        Assertions.assertEquals(1, image.getId());
    }

}
