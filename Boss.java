import java.util.Random;


public class Boss{

    protected String name;
    protected int hp=100;
    protected boolean taunt = false;

    public Boss(String name){
        this.name=name;
    }

    int[] attack(){
        int[] result = new int[2];
        Random rand = new Random();
        int dice = rand.nextInt(24);
        dice=dice+1;
        result[0] = dice;
        int hit = 20;
        if(dice>= 12){
            if(taunt){
                this.taunt();
                result[1] = hit/2;
            }
            else{
                result[1] = hit;
            }
            return result;
        }else{
            result[1] = 5;
            return result;
        } 
    }

    public void sethp(int hp){
        this.hp=hp;
    }

    public int gethp(){
        return hp;
    }

    public String getName(){
        return name;
    }

    public void taunt(){
        if(taunt){
            taunt = false;
        }
        else{
            taunt = true;
        }
    }

}
