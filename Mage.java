import java.util.Random;


public class Mage extends Character{

    protected String name;
    protected int hp = 100;
    protected String[] attacks = {"Normal","Fireball"};


    public Mage(String name) {
        this.name = name;
    }

    int[] attack(int act) {
        int[] result = new int[3];
        Random rand = new Random();
        int dice = rand.nextInt(24);
        dice=dice+1;
        result[0] = dice;
        result[2] = act;
        int hit = 5;
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
        return this.attacks;
    }

    int gethp() {
        return this.hp;
    }

    void sethp(int hp) {
        this.hp = hp;
        
    }

    String getPName() {
        return this.name;
    }
    

}



    

