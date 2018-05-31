package com.efo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;

import com.efo.dao.PettyCashVoucherDao;
import com.efo.entity.PettyCashVoucher;
import com.efo.interfaces.IPettyCashVoucher;

@Service
public class PettyCashVoucherService implements IPettyCashVoucher {
	
	@Autowired
	private PettyCashVoucherDao pettyCashVoucherDao;

	@Override
	public void create(PettyCashVoucher pettyCash) {
		pettyCashVoucherDao.create(pettyCash);
	}

	@Override
	public PettyCashVoucher retrieve(int id) {
		return pettyCashVoucherDao.retrieve(id);
	}

	@Override
	public void update(PettyCashVoucher pettyCash) {
		pettyCashVoucherDao.update(pettyCash);
	}

	@Override
	public void delete(int id) {
		pettyCashVoucherDao.delete(id);
	}

	@Override
	public void delete(PettyCashVoucher pettyCash) {
		pettyCashVoucherDao.delete(pettyCash);
	}
	
	public PagedListHolder<PettyCashVoucher> retrieveList() {
		return new PagedListHolder<PettyCashVoucher>(pettyCashVoucherDao.retrieveList());
	}
}
