package com.autobuds.PMDReportAggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobuds.PMDReportAggregator.model.Org;
import com.autobuds.PMDReportAggregator.model.OrgId;

public interface OrgRepository extends JpaRepository<Org, OrgId> {

}
