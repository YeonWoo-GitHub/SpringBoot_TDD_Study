package com.test.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.test.demo.dto.userDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
//import static org.mockito.Mockito.mock;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class controllerTest {

    @InjectMocks
    private userController userController;

    @Mock
    private userService userService;

    // @Autowired
    // private MockMvc mockMvc;

    // user sample
    private static String testUserId;
    private static String testUserName = "testName";

    // @Test
    // public void ListTest_return_apple() {
    //
    // // given
    // List mockList = getList_apple();
    //// given(mockList).willReturn(getList_apple());
    //
    // // when
    // String result = mockList.get(0).toString();
    //
    // // then
    // assertEquals("apple", result);
    // }
    //
    // private List<String> getList_apple() {
    //
    // List<String> result = new ArrayList<>();
    //
    // result.add("apple");
    //
    // return result;
    // }

    // 아래부터는 mvc패턴을 사용한 실제 웹 클래스들의 테스트이다.
    // 컨트롤러단에서는 service밖에 호출을 하지 않기 때문에, dao 목업은 필요가 없다.
    @Test
    @Order(1)
    @DisplayName("join 테스트")
    public void joinTest() {

        // given
        String testId = "a";
        String testName = testUserName;
        userDto reqDto = userDto
                .builder()
                .id(testId)
                .userName(null)
                .build();

        given(userService.join(any(userDto.class))).willReturn(testId); // testId가 리턴되야 할 것이다~ / any(userDto.class) ,
                                                                        // userService.join() 이라는 애가 이렇게 동작할 것이다~
                                                                        // userService는 Mock 객체인 상태이므로! 빈깡통임

        // when
        String result = userController.join(reqDto); // userController.join 안에서 userService.join을 호출하므로

        // then
        then(userService).should().join(reqDto); // then 에서 userService.join을 반드시 호출하는지 확인(실제로 호출했는지 검증하는 부분)
                                                 // 함수 로직에서 실제로 userService.join() 호출해야만 한다, 만약 호출 안했을 경우 fail 리턴

        assertThat(result).isEqualTo(testId); // userController.join() API 자체에 대한 response 확인이며, response가 testId랑 동일한지
                                              // 확인

        testUserId = result; // order에 의해서 다음 테스트에 사용할 수 있도록 static id 저장
    }

    @Test
    @Order(2)
    @DisplayName("findUser 테스트")
    public void findUserTest() {

        // given
        String id = testUserId;
        String getUser = testUserName;
        String result;

        given(userService.findUser(any(String.class))).willReturn(getUser); //

        // when
        result = userController.findUser(id);

        // then
        then(userService).should().findUser(id); // 호출 여부 검사
        assertThat(result).isEqualTo(testUserName); // 값 검사

    }

}
