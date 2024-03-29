# Getting Started

### Set up DDL for ff4j in Postgres

* Ensure that Postgres is running
* Create a database ff4j
* Execute the below SQL
```sql
-- Main Table to store Features
CREATE TABLE FF4J_FEATURES (
                               FEAT_UID VARCHAR(100),
                               ENABLE INTEGER NOT NULL,
                               DESCRIPTION 	VARCHAR(1000),
                               STRATEGY		VARCHAR(1000),
                               EXPRESSION	    VARCHAR(255),
                               GROUPNAME		VARCHAR(100),
                               PRIMARY KEY(FEAT_UID)
);

-- Roles to store ACL, FK to main table
CREATE TABLE FF4J_ROLES (
                            FEAT_UID     VARCHAR(100) REFERENCES FF4J_FEATURES(FEAT_UID),
                            ROLE_NAME    VARCHAR(100),
                            PRIMARY KEY(FEAT_UID, ROLE_NAME)
);

-- Feature Internal Custom Properties
CREATE TABLE FF4J_CUSTOM_PROPERTIES (
                                        PROPERTY_ID  VARCHAR(100) NOT NULL,
                                        CLAZZ 		 VARCHAR(255) NOT NULL,
                                        CURRENTVALUE VARCHAR(255),
                                        FIXEDVALUES	 VARCHAR(1000),
                                        DESCRIPTION	 VARCHAR(1000),
                                        FEAT_UID     VARCHAR(100) REFERENCES FF4J_FEATURES(FEAT_UID),
                                        PRIMARY KEY(PROPERTY_ID, FEAT_UID)
);

-- @PropertyStore (edit general properties)
CREATE TABLE FF4J_PROPERTIES (
                                 PROPERTY_ID  VARCHAR(100) NOT NULL,
                                 CLAZZ 		 VARCHAR(255) NOT NULL,
                                 CURRENTVALUE VARCHAR(255),
                                 FIXEDVALUES	 VARCHAR(1000),
                                 DESCRIPTION	 VARCHAR(1000),
                                 PRIMARY KEY(PROPERTY_ID)
);

-- @see JdbcEventRepository (audit event)
CREATE TABLE FF4J_AUDIT (
                            EVT_UUID 	 VARCHAR(40)  NOT NULL,
                            EVT_TIME 	 TIMESTAMP 	  NOT NULL,
                            EVT_TYPE 	 VARCHAR(30)  NOT NULL,
                            EVT_NAME 	 VARCHAR(100)  NOT NULL,
                            EVT_ACTION 	 VARCHAR(100)  NOT NULL,
                            EVT_HOSTNAME VARCHAR(100)  NOT NULL,
                            EVT_SOURCE 	 VARCHAR(30)  NOT NULL,
                            EVT_DURATION INTEGER,
                            EVT_USER 	 VARCHAR(30),
                            EVT_VALUE 	 VARCHAR(100),
                            EVT_KEYS 	 VARCHAR(255),
                            PRIMARY KEY(EVT_UUID, EVT_TIME)
);

```

Important URLS:
Swagger - http://localhost:8080/ff4j-server/swagger-ui/index.html 
Dashboard: http://localhost:8080/ff4j-server/ff4j-web/home