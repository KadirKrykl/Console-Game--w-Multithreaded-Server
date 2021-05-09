import java.util.Random;


public class Boss extends Character{

    protected String name;
    protected int hp=100;



    public Boss(String name){
        this.name=name;
    }
    



    public int attack(String act){
        Random rand = new Random();
        int dice = rand.nextInt(24);
        dice=dice+1;
        System.out.println("your dice : " + dice); 
        int hit = 5;
        if(dice>= 12){
            System.out.println( name + " attacked." ); 
            System.out.println(name + " hit : " + hit + " HP"); 
            return hit;
        }else{
            System.out.println(name +  "attack missed"); 
            return 0;
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

    @Override
    void act_list() {
        // TODO Auto-generated method stub
        
    }
}
