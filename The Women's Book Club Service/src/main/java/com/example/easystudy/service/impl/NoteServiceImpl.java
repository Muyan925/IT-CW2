package com.example.easystudy.service.impl;

import com.example.easystudy.dao.NoteDao;
import com.example.easystudy.result.Result;
import com.example.easystudy.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.UUID;

/**
 * 笔记（在线编辑）业务类实现
 * @author SMH
 */
@Service
public class NoteServiceImpl implements NoteService {

    @Value("${baseUrl}")
    private String baseUrl;

    /**
     * 在线保存
     * 测试通过
     * @param noteName
     * @param note
     * @return
     */
    @Override
    public Result saveNote(String noteName, String note) {
        /*System.out.println(note);*/
        String filePath = System.getProperty("user.dir") + "/note/" + noteName;
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath);
            writer.write(note);
            return new Result(true, "保存笔记成功");
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "保存笔记失败");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 笔记插入图片
     * 测试通过
     * @param image
     * @return
     */
    @Override
    public Result uploadImage(MultipartFile image) {
        String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + "/md-img/" + fileName;
        String returnLink = baseUrl + "/link/" + fileName;
        try {
            image.transferTo(new File(filePath));
            return new Result(true, "上传图片成功", returnLink);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "上传图片失败");
        }
    }
}
