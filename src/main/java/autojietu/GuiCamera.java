package autojietu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GuiCamera {
    private String fileName;
    private String defaultName="GuiCamera";
    static int serialNum=0;
    private String imageFormat;//图像文件的格式
    private String defaultImageFormat="jpg";
    Dimension d=Toolkit.getDefaultToolkit().getScreenSize();

    public  GuiCamera(){
        fileName=defaultName;
        imageFormat=defaultImageFormat;
    }

    public GuiCamera(String s,String format) {
        fileName=s;
        imageFormat=format;
    }
    /**
     * 对屏幕进行拍照
     *
     * **/
    public void snapshot(){
        try {
            //拷贝屏幕到一个BufferedImage对象screenshot
//            BufferedImage screenshot=(new Robot()).createScreenCapture(
//                    new Rectangle(0,0,(int)d.getWidth(),(int)d.getHeight()));
            BufferedImage screenshot=(new Robot()).createScreenCapture(
                    new Rectangle(856,235,400,60));
//            serialNum++;
            //根据文件前缀变量和文件格式变量，自动生成文件名
            String name=fileName+String.valueOf(serialNum)+"."+imageFormat;
            System.out.println(name);
            File f=new File(name);
            System.out.println("Save File-"+name);
            //将screenshot对象写入图像文件
            ImageIO.write(screenshot, imageFormat, f);
            System.out.println("..Finished");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) throws Exception{
        //"C:\\sally\\bootstrap栅格"是根据自己随意找的一个图片形式，"jpg"是图片的格式
        while (true) {
            GuiCamera cam = new GuiCamera("D:\\pic\\img3\\", "jpg");
            cam.snapshot();
            ImagePreProcess3 ima = new ImagePreProcess3();
            String text = ima.getAllOcr("D:\\pic\\img3\\0.jpg");
            System.out.println("0.jpg = " + text);
            Thread.currentThread().sleep(2000);//毫秒
        }
    }
}