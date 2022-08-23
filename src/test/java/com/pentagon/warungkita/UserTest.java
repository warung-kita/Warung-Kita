package com.pentagon.warungkita;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

//    @WithMockUser("/javatechie-1")
//    @Test
//    public void testSavePerson() throws Exception {
//
//        Person person = new Person(0, "user8", "CIVIL");
//        String jsonRequest = mapper.writeValueAsString(person);
//
//        MvcResult result = mockMvc
//                .perform(post("/savePerson").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andReturn();
//
//        assertEquals(200, result.getResponse().getStatus());
//
//    }


    @Test
    public void testgetPersons() throws Exception {

        MvcResult result = mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertEquals(200, result.getResponse().getStatus());

    }

}
