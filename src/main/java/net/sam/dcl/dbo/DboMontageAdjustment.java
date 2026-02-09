package net.sam.dcl.dbo;


import javax.persistence.*;

@Entity
@Table(name = "DCL_MONTAGE_ADJUSTMENT")
@SequenceGenerator( name = "GEN_DCL_MONTAGE_ADJUSTMENT_ID",sequenceName = "GEN_DCL_MONTAGE_ADJUSTMENT_ID", allocationSize = 1)
public class DboMontageAdjustment implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_DCL_MONTAGE_ADJUSTMENT_ID")
	@Column(name = "MAD_ID", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STF_ID", nullable = false)
	private DboStuffCategory stuffCategory;

	@Column(name = "MAD_MACHINE_TYPE", nullable = false, length = 1000)
	private String machineType;

	@Column(name = "MAD_COMPLEXITY", nullable = false, length = 10)
	private String complexity;

	public DboMontageAdjustment() {
	}

  public DboMontageAdjustment(Integer id) {
    this.id = id;
  }

  public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DboStuffCategory getStuffCategory() {
		return stuffCategory;
	}

	public void setStuffCategory(DboStuffCategory stuffCategory) {
		this.stuffCategory = stuffCategory;
	}

  public String getMachineType() {
    return machineType;
  }

  public void setMachineType(String machineType) {
    this.machineType = machineType;
  }

  public String getComplexity() {
    return complexity;
  }

  public void setComplexity(String complexity) {
    this.complexity = complexity;
  }
}