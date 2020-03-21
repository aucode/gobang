package au.gobang.game.frame;

import au.gobang.game.uitl.Comm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Project gobang
 * @Description: FrameTool
 * @Author AU
 * @Explain 五子棋主界面类
 * @Date 2020-03-16 11:37
 */
public class MyJFrame extends JFrame implements MouseListener,Runnable {
    //获取屏幕大小
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    //图片
    BufferedImage bjImage = null;
    //棋子坐标
    int x = 0;
    int y = 0;
    /**
     * 保存所有的棋子坐标
     * 0:表示该点没有棋子；1：表示黑棋；2：表示白棋。
     *
     */
    int[][] allChess = new int[19][19];
    //标记黑棋或白棋
    boolean isBlack = true;
    //标记游戏是否结束
    boolean canPlay = true;
    //保存提示信息
    String message = "黑方走棋";
    //保存最大时间
    int maxTime = 0;
    //倒计时线程类
    Thread t = new Thread(this);
    //保存黑方、白方倒计时
    int blackTime = 0;
    int whiteTime = 0;
    //保存黑方、白方倒计时信息
    String blackTimeMessage = "无限制";
    String whiteTimeMessage = "无限制";
    public MyJFrame(){
        this.setTitle("五子棋");
        this.setSize(Comm.WIDTH,Comm.HEIGHT);
        this.setResizable(false);
        //关闭退出程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置居中
        this.setLocation((width-Comm.WIDTH)/2,(height-Comm.HEIGHT)/2);
        //添加监听事件
        this.addMouseListener(this);
        try {
            String projectPath = System.getProperty("user.dir");
            bjImage = ImageIO.read(new File(projectPath+"\\out\\Resources\\0.jpg"));
            //bjImage = ImageIO.read(new File("C:/Users/Administrator.WIN-IHVBI18K8J9/Pictures/Saved Pictures/0.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setVisible(true);
        //防止未加载，无法显示。
        this.repaint();
        t.start();
        t.suspend();//挂起
    }
    //绘制界面
    public void paint(Graphics g){
        //双缓冲技术防止屏幕闪烁
        BufferedImage bufferedImage = new BufferedImage(800,600,BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = bufferedImage.createGraphics();
        g2.setColor(Color.BLACK);
        g2.drawImage(bjImage,0,20,this);
        g2.setFont(new Font("黑体",Font.BOLD,30));
        g2.drawString("游戏信息："+message,60,80);
        g2.setFont(new Font("宋体",Font.BOLD,20));
        g2.drawString("黑方时间：" + blackTimeMessage,60,580);
        g2.drawString("白方时间：" + whiteTimeMessage,360,580);
        g2.fillRect(500,110,150,50);
        g2.fillRect(500,200,150,50);
        g2.fillRect(500,290,150,50);
        g2.fillRect(500,380,150,50);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("宋体",Font.BOLD,24));
        g2.drawString("开始游戏",525,142);
        g2.drawString("游戏设置",525,232);
        g2.drawString("认    输",525,322);
        g2.drawString("退出游戏",525,412);
        g2.setColor(Color.BLACK);
        //绘制棋盘
        for (int i = 0;i < 19;i++){
            g2.drawLine(60,110 + 20 * i,440,110 + 20 * i);
        }
        for (int i = 0;i <= 19;i++){
            g2.drawLine(60 + 20 * i,110,60 + 20 * i,470);
        }
        //点位
        g2.fillOval(117,167,6,6);
        g2.fillOval(377,407,6,6);
        g2.fillOval(377,167,6,6);
        g2.fillOval(117,407,6,6);
        g2.fillOval(237,167,6,6);
        g2.fillOval(377,287,6,6);
        g2.fillOval(117,287,6,6);
        g2.fillOval(237,287,6,6);

        //绘制棋子
        for (int i = 0;i < 19;i++){
            for (int j = 0;j < 19;j++){
                if (allChess[i][j] == 1){
                    //黑
                    int tempX = i * 20 + 60;
                    int tempY = j * 20 + 110;
                    g2.fillOval(tempX-7,tempY-7,14,14);
                }
                if (allChess[i][j] == 2){
                    //白
                    int tempX = i * 20 + 60;
                    int tempY = j * 20 + 110;
                    g2.setColor(Color.WHITE);
                    g2.fillOval(tempX-7,tempY-7,14,14);
                    g2.setColor(Color.BLACK);
                    g2.drawOval(tempX-7,tempY-7,14,14);
                }
            }
        }
        g.drawImage(bufferedImage,0,0,this);
    }

    /**
     * @Explain 监听鼠标点击事件
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("点击事件："+e.getX()+","+e.getY());
    }
    /**
     * @Explain 监听鼠标按下事件
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (canPlay == true){
            x = e.getX();
            y = e.getY();
            if (x >= 60 && x <= 440 && y >= 110 && y <= 470) {
                //判断距离最近的交叉点
                x = (int) Math.round((x - 60) / 20.0);
                y = (int) Math.round(((y - 110)) / 20.0);
                //判断是否有棋子
                if (allChess[x][y] == 0) {
                    //判断棋子颜色
                    if (isBlack == true) {
                        allChess[x][y] = 1;
                        isBlack = false;
                        message = "白方走棋";
                    } else {
                        allChess[x][y] = 2;
                        isBlack = true;
                        message = "黑方走棋";
                    }
                    this.repaint();
                    //判断是否胜利
                    boolean victory = this.isVictory();
                    if (victory == true) {
                        JOptionPane.showMessageDialog(this, "游戏结束" + (allChess[x][y] == 1 ? "黑棋" : "白棋") + "胜利！");
                        canPlay = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "当前位置已有棋子，请重新落子。");
                }
            }
        }
        //按钮功能
        //开始
        if(e.getX() >= 500 && e.getX() <= 650 && e.getY() >= 110 && e.getY() <= 160){
            int result = JOptionPane.showConfirmDialog(this,"是否重新开始游戏？");
            if (result == 0){
                //1. 清空棋盘 allChess[][];
                allChess = new int[19][19];
                //2. 提示信息初始化
                message = "黑方走棋";
                //3. 标记黑棋或白棋初始化
                isBlack = true;
                blackTime = maxTime;
                whiteTime = maxTime;
                if (maxTime == 0){
                    blackTimeMessage = "无限制";
                    whiteTimeMessage = "无限制";
                }else {
                    whiteTimeMessage = maxTime / 3600 +":"+(maxTime / 60 - maxTime / 3600 * 60)+":"+(maxTime - maxTime / 60 * 60);
                    blackTimeMessage = maxTime / 3600 +":"+(maxTime / 60 - maxTime / 3600 * 60)+":"+(maxTime - maxTime / 60 * 60);
                    t.resume();
                }
                this.repaint();
            }
        }
        //游戏设置
        if(e.getX() >= 500 && e.getX() <= 650 && e.getY() >= 200 && e.getY() <= 250){
            String input = JOptionPane.showInputDialog("请输入走棋时间（单位：分钟，输入0，表示无时间限制）");
            try {
                maxTime = Integer.parseInt(input) * 60;
                if (maxTime < 0){
                    JOptionPane.showMessageDialog(this,"请正确输入时间大于0。");
                }
                if ( maxTime > 0){
                    int result = JOptionPane.showConfirmDialog(this,"设置完成，是否重新开始游戏？");
                    if (result == 0){
                        //1. 清空棋盘 allChess[][];
                        allChess = new int[19][19];
                        //2. 提示信息初始化
                        message = "黑方走棋";
                        //3. 标记黑棋或白棋初始化
                        isBlack = true;
                        blackTime = maxTime;
                        whiteTime = maxTime;
                        whiteTimeMessage = maxTime / 3600 +":"+(maxTime / 60 - maxTime / 3600 * 60)+":"+(maxTime - maxTime / 60 * 60);
                        blackTimeMessage = maxTime / 3600 +":"+(maxTime / 60 - maxTime / 3600 * 60)+":"+(maxTime - maxTime / 60 * 60);
                        t.resume();
                        this.repaint();
                    }
                }
                if (maxTime == 0 ){
                    int result = JOptionPane.showConfirmDialog(this,"设置完成，是否重新开始游戏？");
                    if (result == 0){
                        //1. 清空棋盘 allChess[][];
                        allChess = new int[19][19];
                        //2. 提示信息初始化
                        message = "黑方走棋";
                        //3. 标记黑棋或白棋初始化
                        isBlack = true;
                        blackTime = maxTime;
                        whiteTime = maxTime;
                        blackTimeMessage = "无限制";
                        whiteTimeMessage = "无限制";
                        this.repaint();
                    }
                }
            }catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(this,"请正确输入信息！");
            }
        }
        //认    输
        if(e.getX() >= 500 && e.getX() <= 650 && e.getY() >= 290 && e.getY() <= 340){
            int result = JOptionPane.showConfirmDialog(this,"是否认输");
            if (result == 0){
                JOptionPane.showMessageDialog(this,(isBlack ? "黑" : "白")+"棋认输，游戏结束。");
                canPlay = false;
            }
        }
        //退出游戏
        if(e.getX() >= 500 && e.getX() <= 650 && e.getY() >= 380 && e.getY() <= 420){
            JOptionPane.showMessageDialog(this, "退出游戏");
            System.exit(0);
        }
    }
    /**
     * @Explain 监听鼠标离开事件
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    /**
     * @Explain 监听鼠标进入事件
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    /**
     * @Explain 监听鼠标抬起事件
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }
    //判断是否胜利 isVictory
    private Boolean isVictory(){

        boolean flag = false;
        //保持相连数
        int count = 1;
        int color = allChess[x][y];
        // 判断横向的棋子
        count = this.checkCount(1,0,color);
        if (count >= 5){
            flag = true;
        }else {
            // 判断纵向的棋子
            count = this.checkCount(0,1,color);
            if (count >= 5){
                flag = true;
            }else{
                // 判断右斜向的棋子
                count = this.checkCount(1,-1,color);
                if (count >= 5){
                    flag = true;
                }else {
                    // 判断左斜向的棋子
                    count = this.checkCount(1,1,color);
                    if (count >= 5){
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }
    //判断棋子数量
    private int checkCount(int xChange,int yChange,int color){
        int count = 1;
        int tempX = xChange;
        int tempY = yChange;
        while (x + xChange >=0 && x + xChange <= 18 && y + yChange >=0 && y + yChange <= 18 && color == allChess[x + xChange][y + yChange]){
            count++;
            if (xChange != 0)
                xChange++;
            if (yChange != 0){
                if (yChange > 0)
                    yChange++;
                else
                    yChange--;
            }
        }
        xChange = tempX;
        yChange = tempY;
        while (x - xChange >=0 && x - xChange <= 18 && y - yChange >=0 && y - yChange <= 18 && color == allChess[x - xChange][y - yChange]){
            count++;
            if (xChange != 0)
                xChange++;
            if (yChange != 0){
                if (yChange > 0)
                    yChange++;
                else
                    yChange--;
            }
        }
        return count;
    }

    @Override
    public void run() {
        if (maxTime > 0){
            while (true){
                if (isBlack){
                    blackTime--;
                    if (blackTime == 0){
                        JOptionPane.showMessageDialog(this, "黑方超时，游戏结束！");
                        canPlay = false;
                        t.suspend();//挂起
                    }
                }else {
                    whiteTime--;
                    if (whiteTime == 0){
                        JOptionPane.showMessageDialog(this, "白方超时，游戏结束！");
                        canPlay = false;
                        t.suspend();//挂起
                    }
                }
                whiteTimeMessage = whiteTime / 3600 +":"+(whiteTime / 60 - whiteTime / 3600 * 60)+":"+(whiteTime - whiteTime / 60 * 60);
                blackTimeMessage = blackTime / 3600 +":"+(blackTime / 60 - blackTime / 3600 * 60)+":"+(blackTime - blackTime / 60 * 60);
                this.repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(blackTime+"-------"+whiteTime);
            }
        }
    }
}
