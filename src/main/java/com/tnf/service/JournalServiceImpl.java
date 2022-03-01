package com.tnf.service;

import com.tnf.dao.JournalDaoImpl;
import com.tnf.entity.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalServiceImpl implements JournalService {
    @Autowired
    JournalDaoImpl journalDao;

    @Override
    public void insertOrUpdate(boolean d) {
        journalDao.createJournal(d);
    }

    @Override
    public List<Journal> getJournals() {
        return journalDao.getAllJournal();
    }
}
