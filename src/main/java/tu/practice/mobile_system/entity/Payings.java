package tu.practice.mobile_system.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payings")
@Getter
@Setter
public class Payings {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	 @Column(name = "date_of_paying")
	 private Date dateOfPaying;
	 
	 @Column(name = "customer_service_id")
	 private Long customer_serviceId;
	 
	 @ManyToOne
	    @JoinColumn(name="payings")
	    private CustomerServiceEntity customerServiceRelations;

	
	 public Payings() {}
	
	 public Payings(Long id, Date dateOfPaying, Long customer_serviceId,
			CustomerServiceEntity customerServiceRelations) {
		super();
		this.id = id;
		this.dateOfPaying = dateOfPaying;
		this.customer_serviceId = customer_serviceId;
		this.customerServiceRelations = customerServiceRelations;
	}
}
