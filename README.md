# multi-tenancy-using-spring
A demonstartation of Multi-tenancy using Spring

```

## Running this demo
You need a database which has a structure like this:

```
CREATE TABLE dbentries (
    id int not null auto_increment,
    date datetime not null,
    primary key(id)
);


Create a new database per tenant and include a new properties file
in the allTenants folder in the root of the solution.
The contents of this file looks like this:

```

name=<tenant id>
datasource.url=jdbc:mysql://localhost:3306/<tenant>
datasource.username=
datasource.password=
```

Finally Run the application as Springboot application and try it out using SOAPUI, Postman or Curl.
