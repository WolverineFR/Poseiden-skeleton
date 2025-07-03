package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListServiceImpl implements BidListService {

	@Autowired
	private BidListRepository bidListRepository;

	@Override
	public List<BidList> getAllBids() {
		return bidListRepository.findAll();
	}

	@Override
	public BidList saveBid(BidList bid) {
		return bidListRepository.save(bid);
	}

	@Override
	public BidList getBidById(Integer id) {
		return bidListRepository.getById(id);
	}

	@Override
	public void deleteBidById(Integer id) {
		bidListRepository.deleteById(id);

	}

}
