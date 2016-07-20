package com.sirsendu.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Sirsendu Konar
 *
 */
@Entity
@Table(name = "dbentries")
public class DbEntry {

    public DbEntry() {
    }

    public DbEntry(final Date date) {
        this.date = date;
    }

    @Id
    @Column(nullable = false, name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, name = "date")
    private Date date;
}
