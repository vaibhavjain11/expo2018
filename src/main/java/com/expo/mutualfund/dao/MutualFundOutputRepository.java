package com.expo.mutualfund.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MutualFundOutputRepository extends MongoRepository<MutualFundOutput, Integer> {

    List<MutualFundOutput> findAllByDuration(int duration);
}
