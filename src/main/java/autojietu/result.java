package autojietu;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

public class result {

    /**
     * @param args
     * @throws AWTException
     */
    public static void main(String[] args) throws Exception {
        //"C:\\sally\\bootstrap栅格"是根据自己随意找的一个图片形式，"jpg"是图片的格式
        while (true) {
            GuiCamera cam = new GuiCamera("D:\\pic\\img3\\", "jpg");
            cam.snapshot();
            ImagePreProcess2 ima2 = new ImagePreProcess2();
            String text = ima2.getAllOcr("D:\\pic\\img3\\0.jpg");
            if(text==null||text.equals("")){
                ImagePreProcess3 ima3 = new ImagePreProcess3();
                text = ima3.getAllOcr("D:\\pic\\img3\\0.jpg");
            }
            System.out.println("0.jpg = " + text);
            Thread.currentThread().sleep(1000);//毫秒
            Robot robot = new Robot(); //创建一个robot对象


//        RunTime.getRuntime().exec("notepad");        //打开一个记事本程序
//        robot.delay(2000);        //等待 2秒
//        //窗口最大化
//        keyPressWithAlt(robot, KeyEvent.VK_SPACE); //按下 alt+ 空格
//            keyPress(robot, KeyEvent.VK_X);  //按下x键
//            robot.delay(1000);  //等待 1秒
            keyPressString(robot, text); //输入字符串
            keyPress(robot, KeyEvent.VK_SPACE);
            robot.delay(2000);  //等待 2秒
//            keyPress(robot, KeyEvent.VK_ENTER); // 按下 enter 换行
//            keyPress(robot, KeyEvent.VK_A); //按下 CopyOfCleanLines 键
//            keyPress(robot, KeyEvent.VK_B); //按下 b 键
//            keyPress(robot, KeyEvent.VK_C); //按下 c 键
//            keyPress(robot, KeyEvent.VK_D); //按下 d 键
//            keyPressWithCtrl(robot, KeyEvent.VK_A); //按下 ctrl+A 全选
//            keyPress(robot, KeyEvent.VK_DELETE); //清除


        }
    }

    // shift+ 按键
    public static void keyPressWithShift(Robot r, int key) {
        r.keyPress(KeyEvent.VK_SHIFT);
        r.keyPress(key);
        r.keyRelease(key);
        r.keyRelease(KeyEvent.VK_SHIFT);
        r.delay(100);
    }

    // ctrl+ 按键
    public static void keyPressWithCtrl(Robot r, int key) {
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(key);
        r.keyRelease(key);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.delay(100);
    }

    // alt+ 按键
    public static void keyPressWithAlt(Robot r, int key) {
        r.keyPress(KeyEvent.VK_ALT);
        r.keyPress(key);
        r.keyRelease(key);
        r.keyRelease(KeyEvent.VK_ALT);
        r.delay(100);
    }
    //打印出字符串
    public static void keyPressString(Robot r, String str){
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();//获取剪切板
        Transferable tText = new StringSelection(str);
        clip.setContents(tText, null); //设置剪切板内容
        keyPressWithCtrl(r, KeyEvent.VK_V);//粘贴
        r.delay(100);
    }

    //单个 按键
    public static void keyPress(Robot r,int key){
        r.keyPress(key);
        r.keyRelease(key);
        r.delay(100);
    }
}
