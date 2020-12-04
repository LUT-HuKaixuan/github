package entity;

public class User  {
    private int hukaixuan_id;
    private String hukaixuan_name;
    private String hukaixuan_tel;

    public User() {
    }

    public User(int hukaixuan_id, String hukaixuan_name, String hukaixuan_tel) {
        this.hukaixuan_id = hukaixuan_id;
        this.hukaixuan_name = hukaixuan_name;
        this.hukaixuan_tel = hukaixuan_tel;
    }

    public int getHukaixuan_id() {
        return hukaixuan_id;
    }

    public void setHukaixuan_id(int hukaixuan_id) {
        this.hukaixuan_id = hukaixuan_id;
    }

    public String getHukaixuan_name() {
        return hukaixuan_name;
    }

    public void setHukaixuan_name(String hukaixuan_name) {
        this.hukaixuan_name = hukaixuan_name;
    }

    public String getHukaixuan_tel() {
        return hukaixuan_tel;
    }

    public void setHukaixuan_tel(String hukaixuan_tel) {
        this.hukaixuan_tel = hukaixuan_tel;
    }

    @Override
    public String toString() {
        return "User{" +
                "hukaixuan_id=" + hukaixuan_id +
                ", hukaixuan_name='" + hukaixuan_name + '\'' +
                ", hukaixuan_tel='" + hukaixuan_tel + '\'' +
                '}';
    }
}
