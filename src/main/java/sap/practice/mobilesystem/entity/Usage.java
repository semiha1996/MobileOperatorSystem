package sap.practice.mobilesystem.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usage")
@Getter
@Setter
public class Usage {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usageId;

	@Column(name = "date_to_be_payed")
	private Date dateToBePayed;

	@Column(name = "megabytes_left")
	private Integer megabytesLeft;

	@Column(name = "sms_left")
	private Integer smsLeft;

	@Column(name = "minutes_left")
	private Integer minutesLeft;

	@Column(name = "customer_service_id")
	private Long customer_serviceId;

	public Usage() {
		super();
	}

	public Usage(Long usageId, Date dateToBePayed, Integer megabytesLeft, Integer smsLeft, Integer minutesLeft,
			Long customer_serviceId, CustomerMobilePlan customerMobilePlan) {
		super();
		this.usageId = usageId;
		this.dateToBePayed = dateToBePayed;
		this.megabytesLeft = megabytesLeft;
		this.smsLeft = smsLeft;
		this.minutesLeft = minutesLeft;
		this.customer_serviceId = customer_serviceId;
		this.customerMobilePlan = customerMobilePlan;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_service_id", referencedColumnName = "id")
	private CustomerMobilePlan customerMobilePlan;
	// private CustomerMobilePlan customerServiceRelations;

}
