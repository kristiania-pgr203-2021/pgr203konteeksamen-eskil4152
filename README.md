[![Java CI with Maven](https://github.com/kristiania-pgr203-2021/pgr203konteeksamen-eskil4152/actions/workflows/maven.yml/badge.svg)](https://github.com/kristiania-pgr203-2021/pgr203konteeksamen-eskil4152/actions/workflows/maven.yml)
# PGR203 Avansert Java eksamen# pgr203konteeksamen-eskil4152
pgr203konteeksamen-eskil4152 created by GitHub Classroom

## Linker til relevante repositories:
### Tidligere arbeidskrav (som vi hentet kode og instruksjoner fra)
* https://github.com/kristiania-pgr203-2021/pgr203-exam-CrumbMonsters
* https://github.com/kristiania-pgr203-2021/pgr203-innlevering-3-eskil4152

## Andre linker: 
* Github actions: https://github.com/kristiania-pgr203-2021/pgr203konteeksamen-eskil4152/actions
* Github classrooms/repository: https://github.com/kristiania-pgr203-2021/pgr203konteeksamen-eskil4152
* UML: documents/EnkelUML.puml

## Beskriv hvordan programmet skal testes:

### Testing
* Når du skal teste om serveren kjører går vi først til `localhost:1962`. Deretter tester vi følgende:
  * Se alle forfattere
  * Legg til bok på en forfatter
  * Vise alle bøker
  * Endre en forfatter
  * Endre en bok
  * Legge til enda en forfatter på en bok
  * Filtrere bøker etter en forfatter

## Dokumentasjon av bygging og kjøring via CMD
* For å komme i gang starter vi med å kjøre en maven Clean. Dette kan gjøres gjennom Maven baren til høyre, under Lifcycle og så velge clean eller i terminal med "mvn clean". Dette gjøres for å forsikre oss om at rester etter tidligere programkjøringer blir borte.
* Deretter kjører vi en Maven Package. Dette kan gjøres på samme måte som steg 1, men i stedet for clean velger vi package under Lifecycle og i terminal skriver vi "mvn package". Det kan hende at package failer ved test feil, det skjer noen ganger med httpclient, når httpbin.org ikke svarer som den skal. Da er det bare å kjøre mvn-clean og mvn-package igjen.
Vi bør a få en build success og fått en JAR fil vi kan eksekvere til en egen mappe, den ligger nå i. target mappen.
* Opprett pgr203.properties med følgende verdier:
  * `dataSource.url=[database-url]` (Din url)
  * `dataSource.username=[brukernavn]` (Ditt username)
  * `datasource.password=[passord]` (Ditt passord)
  * Legg filen i root directory
* Programmet kan nå startes og bør kunne lese verdiene fra pgr203.properties av seg selv.
* Skriv så 'java -jar java -jar pgr203konteeksamen-eskil4152-1.0-SNAPSHOT.jar' i terminalen. Dette skal eksekvere JAR filen og starte serveren.
* Du må legge `pgr203.properties` i i target directory om du ikke kjører java -jar med target/..
* For å avslutte programmet må man per nå gå i terminal og trykke control c

## Leveranse: 
* [x] Brukeren kan opprette forfatter
* [x] Brukeren kan liste ut forfattere 
* [x] Brukeren kan legge til bøker på en forfatter
* [x] Bruker kan liste ut alle bøker
* [x] Bruker kan liste ut alle bøker pr forfatter
* [x] Vise redigeringsskjermbilde for forfatter?
* [x] Bruker kan endre navn på forfatter
* [x] Vise redigeringsskjermbilde for bøker?
* [x] Bruker kan endre navn på bok
* [x] Bruker kan endre beskrivelse på bok
* [x] Brukeren kan legge til flere forfattere pr bok

## Ekstra leveranse: 
* [x] Redirect etter POST
* [x] POST og GET med norske tegn
* [ ] POST og GET med norske tegn i test
* [ ] POST og GET til samme requestTarget
* [ ] Feilhåndtering
* [x] Welcome page
* [ ] FileController

## Design: 
Vi designet programmet ved å først kunne lagt author og book i en database. Så la vi til at du kunne endre author og book, ved at den har flere verdier som skal endres, som endres ved select som printer ut navnet. Dermed vil den endre navnet til bok/forfatter hvor den matcher det gamle navnet du selecter. Så vil de oppdatere de andre tingene til objektet som har det nye navnet som ble oppdatert. Vi designet programmet ved å først kunne lagt author og book i en database. Så la vi til at du kunne endre author og book, ved at den har flere verdier som skal endres, som endres ved select som printer ut navnet. Dermed vil den endre navnet til bok/forfatter hvor den matcher det gamle navnet du selecter. Så vil de oppdatere de andre tingene til objektet som har det nye navnet som ble oppdatert.
Når du legger til forfattere til en bok, vil du måtte bruke select til å velge forfatter og bok, så du kun kan velge forfattere eller bøker som eksisterer i database. Så vil den finne boken som ble valgt, og oppdatere forfatter kolonnen til eksisterende verdier + valgt forfatter name. 
Når du filtrerer bøker etter forfatter, eller printer alle forfattere vil den printe alle bøker som forfatteren(e) er en del av. Det gjorde vi gjennom at Postgres bruker regex til å finne forfatternavn i forfatterkolonnen til bøkene, og om det finnes inni der, vil den skrive ut boken til author.getBooks().

Vi har også en EmptyTargetController, som er laget på en ganske enkel måte. Det er en kontroller i serveren som reagerer på "/", da vi den gå til kontrollen som kun returnerer en 303 til /index.html. 

## UML
![UMLPain](https://user-images.githubusercontent.com/10678081/155895174-1732bf97-b4f0-4bcd-9505-0841164e9ef1.jpeg)
