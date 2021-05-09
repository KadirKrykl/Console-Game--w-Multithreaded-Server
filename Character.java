abstract class Character {
    

    protected String name;
    protected int hp;
    abstract int attack(String act);
    abstract void act_list();
    abstract int gethp();
    abstract void sethp(int hp);
    abstract String getName();


}
