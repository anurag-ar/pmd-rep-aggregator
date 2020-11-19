package com.autobuds.PMDReportAggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.autobuds.PMDReportAggregator.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
