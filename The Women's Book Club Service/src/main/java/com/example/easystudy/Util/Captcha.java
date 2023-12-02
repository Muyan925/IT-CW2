package com.example.easystudy.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
@Component
public class Captcha {
private String code = "";
private String codePath = System.getProperty("user.dir") +"/code/code.png";
public void makeCaptcha()  {
    BufferedImage bufferedImage = new BufferedImage(160,80,BufferedImage.TYPE_INT_BGR);
    Graphics graphics = bufferedImage.getGraphics();
    graphics.setClip(0,0,160,80);
    graphics.setColor(new Color(234,234,234));
    graphics.fillRect(0,0,160,80);
    Font font = new Font("微软雅黑",Font.PLAIN,60);
    graphics.setFont(font);

    String str = "abcdefghijklmnopqrstuvwxyz0123456789";
    Random random  = new Random();
    StringBuffer stringBuffer = new StringBuffer();
    for(int i=0;i<4;i++)
    {
        int number = random.nextInt(str.length());
        ColorUtil colorUtil = new ColorUtil();
        graphics.setColor(colorUtil.getColor());

        String str1 = str.charAt(number)+"";
        int offset = random.nextInt(80-60);
        if (i ==0)
        {
            graphics.drawString(str1,0,offset+60);
        }else {
            graphics.drawString(str1, (i + 1) * 30, offset + 45);
        }
        stringBuffer.append(str.charAt(number));
    }
    code = stringBuffer.toString();
    System.out.println(stringBuffer);
    for (int i = 0; i < 25; i++) {
        graphics.setColor(new Color((int) (Math.random()*255), (int) (Math.random()*255), (int) (Math.random()*255)));
        graphics.drawLine((int) (Math.random()*160),(int) (Math.random()*64),(int) (Math.random()*160),(int) (Math.random()*160));
    }
    graphics.dispose();

    try {
        ImageIO.write(bufferedImage, "png", new File(codePath));// 输出png图片
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
public String getCode()
{
    return this.code;
}
}
