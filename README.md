# shadower

###For now,shadower is just the skeleton.
####how to run shadower:
* 1) cd shadower/
* 2) mvn install;
* 3) cd shadower/shadower-server/;
* 4) mvn jetty:run
* 5) visit http://localhost:8080/shadower/

####Now to get some example datas, we run shadower-example.
* 1) install zookeeper and config the port 2181;
* 2) cd shadower-example/;
* 3) mvn install;
* 4) cd shadower-example-dubbo-address-service/,run AddressServiceBootStrap.java
* 5) cd shadower-example-dubbo-user-service/,run UserServiceBootStrap.java
* 6) cd shadower-example-dubbo-client/,run Client.java
* 7) wait one minute,visit http://localhost:8080/shadower/ again and input tracedId 1, click query button,
  then you will see something meaningful.
