package com.efimov.orchestrator.controller;

import com.efimov.orchestrator.domain.entity.Chat;
import com.efimov.orchestrator.domain.entity.User;
import com.efimov.orchestrator.repository.UserRepository;
import com.efimov.orchestrator.service.ChatOperationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.Charset;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MessageOrchestratorControllerTest {
    public static final String ADMIN = "admin";
    public static final String CHAT = "chat";
    public static final String AGE = "age";
    public static final String URL_TEMPLATE = "/chat/{chat}";
    public static final String URL_TEMPLATE1 = "/msg/chat";
    public static final String URL_TEMPLATE2 = "/token";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String TEXT_PLAIN_CHARSET_UTF_8 = "text/plain;charset=UTF-8";
    @Autowired
    private MockMvc mockMvc;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    private final UserRepository userRepository = mock(UserRepository.class);
    @MockBean
    private ChatOperationsService chatOperationsService;

    @SneakyThrows
    @Test
    void getAllCacheMessagesInChat() {
        User user1 = new User();
        user1.setLogin(ADMIN);
        user1.setPassword(ADMIN);
        Chat chat = new Chat();
        chat.setName(CHAT);
        user1.setAge(13);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(user1);

        ResultActions result =
                mockMvc.perform(post(URL_TEMPLATE2).contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                        .andExpect(status().isOk());
        Optional<User> user = Optional.of(user1);
        when(userRepository.findOneByLogin(anyString())).thenReturn(user);
        doNothing().when(chatOperationsService).createChat(anyString(), anyInt());
        String resultString = result.andReturn().getResponse().getContentAsString();
        mockMvc.perform(post(URL_TEMPLATE, CHAT).param(AGE, "12")
                .header(AUTHORIZATION, BEARER + resultString))
                .andExpect(status().isOk());


        mockMvc.perform(get(URL_TEMPLATE1)
                .header(AUTHORIZATION, BEARER + resultString))
                .andExpect(status().isOk()).andExpect(content().contentType(TEXT_PLAIN_CHARSET_UTF_8));


    }

}