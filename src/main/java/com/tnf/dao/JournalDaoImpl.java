package com.tnf.dao;

import com.tnf.entity.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

import static com.tnf.builder.DocBuilder.*;

@Repository
@Transactional
public class JournalDaoImpl implements JournalDao {
    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public void createJournal(boolean sORu) {
        NodeList parentNodeList = getValues();
        int length = parentNodeList.getLength();
        System.out.println("parentNodeList.getLength(): " + length);
        for (int pnIndex = 0; pnIndex < length; pnIndex++) {
            Journal journal = new Journal();
            Node parentNode = parentNodeList.item(pnIndex);
            Element eElement = (Element) parentNode;
            String acronym = eElement.getAttribute(attribute).trim().substring(8, 12).toUpperCase();
            journal.setAcronym(acronym);
            System.out.println("INSERTING INTO " + acronym);
            NodeList childNodeList = (eElement).getElementsByTagName(tagName);
            for (int cnIndex = 0; cnIndex < childNodeList.getLength(); cnIndex++) {
                Node childNode = childNodeList.item(cnIndex);
                Element childElement = (Element) childNode;
                String cpname = (childElement.getAttribute(attributeName));
                String data = eElement.getElementsByTagName(tagName).item(cnIndex).getTextContent().trim();
                switch (cpname) {
                    case "impactFactor2y":
                        journal.setImpactfactor2y(data);
                        break;
                    case "ifBestQuartile":
                        journal.setIfbestquartile(data);
                        break;
                    case "impactFactor5y":
                        journal.setImpactfactor5y(data);
                        break;
                    case "citeScore":
                        journal.setCitescore(data);
                        break;
                    case "SNIP":
                        journal.setSnip(data);
                        break;
                    case "SJR":
                        journal.setSjr(data);
                        break;
                    case "speedSubDec":
                        journal.setSpeedsubdec(data);
                        break;
                    case "speedSubPRDec":
                        journal.setSpeedsubprdec(data);
                        break;
                    case "speedAcceptPub":
                        journal.setSpeedacceptpub(data);
                        break;
                    case "acceptanceRate":
                        journal.setAcceptancerate(data);
                        break;
                    case "citeScoreBestQuartile":
                        journal.setCitescorebestquartile(data);
                        break;
                    case "usage":
                        journal.setUsages(data);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + data);
                }
            }
            if (sORu) {
                hibernateTemplate.save(journal);
            } else {
                hibernateTemplate.update(journal);
            }
        }
    }

    @Override
    public List<Journal> getAllJournal() {
        CriteriaQuery<Journal> query = Objects
                .requireNonNull(hibernateTemplate.getSessionFactory())
                .getCurrentSession()
                .getCriteriaBuilder()
                .createQuery(Journal.class);
        Root<Journal> root = query.from(Journal.class);
        CriteriaQuery<Journal> select = query.select(root);
        return hibernateTemplate
                .getSessionFactory()
                .getCurrentSession()
                .createQuery(select)
                .getResultList();
    }
}
