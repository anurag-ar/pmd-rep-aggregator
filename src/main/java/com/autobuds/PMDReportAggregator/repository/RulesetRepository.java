package com.autobuds.PMDReportAggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobuds.PMDReportAggregator.model.Ruleset;
import com.autobuds.PMDReportAggregator.model.RulesetId;

public interface RulesetRepository extends JpaRepository<Ruleset, RulesetId> {

}
