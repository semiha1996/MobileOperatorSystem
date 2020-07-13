package sap.practice.mobilesystem.entity;

import java.beans.Transient;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "phone")
	private String phone;

	@Column(name = "iban")
	private String iban;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	// for table admin_customer
	@ManyToMany(mappedBy = "customers", cascade = CascadeType.ALL)
	private List<Administrator> admins;

	// for table customer_service
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "customer_service", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
	private List<MobilePlan> services;

	public Customer() {

	}

	public Customer(Long userId, String name, String phone, String iban, String address, String username,
			String password, String email, List<Administrator> admins, List<MobilePlan> services) {
		super();

	}
}
