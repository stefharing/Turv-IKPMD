package nl.stefharing.turv;

public class Person {

    private String name;
    private int age;

    private int bier;
    private int wijn;
    private int fris;

    public Person(String name, int age, int bier, int wijn, int fris) {
        this.name = name;
        this.age = age;
        this.bier = bier;
        this.wijn = wijn;
        this.fris = fris;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBier() {
        return bier;
    }

    public void setBier(int bier) {
        this.bier = bier;
    }

    public int getWijn() {
        return wijn;
    }

    public void setWijn(int wijn) {
        this.wijn = wijn;
    }

    public int getFris() {
        return fris;
    }

    public void setFris(int fris) {
        this.fris = fris;
    }
}
