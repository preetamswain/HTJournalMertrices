package com.tnf.dao;

import com.tnf.entity.Journal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.tnf.builder.DocBuilder.*;

@Repository
@Transactional
public class JournalDaoImpl implements JournalDao {
    @Autowired
    private SessionFactory factory;

    public Session getSession() {
        Session session = factory.getCurrentSession();
        if (session == null) {
            session = factory.openSession();
        }
        return session;
    }

    @Override
    public void createJournal(boolean sORu) {
        NodeList parentNodeList = getValues();
        int length = parentNodeList.getLength();
        System.out.println("parentNodeList.getLength(): " + length);
        for (int pnIndex = 0; pnIndex < length; pnIndex++) {
            Journal j = new Journal();
            Node parentNode = parentNodeList.item(pnIndex);
            Element eElement = (Element) parentNode;
            String doi = eElement.getAttribute(attribute);
            String acronym = doi.trim().substring(8, 12).toUpperCase();
            j.setAcronym(acronym);
            System.out.println("INSERTING INTO " + acronym);
            NodeList childNodeList = (eElement).getElementsByTagName(tagName);
            for (int cnIndex = 0; cnIndex < childNodeList.getLength(); cnIndex++) {
                Node childNode = childNodeList.item(cnIndex);
                Element childElement = (Element) childNode;
                String cpname = (childElement.getAttribute(attributeName));
                String data = eElement.getElementsByTagName(tagName).item(cnIndex).getTextContent().trim();
                switch (cpname) {
                    case "impactFactor2y":
                        j.setImpactfactor2y(data);
                        break;
                    case "ifBestQuartile":
                        j.setIfbestquartile(data);
                        break;
                    case "impactFactor5y":
                        j.setImpactfactor5y(data);
                        break;
                    case "citeScore":
                        j.setCitescore(data);
                        break;
                    case "SNIP":
                        j.setSnip(data);
                        break;
                    case "SJR":
                        j.setSjr(data);
                        break;
                    case "speedSubDec":
                        j.setSpeedsubdec(data);
                        break;
                    case "speedSubPRDec":
                        j.setSpeedsubprdec(data);
                        break;
                    case "speedAcceptPub":
                        j.setSpeedacceptpub(data);
                        break;
                    case "acceptanceRate":
                        j.setAcceptancerate(data);
                        break;
                    case "citeScoreBestQuartile":
                        j.setCitescorebestquartile(data);
                        break;
                    case "usage":
                        j.setUsages(data);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + data);
                }
            }
            if (sORu) {
                getSession().save(j);
            } else {
                getSession().update(j);
            }
        }
    }

    @Override
    public List<Journal> getAllJournal() {
        CriteriaQuery<Journal> cq = getSession().getCriteriaBuilder().createQuery(Journal.class);
        Root<Journal> journalRoot = cq.from(Journal.class);
        CriteriaQuery<Journal> select = cq.select(journalRoot);
        return getSession().createQuery(select).getResultList();
    }
}
