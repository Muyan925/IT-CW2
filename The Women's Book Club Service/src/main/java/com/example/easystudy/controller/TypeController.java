package com.example.easystudy.controller;


import com.example.easystudy.dao.NoteDao;
import com.example.easystudy.dao.TypeDao;
import com.example.easystudy.result.Result;
import com.example.easystudy.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 类型控制器
 * @author BB4U
 */
@RestController
@CrossOrigin
public class TypeController {

    //Spring推荐使用setter方式注入，所以Field注入方式不再使用
    //BB4U
    //02.16

    private TypeDao typeDao;

    private NoteDao noteDao;

    @Autowired
    public void setTypeDao(TypeDao typeDao) {
        this.typeDao = typeDao;
    }

    @Autowired
    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    /**
     * 根据uId获取笔记列表
     * 测试通过
     */
    @GetMapping("/Type/GetTypeListByUid/{uId}")
    public Result GetTypeListByUid(@PathVariable("uId") int uId)
    {
        Result result = new Result();
        if (uId>0) {
            List<Type> typesList = typeDao.selectTypeListByUid(uId);
            result.setFlag(true);
            result.setMsg("successful");
            result.setData(typesList);
        }else
        {
            result.setFlag(false);
            result.setMsg("unsuccessful");
        }
        return  result;
    }

    /**
     * 根据tid更新type信息
     * 测试通过
     * @param type
     * @return
     */
    @PostMapping("/Type/UpdateTypeByTid")
    public Result UpdateTypeByTid(@RequestBody Type type)
    {

        List<Type> types = typeDao.selectTypeListByUid(type.uId);
        Result result = new Result();
        int flag = 1;
        for (Type type1:types) {
            if (type1.typeName.equals(type.typeName) && type1.tId!=type.tId)
            {
                result.setFlag(false);
                result.setMsg("repeated!");
                flag = 0;
            }
        }
        if (flag==1) {
            typeDao.updateTypeByTid(type.tId, type.typeName);
            result.setFlag(true);
            result.setMsg("successful！");
        }
        return result;
    }

    /**
     * 增加类型
     * 测试通过
     * @param type
     * @return
     */
    @PostMapping("/Type/AddType")
    public Result addType(@RequestBody Type type)
    {
        List<Type> types = typeDao.selectTypeListByUid(type.uId);
        Result result = new Result();
        int flag = 1;
        for (Type type1:types)
        {
            if (type.typeName.equals(type1.typeName))
            {
                flag = 0;
                result.setFlag(false);
                result.setMsg("unsuccessful！");
            }
        }
        if (flag==1)
        {
            typeDao.addType(type);
            result.setFlag(true);
            result.setMsg("successful！");
        }
        return result;
    }

    /**
     * 删除类型
     * 测试通过
     * @param tId
     * @return
     */
    @PostMapping("/Type/DeleteTypeByTid/{tId}")
    public Result DeleteTypeByTid(@PathVariable("tId") int tId)
    {
        int number = typeDao.deleteTypeByTid(tId);
        Result result = new Result();
        if (number==1)
        {
            noteDao.deleteNoteByTid(tId);
            result.setFlag(true);
            result.setMsg("successful！");
        }else {
            result.setFlag(false);
            result.setMsg("unsuccessful！");
        }
        return result;
    }

    /**
     * 批量删除类型
     * 测试通过
     * @param tIds
     * @return
     */
    @PostMapping("/Type/DeleteTypeByTid/")
    public Result DeleteTypeByTids(@RequestParam("tIds") List<String> tIds)
    {
        Result result = new Result();
        for(String tId : tIds)
        {
            typeDao.deleteTypeByTid(Integer.parseInt(tId));
            noteDao.deleteNoteByTid(Integer.parseInt(tId));
        }

        result.setFlag(true);
        result.setMsg("successful");
        return result;
    }
}
