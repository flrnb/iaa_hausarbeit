package de.nak.iaa.server.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.nak.iaa.server.business.StudentService;
import de.nak.iaa.server.dao.ManipelDAO;
import de.nak.iaa.server.entity.Manipel;

public class StudentServiceImpl implements StudentService {

	@Autowired
	private ManipelDAO manipelDAO;

	@Override
	public List<Manipel> getAllManipel() {
		return manipelDAO.findAll();
	}

	public void setManipelDAO(ManipelDAO manipelDAO) {
		this.manipelDAO = manipelDAO;
	}

}
