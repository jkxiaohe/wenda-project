package com.nowcoder.service;

import org.springframework.stereotype.Service;

/**
 * Created by dell on 2017/5/24.
 */
@Service
public class WendaService {
    public String getMessage(int userId){
        return "Hello Message : " + String.valueOf(userId);
    }
}
