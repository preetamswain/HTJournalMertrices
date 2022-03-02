package com.tnf.service;

import com.tnf.entity.Journal;

import java.util.List;


public interface JournalService {
    public void insertOrUpdate(boolean update);
    public List<Journal> getJournals();
}
