import java.util.Random;


public class Warrior extends Character{

    protected String name;
    protected int hp=100;


    public Warrior(String name) {
        this.name=name;
    }

    @Override
    int attack(String act) {
        if (act.equals("1")){
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
                System.out.println("your attack missed"); 
                return 0;
            }
        }else if(act.equals("2")){
                Random rand = new Random();
                int dice = rand.nextInt(24);
                dice=dice+1;
                System.out.println("your dice : " + dice); 
                int taunt = 1;
                if(dice>= 12){
                    System.out.println( name + " taunt to enemy." ); 
                    return taunt;
                }else{
                    System.out.println("your attack missed"); 
                    return 0;
                } 
        }else{
            System.out.println("skill yanlış girildi");
            return 0;
        }
    }
     
    public void act_list(){
        System.out.println("1 : Normal Attack , 2: taunt");
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
        
    
}

    


    


