FROM liquibase/liquibase:latest

WORKDIR /liquibase-changelog

RUN lpm add postgresql --global

COPY master.xml master.xml
COPY changelog changelog

ENTRYPOINT [ "liquibase", "update", "--changelog-file=master.xml" ]