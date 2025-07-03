package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.CurvePoint;

public interface CurvePointService {
List<CurvePoint> getAllCurve();
CurvePoint getCurveById(Integer id);
CurvePoint saveCurvePoint(CurvePoint cp);
void deleteCurvePointById(Integer id);
}
