package com.autobuds.PMDReportAggregator.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OrgId implements Serializable {
	
	@Column(name = "user_id")
	@JsonProperty(access=Access.WRITE_ONLY)
	private String userId;
	
	@Column(name = "org_id")
	private String orgId;

}