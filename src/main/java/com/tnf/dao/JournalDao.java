package com.tnf.dao;

import com.tnf.entity.Journal;

import java.util.List;

public interface JournalDao {
    public void createJournal(boolean sORu);
    public List<Journal> getAllJournal();
}
