package jietu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class NewFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    /*
     * 创建一个小的窗口。点击按钮来截屏。
     */
    JButton button;
    NewFrame()
    {
        setVisible(true);
        setLayout(new FlowLayout());
        setBounds(1000, 600, 100, 100);
        setResizable(false);
        button = new JButton("截图");
        add(button);
        button.addActionListener(new ActionListener()
        {//鼠标点击按钮，new 一个ScreenFrame，设置可见，
            public void actionPerformed(ActionEvent e)
            {
                ScreenFrame sf = new ScreenFrame();
                sf.setAlwaysOnTop(true);
                sf.setUndecorated(true);
                sf.setVisible(true);
            }
        });
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }
}