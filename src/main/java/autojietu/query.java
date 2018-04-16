package autojietu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.TimerTask;

public class query {
    static boolean ifRun = true;
    static JButton startBt;
    static JButton endBt;


    public static void main(String[] args) {
        // 显示主界面
        showMain();

    }

    static Thread thread = new Thread(new Runnable() {


        public void run() {
            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            // 模拟移动到当前鼠标位置
            while (ifRun) {
                Point point = MouseInfo.getPointerInfo().getLocation();
                System.out.println("x=" + point.getX() + ",y=" + point.getY());
                robot.mouseMove((int) point.getX(), (int) point.getY());
                // 模拟鼠标按下左键
                robot.mousePress(InputEvent.BUTTON1_MASK);
                // 模拟鼠标松开左键
                robot.mouseRelease(InputEvent.BUTTON1_MASK);

                try {
                    thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    });

    static public void showMsg(String msg) {
        JOptionPane.showMessageDialog(null, msg, "提示信息",
                JOptionPane.PLAIN_MESSAGE);
    }

    static public void showMain() {
        JDialog dialog = new JDialog();
        // 设置大小
        dialog.setSize(200, 100);
        // 设置标题
        dialog.setTitle("界面");

        startBt = new JButton("开始");
        endBt = new JButton("结束");
        // 绑定监听
        startBt.addActionListener(actionListener);
        endBt.addActionListener(actionListener);
        startBt.setBounds(35, 10, 60, 40);
        endBt.setBounds(90, 10, 60, 40);
        // 设置布局为空，使用坐标控制控件位置的时候，一定要设置布局为空
        dialog.setLayout(null);
        // 添加控件
        dialog.add(startBt);
        dialog.add(endBt);
        // 设置dislog的相对位置，参数为null，即显示在屏幕中间
        dialog.setLocationRelativeTo(null);
        // 设置当用户在此对话框上启动 "close" 时默认执行的操作
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // 设置是否显示
        dialog.setVisible(true);
    }

    static ActionListener actionListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == startBt) {
                showMsg("已开始——————请在三秒内点击");
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        showMsg("已开始获取鼠标位置并已启动线程");
                        thread.start();
                    }
                }, 3000);

            }

            if (e.getSource() == endBt) {
                ifRun = false;
                showMsg("结束");
            }
        }
    };

}
