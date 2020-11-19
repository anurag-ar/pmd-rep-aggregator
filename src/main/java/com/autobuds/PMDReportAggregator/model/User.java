package com.autobuds.PMDReportAggregator.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;


@Data
@Entity
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String userName;
	
	@Column
	private String  email;
	
	@Column
	private String password;
	
    @OneToMany(fetch = FetchType.LAZY,mappedBy ="user",cascade = CascadeType.ALL)
    private List<Org> orgs;
    
    @OneToMany(fetch = FetchType.LAZY,mappedBy ="user",cascade = CascadeType.ALL)
    private List<Ruleset> rulesets;
}
