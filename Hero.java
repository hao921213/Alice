
public class Hero extends Creature{
    int money;//玩家金幣
    //武器    
    //防具
    //藥水
    public Hero(){
        this.x=0;
        this.y=0;
        this.hp=5;
        this.attack=2;
        this.die=false;
    }
    public void getMoney(int num){
        money+=num;
    }

}
