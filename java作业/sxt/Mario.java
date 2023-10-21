package com.sxt;

import java.awt.image.BufferedImage;

public class Mario implements Runnable{
    //用于表示横坐标
    private int x;
    private int y;
    //用于表示当前状态
    private String statues;
    //用于显示当前状态对应的图像
    private BufferedImage show=null;
    //定义一个Background对象，用于获取障碍物的信息
    private BackGround backGround=new BackGround();
    //用来实现马里奥的动作(创建一个线程对象)
    private Thread thread=null;
    //马里奥的移动速度
    private int xSpeed;
    //马里奥的跳跃速度
    private int ySpeed;
    //定义一个索引(用于取得马里奥的运动图像)
    private int index;
    //表示马里奥上升的时间
    private int upTime=0;
    //用于判断马里奥是否走到了城堡门口
    private boolean isOK;
    //用于判断马里奥是否死亡
    private boolean isDeath=false;
    //表示分数
    private int score=0;


   public Mario()
   {

   }
    public Mario(int x,int y)
    {
        this.x=x;
        this.y=y;
        show=StaticValue.stand_R;
        this.statues="stand--right";
        thread=new Thread(this);
        thread.start();//启动线程
    }
    //马里奥的死亡方法
    public void death()
    {
        isDeath=true;
    }
    //马里奥向左移动
    public void leftMove()
    {
        //改变速度
        xSpeed=-5;
        //判断马里奥是否碰到旗子
        if(backGround.isReach())
        {
            xSpeed=0;
        }
        //判断马里奥是否处于空中
        if(statues.indexOf("jump")!=-1) {
            statues = "jump--left";
        }else {
            statues="move--left";
        }
    }

    //马里奥向右移动
    public void rightMove()
    {
        //改变速度
        xSpeed=5;
        //判断马里奥是否碰到旗子
        if(backGround.isReach())
        {
            xSpeed=0;
        }
        //判断马里奥是否处于空中
        if(statues.indexOf("jump")!=-1) {
            statues = "jump--right";
        }else {
            statues="move--right";
        }
    }
    //马里奥向左停止
    public void leftStop()
    {
        //改变速度
        xSpeed=0;
        //判断马里奥是否处于空中
        if(statues.indexOf("jump")!=-1) {
            statues = "jump--left";
        }else {
            statues="stop--left";
        }
    }
    //马里奥向右停止
    public void rightStop()
    {
        //改变速度
        xSpeed=0;
        //判断马里奥是否处于空中
        if(statues.indexOf("jump")!=-1) {
            statues = "jump--right";
        }else {
            statues="stop--right";
        }
    }
    //马里奥跳跃
   public void jump()
   {
       if(statues.indexOf("jump")==-1)
       {
            //表明此时的方向向左
           if(statues.indexOf("left")!=-1)
           {
               statues="jump--left";
           }else {
               statues="jump--right";
           }
           //垂直速度
           ySpeed=-10;
           //马里奥跳跃的高度为70
           upTime = 7;
       }
       //判断马里奥是否碰到旗子
       if(backGround.isReach())
       {
           ySpeed=0;
       }
   }

    //马里奥下落
    public void fall()
    {
         if(statues.indexOf("left")!=-1)
        {
            statues="jump--left";
        }else {
            statues="jump--right";
        }
        ySpeed=10;
    }

