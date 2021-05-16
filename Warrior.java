import java.util.Random;


public class Warrior extends Character{

    protected String name;
    protected int hp=100;
    protected String[] attacks = {"Normal","Taunt"};


    public Warrior(String name) {
        this.name=name;
    }

    @Override
    int[] attack(int act) {
        int[] result = new int[3];
        Random rand = new Random();
        int dice = rand.nextInt(24);
        dice=dice+1;
        result[0] = dice;
        result[2] = act;
        int hit = 5;
        if(act == 0){
            hit = 5;
        }
        else{
            hit = 10;
        }
        if(dice >= 12){
            result[1] = hit;
            return result;
        }
        else{
            result[1] = 0;
            return result;
        }
    }
     
    public String[] act_list(){
        return attacks;
    }

    public void sethp(int hp){
        this.hp=hp;
    }
    public int gethp(){
        return hp;
    }

    public String getPName(){
        return name;
    }
        
    
}

    


    


