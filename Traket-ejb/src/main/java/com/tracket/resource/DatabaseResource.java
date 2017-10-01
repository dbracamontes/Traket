/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tracket.resource;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author dani
 */
@DataSourceDefinition(
        name = "java:app/traket",
        className = "org.postgresql.ds.PGPoolingDataSource",
        url = "jdbc:postgresql://localhost:5432/traket",
        user = "postgres",
        password = "daniel87",
        databaseName = "traket"
)
@Startup
@Singleton
public class DatabaseResource {

    /*
    @Produces
    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;*/
}
