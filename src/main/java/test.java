import autojietu.GuiCamera;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.io.*;

public class test {

    public static void keyPressWithCtrl(Robot r, int key) {
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(key);
        r.keyRelease(key);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.delay(100);
    }
    public static void keyPressString(Robot r, String str){
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(str);
        clip.setContents(tText, null);
        keyPressWithCtrl(r, KeyEvent.VK_V);
        r.delay(100);
    }
    public static void keyPress(Robot r, int key){
        r.keyPress(key);
        r.keyRelease(key);
        r.delay(100);
    }
    public static void main(String[] args) throws Exception {
        String s1 = "";
        while (true) {
            GuiCamera cam = new GuiCamera("D:\\pic\\img3\\", "jpg");
            cam.snapshot();
            Process pro = null;
            try {
                String s = "";
                pro = Runtime.getRuntime().exec(new String[]{"D:\\gongju\\Tesseract-OCR\\tesseract",
                        "D:\\pic\\img3\\13.jpg", "D:\\pic\\result3\\result",
                        "-l", "eng"});
                pro.waitFor();
                InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("D:\\pic\\result3\\result.txt")));
                BufferedReader br = new BufferedReader(isr);
                String txt = null;
                Robot robot = new Robot();
                while ((txt = br.readLine()) != null) {
                    s += txt;
                }
                if(s.equals("")||s.equals(s1)){
                    robot.delay(4000);
                    continue;
                }
                s1=s;

                if(!s.matches("[a-zA-Z]+")||!s.matches("[0-9]+")){
                    s=s.replace("二","r");
                }
                robot.mouseMove(377, 632);
                robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);

                keyPressString(robot, s);
                robot.delay(1000);
                keyPress(robot, KeyEvent.VK_SPACE);
                robot.delay(1000);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}