
public class Creature{
    int hp;//血量
    int defense;//防禦力
    int attack;//攻擊力
    int x;
    int y;
    boolean die;
    //武器
    
    //取得數值
    public int getHp(){
        return hp;
    }
    public int getDefense(){
        return defense;
    }
    public int getAttack(){
        return attack;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean getDie(){
        return die;
    }


    //更改數值
    public void setHp(int num){
        hp=num;
    }
    public void setDefense(int num){
        defense=num;
    }
    public void setX(int num){
        x=num;
    }
    public void setY(int num){
        y=num;
    }
    public void setDie(boolean die){
        this.die=die;
    }
}