package com.buyersfirst.core.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "desire_tags")
public class DesireTags {
    
    public DesireTags(Integer tagId, Integer desireId) {
        TagId = tagId;
        DesireId = desireId;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }    

    @Column(name = "tag_id")
    private Integer TagId;

    @Column(name = "desire_id")
    private Integer DesireId;
    
    public Integer getTagId() {
        return TagId;
    }
    public void setTagId(Integer tagId) {
        TagId = tagId;
    }
    public Integer getDesireId() {
        return DesireId;
    }
    public void setDesireId(Integer desireId) {
        DesireId = desireId;
    }
}
