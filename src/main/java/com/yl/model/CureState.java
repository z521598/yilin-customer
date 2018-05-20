package com.yl.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/26.
 */
@Entity
public class CureState {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long customerId;
    @Column
    private String symptom;
    @Column
    private String people;
    @Column
    private String effect;
    @Column
    private String remark;
    @Column
    private Date date = new Date();

    public CureState() {
    }

    public CureState(Long customerId) {
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CureState{");
        sb.append("id=").append(id);
        sb.append(", customerId=").append(customerId);
        sb.append(", symptom='").append(symptom).append('\'');
        sb.append(", people='").append(people).append('\'');
        sb.append(", effect='").append(effect).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }

    public static Object[] tableColumns = new Object[]{"id", "日期", "症状", "推拿人", "效果", "备注",};
    public static Integer[] columnWidths = {40, 80, 370, 60, 370, 300};

    public Object[] toArray() {
        return new Object[]{id, dateFormat.format(date), symptom, people, effect, remark};
    }
}
