package me.lolico.learning.springboot.common.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author Liar
 */
public class CaptchaGenerator {
    /**
     * 图片宽
     */
    private int width;
    /**
     * 图片长
     */
    private int height;
    /**
     * 干扰线数量
     */
    private int lineNum;
    /**
     * 随机产生字符数量
     */
    private int charNum;
    private final Random random = new Random();
    /**
     * 验证码
     */
    private StringBuffer captchaCode = new StringBuffer();
    /**
     * 用于产生随机字符
     */
    private final String randString = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";

    public String getCaptchaCode() {
        return this.captchaCode.toString();
    }

    public CaptchaGenerator setHeight(int height) {
        this.height = height;
        return this;
    }

    public CaptchaGenerator setWidth(int width) {
        this.width = width;
        return this;
    }

    public CaptchaGenerator setLineNum(int lineNum) {
        this.lineNum = lineNum;
        return this;
    }

    public CaptchaGenerator setCharNum(int charNum) {
        this.charNum = charNum;
        return this;
    }

    /**
     * @param g     用于绘图操作的对象
     * @param font  绘图风格
     * @param color 绘制所用的颜色
     */
    private void drawLine(Graphics g, Font font, Color color) {
        g.setFont(font);
        g.setColor(color);

        int x1 = this.random.nextInt(width);
        int y1 = this.random.nextInt(height);
        int x2 = x1 + this.random.nextInt(width / 8);
        int y2 = y1 + this.random.nextInt(height / 3);

        //在[(x1,y1),(x2,y2)]之间绘制一条线
        g.drawLine(x1, y1, x2, y2);
    }

    /**
     * @param g     用于绘图操作的对象
     * @param font  绘图风格
     * @param color 绘制所用的颜色
     * @param i     相对于绘制的基准位置的偏移量
     */
    private String drawString(Graphics g, Font font, Color color, int i) {
        g.setFont(font);
        g.setColor(color);
        //产生一个随机字符
        String rand = String.valueOf(randString.charAt(random.nextInt(randString.length())));
        g.translate(this.random.nextInt(3), this.random.nextInt(3));
        int x = (this.width / this.charNum - 14) * i;
        int y = this.height - 7;
        //(x,y)绘制的基准位置
        g.drawString(rand, x, y);
        return rand;
    }

    /**
     * @return 一个带缓冲区的Image
     */
    public BufferedImage generateImage() {
        //初始化验证码
        captchaCode = new StringBuffer();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        //绘图区域
        g.fillRect(0, 0, width, height);
        //干扰线风格
        Font font = new Font("Times New Roman", Font.PLAIN, 30);
        //绘制干扰线所用颜色
        Color color = new Color(110 + random.nextInt(20), 110 + random.nextInt(20), 110 + random.nextInt(20));
        //绘制干扰线
        for (int i = 0; i < lineNum; i++) {
            drawLine(g, font, color);
        }

        //验证码字体风格
        font = new Font("Times New Roman", Font.BOLD, 30);
        //绘制字符
        for (int i = 1; i <= charNum; i++) {
            //每个字符用不同颜色绘制
            //绘制验证码所用颜色
            color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            //绘制一个字符
            String s = drawString(g, font, color, i);
            //拼接验证码
            captchaCode.append(s);
        }
        return image;
    }

    /**
     * 默认初始化
     */
    private CaptchaGenerator() {
        this.width = 135;
        this.height = 40;
        this.lineNum = 50;
        this.charNum = 4;
    }

    private static class InstanceHandel {
        private static final CaptchaGenerator INSTANCE = new CaptchaGenerator();
    }

    public static CaptchaGenerator getInstance() {
        return InstanceHandel.INSTANCE;
    }
}
