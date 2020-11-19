package com.autobuds.PMDReportAggregator.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Org implements Serializable {
	@Getter @Setter
	@EmbeddedId
	public OrgId id;
	@Getter @Setter
	private String refreshToken;
	@Getter @Setter
	private String accessToken;
	@Getter @Setter
	@Column(precision = 8, scale = 1)
	private BigDecimal salesforceVersion;
	
	@JsonIgnore
    @MapsId("userId")
    @JoinColumn(name="user_id", referencedColumnName="id")
    @ManyToOne
    public User user;

	@Override
	public String toString() {
		return "Org [id=" + id + ", refreshToken=" + refreshToken + ", accessToken=" + accessToken
				+ ", salesforceVersion=" + salesforceVersion + "]";
	}

	public Org(OrgId id, String refreshToken, String accessToken, BigDecimal salesforceVersion) {
		super();
		this.id = id;
		this.refreshToken = refreshToken;
		this.accessToken = accessToken;
		this.salesforceVersion = salesforceVersion;
	}

}
