package tu.practice.mobile_system.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.Nullable;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer_service")
@Getter
@Setter
public class CustomerServiceEntity {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

	 @Column(name = "customer_id")
	 private Long customerId;
	 
	 @Column(name = "service_id")
	 private Long serviceId;
	
	 @Column(name = "date_to_be_payed")
	 private Long dateToBePayed;
	 
	 @Column(name = "megabytes_left")
	 private Integer megabytesLeft;
	 
	 @Column(name = "sms_left")
	 private Integer smsLeft;

	 @Column(name = "minutes_left")
	 private Integer minutesLeft;
	 
	 public CustomerServiceEntity() {}

	 public CustomerServiceEntity(Long id, Long customerId, Long serviceId, Long dateToBePayed, int megabytesLeft,
			int smsLeft, int minutesLeft	) {
		super();
		Id = id;
		this.customerId = customerId;
		this.serviceId = serviceId;
		this.dateToBePayed = dateToBePayed;
		this.megabytesLeft = megabytesLeft;
		this.smsLeft = smsLeft;
		this.minutesLeft = minutesLeft;
	}
	 
	 @OneToMany(mappedBy = "customerServiceRelations")
	 private List<Payings> payings;
}
