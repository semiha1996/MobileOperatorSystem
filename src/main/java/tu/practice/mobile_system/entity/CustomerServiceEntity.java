package tu.practice.mobile_system.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mobile_service")
@Getter
@Setter
public class CustomerServiceEntity {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

	 @Column(name = "customer_id")
	 private Long customerId;
	 
	 @Column(name = "service")
	 private Long serviceId;
	
	 @Column(name = "date_to_be_payed")
	 private Long dateToBePayed;
	 
	 public CustomerServiceEntity() {}

	public CustomerServiceEntity(Long id, Long customerId, Long serviceId, Long dateToBePayed) {
		super();
		Id = id;
		this.customerId = customerId;
		this.serviceId = serviceId;
		this.dateToBePayed = dateToBePayed;
	}
	 
	 @OneToMany(mappedBy = "customerServiceRelations")
	 private List<Payings> payings;
}
