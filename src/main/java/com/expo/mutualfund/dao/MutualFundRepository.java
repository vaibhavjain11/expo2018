package com.expo.mutualfund.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MutualFundRepository extends MongoRepository<MutualFund, Integer>{

	List<MutualFund> findAllByName(String name);
}
