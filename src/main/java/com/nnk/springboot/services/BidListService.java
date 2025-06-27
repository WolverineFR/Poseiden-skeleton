package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.BidList;

public interface BidListService {
	List<BidList> getAllBid();
	BidList getBidById(Integer id);
	BidList saveBid(BidList bid);
	void deleteBid(Integer id);

}
