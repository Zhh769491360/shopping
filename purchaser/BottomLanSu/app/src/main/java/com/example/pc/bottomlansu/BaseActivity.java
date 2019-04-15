package com.example.pc.bottomlansu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

//Ctrl + Alt + L 格式化代码，可以实现
//        附一些我常用的快捷键的总结吧
//        18.Alt+Insert 生成构造器/Getter/Setter等
//        5.Ctrl+/和Ctrl+Shift+/ 注释代码
//        26.Ctrl+Q 看JavaDoc
//        37.Ctrl + Alt + L 格式化代码
//        24.Alt+F3 快速寻找
//        23.Ctrl+Shift+Space 在很多时候都能够给出Smart提示
//        Ctrl+X 删除行
//        Ctrl+D 复制当前行到下一行
//        Ctrl+Backspace 删除单词整体
//        sout system.out.println();
//        shift+f6重命名
//        Ctrl + Alt + L 格式化代码
//        Ctrl＋E，可以显示最近编辑的文件列表
//        double Shift:全局查找
//        android测试AndroidTestCase，测试方法时所有方法都必须以test开头
//        Ctrl+Alt+V 引入变量。例如把括号内的SQL赋成一个变量
//        Live Templates! Ctrl+J
//        ctrl+p 参数提示
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        LogUtil.d( "BaseActivity", getClass().getSimpleName() );
    }
}
