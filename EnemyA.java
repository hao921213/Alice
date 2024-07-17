
public class EnemyA extends Creature{
    int money;//被擊殺時掉落金幣
    int idx=0;
    boolean canmove=false;
    public EnemyA(int x,int y){
        this.x=x;
        this.y=y;
        this.attack=3;
        this.hp=6;
        this.die=false;
    }

    public int move(int player_x, int player_y) {
        if (x < player_x) {
            x++;
            idx=(idx+1)%6+18;
            return idx;
        } else if (x > player_x) {
            x--;
            idx=(idx+1)%6+6;
            return idx;
        }
        if (y < player_y) {
            y++;
            idx=(idx+1)%6+12;
            return idx;
        } else if (y > player_y) {
            y--;
            idx=(idx+1)%6;
            return idx;
        }
        return 9; 
    }

    public boolean getCanMove(){
        return canmove;
    }

    public void setCanMove(boolean canmove){
        this.canmove=canmove;
    }

}
