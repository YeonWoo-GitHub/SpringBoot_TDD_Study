package com.test.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.demo.dto.userDto;
import com.test.demo.dto.userPatchDto;
import com.test.demo.dto.userSaveDto;

@Slf4j
@RestController
@RequiredArgsConstructor
public class userController {

    private final userService userService;

    // 회원가입 API
    @PostMapping("/join")
    public String join(@RequestBody userSaveDto requestBody) {

        log.info("[API CALL] : [POST] /join");
        log.info("[REQUEST] : {}", requestBody);

        // userService의 join 메소드
        String userId = userService.join(requestBody);

        // userId가 null이 아니라면 회원가입 성공
        if (null != userId) {
            return "User " + userId + "is Successful join our app";
        } else {
            // null일 경우, 중복 아이디가 있어 회원가입 실패
            return "join fail. " + userId + "already exists";
        }

    }

    // 로그인
    @PostMapping("/login")
    public userDto login(@RequestParam String userId, @RequestParam String password) {

        log.info("[API CALL] : [POST] /login");
        log.info("[REQUEST] : {}", userId + " : " + password);

        // userSerivce의 login 메소드
        userDto result = userService.login(userId, password);

        return result;
    }

    // 회원가입된 유저중에 특정 유저 찾음
    @GetMapping("/find")
    public userDto findUser(@RequestParam String userId) {

        log.info("[API CALL] : [POST] /find");
        log.info("[REQUEST] : {}", userId);

        // userService의 findUser 메소드
        userDto result = userService.findUser(userId);

        return result;
    }

    // 회원가입된 유저 정보 삭제
    @PatchMapping("/update")
    public userDto updateUser(@RequestBody userPatchDto requestBody) {

        log.info("[API CALL] : [POST] /update");
        log.info("[REQUEST] : {}", requestBody);

        // userService의 update 메소드
        userDto result = userService.update(requestBody);

        return result;
    }

    // 회원가입된 유저중에 특정 유저 삭제
    @PostMapping("/delete")
    public userDto deleteUser(@RequestBody String userId) {

        log.info("[API CALL] : [POST] /delete");
        log.info("[REQUEST] : {}", userId);

        // userService의 delete 메소드
        userDto result = userService.deleteUser(userId);

        return result;
    }

    // 간단히 컨트롤러의 호출 여부를 검사할 수 있는 테스트용 APi입니다.
    // @GetMapping("/test")
    // public ResponseEntity getTestUserName(@RequestParam String id) {

    // if (id.equals("A")) {
    // return new ResponseEntity<>("testName", HttpStatus.OK);
    // } else {
    // return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    // }

    // }

}
