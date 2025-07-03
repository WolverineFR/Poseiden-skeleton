package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointServiceImpl implements CurvePointService {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Override
	public List<CurvePoint> getAllCurves() {
		return curvePointRepository.findAll();
	}

	@Override
	public CurvePoint getCurveById(Integer id) {
		return curvePointRepository.getById(id);
	}

	@Override
	public CurvePoint saveCurvePoint(CurvePoint cp) {
		return curvePointRepository.save(cp);
	}

	@Override
	public void deleteCurvePointById(Integer id) {
		curvePointRepository.deleteById(id);

	}

}
