package com.autobuds.PMDReportAggregator.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
public class Ruleset implements Serializable {
	
	@Getter @Setter
	@EmbeddedId
	private RulesetId rulesetId;
	
	@Lob
	@Getter @Setter
	private String rules;
	
	@JsonIgnore
    @MapsId("email")
    @JoinColumn(name="user_id", referencedColumnName="email")
    @ManyToOne
    public User user;

	@Override
	public String toString() {
		return "Ruleset [rulesetId=" + rulesetId + ", rules=" + rules + "]";
	}

	public Ruleset(RulesetId rulesetId, String rules) {
		super();
		this.rulesetId = rulesetId;
		this.rules = rules;
	}

	
	
}
