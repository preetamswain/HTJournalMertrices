package com.tnf.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "journal_matrix")
public class Journal {
    @Column(columnDefinition = "INT(11) NOT NULL UNIQUE KEY auto_increment")
    private int Id;
    @Column(columnDefinition = "tinyint(4)")
    private int active = 1;
    @Column(columnDefinition = "varchar(6)")
    @Id
    private String acronym = "";
    @Column(columnDefinition = "varchar(13)")
    private String impactfactor2y = "";
    @Column(columnDefinition = "varchar(9)")
    private String ifbestquartile = "";
    @Column(columnDefinition = "varchar(13)")
    private String impactfactor5y = "";
    @Column(columnDefinition = "varchar(11)")
    private String citescore = "";
    @Column(columnDefinition = "varchar(12)")
    private String snip = "";
    @Column(columnDefinition = "varchar(13)")
    private String sjr = "";
    @Column(columnDefinition = "varchar(3)")
    private String speedsubdec = "";
    @Column(columnDefinition = "varchar(4)")
    private String speedsubprdec = "";
    @Column(columnDefinition = "varchar(3)")
    private String speedacceptpub = "";
    @Column(columnDefinition = "varchar(4)")
    private String acceptancerate = "";
    @Column(columnDefinition = "varchar(13)")
    private String citescorebestquartile = "";
    @Column(name = "`usage`", columnDefinition = "varchar(9)")
    private String usages = "";
}
