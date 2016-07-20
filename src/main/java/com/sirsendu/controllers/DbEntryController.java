package com.sirsendu.controllers;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sirsendu.TenantContext;
import com.sirsendu.domain.DbEntry;
import com.sirsendu.domain.DbEntryRepository;

/**
 *
 * @author Sirsendu Konar
 *
 */
@Controller
public class DbEntryController {
    @Autowired
    private DbEntryRepository dbEntryRepository;

    @RequestMapping(path = "/entries", method = RequestMethod.POST)
    public ResponseEntity<?> createSampleOrder(@RequestHeader("X-TenantID") final String tenantName) {
        TenantContext.setCurrentTenant(tenantName);

        final DbEntry newOrder = new DbEntry(new Date(System.currentTimeMillis()));
        this.dbEntryRepository.save(newOrder);

        return ResponseEntity.ok(newOrder);
    }
}
