## Blood bank

### Public app:
<hr>
  `cd isa-public` <br>
  `npm install` for installing dependencies <br>
  `ng serve` for a dev server. <br>
  Navigate to `http://localhost:4200/`. 

### Backend server
<hr>

#### Grpc protocol
  Da bi maven prepoznao fajlove za gRPC potrebno je otici u File->Project Structure->Modules, odabrati isa-backend module, pa zatim otvoriti target folder, selektovati foder generated-sources i nakon desnog klika na njega oznaciti ga kao Sources.  
  
#### RabbitMQ
  Da bi RabbitMQ radio, morate skinuti RabbitMQ servis i ERLANG. Nakon toga je bitno pokrenuti RabbitMQ servis. Takođe je bitno i da imate dobro podešen application.properties fajl. Za pokretanje lokacijskog servisa neophodno je prvo pokrenuti Simulator projekat.
  
#### application.properties
  Application.properties sadrži osetljive podatke poput šifri za odredjene servise i biblioteke (Syncfusion scheduler, Mail servis, RabbitMQ,...) i iz tog razloga nije okačen na github platformu. Ukoliko želite da dodate ovaj fajl, kontaktirajte nekoga iz tima ko je radio na projektu.

### Alati
  Frontend: Angular 14
  Backend: Java Spring Boot, Maven
  Alati,biblioteke i slično: RabbitMQ, GRPC protokol, Mail servis, Syncfusion calendar, Material for Angular,...
  
### Skripte za bazu podataka
  Podaci se u bazu ubacuju iz aplikacije, nema posebnih skripti za ubacivanje podataka
  
