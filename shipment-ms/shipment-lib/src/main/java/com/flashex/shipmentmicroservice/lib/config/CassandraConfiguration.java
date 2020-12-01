package com.flashex.shipmentmicroservice.lib.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCassandraRepositories(basePackages = { "com.flashex.shipmentmicroservice.lib" })
public class CassandraConfiguration extends AbstractCassandraConfiguration {
        /*
         * Provide a contact point to the configuration.
         */

        @Value("${spring.data.cassandra.contact-points}")
        private String CONTACT_POINTS;

        @Value("${spring.data.cassandra.keyspace-name}")
        private String KEYSPACE;

        @Override
        public String getContactPoints() {
            return CONTACT_POINTS;
        }

        /*
         * Provide a keyspace name to the configuration.
         */
        @Override
        public String getKeyspaceName() {
            return KEYSPACE;
        }

        @Override
        protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
                CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
                        .createKeyspace(KEYSPACE).ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
                return Arrays.asList(specification);
        }

        @Override
        public String[] getEntityBasePackages() {
                return new String[]{"com.flashex.shipmentmicroservice.lib.model"};
        }

}

