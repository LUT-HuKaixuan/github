package entity;

public class Manager {
    private String hukaixuan_managerid;
    private String hukaixuan_password;

    public Manager() {
    }

    public String getHukaixuan_managerid() {
        return hukaixuan_managerid;
    }

    public void setHukaixuan_managerid(String hukaixuan_managerid) {
        this.hukaixuan_managerid = hukaixuan_managerid;
    }

    public String getHukaixuan_password() {
        return hukaixuan_password;
    }

    public void setHukaixuan_password(String hukaixuan_password) {
        this.hukaixuan_password = hukaixuan_password;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "hukaixuan_managerid='" + hukaixuan_managerid + '\'' +
                ", hukaixuan_password='" + hukaixuan_password + '\'' +
                '}';
    }
}