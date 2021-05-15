abstract class Character {
    

    protected String name;
    protected int hp;
    abstract int[] attack(int act);
    abstract String[] act_list();
    abstract int gethp();
    abstract void sethp(int hp);
    abstract String getPName();


}
