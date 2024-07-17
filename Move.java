import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Move extends JFrame {
    MyPanel mp = new MyPanel();
    public int width = 400;
    public int height = 400;

    public Move() {
        this.add(mp);
        this.setSize(width, height);
        this.addKeyListener(mp);
        this.setTitle("Move");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}

class MyPanel extends JPanel implements KeyListener {

    ArrayList<ArrayList<String>> map=new ArrayList<>();
    ArrayList<ArrayList<String>> big_map=new ArrayList<>();
    ArrayList<ArrayList<String>> ghost_map=new ArrayList<>();
    ArrayList<ArrayList<String>> other_map=new ArrayList<>();
    int curkeycode=0;


    ArrayList<EnemyA> enemyAs;
    int[] eneA_currentindex;

    ArrayList<EnemyB> enemyBs;
    int[] eneB_currentindex;
    
    int elapsedSeconds=0;
    Timer t=null;
    Timer t_attack=null;
    Timer ene_attack=null;
    BufferedImage overlayImage;

    boolean win=false;

    public MyPanel(){

        // 計時器
        Timer timer = new Timer(1000, (ActionEvent e) -> {
            if(elapsedSeconds<300){
                elapsedSeconds++;
            }
            else{
                elapsedSeconds=0;
            }

            repaint();
        });
        timer.start();


        try{
            MapIn mapin=new MapIn();
            map=mapin.getMap();
            big_map=mapin.getBig_Map();
            ghost_map=mapin.getGhost_Map();
            other_map=mapin.getOther_Map();
        }
        catch(IOException e){
            System.out.println("error");
        }

        enemyAs=new ArrayList<>();
        for(int i=0;i<ghost_map.size();i++){
            for(int j=0;j<ghost_map.get(0).size();j++){
                if(ghost_map.get(j).get(i).equals("4")){
                    EnemyA enemy=new EnemyA(40*i, 40*j);
                    enemyAs.add(enemy);
                }
            }
        }

        eneA_currentindex=new int[enemyAs.size()];

        for(int i=0;i<enemyAs.size();i++){

            final int index=i;


            t = new Timer(250, (ActionEvent e) -> {
                if(enemyAs.get(index).getCanMove() && !enemyAs.get(index).getDie()){
                    eneA_currentindex[index]=enemyAs.get(index).move(hero.getX(), hero.getY());
                    if((Math.abs(hero.getX()-enemyAs.get(index).getX()) + Math.abs(hero.getY()-enemyAs.get(index).getY())<=0) && !enemyBs.get(index).getDie()){
                        eneA_currentindex[index]=23;
                        if(ene_attack==null || !ene_attack.isRunning()){
                            ene_attack = new Timer(500, (ActionEvent a) -> {
                                eneA_currentindex[index]++;
                                repaint();
                                if(eneA_currentindex[index]==26){
                                    ene_attack.stop();
                                    ene_attack=null;
                                    eneA_currentindex[index]=0;
                                }
                                repaint();
                            });
                            ene_attack.start();
                        }
                        hero.setHp(hero.getHp()-enemyAs.get(index).getAttack());
                        if(hero.getHp()<=0){
                            hero.setHp(0);
                            hero.setDie(true);
                            timer.stop();
                        }
                    }
                }
                else{
                    if(Math.abs(hero.getX()-enemyAs.get(index).getX()) + Math.abs(hero.getY()-enemyAs.get(index).getY())<=100){
                        enemyAs.get(index).setCanMove(true);
                    }
                }

                repaint();
            });
            t.start();            

        }


        enemyBs=new ArrayList<>();
        for(int i=0;i<ghost_map.size();i++){
            for(int j=0;j<ghost_map.get(0).size();j++){
                if(ghost_map.get(j).get(i).equals("2")){
                    EnemyB enemy=new EnemyB(40*i, 40*j);
                    enemyBs.add(enemy);
                }
            }
        }

        eneB_currentindex=new int[enemyBs.size()];

        for(int i=0;i<enemyBs.size();i++){
            final int index=i;

            t = new Timer(200, (ActionEvent e) -> {
                if(enemyBs.get(index).getCanMove()){
                    eneB_currentindex[index]=enemyBs.get(index).move(hero.getX(), hero.getY());
                    if((Math.abs(hero.getX()-enemyBs.get(index).getX()) + Math.abs(hero.getY()-enemyBs.get(index).getY())<=0) && !enemyBs.get(index).getDie()) {
                        hero.setHp(hero.getHp()-enemyBs.get(index).getAttack());
                        if(hero.getHp()<=0){
                            hero.setHp(0);
                            hero.setDie(true);
                            timer.stop();
                        }
                    }
                }
                else{
                    if(Math.abs(hero.getX()-enemyBs.get(index).getX())+ Math.abs(hero.getY()-enemyBs.get(index).getY())<=100){
                        enemyBs.get(index).setCanMove(true);
                    }
                }
                repaint();
            });
            t.start();            
        }


        overlayImage = createOverlayImage(400, 400);

    }


    Hero hero=new Hero();
    int speed = 2;
    int heroWidth=60;
    int heroHeight=60;
    int hero_currentindex=0;
    Image[] hero_image={
        //移動(56)
        new ImageIcon("主角移動\\主角右向動畫1.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫2.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫3.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫4.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫5.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫6.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫7.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫8.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫9.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫10.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫11.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫12.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫13.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫14.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫15.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫16.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫1.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫2.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫3.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫4.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫5.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫6.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫7.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫8.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫9.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫10.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫11.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫12.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫13.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫14.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫15.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫16.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫1.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫2.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫3.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫4.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫5.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫6.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫7.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫8.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫9.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫10.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫11.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫12.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫13.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫14.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫15.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫16.png").getImage(),
        new ImageIcon("主角移動\\主角下走1.png").getImage(),
        new ImageIcon("主角移動\\主角下走2.png").getImage(),
        new ImageIcon("主角移動\\主角下走3.png").getImage(),
        new ImageIcon("主角移動\\主角下走4.png").getImage(),
        new ImageIcon("主角移動\\主角下走5.png").getImage(),
        new ImageIcon("主角移動\\主角下走6.png").getImage(),
        new ImageIcon("主角移動\\主角下走7.png").getImage(),
        new ImageIcon("主角移動\\主角下走8.png").getImage(),
        //攻擊(25)
        new ImageIcon("主角移動\\主角向上動畫17.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫18.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫19.png").getImage(),
        new ImageIcon("主角移動\\主角向上動畫20.png").getImage(),
        new ImageIcon("主角移動\\主角下走9.png").getImage(),
        new ImageIcon("主角移動\\主角下走10.png").getImage(),
        new ImageIcon("主角移動\\主角下走11.png").getImage(),
        new ImageIcon("主角移動\\主角下走12.png").getImage(),
        new ImageIcon("主角移動\\主角下走13.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫17.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫18.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫19.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫20.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫21.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫22.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫23.png").getImage(),
        new ImageIcon("主角移動\\主角右向動畫24.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫17.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫18.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫19.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫20.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫21.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫22.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫23.png").getImage(),
        new ImageIcon("主角移動\\主角左向動畫24.png").getImage(),
        //待機(8)
        new ImageIcon("主角移動\\主角待機新版1.png").getImage(),
        new ImageIcon("主角移動\\主角待機新版2.png").getImage(),
        new ImageIcon("主角移動\\主角待機新版3.png").getImage(),
        new ImageIcon("主角移動\\主角待機新版4.png").getImage(),
        new ImageIcon("主角移動\\主角待機新版5.png").getImage(),
        new ImageIcon("主角移動\\主角待機新版6.png").getImage(),
        new ImageIcon("主角移動\\主角待機新版7.png").getImage(),
        new ImageIcon("主角移動\\主角待機新版8.png").getImage(),



    };


    int eneAWidth=60;
    int eneAHeigth=60;
    Image[] eneA_image={
        //移動
        new ImageIcon("鬼移動\\倒走1.png").getImage(),
        new ImageIcon("鬼移動\\倒走2.png").getImage(),
        new ImageIcon("鬼移動\\倒走3.png").getImage(),
        new ImageIcon("鬼移動\\倒走4.png").getImage(),
        new ImageIcon("鬼移動\\倒走5.png").getImage(),
        new ImageIcon("鬼移動\\倒走6.png").getImage(),
        new ImageIcon("鬼移動\\怪物向左1.png").getImage(),
        new ImageIcon("鬼移動\\怪物向左2.png").getImage(),
        new ImageIcon("鬼移動\\怪物向左3.png").getImage(),
        new ImageIcon("鬼移動\\怪物向左4.png").getImage(),
        new ImageIcon("鬼移動\\怪物向左5.png").getImage(),
        new ImageIcon("鬼移動\\怪物向左6.png").getImage(),
        new ImageIcon("鬼移動\\正走1.png").getImage(),
        new ImageIcon("鬼移動\\正走2.png").getImage(),
        new ImageIcon("鬼移動\\正走3.png").getImage(),
        new ImageIcon("鬼移動\\正走4.png").getImage(),
        new ImageIcon("鬼移動\\正走3.png").getImage(),
        new ImageIcon("鬼移動\\正走4.png").getImage(),
        new ImageIcon("鬼移動\\怪物向右1.png").getImage(),
        new ImageIcon("鬼移動\\怪物向右2.png").getImage(),
        new ImageIcon("鬼移動\\怪物向右3.png").getImage(),
        new ImageIcon("鬼移動\\怪物向右4.png").getImage(),
        new ImageIcon("鬼移動\\怪物向右5.png").getImage(),
        new ImageIcon("鬼移動\\怪物向右6.png").getImage(),
        //攻擊
        new ImageIcon("鬼移動\\怪物向右7.png").getImage(),
        new ImageIcon("鬼移動\\怪物向右8.png").getImage(),
        new ImageIcon("鬼移動\\怪物向右9.png").getImage(),
        new ImageIcon("鬼移動\\怪物向左7.png").getImage(),
        new ImageIcon("鬼移動\\怪物向左8.png").getImage(),
        new ImageIcon("鬼移動\\怪物向左9.png").getImage(),

    };

    int eneBWidth=40;
    int eneBHeigth=40;
    Image[] eneB_image={
        new ImageIcon("桶子鬼移動\\桶子鬼1.png").getImage(),
        new ImageIcon("桶子鬼移動\\桶子鬼2.png").getImage(),
        new ImageIcon("桶子鬼移動\\桶子鬼3.png").getImage(),
        new ImageIcon("桶子鬼移動\\桶子鬼4.png").getImage(),
        new ImageIcon("桶子鬼移動\\桶子鬼5.png").getImage()
    };


    Image[] map_Images={
        new ImageIcon("map\\藤蔓石牆.png").getImage(),
        new ImageIcon("map\\石牆.png").getImage(),
        new ImageIcon("map\\泥土路.png").getImage(),
        new ImageIcon("酒桶.png").getImage()
        };



    public BufferedImage createOverlayImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
    

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
    

        g2d.translate(-20, -10);
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillOval(hero.getX(), hero.getY(), 100, 100);
        AlphaComposite composite;
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
        g2d.setComposite(composite);
        g2d.fillOval(hero.getX(), hero.getY(), 100, 100);


        g2d.translate(10, 10);
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillOval(hero.getX(), hero.getY(), 80, 80);
    
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
        g2d.setComposite(composite);
        g2d.fillOval(hero.getX(), hero.getY(), 80, 80);


        g2d.dispose();
        return image;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 畫出地圖
        for(int i=0;i<map.size();i++){
            for(int j=0;j<map.get(0).size();j++){
                if(map.get(j).get(i).equals("1")){
                    g.drawImage(map_Images[0], 40*i, 40*j, 40, 40, this);
                }
                else if(map.get(j).get(i).equals("0")){
                    g.drawImage(map_Images[2], 40*i, 40*j, 40, 40, this);
                }
            }
        }

        for(int i=0;i<ghost_map.size();i++){
            for(int j=0;j<ghost_map.get(0).size();j++){
                if(ghost_map.get(j).get(i).equals("3")){
                    g.drawImage(map_Images[3], 40*i, 40*j, 40, 40, this);
                }
            }
        }


        Graphics2D g1d = (Graphics2D) g.create();
        g1d.translate(0, 0);
        for(int i=0;i<enemyAs.size();i++){
            if(!enemyAs.get(i).getDie()){
                g1d.drawImage(eneA_image[eneA_currentindex[i]], enemyAs.get(i).getX(), enemyAs.get(i).getY(), eneAWidth, eneAHeigth, this);
            }
        }

        for(int i=0;i<enemyBs.size();i++){
            if(!enemyBs.get(i).getDie()){
                g1d.drawImage(eneB_image[eneB_currentindex[i]], enemyBs.get(i).getX(), enemyBs.get(i).getY(), eneBWidth, eneBHeigth, this);
            }
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(0,0);
        if(!hero.getDie()){
            g2d.drawImage(hero_image[hero_currentindex], hero.getX(), hero.getY(), heroWidth, heroHeight, this);
        }


        Graphics2D g3d = (Graphics2D) g;
        g3d.drawImage(overlayImage, 0, 0, null);


        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("time:"+(300-elapsedSeconds), 200, 20);
        g.drawString("Hp:"+hero.getHp(), 300, 20);

        if(hero.getDie() || elapsedSeconds==300){
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            g2d.drawString("game over!", 50, 200);
        }
        else if(win){
            g2d.setFont(new Font("Arial", Font.BOLD, 50));
            g2d.drawString("game win!", 50, 200);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int newX = hero.getX();
        int newY = hero.getY();


        if (keyCode == KeyEvent.VK_DOWN && t_attack==null) {
            newY += speed;
            hero_currentindex=(hero_currentindex+1)%8+48;
            // System.out.println(hero_currentindex);

        } else if (keyCode == KeyEvent.VK_UP && t_attack==null) {
            newY -= speed;
            hero_currentindex=(hero_currentindex+1)%16+32;
            // System.out.println(hero_currentindex);


        } else if (keyCode == KeyEvent.VK_RIGHT && t_attack==null) {
            newX += speed;
            hero_currentindex=(hero_currentindex+1)%16;
            // System.out.println(hero_currentindex);

        } else if (keyCode == KeyEvent.VK_LEFT&& t_attack==null) {
            newX -= speed;
            hero_currentindex=(hero_currentindex+1)%16+16;
            // System.out.println(hero_currentindex);

        }


        if(keyCode==KeyEvent.VK_F){
            if(curkeycode==KeyEvent.VK_UP){
                for(int i=0;i<enemyAs.size();i++){
                    if(enemyAs.get(i).getY()-hero.getY()<=40 && enemyAs.get(i).getY()-hero.getY()>=0 && Math.abs(enemyAs.get(i).getX()-hero.getX())<=20 && !enemyAs.get(i).getDie()){
                        enemyAs.get(i).setHp(enemyAs.get(i).getHp()-hero.getAttack());
                        if(enemyAs.get(i).getHp()<=0){
                            enemyAs.get(i).setDie(true);
                        }
                    }
                }
                for(int i=0;i<enemyBs.size();i++){
                    if(enemyBs.get(i).getY()-hero.getY()<=40 && enemyBs.get(i).getY()-hero.getY()>=0  && Math.abs(enemyBs.get(i).getX()-hero.getX())<=20 && !enemyBs.get(i).getDie()){
                        enemyBs.get(i).setHp(enemyBs.get(i).getHp()-hero.getAttack());
                        if(enemyBs.get(i).getHp()<=0){
                            enemyBs.get(i).setDie(true);
                        }
                    }
                }
                if(t_attack==null || !t_attack.isRunning()){
                    hero_currentindex=56;
                    t_attack = new Timer(50, (ActionEvent a) -> {
                        hero_currentindex++;
                        // System.out.println(hero_currentindex);
                        repaint();
                        if(hero_currentindex==59){
                            t_attack.stop();
                            t_attack=null;
                            hero_currentindex=81;

                        }
                    });
                    t_attack.start();
                }
            }
            else if(curkeycode==KeyEvent.VK_DOWN){
                for(int i=0;i<enemyAs.size();i++){
                    if(hero.getY()-enemyAs.get(i).getY()<=0 && hero.getY()-enemyAs.get(i).getY()>=-40  && Math.abs(enemyAs.get(i).getX()-hero.getX())<=20 && !enemyAs.get(i).getDie()){
                        enemyAs.get(i).setHp(enemyAs.get(i).getHp()-hero.getAttack());
                        if(enemyAs.get(i).getHp()<=0){
                            enemyAs.get(i).setDie(true);
                        }
                    }
                }
                for(int i=0;i<enemyBs.size();i++){
                    if(hero.getY()-enemyBs.get(i).getY()<=0 && hero.getY()-enemyBs.get(i).getY()>=-40  && Math.abs(enemyBs.get(i).getX()-hero.getX())<=20 && !enemyBs.get(i).getDie()){
                        enemyBs.get(i).setHp(enemyBs.get(i).getHp()-hero.getAttack());
                        if(enemyBs.get(i).getHp()<=0){
                            enemyBs.get(i).setDie(true);
                        }
                    }
                }
                if(t_attack==null || !t_attack.isRunning()){
                    hero_currentindex=60;
                    t_attack = new Timer(50, (ActionEvent a) -> {
                        hero_currentindex++;
                        // System.out.println(hero_currentindex);
                        repaint();
                        if(hero_currentindex==64){
                            t_attack.stop();
                            t_attack=null;
                            hero_currentindex=81;
                        }
                    });
                    t_attack.start();
                }
            }
            else if(curkeycode==KeyEvent.VK_RIGHT){
                for(int i=0;i<enemyAs.size();i++){
                    if(enemyAs.get(i).getX()-hero.getX()<=40 && enemyAs.get(i).getX()-hero.getX()>=0 && Math.abs(enemyAs.get(i).getY()-hero.getY())<=20 && !enemyAs.get(i).getDie()){
                        enemyAs.get(i).setHp(enemyAs.get(i).getHp()-hero.getAttack());
                        if(enemyAs.get(i).getHp()<=0){
                            enemyAs.get(i).setDie(true);
                        }
                    }
                }
                for(int i=0;i<enemyBs.size();i++){
                    if(enemyBs.get(i).getX()-hero.getX()<=40 && enemyBs.get(i).getX()-hero.getX()>=0  && Math.abs(enemyBs.get(i).getY()-hero.getY())<=20 && !enemyBs.get(i).getDie()){
                        enemyBs.get(i).setHp(enemyBs.get(i).getHp()-hero.getAttack());
                        if(enemyBs.get(i).getHp()<=0){
                            enemyBs.get(i).setDie(true);
                        }
                    }
                }
                if(t_attack==null || !t_attack.isRunning()){
                    hero_currentindex=65;
                    t_attack = new Timer(50, (ActionEvent a) -> {
                        hero_currentindex++;
                        repaint();
                        if(hero_currentindex==72){
                            t_attack.stop();
                            t_attack=null;
                            hero_currentindex=81;
                        }
                    });
                    t_attack.start();
                }
            }
            else if(curkeycode==KeyEvent.VK_LEFT){
                for(int i=0;i<enemyAs.size();i++){
                    if(hero.getX()-enemyAs.get(i).getX()<=0 && hero.getX()-enemyAs.get(i).getX()>=-40 && Math.abs(enemyAs.get(i).getY()-hero.getY())<=20 && !enemyAs.get(i).getDie()){
                        enemyAs.get(i).setHp(enemyAs.get(i).getHp()-hero.getAttack());
                        if(enemyAs.get(i).getHp()<=0){
                            enemyAs.get(i).setDie(true);
                        }
                    }
                }
                for(int i=0;i<enemyBs.size();i++){
                    if(hero.getX()-enemyBs.get(i).getX()<=0 && hero.getX()-enemyBs.get(i).getX()>=-40  && Math.abs(enemyBs.get(i).getY()-hero.getY())<=20 && !enemyBs.get(i).getDie()){
                        enemyBs.get(i).setHp(enemyBs.get(i).getHp()-hero.getAttack());
                        if(enemyBs.get(i).getHp()<=0){
                            enemyBs.get(i).setDie(true);
                        }
                    }
                }
                if(t_attack==null || !t_attack.isRunning()){
                    hero_currentindex=73;
                    t_attack = new Timer(50, (ActionEvent a) -> {
                        hero_currentindex++;
                        repaint();
                        if(hero_currentindex==80){
                            t_attack.stop();
                            t_attack=null;
                            hero_currentindex=81;
                        }
                    });
                    t_attack.start();
                }
            }
        }        

        // 檢查新位置是否會碰到矩形或邊界
        if (!isCollision(newX, newY)) {
            hero.setX(newX);
            hero.setY(newY);
            if((400<=hero.getX() || hero.getX()>=320)&& (400<=hero.getY() || hero.getY()>=320)){
                win=true;
            }
        }
        if(keyCode==KeyEvent.VK_DOWN || keyCode==KeyEvent.VK_UP || keyCode==KeyEvent.VK_RIGHT || keyCode==KeyEvent.VK_LEFT){
            curkeycode=keyCode;
        }
        overlayImage = createOverlayImage(400, 400);
        this.repaint(); 
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }


    // 碰撞檢測方法
    private boolean isCollision(int newX, int newY) {

        // 邊界檢查
        if (newX < 0 || newX + heroWidth > getWidth() || newY < 0 || newY + 40 > getHeight()) {
            return true;
        }

        // 檢查新位置是否會和矩形A重疊
        if(big_map.get(newY+22).get(newX+20).equals("1") || big_map.get(newY+22).get(newX+45).equals("1")  || big_map.get(newY+55).get(newX+20).equals("1") || big_map.get(newY+55).get(newX+45).equals("1")){
            return true;
        }

        if(other_map.get(newY+22).get(newX+20).equals("3") || other_map.get(newY+22).get(newX+45).equals("3")  || other_map.get(newY+55).get(newX+20).equals("3") || other_map.get(newY+55).get(newX+45).equals("3")){
            return true;
        }

        return false;
    }



}
