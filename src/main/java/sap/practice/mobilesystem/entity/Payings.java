package sap.practice.mobilesystem.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payings")
@Getter
@Setter
@EqualsAndHashCode
public class Payings {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date_of_paying")
	private Date dateOfPaying;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_service_id")
	private CustomerMobilePlan customerServiceRelations;

	public Payings() {
	}

	public Payings(Long id, Date dateOfPaying, Long customer_serviceId, CustomerMobilePlan customerServiceRelations) {
		super();
		this.id = id;
		this.dateOfPaying = dateOfPaying;
		this.customerServiceRelations = customerServiceRelations;
	}
}
