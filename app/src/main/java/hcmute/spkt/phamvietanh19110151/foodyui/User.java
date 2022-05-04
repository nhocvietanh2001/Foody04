package hcmute.spkt.phamvietanh19110151.foodyui;

public class User {
    private String phone;
    private String name;
    private String pass;
    private String address;

    public User(String phone, String name, String pass, String address) {
        this.phone = phone;
        this.name = name;
        this.pass = pass;
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
