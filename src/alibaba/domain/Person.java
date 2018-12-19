package alibaba.domain;

public class Person {
    private String name;
    private String passsword;

    public Person() {
    }

    public Person(String name, String passsword) {
        this.name = name;
        this.passsword = passsword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", passsword='" + passsword + '\'' +
                '}';
    }
}
