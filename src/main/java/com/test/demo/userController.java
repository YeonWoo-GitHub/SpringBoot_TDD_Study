package com.test.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class userController {

    private final userService userService;

    // 회원가입을 할 수 있는 API...
    @PostMapping("/join")
    public String join(@RequestBody userDto userInfo) {

        String userId = userService.join(userInfo);

        return userId;

    }

    // 회원가입된 유저중에 특정 유저 찾음
    @GetMapping("/find")
    public String findUser(@RequestParam String id) {

        String result = userService.findUser(id);

        if (result == null) {

            return "error";

        }

        return result;
    }

    // 회원가입된 유저중에 특정 유저 삭제
    @PostMapping("/delete")
    public Boolean deleteUser(@RequestBody String id) {

        if (null == id) {

            return false;
        }

        if (null == userService.findUser(id)) {

            return false;
        }

        Boolean result = userService.deleteUser(id);

        return result;
    }

    // 간단히 컨트롤러의 호출 여부를 검사할 수 있는 테스트용 APi입니다.
    @GetMapping("/test")
    public ResponseEntity getTestUserName(@RequestParam String id) {

        if (id.equals("A")) {
            return new ResponseEntity<>("testName", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
