/* StaticValue.java

静态变量类，用于存储游戏中所需要的图片资源
包含了游戏中所有需要使用的背景、角色、障碍物等元素的图片资源
通过静态变量和静态方法来调用 */
package com.sxt;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 常量类的创建
public class StaticValue {
    // 背景
    public static BufferedImage bg=null; // 游戏背景1
    public static BufferedImage bg2=null; // 游戏背景2
    // 角色图片
    public static BufferedImage jump_L=null; // 向左跳跃图片
    public static BufferedImage jump_R=null; // 向右跳跃图片
    public static BufferedImage stand_L=null; // 向左站立图片
    public static BufferedImage stand_R=null; // 向右站立图片
    // 游戏元素图片
    public static BufferedImage tower=null; // 城堡图片
    public static BufferedImage gan=null; // 旗杆图片
    // 障碍物(定义一个列表)
    public static List<BufferedImage> obstacle=new ArrayList<>(); // 障碍物列表
    // 马里奥向左/右跑的图片
    public static List<BufferedImage> run_L=new ArrayList<>(); // 向左跑的图片列表
    public static List<BufferedImage> run_R=new ArrayList<>(); // 向右跑的图片列表
    // 敌人图片
    public static List<BufferedImage> mogu=new ArrayList<>(); // 蘑菇敌人图片列表
    public static List<BufferedImage> flower=new ArrayList<>(); // 食人花敌人图片列表
    // 路径的前缀，方便后续调用（绝对路径）
    public static String path=System.getProperty("user.dir")+"/src/img1/";

    // 初始化方法
    public static void init()
    {
        // 加载游戏背景图片
        try {
            bg = ImageIO.read(StaticValue.class.getResource( "/img1/bg.png")); // 加载游戏背景1
            bg2 = ImageIO.read(StaticValue.class.getResource( "/img1/bg2.png")); // 加载游戏背景2
        } catch(IOException e) {
            e.printStackTrace();
        }

        // 加载角色图片
        try {
            jump_L = ImageIO.read(StaticValue.class.getResource("/img1/s_mario_stand_L.png")); // 加载向左跳跃图片
            jump_R = ImageIO.read(StaticValue.class.getResource("/img1/s_mario_stand_R.png")); // 加载向右跳跃图片
            stand_L = ImageIO.read(StaticValue.class.getResource("/img1/s_mario_stand_L.png")); // 加载向左站立图片
            stand_R = ImageIO.read(StaticValue.class.getResource("/img1/s_mario_stand_R.png")); // 加载向右站立图片
        } catch(IOException e) {
            e.printStackTrace();
        }

        // 加载游戏元素图片
        try {
            tower = ImageIO.read(StaticValue.class.getResource("/img1/tower.png")); // 加载城堡图片
            gan = ImageIO.read(StaticValue.class.getResource("/img1/gan.png")); //  加载旗杆图片
        } catch(IOException e) {
            e.printStackTrace();
        }

        // 加载马里奥向左/右跑的图片（使用for循环加载）
        for(int i=1; i<=2; i++) {
            try {
                run_L.add(ImageIO.read(StaticValue.class .getResource("/img1/"+"s_mario_run"+i+"_L.png"))); // 向左跑的图片
                run_R.add(ImageIO.read(StaticValue.class .getResource("/img1/"+"s_mario_run"+i+"_R.png"))); // 向右跑的图片
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        // 加载障碍物
        try {
            obstacle.add(ImageIO.read(StaticValue.class.getResource( "/img1/brick.png"))); // 普通砖块
            obstacle.add(ImageIO.read(StaticValue.class.getResource("/img1/soil_up.png"))); // 上地面
            obstacle.add(ImageIO.read(StaticValue.class.getResource("/img1/soil_base.png"))); // 下地面
        } catch(IOException e) {
            e.printStackTrace();
        }

        // 加载水管
        for(int i=1; i<=4; i++) {
            try {
                obstacle.add(ImageIO.read(StaticValue.class.getResource("/img1/" +"pipe" + i + ".png"))); // 不同种类的水管
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        // 加载不可破坏的砖块和旗子
        try {
            obstacle.add(ImageIO.read(StaticValue.class.getResource("/img1/brick2.png"))); // 不可破坏的砖块
            obstacle.add(ImageIO.read(StaticValue.class.getResource("/img1/flag.png"))); // 旗子
        } catch(IOException e) {
            e.printStackTrace();
        }

        // 加载敌人图片（蘑菇、食人花）
        //加载蘑菇敌人
        for(int i=1; i<=3; i++) {
            try {
                mogu.add(ImageIO.read(StaticValue.class.getResource("/img1/" + "fungus" + i + ".png"))); // 蘑菇敌人
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
       //加载食人花敌人
        for(int i=1; i<=2; i++) {
            try {
                flower.add(ImageIO.read(StaticValue.class.getResource("/img1/" + "flower1." + i + ".png"))); // 食人花敌人
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}