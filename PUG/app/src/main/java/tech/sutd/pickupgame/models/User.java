package tech.sutd.pickupgame.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "users")
public class User {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private String age;

    @SerializedName("email")
    private String email;

    @SerializedName("passwd")
    private String passwd;

    @SerializedName("img")
    private String img;

    public User(@NonNull String id, String name, String age, String email, String passwd, String img) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.passwd = passwd;
        this.img = img;
    }

    public User(final Builder builder) {
        id = builder.id;
        name = builder.name;
        age = builder.age;
        email = builder.email;
        passwd = builder.passwd;
        img = builder.img;
    }

    public static User defaultUser() {
        return new User("0", "-1", "-1", "-1", "-1", "-1");
    }

    public static class Builder {
        private final String id;
        private String name;
        private String age;
        private String email;
        private String passwd;
        private String img;

        public Builder(String id) {
            this.id = id;
        }

        public Builder setName(final String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(String age) {
            this.age = age;
            return this;
        }

        public Builder setEmail(final String email) {
            this.email = email;
            return this;
        }

        public Builder setPasswd(final String passwd) {
            this.passwd = passwd;
            return this;
        }

        public Builder setImg(String img) {
            this.img = img;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @NonNull
    public String getId() {
        return id;
    }


    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
