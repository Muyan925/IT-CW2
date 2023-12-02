package com.example.easystudy.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.easystudy.Util.*;
import com.example.easystudy.dao.NoteDao;
import com.example.easystudy.dao.PlanDao;
import com.example.easystudy.dao.UserDao;
import com.example.easystudy.pojo.UserDto;
import com.example.easystudy.result.Result;
import com.example.easystudy.pojo.Note;
import com.example.easystudy.pojo.Plan;
import com.example.easystudy.pojo.User;
import com.example.easystudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 用户控制器
 * @author BB4U
 */
@RestController
@CrossOrigin
public class UserController {

    @Value("${baseUrl}")
    private String baseUrl;
    @Autowired
    UserDao userDao;

    NoteDao noteDao;

    PlanDao planDao;
    @Resource
    Captcha captcha;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Autowired
    public void setPlanDao(PlanDao planDao) {
        this.planDao = planDao;
    }
    @Autowired
    private TokenUtil tokenUtil;
    /**
     * 登陆
     * 通过测试
     * @param httpServletRequest
     * @return
     */
    @GetMapping ("/login")
    public Result login(HttpServletRequest httpServletRequest)
    {
        String userId = httpServletRequest.getParameter("userId");
        String userPassword = httpServletRequest.getParameter("userPassword");
        String code = httpServletRequest.getParameter("code").toLowerCase();
        UserDto userDto = new UserDto(userId);

        String token = "";
        //md5加密
        userPassword = DigestUtils.md5DigestAsHex(userPassword.getBytes());
//        System.out.println(userPassword);
        Result result = new Result();
        if (!code.equals(stringRedisTemplate.opsForValue().get("code")))
        {
            result.setMsg("error");
            result.setFlag(false);
            return  result;
        }
        if(!userId.equals("") && !userPassword.equals("")) {
            String rPassword = userDao.selectUser(userId);
            System.out.println(rPassword);
            System.out.println(userPassword);
            if (rPassword!=null) {
                if (rPassword.trim().equals(userPassword)) {
                    result.setFlag(true);
                    result.setMsg("successful！");
                    token = JwtTokenUtil.createToken(userDto);

                    Boolean flag= userService.setToken(token, userId);
                    if (!flag)
                    {
                        token = userService.getToken(userId);
                    }
                    Map<String,Object> map = new HashMap();
                    BeanUtil.copyProperties(userDao.SelectUserByUserId(userId),userDto);

                    map.put("token",token);
                    map.put("user",userDto);

                    result.setData(map);
                    stringRedisTemplate.delete("code");
                } else {
                    result.setFlag(false);
                    result.setMsg("Wrong password or account number！");
                }
            }else
            {
                result.setFlag(false);
                result.setMsg("Account does not exist！");
            }
        }else {
            result.setFlag(false);
            result.setMsg("Password or account number cannot be empty！");
        }
        return result;
    }

    /**
     * 注册
     * 测试通过
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result Register(@RequestBody User user)
    {
        Result result = new Result();
        System.out.println(user);
        user.setUserAddress("Tell me where you are");
        user.setUserImage(baseUrl + "/img/default.png");
        user.setUserGender("secrecy");
        user.setUserInfo("Introduce yourself");
        //md5加密
        String md5PassWord = DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes());
        user.setUserPassword(md5PassWord);

        if (user.getUserPassword().equals("") || user.getUserId().equals("")) {
            result.setFlag(false);
            result.setMsg("User information cannot be null");
        }
        else {
            if (userDao.selectUser(user.userId) != null) {
                result.setFlag(false);
                result.setMsg("Account already exists");
            } else {
                if (userDao.registerUser(user) != 0) {
                    result.setFlag(true);
                    result.setMsg("Successful account registration！");
                }
            }
        }

        System.out.println(result);
        return result;
    }

    //更新用户基础信息
    //Modify By：薛永淇
    //修改了接口名称

    /**
     * 更新用户基础信息
     * 测试通过
     * @param user
     * @return
     */
    @PostMapping("/user/UpdateUserBasicInfoByUid")
    public Result UpdateUserBasicInfoByUid(@RequestBody User user)
    {
        Result result = new Result();
        int number = userDao.updateUser(user);
        if (number!=0)
        {
            result.setFlag(true);
            result.setMsg("Successful modification of information！");
        }else
        {
            result.setFlag(false);
            result.setMsg("Failed to modify information！");
        }
        return result;
    }

