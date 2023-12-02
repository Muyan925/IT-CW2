package com.example.easystudy.Util;

import com.example.easystudy.pojo.UserDto;

public class UserThreadLocal {
    public static ThreadLocal<UserDto> userDtoThreadLocals = new ThreadLocal<>();
    public static void set(UserDto userDto)
    {
        userDtoThreadLocals.set(userDto);
    }
    public static UserDto get()
    {
        return  userDtoThreadLocals.get();
    }
    public static void remove()

    {
        userDtoThreadLocals.remove();
    }
}
