public class EnemyB extends Creature{
    int money;//被擊殺時掉落金幣
    int idx=0;
    boolean canmove=false;
    public EnemyB(int x,int y){
        this.x=x;
        this.y=y;
        this.attack=1;
        this.hp=10;
        this.die=false;
    }

    public int move(int player_x, int player_y) {
        if (x < player_x) {
            x++;
        } else if (x > player_x) {
            x--;
        }
        if (y < player_y) {
            y++;
        } else if (y > player_y) {
            y--;
        }
        idx=(idx+1)%4;
        return idx;
    }

    public boolean getCanMove(){
        return canmove;
    }

    public void setCanMove(boolean canmove){
        this.canmove=canmove;
    }

}
