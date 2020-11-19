package com.autobuds.PMDReportAggregator.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;


@Data
@Entity
public class User implements Serializable{
		
	@Column
	private String userName;
		
	@Id @Column
	private String  email;
	
	@Column
	@JsonProperty(access = Access.WRITE_ONLY )
	private String password;
	
    @OneToMany(fetch = FetchType.LAZY,mappedBy ="user",cascade = CascadeType.ALL)
    private List<Org> orgs;
    
    @OneToMany(fetch = FetchType.LAZY,mappedBy ="user",cascade = CascadeType.ALL)
    private List<Ruleset> rulesets;
}
