package com.cxylk;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import org.junit.Test;

import java.io.*;
import java.util.Date;

/**
 * @Classname SerializableTest
 * @Description java序列化与hessian2序列化对比
 * @Author likui
 * @Date 2021/3/20 16:47
 **/
public class SerializableTest {
    @Test
    public void javaWriteTest() throws IOException {
        String f=System.getProperty("user.dir")+"/target/User_java";
        ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream(f));
        User user=new User("lk",new Date(),Sex.MAN);
        outputStream.writeObject(user);
   }

   @Test
   public void hessian2WriteTest() throws IOException {
       String f=System.getProperty("user.dir")+"/target/User_hessian";
       Hessian2Output out=new Hessian2Output(new FileOutputStream(f));
       User user=new User("lk",new Date(),Sex.MAN);
       out.writeObject(user);
       out.flush();
   }

    @Test
    public void hessian2ReadTest() throws IOException {
        String f=System.getProperty("user.dir")+"/target/User_hessian";
        Hessian2Input input=new Hessian2Input(new FileInputStream(f));
        User user = (User) input.readObject();
        System.out.println(user);
    }

   @Test
   public void javaReadTest() throws IOException, ClassNotFoundException {
       String f=System.getProperty("user.dir")+"/target/User_java";
       ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(f));
       User user = (User) inputStream.readObject();
       System.out.println(user);
   }

    public static enum  Sex{
        MAN,WOMAN,RENYAO
    }



    public static class User implements Serializable{
        private String name;
        private Date birthday;
        private Integer age;
        private Sex sex;

        public User(String name, Date birthday, Sex sex) {
            this.name = name;
            this.birthday = birthday;
            this.sex = sex;
        }

        public Sex getSex() {
            return sex;
        }

        public void setSex(Sex sex) {
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
