package com.sxt;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

public class Music {
    Player player; // 声明一个播放器对象

    public Music() throws FileNotFoundException, JavaLayerException {
        // 通过类加载器获取资源文件的 InputStream
        InputStream inputStream = Music.class.getResourceAsStream("music.wav");
// 创建一个 BufferedInputStream 流读取音频文件
        BufferedInputStream name = new BufferedInputStream(inputStream);
        // 使用 JLayer 对象创建一个播放器对象
        player = new Player(name);
        // 先确保 player 对象不为空
        if (player != null) {
            // 播放音频文件
            player.play();
        } else {
            System.out.println("无法播放音频文件！");
        }
    }
}
