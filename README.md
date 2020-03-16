# gobang
java 五子棋游戏 

## 功能流程
1） 鼠标点击时，在相应的位置显示棋子。<br/>
2） 可以判断游戏是否结束，判断黑方或白方胜利。<br/>
3） 下棋时间是否超时。<br/>
## 按钮功能
1）开    始
> 重新开始游戏

2）游戏设置`线程`
> 设置倒计时时间

3）认    输
> 放弃游戏

4）退出游戏
> 结束程序

## 界面设置
 1. JFrame的swing类创建窗体。
 ```
 	# 设置标题、宽度、高度、窗体显示位置
 	JFrame jFrame = new JFrame();

 ```
2. 通过鼠标监听器监听鼠标操作`（下棋操作）`
```$xslt
    # implements MouseListener
    mouseClicked    #监听鼠标点击事件
    
```
3. 判断棋子位置`(MouseEvent)`
```$xslt
    # getX()    X坐标
    # getY()    Y坐标
```
4. 绘制棋子`(Graphics)`
```$xslt
    1）drawString(x,y);     #绘制字符串
    2）drawOval(x,y,width,height);       #绘制园形（空心）
    3）fllOval(x,y,width,height);        #绘制园形（实心）
    4）drawLine(x1,y1,x2,y2);       #绘制一条线
    5）drawRect(x,y,width,height);       #绘制矩形（空心）
    6）fillRect(x,y,width,height);        #绘制矩形（实心）
    7）drawImage(img,x,y);      #绘制图片
    8）setColor()        #设置颜色
    9）setFont()         #设置文字大小
```
5. 绘制棋盘`(20 * 20)`
```$xslt
    # 总宽400,每一个是20px
    # 总高400,每一个是20px
```
6. 下棋功能
```$xslt
    paint
```
7. 处理屏幕闪烁问题。
```$xslt
    //双缓冲技术防止屏幕闪烁
    BufferedImage bufferedImage = new BufferedImage(800,600,BufferedImage.TYPE_INT_ARGB);
    Graphics g2 = bufferedImage.createGraphics();
```
