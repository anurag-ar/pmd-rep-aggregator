package com.autobuds.PMDReportAggregator.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrgId implements Serializable {
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "org_name")
	private String orgName;
	
	@Column(name = "org_id")
	private String orgId;

}