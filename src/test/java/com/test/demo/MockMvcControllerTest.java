package com.test.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc // 가상 컨테이너 생성( 테스트 위해 실제로 프로젝트를 실행시키긴 힘드니까.. )
public class MockMvcControllerTest {

    @Autowired
    private MockMvc mockMvc; // Bean 객체를 주입받아서 씀!

    @Test
    @DisplayName("mockmvc example")
    public void MockMvcExample() throws Exception {

        // given
        String reqId = "A";

        mockMvc.perform(get("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", reqId))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @Order(1)
    @DisplayName("mockmvc join test")
    public void MockMvcTestJoin() throws Exception {

        // given
        String content = "{\"id\": \"hyejeong.go\", \"userName\": \"혜정\"}";

        // when
        mockMvc.perform(post("/join") // post 주소. userController.join에서 @PostMapping("/join") 이라고 명시되어 있으니까 자동으로
                                      // userController.join() API 찾아서 동작시킴.
                .contentType(MediaType.APPLICATION_JSON) // JSON 형식으로
                .content(content)) // content 내용을 넣을거임(JSON 형식으로 쓴 것)
                .andExpect(status().isOk())
                .andExpect(content().string("hyejeong.go"))
                .andDo(print());

    }

    @Test
    @Order(2)
    @DisplayName("mockmvc find test")
    public void MockMvcTestFindUser() throws Exception {

        // given
        String id = "hyejeong.go";
        String resultName = "혜정";

        // when
        mockMvc.perform(get("/find")
                .param("id", id))
                .andExpect(status().isOk())
                .andExpect(content().string(resultName))
                .andDo(print());

    }
}
