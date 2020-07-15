package sap.practice.mobilesystem.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer_service")
@Getter
@Setter
@EqualsAndHashCode
public class CustomerMobilePlan {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "service_id")
	private Long serviceId;

	public CustomerMobilePlan() {
	}

	public CustomerMobilePlan(Long id, Long customerId, Long serviceId) {
		super();
		Id = id;
		this.customerId = customerId;
		this.serviceId = serviceId;
	}

	@OneToMany(mappedBy = "customerServiceRelations", cascade = CascadeType.ALL)
	private List<Payings> payings;

	
}
