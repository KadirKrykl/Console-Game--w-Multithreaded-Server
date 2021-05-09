import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner; 

public class main {
    

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Oyuncu sayısı giriniz");
        String gamer_number = scan.nextLine();
        int gamer_num = Integer.parseInt(gamer_number);
        List<Character> character_list = new ArrayList<Character>();
        // karakter yaratış
        for(int i=0; i<gamer_num; i++ ){
            Scanner scan2 = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Nickname giriniz:");
            String nick = scan2.nextLine();
            Scanner scan3 = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Class seçiniz : warrior-mage-archer");
            String hero_class = scan3.nextLine();
            if(hero_class.equals("warrior")){
                Warrior warrior = new Warrior(nick);
                character_list.add(warrior);
            }else if(hero_class.equals("mage")) {
                Mage mage = new Mage(nick);
                character_list.add(mage);
            }else if(hero_class.equals("archer")){
                Archer archer = new Archer(nick);
                character_list.add(archer);
            }else {
                System.out.println("class yanlış yazıldı");
            }
        }
        //boss yaratış
        Boss boss = new Boss("diablo");
        character_list.add(boss);
        // savaş
        int turn=1;
        while((character_list.get(character_list.size()-1).gethp()<=100) & (character_list.get(character_list.size()-1).gethp()>0)){
            System.out.println("turn :"+ turn);
            for(int i =0; i<character_list.size(); i++){
                if(i==character_list.size()-1){
                    Random rand = new Random();
                    int target = rand.nextInt(character_list.size()-1);
                    int hit= character_list.get(i).attack("1");
                    int hp = character_list.get(target).gethp()-hit;
                    character_list.get(target).sethp(hp);
                    System.out.println(character_list.get(target).getName() + "hp is : "+ character_list.get(target).gethp());
                }else{
                    character_list.get(i).act_list();
                    Scanner scan4 = new Scanner(System.in);  // Create a Scanner object
                    System.out.println("Saldırınızı seçiniz :");
                    String act = scan4.nextLine();
                    int hit = character_list.get(i).attack(act);
                    int hp = character_list.get(character_list.size()-1).gethp()-hit;
                    character_list.get(character_list.size()-1).sethp(hp);
                    System.out.println(character_list.get(character_list.size()-1).getName() + "hp is : "+ character_list.get(character_list.size()-1).gethp());
                }
                //Boss ölümü için 'if' yazılcak 'break;'
            }
            turn=turn+1;
        }

    }
}
