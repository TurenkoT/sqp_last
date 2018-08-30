:: mvn -P[dev, test, prod] clean install
:: mvn -Pdev clean install by default

call mvn clean install

call mvn spring-boot:run -Drun.jvmArguments="-Dfile.encoding=UTF-8"