package bean;


import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private static final long serialVersionUID = 6374413683094793576L;
    private Integer id;
    private String name;
    private Character gender;
    private Integer age;
    private String phone;
    private List<String> hobby; // 爱好
    private Address address; // 地址
    private Integer status; // 用户状态

    public User() {
    }

    public User(Integer id, String name, Character gender, Integer age, String phone, Integer status) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.status = status;
    }

    public User(Integer id, String name, Character gender, Integer age, String phone, List<String> hobby, Address address, Integer status) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.hobby = hobby;
        this.address = address;
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", hobby=" + hobby +
                ", address=" + address +
                ", status=" + status +
                '}';
    }
}
