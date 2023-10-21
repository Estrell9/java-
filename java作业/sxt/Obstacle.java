package com.sxt;

import java.awt.image.BufferedImage;

//障碍物类
public class Obstacle implements Runnable {
    //用于表示坐标
    private int x;
    private int y;
    //用于记录障碍物类型
    private int type;
    //用于显示图像
    private BufferedImage show=null;
    //定义当前场景对象
    private BackGround bg=null;
    //定义一个线程对象(用于完成旗子下落的过程）
    private Thread thread=new Thread(this);

    public Obstacle(int x,int y,int type,BackGround bg)
    {
        this.x=x;
        this.y=y;
        this.type=type;
        this.bg=bg;
        //得到该类型障碍物的图像
        show=StaticValue.obstacle.get(type);
        //如果是旗子的话，启动线程
        if(type==8)
        {
            thread.start();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public BufferedImage getShow() {
        return show;
    }

    @Override
    public void run() {
        while(true)
        {
            if(this.bg.isReach())
            {
                if(this.y<374)
                {
                    this.y+=5;
                }else {
                    this.bg.setBase(true);//表示旗子已经成功的落到了地上
                }

            }
            try {
                Thread.sleep(50);
            }catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }
}
