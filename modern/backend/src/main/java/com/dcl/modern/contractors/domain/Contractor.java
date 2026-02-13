package com.dcl.modern.contractors.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 * dcl_contractor. Legacy: ContractorAction create/process.
 */
@Entity
@Table(name = "dcl_contractor")
public class Contractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ctr_id")
    private Integer id;

    @Column(name = "ctr_name", nullable = false, length = 200)
    private String name;

    @Column(name = "ctr_full_name", length = 300)
    private String fullName;

    @Column(name = "ctr_email", length = 40)
    private String email;

    @Column(name = "cut_id", nullable = false)
    private Integer countryId;

    @Column(name = "ctr_unp", length = 15)
    private String unp;

    @Column(name = "rpt_id")
    private Integer reputationId;

    @Column(name = "ctr_create_date")
    private LocalDateTime createDate;

    @Column(name = "usr_id_create")
    private Integer createdBy;

    @Column(name = "ctr_edit_date")
    private LocalDateTime editDate;

    @Column(name = "usr_id_edit")
    private Integer editedBy;

    @Column(name = "ctr_index", length = 20)
    private String index;

    @Column(name = "ctr_region", length = 50)
    private String region;

    @Column(name = "ctr_place", length = 50)
    private String place;

    @Column(name = "ctr_street", length = 50)
    private String street;

    @Column(name = "ctr_building", length = 10)
    private String building;

    @Column(name = "ctr_add_info", length = 1000)
    private String addInfo;

    @Column(name = "ctr_phone", length = 100)
    private String phone;

    @Column(name = "ctr_fax", length = 100)
    private String fax;

    @Column(name = "ctr_okpo", length = 15)
    private String okpo;

    @Column(name = "ctr_bank_props", length = 800)
    private String bankProps;

    @Column(name = "ctr_comment", length = 5000)
    private String comment;

    @Column(name = "ctr_block")
    private Short block;

    protected Contractor() {}

    public Contractor(String name, Integer countryId, Integer reputationId, String fullName,
            String email, String index, String region, String place, String street, String building,
            String addInfo, String phone, String fax, String okpo, String unp, String bankProps,
            String comment, LocalDateTime now, Integer userId) {
        this.name = name != null ? name.trim() : "";
        this.countryId = countryId;
        this.reputationId = reputationId;
        this.fullName = fullName != null ? fullName.trim() : null;
        this.email = email;
        this.index = index;
        this.region = region;
        this.place = place;
        this.street = street;
        this.building = building;
        this.addInfo = addInfo;
        this.phone = phone;
        this.fax = fax;
        this.okpo = okpo;
        this.unp = unp;
        this.bankProps = bankProps;
        this.comment = comment;
        this.createDate = now;
        this.createdBy = userId;
        this.editDate = now;
        this.editedBy = userId;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getFullName() { return fullName; }
    public Integer getCountryId() { return countryId; }
    public Integer getReputationId() { return reputationId; }
    public Integer getCreatedBy() { return createdBy; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getFax() { return fax; }
    public String getBankProps() { return bankProps; }
    public String getUnp() { return unp; }
    public String getOkpo() { return okpo; }
    public String getComment() { return comment; }
    public Short getBlock() { return block; }
    public void setBlock(Short block) { this.block = block; }
    public String getIndex() { return index; }
    public String getRegion() { return region; }
    public String getPlace() { return place; }
    public String getStreet() { return street; }
    public String getBuilding() { return building; }
    public String getAddInfo() { return addInfo; }
    public LocalDateTime getCreateDate() { return createDate; }
    public LocalDateTime getEditDate() { return editDate; }
    public Integer getEditedBy() { return editedBy; }

    public void setName(String name) { this.name = name != null ? name.trim() : ""; }
    public void setFullName(String fullName) { this.fullName = fullName != null ? fullName.trim() : null; }
    public void setCountryId(Integer countryId) { this.countryId = countryId; }
    public void setReputationId(Integer reputationId) { this.reputationId = reputationId; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setFax(String fax) { this.fax = fax; }
    public void setBankProps(String bankProps) { this.bankProps = bankProps; }
    public void setUnp(String unp) { this.unp = unp; }
    public void setOkpo(String okpo) { this.okpo = okpo; }
    public void setComment(String comment) { this.comment = comment; }
    public void setIndex(String index) { this.index = index; }
    public void setRegion(String region) { this.region = region; }
    public void setPlace(String place) { this.place = place; }
    public void setStreet(String street) { this.street = street; }
    public void setBuilding(String building) { this.building = building; }
    public void setAddInfo(String addInfo) { this.addInfo = addInfo; }
    public void setEditDate(LocalDateTime editDate) { this.editDate = editDate; }
    public void setEditedBy(Integer editedBy) { this.editedBy = editedBy; }

    /** Build address string from parts. Legacy: ctr_address like. */
    public String buildAddress() {
        StringBuilder sb = new StringBuilder();
        if (index != null && !index.isBlank()) sb.append(index).append(" ");
        if (region != null && !region.isBlank()) sb.append(region).append(" ");
        if (place != null && !place.isBlank()) sb.append(place).append(" ");
        if (street != null && !street.isBlank()) sb.append(street).append(" ");
        if (building != null && !building.isBlank()) sb.append(building).append(" ");
        if (addInfo != null && !addInfo.isBlank()) sb.append(addInfo);
        return sb.toString().trim();
    }
}
