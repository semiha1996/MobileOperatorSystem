package sap.practice.mobilesystem.entity;

import java.util.List;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import sap.practice.mobilesystem.entity.Administrator;

@Entity
@Table(name = "administrator")
@Getter
@Setter
@EqualsAndHashCode
public class Administrator {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adminId;

	@Column(name = "username")
	private String userName;

	@Column(name = "password")
	private String password;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "admin_customer", joinColumns = @JoinColumn(name = "admin_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"))
	private List<Customer> customers;

	public Administrator() {

	}

	public Administrator(Long adminId, String userName, String password) {
		super();
		this.adminId = adminId;
		this.userName = userName;
		this.password = password;
	}
}
