package com.chunyue.springbootmall.controller;

import com.chunyue.springbootmall.dao.UserDao;
import com.chunyue.springbootmall.dto.UserLoginRequest;
import com.chunyue.springbootmall.dto.UserRegisterRequest;
import com.chunyue.springbootmall.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;

    //一.註冊功能

    //1. 註冊成功
    @Test
    public void register_success() throws Exception {

        String mockEmail = "testsuccess@gmail.com";

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail(mockEmail);
        userRegisterRequest.setPassword("P@ssw0rd");

        ObjectMapper objectMapper = new ObjectMapper();

        String userRegisterRequestJson =  objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRegisterRequestJson);


        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email",equalTo(mockEmail)))
                .andExpect(jsonPath("$.userId", notNullValue()))
                .andExpect(jsonPath("$.createdDate",notNullValue()));

        //檢查資料庫中的密碼不為明碼

        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        assertNotEquals(userRegisterRequest.getPassword(), user.getPassword());

    }

    @Test
    public void register_blankInput() throws Exception {


        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();

        ObjectMapper objectMapper = new ObjectMapper();

        String userRegisterRequestJson =  objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRegisterRequestJson);


        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void register_duplicatedEmail() throws Exception {

        String duplicatedEmail = "test9999@gmail.com";

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail(duplicatedEmail);
        userRegisterRequest.setPassword("P@ssw0rd");

        ObjectMapper objectMapper = new ObjectMapper();

        String userRegisterRequestJson =  objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRegisterRequestJson);

        //正常註冊一次
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(201));


        //同樣的參數再呼叫一次
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void login_success() throws Exception {

        //需先註冊一個此單元測試要用的帳號，（單元測試必須互相獨立，彼此之間不能有相依性）
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test999@gmail.com");
        userRegisterRequest.setPassword("P@ssw0rd123");

        register(userRegisterRequest);


        //嘗試登入
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(userRegisterRequest.getEmail());
        userLoginRequest.setPassword(userRegisterRequest.getPassword());

        ObjectMapper objectMapper = new ObjectMapper();
        String userLoginRequestJson = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userLoginRequestJson);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", notNullValue()));

    }

    @Test
    public void login_noEmail_fail() throws Exception {

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("notEsits@gmail.com");
        userLoginRequest.setPassword("P@ssw0rd123");


        ObjectMapper objectMapper = new ObjectMapper();
        String userLoginRequestJson = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userLoginRequestJson);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void login_wrongPassword_fail() throws Exception {

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test999@gmail.com");
        userRegisterRequest.setPassword("P@ssw0rd123");
        register(userRegisterRequest);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail(userRegisterRequest.getEmail());
        //測試密碼輸入錯誤
        userLoginRequest.setPassword("wrongPassword");

        ObjectMapper objectMapper = new ObjectMapper();
        String userLoginRequestJson = objectMapper.writeValueAsString(userLoginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userLoginRequestJson);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    private void register(UserRegisterRequest userRegisterRequest) throws Exception {


        ObjectMapper objectMapper = new ObjectMapper();

        String userRegisterRequestJson =  objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRegisterRequestJson);

        mockMvc.perform(requestBuilder);
    }
}