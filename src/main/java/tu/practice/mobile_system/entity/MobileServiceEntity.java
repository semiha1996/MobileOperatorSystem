package tu.practice.mobile_system.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import tu.practice.mobile_system.entity.MobileServiceEntity;

@Entity
@Table(name = "mobile_service")
@Getter
@Setter
public class MobileServiceEntity {
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "megabytes")
    private int megabytes;
    
    @Column(name = "sms_number")
    private int smsNumber;

	@Column(name = "minutes")
    private int minutes;
    
    @Column(name = "price")
    private float price;
    
    @Column(name = "period_activation")
    private float periodOfActivation;
	
    
    //for table customer_service
    @ManyToMany(mappedBy = "services")
    private List<Customer> customers;
	
    public MobileServiceEntity() {}

    public MobileServiceEntity(Long serviceId, String name, int megabytes, int smsNumber, int minutes, float price,
			float periodOfActivation) {
		super();
		this.serviceId = serviceId;
		this.name = name;
		this.megabytes = megabytes;
		this.smsNumber = smsNumber;
		this.minutes = minutes;
		this.price = price;
		this.periodOfActivation = periodOfActivation;
	}
}
