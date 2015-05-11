package net.corp.core.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Created by sushiljoshi on 12/01/15.
 */
@SuppressWarnings("serial")
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name="md_contacts")
public class Contacts implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "CONTACT_ID")
    private Integer contactId;

    @Column(name = "CONTACT_NAME")
    private String contactName;

    @Column(name = "CONTACT_NUMBER")
    private String contactNumber;

    @Column(name = "CONTACT_ACTIVE")
    private Boolean contactActive;

    @Column(name = "GROUP_IND")
    private Boolean groupInd;

    public Boolean getGroupInd() {
        return groupInd;
    }

    public void setGroupInd(Boolean groupInd) {
        this.groupInd = groupInd;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Boolean getContactActive() {
        return contactActive;
    }

    public void setContactActive(Boolean contactActive) {
        this.contactActive = contactActive;
    }

}
