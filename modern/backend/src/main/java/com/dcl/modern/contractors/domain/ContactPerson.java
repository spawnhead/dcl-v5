package com.dcl.modern.contractors.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * dcl_contact_person. SNAPSHOT §4.4 gridContactPersons.
 * Legacy: ContractorAction, editPersonInContractor, addPersonInContractor.
 */
@Entity
@Table(name = "dcl_contact_person")
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cps_id")
    private Integer id;

    @Column(name = "ctr_id", nullable = false)
    private Integer contractorId;

    @Column(name = "cps_name", nullable = false, length = 200)
    private String name;

    @Column(name = "cps_position", length = 150)
    private String position;

    @Column(name = "cps_on_reason", length = 150)
    private String onReason;

    @Column(name = "cps_phone", length = 150)
    private String phone;

    @Column(name = "cps_mob_phone", length = 150)
    private String mobPhone;

    @Column(name = "cps_fax", length = 150)
    private String fax;

    @Column(name = "cps_email", length = 50)
    private String email;

    @Column(name = "cps_contract_comment", length = 300)
    private String contractComment;

    @Column(name = "cps_fire")
    private Short fire;

    @Column(name = "cps_block")
    private Short block;

    protected ContactPerson() {}

    public ContactPerson(Integer contractorId, String name, String position, String onReason,
            String phone, String mobPhone, String fax, String email, String contractComment,
            Short fire, Short block) {
        this.contractorId = Objects.requireNonNull(contractorId);
        this.name = name != null ? name.trim() : "";
        this.position = position != null ? position.trim() : null;
        this.onReason = onReason != null ? onReason.trim() : null;
        this.phone = phone != null ? phone.trim() : null;
        this.mobPhone = mobPhone != null ? mobPhone.trim() : null;
        this.fax = fax != null ? fax.trim() : null;
        this.email = email != null ? email.trim() : null;
        this.contractComment = contractComment != null ? contractComment.trim() : null;
        this.fire = fire;
        this.block = block;
    }

    public Integer getId() { return id; }
    public Integer getContractorId() { return contractorId; }
    public String getName() { return name; }
    public String getPosition() { return position; }
    public String getOnReason() { return onReason; }
    public String getPhone() { return phone; }
    public String getMobPhone() { return mobPhone; }
    public String getFax() { return fax; }
    public String getEmail() { return email; }
    public String getContractComment() { return contractComment; }
    public Short getFire() { return fire; }
    public Short getBlock() { return block; }

    public void setContractorId(Integer contractorId) { this.contractorId = contractorId; }
}