    @Override
    public void run() {
       while(true)
       {
           //判断是否处于障碍物上
          boolean onObstacle=false;
          //判断是否可以往右走
           boolean canRight=true;
           //判断是否可以往左走
           boolean canLeft=true;
           //判断马里奥是否到达旗杆位置
           if(backGround.isFlag()&&this.x>=500) {
               this.backGround.setReach(true);
               //判断旗子是否下落完成
               if (this.backGround.isBase()) {
                   statues = "move--right";
                   //是否移动到城堡中间
                   if (x < 690) {
                       x += 5;
                   } else {
                       isOK = true;//已经走到了城堡处
                   }
               } else {
                   //判断马里奥是否在空中
                   if (y < 395) {
                       xSpeed = 0;
                       this.y += 5;
                       statues = "jump--right";
                   }
                   if (y > 395) {
                       this.y = 395;//会落到地上，不会再继续下落
                       statues = "stop--right";
                   }
               }
           }
           //遍历当前场景里所有是障碍物
           for(int i=0;i<backGround.getObstacleList().size();i++)
           {
               Obstacle ob=backGround.getObstacleList().get(i);
               //判断马里奥是否位于障碍物上
               if(ob.getY()==this.y+25&&(ob.getX()>this.x-30&&ob.getX()<this.x+25))
               {
                onObstacle=true;
               }
               //判断是否跳起来顶到砖块
               if((ob.getY()>=this.y-30&&ob.getY()<=this.y-20)&&(ob.getX()>this.x-30&&ob.getX()<this.x+25))
               {
                   //判断是否是普通砖块
                  if(ob.getType()==0)
                  {
                      backGround.getObstacleList().remove(ob);//把砖块移除出去
                      score+=1;
                  }
                  //将 upTime 置为 0。这是因为马里奥在顶到砖块之后需要立刻下落，因此将 upTime 置为 0，可以让马里奥立即结束跳跃，并开始下落。
                  upTime=0;
               }
               //判断是否可以往右走
               if(ob.getX()==this.x+25&&(ob.getY()>this.y-30&&ob.getY()<this.y+25))
               {
                   canRight=false;
               }
               //判断是否可以往左走
               if(ob.getX()==this.x-30&&(ob.getY()>this.y-30&&ob.getY()<this.y+25))
               {
                   canLeft=false;
               }
           }
           //判断马里奥是否碰到敌人死亡或者踩死蘑菇敌人
           for(int i=0;i<backGround.getEnemyList().size();i++)
           {
               Enemy e=backGround.getEnemyList().get(i);
               if(e.getY()==this.y+20&&(e.getX()-25<=this.x&&e.getX()+35>=this.x))
               {
                 if(e.getType()==1)//蘑菇敌人
                 {
                     e.death();
                     score+=2;
                     upTime=3;//将 upTime 设为 3，表示马里奥此时可以向上跳跃的时间；
                     ySpeed=-10;
                 }else if(e.getType()==2)
                 {
                     //马里奥死亡
                     death();
                 }
               }
               if((e.getX()+35>this.x&&e.getX()-25<this.x)&&(e.getY()+35>this.y&&e.getY()-20<this.y))
               {
                   //马里奥死亡
                   death();
               }
           }

           //进行马里奥跳跃的操作
        if(onObstacle&&upTime==0)
        {
            if(statues.indexOf("left")!=-1)
            {
                if(xSpeed!=0)
                {
                    statues="move--left";
                }else
                {
                    statues="stop--left";
                }
            }else {
                if(xSpeed!=0)
                {
                    statues="move--right";
                }else {
                    statues="stop--right";
                }
            }
        }else {
            //处于上升状态
            if(upTime!=0)
            {
                upTime--;
            }else
            {
                fall();
            }
            y+=ySpeed;
        }
           //判断马里奥是否在运动
           if((canLeft&&xSpeed<0)||(canRight&&xSpeed>0))
           {
               x+=xSpeed;
               //判断马里奥是否到了最左边
               if(x<0)
               {
                   x=0;
               }
           }
           //判断当前是否是移动状态
           if(statues.contains("move"))
           {
               index=index==0?1:0;
           }
           //判断是否向左移动
           if("move--left".equals(statues))
           {
               show=StaticValue.run_L.get(index);
           }

           //判断是否向右移动
           if("move--right".equals(statues))
           {
               show=StaticValue.run_R.get(index);
           }
           //判断是否向左停止
           if("stop--left".equals(statues))
           {
               show=StaticValue.stand_L;
           }

           //判断是否向右停止
           if("stop--right".equals(statues))
           {
               show=StaticValue.stand_R;
           }
           //判断是否向左跳跃
           if("jump--left".equals(statues))
           {
               show=StaticValue.jump_L;
           }
           //判断是否向右跳跃
           if("jump--right".equals(statues))
           {
               show=StaticValue.jump_R;
           }
           try {
               Thread.sleep(50);
           }catch(InterruptedException e)
           {
               e.printStackTrace();
           }

       }

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getShow() {
        return show;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }
    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }
    public boolean isOK() {
        return isOK;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public int getScore() {
        return score;
    }
}
