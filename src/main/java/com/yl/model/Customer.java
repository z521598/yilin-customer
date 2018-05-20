package com.yl.model;

import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/25.
 */
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    // 纯中文
    private String name;
    @Column
    private String englishName;
    @Column
    private String headEnglishName;
    @Column
    private String sex = "男";
    @Column
    private Integer age;
    @Column
    private Integer height;
    @Column
    private Integer weight;
    @Column
    private Integer cal;
    @Column
    private String exercise;
    @Column
    private String remark;
    @Column
    private String phoneNumber;

    public Customer() {
    }

    public Customer(Long id, String name, String phoneNumber, String sex) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getHeadEnglishName() {
        return headEnglishName;
    }

    public void setHeadEnglishName(String headEnglishName) {
        this.headEnglishName = headEnglishName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCal() {
        return cal;
    }

    public void setCal(Integer cal) {
        this.cal = cal;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", englishName='").append(englishName).append('\'');
        sb.append(", headEnglishName='").append(headEnglishName).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", age=").append(age);
        sb.append(", height=").append(height);
        sb.append(", weight=").append(weight);
        sb.append(", cal=").append(cal);
        sb.append(", exercise='").append(exercise).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static String[] tableColumns = {"id", "姓名", "性别", "年龄", "身高(cm)", "体重(kg)", "摄入卡路里(cal)", "锻炼方法", "电话", "备注"};
    public static Integer[] columnWidths = {40, 90, 40, 40, 60, 60, 100, 200, 100, 500};

    public Object[] toArray() {
        return new Object[]{id, name, sex, age, height, weight, cal, exercise, phoneNumber, remark};
    }
}
