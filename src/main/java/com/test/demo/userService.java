package com.test.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class userService {

    private final userDao userDao;

    public String findUser(String id) {

        return userDao.select(id);

    }

    public String join(userDto userInfo) {

        String key = userInfo.getId();

        String value = userInfo.getUserName();

        return userDao.insert(key, value); // key 값 리턴됨

    }

    public Boolean deleteUser(String id) {

        Boolean ret = userDao.delete(id);

        if (false == ret) {
            return false;
        }

        return true;
    }

}