    //更新用户头像
    //Modify By：薛永淇
    //修改了接口名称

    /**
     * 更新用户头像
     * 测试通过
     * @param multipartFile
     * @param uId
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/user/UpdateUserPhotoByUid/{uId}")
    public Result UpdateUserPhotoByUid(@RequestParam("photo") MultipartFile multipartFile,@PathVariable("uId") String uId,HttpServletRequest httpServletRequest )
    {
        String filePath = System.getProperty("user.dir") + "/img/";
        String filePath1 = baseUrl + "/img/";
        //获取图片文件后缀
        String suffixName  = multipartFile.getOriginalFilename();
        String newFileName = UUID.randomUUID()+suffixName;
        String newFilePath = filePath + newFileName;
        //相对路径
        String newFilePath1 = filePath1 + newFileName;
        Result result = new Result();
        try {
            //将传来的文件写入新建的文件
            multipartFile.transferTo(new File(newFilePath));
            int number = userDao.updateImage(Integer.valueOf(uId),newFilePath1);
            if (number==1)
            {
                result.setFlag(true);
                result.setMsg("Successfully updated avatar");
            }else
            {
                result.setFlag(false);
                result.setMsg("Failed to update avatar");
            }
        }catch (IllegalStateException | IOException e ) {
            //处理异常
            e.printStackTrace();
        }

        return result;

    }

    //根据uid查询用户信息
    //Modify By：薛永淇
    //修改了接口名称

    /**
     * 查询用户信息
     * 测试通过
     * @param uId
     * @return
     */
    @GetMapping("/user/GetUserInfoByUid/{uId}")
    public Result GetUserInfoByUid(@PathVariable("uId") int uId)
    {
        Result result = new Result();
        result.setMsg("Enquiry Successful！");
        result.setFlag(true);
        result.setData(userDao.selectUserinfo(uId));
        return result;
    }

    //根据uid修改用户密码
    //Modify By：薛永淇
    //修改了接口名称，修改了接收的参数的名称

    /**
     * 修改用户密码
     * 测试通过
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/user/UpdateUserPasswordByUid")
    public Result UpdateUserPasswordByUid(HttpServletRequest httpServletRequest)
    {
        Result result = new Result();
        String uId = httpServletRequest.getParameter("uId");
        String oldPassword = httpServletRequest.getParameter("oldPassword");
        String newPassword = httpServletRequest.getParameter("newPassword");
        //String newPassword1 = httpServletRequest.getParameter("confirmPassword");
        if (!userDao.selectUserinfo(Integer.parseInt(uId)).userPassword.equals(DigestUtils.md5DigestAsHex(oldPassword.getBytes())))
        {
            result.setFlag(false);
            result.setMsg("Old password input error！");
        }else
        {
                int number = userDao.updatePassword(Integer.parseInt(uId), DigestUtils.md5DigestAsHex(newPassword.getBytes()));
                if (number==1) {
                    result.setFlag(true);
                    result.setMsg("Password changed successfully！");
                }
                else {
                    result.setFlag(false);
                    result.setMsg("Failed to change password！");
                }

        }
        return result;
    }

    //获取上传笔记数
    //请用postman测试一下
    //薛永淇 2023.02.06

    /**
     * 获取用户上传笔记数
     * 测试通过
     * @param uId
     * @return
     */
    @GetMapping("/user/GetUploadNotesNumByUid/{uId}")
    public Result GetUploadNotesNumByUid(@PathVariable("uId") int  uId)
    {
        Result result = new Result();
        result.setFlag(true);
        result.setMsg("Successfully");
        result.setData(userDao.GetUploadNotesNumByUid(uId));
        return result;
    }

