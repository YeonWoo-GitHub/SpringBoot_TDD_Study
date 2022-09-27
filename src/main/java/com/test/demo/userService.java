package com.test.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.test.demo.dto.userDto;
import com.test.demo.dto.userPatchDto;
import com.test.demo.dto.userSaveDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class userService {

    // repository로 선언된 userDao 사용을 위함
    private final userDao userDao;

    // 유저 찾기
    public userDto findUser(String userId) {

        userDao.outDatabase();

        // userDao.search 이용하여 유저 검색
        userDto result = userDao.search(userId);

        log.info("Find request, find user information : {}", result);

        // 검색 결과가 없을 시
        if (result == null) {

            // 에러 반환
            throw new NoSuchElementException("not found user");
        }

        // 검색에 성공하여 검색한 유저 정보 반환
        return result;

    }

    // 회원가입
    public String join(userSaveDto req) {

        log.info("Join request, user information : {}", req);

        // 요청이 들어온 아이디 값으로 유저 먼저 검색
        userDto existUser = userDao.search(req.getId());

        // 해당 아이디를 가진 유저가 이미 존재하기에 회원가입 진행 불가능
        if (null != existUser) {

            log.error("userId [{}] is already exist, Don't save !", req.getId());

            return null;
        }

        // userSaveDto 의 static 메소드로 선언된 convert() 를 통해서 userSaveDto -> userDto(entity)
        userDto entity = userSaveDto.convertDto(req);

        // userDao의 save 함수 실행
        return userDao.save(entity);
    }

    // 로그인
    public userDto login(String userId, String password) {

        // 유저 검증을 위해 userDao.get 이용하여 유저 검색
        userDto user = userDao.search(userId);

        // 검색된 유저가 없다면 에러 반환
        if (null == user) {

            throw new NoSuchElementException("not found user");
        }

        // 유저 검색에 성공, 비밀번호 검증을 위해서 userDb의 정보와 입력한 password 동일한 지 검증
        if (userDao.validPassword(userId, password)) {

            // 검증에 성공, 로그인 되었다고 가정하고 로그인된 유저의 정보 반환
            return user;
        } else {

            // 검증 실패, 에러 반환
            throw new InputMismatchException("Invalid password");
        }
    }

    // 유저 정보 업데이트
    public userDto update(userPatchDto updateReq) {

        userDto entity = userPatchDto.convertDto(updateReq);

        // userDao.update 메소드
        userDto result = userDao.update(entity);

        // 업데이트 이후 userDb List 상태 표시
        userDao.outDatabase();

        // 업데이트 된 정보 반환
        return result;
    }

    // 유저 정보 삭제
    public userDto deleteUser(String userId) {

        // userDao.delete
        userDto result = userDao.delete(userId);

        // 유저 삭제 이후 userDb List 상태 표시
        userDao.outDatabase();

        // 삭제한 유저 정보 반환
        return result;
    }

}
