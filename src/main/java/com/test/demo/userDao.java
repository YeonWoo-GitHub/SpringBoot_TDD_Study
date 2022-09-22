package com.test.demo;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class userDao {

    private static Map<String, String> userDb = new HashMap<>();

    public String insert(String key, String value) {

        userDb.put(key, value);

        System.out.println("key : " + key);
        System.out.println("value : " + value);

        return key;

    }

    public String select(String key) {

        System.out.println(userDb);

        return userDb.get(key);

    }

    public Boolean delete(String id) {

        String ret = userDb.remove(id); // 값이 없으면 null 리턴

        if (null == ret) {

            return false;
        }

        return true;
    }

}