    //获取创建学习计划数
    //请用postman测试一下
    //薛永淇 2023.02.06

    /**
     * 获取创建计划数
     * 测试通过
     * @param uId
     * @return
     */
    @GetMapping("/user/GetCreatedPlansNumByUid/{uId}")
    public Result GetCreatedPlansNumByUid(@PathVariable("uId") int  uId)
    {
        Result result = new Result();
        result.setFlag(true);
        result.setMsg("Successfully");
        result.setData(userDao.GetCreatedPlansNumByUid(uId));
        return result;
    }

    //在系统首页展示用户上传笔记数与上传计划数消息
    //请用postman再测试一下
    //薛永淇 2023.02.07

    /**
     * 在系统首页展示用户上传笔记数与上传计划数消息
     * @param uId
     * @return
     */
    @GetMapping("/user/ShowCountAtHomePageByUid/{uId}")
    public Result ShowCountAtHomePageByUid(@PathVariable("uId") int  uId)
    {
        Result result = new Result();
        Map<String,Integer> map = new HashMap<>();
        int uploadNotesNum = userDao.GetUploadNotesNumByUid(uId);//上传笔记数
        int createdPlansNum = userDao.GetCreatedPlansNumByUid(uId);//上传计划数

        map.put("uploadNotesNum", uploadNotesNum);
        map.put("createdPlansNum", createdPlansNum);

        result.setFlag(true);
        result.setMsg("Successfully");
        result.setData(map);

        return  result;
    }

    //在系统首页展示用户的笔记列表
    //请用postman再测试一下
    //薛永淇 2023.02.07

    /**
     * 在系统首页展示用户的笔记列表
     * @param uId
     * @return
     */
    @GetMapping("/user/ShowNotesAtHomePageByUid/{uId}")
    public Result ShowNotesAtHomePageByUid(@PathVariable("uId") int  uId)
    {
        Result result = new Result();
        List<Note> notesList = noteDao.GetAllNoteByUid(uId, 0, 10);
        result.setFlag(true);
        result.setMsg("Successfully");
        result.setData(notesList);

        return  result;
    }

    //在系统首页展示用户的未完成计划
    //请用postman再测试一下
    //薛永淇 2023.02.07

    /**
     * 在系统首页展示用户的未完成计划
     * @param uId
     * @return
     */
    @GetMapping("/user/ShowUnfinishedPlansAtHomePageByUid/{uId}")
    public Result ShowUnfinishedPlansAtHomePageByUid(@PathVariable("uId") int  uId)
    {
        Result result = new Result();
        List<Plan> unfinishedPlansList = planDao.GetUnfinishedPlansByUid(uId);
        result.setFlag(true);
        result.setMsg("Successfully");
        result.setData(unfinishedPlansList);

        return  result;
    }
    @GetMapping(value = "/user/getCaptcha" , produces = MediaType.IMAGE_JPEG_VALUE)
    public String getCaptcha(HttpServletResponse httpServletResponse) throws IOException {
        captcha.makeCaptcha();
        stringRedisTemplate.opsForValue().set("code",captcha.getCode());
        String filePath = System.getProperty("user.dir")+"/code/code.png";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes,0,fileInputStream.available());
        UtilHelper utilHelper = new UtilHelper();
        String code = utilHelper.byte2Base64StringFun(bytes);
        String code64 = "data:image/png;base64,"+ code;
        return code64;
//        return bytes;
    }

    @PostMapping("/users/send/{phoneNumber}")
    public Result sendMessage(@PathVariable("phoneNumber") String phoneNumber) {
        return userService.sendCode(phoneNumber);
    }
}

