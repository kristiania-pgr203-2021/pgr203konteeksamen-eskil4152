[![Java CI with Maven](https://github.com/kristiania-pgr203-2021/pgr203konteeksamen-eskil4152/actions/workflows/maven.yml/badge.svg)](https://github.com/kristiania-pgr203-2021/pgr203konteeksamen-eskil4152/actions/workflows/maven.yml)
# PGR203 Avansert Java eksamen# pgr203konteeksamen-eskil4152
pgr203konteeksamen-eskil4152 created by GitHub Classroom

## Linker til relevante repositories:
### Tidligere arbeidskrav (som vi hentet kode og instruksjoner fra)
* https://github.com/kristiania-pgr203-2021/pgr203-exam-CrumbMonsters
* https://github.com/kristiania-pgr203-2021/pgr203-innlevering-3-eskil4152

## Beskriv hvordan programmet skal testes:

### Testing
* Når du skal teste om serveren kjører går vi først til `localhost:8080`. Deretter tester vi følgende:
  * 
  * 
  * 
  * 
  * 

## Dokumentasjon av bygging og kjøring via CMD
* For å komme i gang starter vi med å kjøre en maven Clean. Dette kan gjøres gjennom Maven baren til høyre, under Lifcycle og så velge clean eller i terminal med "mvn clean". Dette gjøres for å forsikre oss om at rester etter tidlgiere programkjøringer blir borte.
* Deretter kjører vi en Maven Package. Dette kan gjøres på samme måte som steg 1, men i stedet for clean velger vi package under Lifecycle og i terminal skriver vi "mvn package".
Vi bør a få en build success og fått en JAR fil vi kan eksekvere til en egen mappe. MERK: Mappen må ha en properties fil med navn "config.properties" for å fungere. Denne filen er nødvendig for å få tilgang til postgres databasene.
* Jar-filen vil ligge i target. Denne kan nå flyttes hvor som helst
* Opprett pgr203.properties med følgende verdier:
  * `dataSource.url=[database-url]` (Din url)
  * `dataSource.username=[brukernavn]` (Ditt username)
  * `datasource.password=[passord]` (Ditt passord)
* Legg `pgr203.properties` i samme directory som jar-filen
* Programmet kan nå startes og bør kunne lese verdiene fra config.properties av seg selv.
* Skriv så 'java -jar Java-Eksamen-1.0-SNAPSHOT.jar' i terminalen. Dette skal eksekvere JAR filen og starte serveren.
* For å avslutte programmet må man per nå gå i terminal og trykke control c. Kleint
