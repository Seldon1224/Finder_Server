package edu.swjtu.finderapp.pojo;

import javax.persistence.*;

@Entity
@Table(name = "table_user_info")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long user_id;
    private String user_name;
    private String user_password;
    private String user_phone;
    private String user_pic_path;

    public  User(){}

    public String getUser_pic_path() {
        return user_pic_path;
    }

    public void setUser_pic_path(String user_pic_path) {
        if(user_pic_path!=null)
            this.user_pic_path = user_pic_path;
        else
            this.user_pic_path = "null";
    }

    public User(String user_name, String user_password, String user_phone, String user_pic_path) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_phone = user_phone;
        this.user_pic_path = user_pic_path;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_phone() {
        return user_phone;
    }
}
