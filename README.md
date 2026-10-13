# Vidéothèque : API REST Spring Boot

Projet du labo MSI (Framework Spring Boot) : une API REST pour gérer une vidéothèque
(films, catégories, acteurs).

## Technologies

- Java 17
- Spring Boot 3.0.0 (Web, Data JPA, Validation)
- Base H2 en mémoire
- Swagger UI (springdoc-openapi 2.0.0)
- Tests : JUnit 5, Mockito, MockMvc, AssertJ

## Modèle de données

| Entité | Attributs |
|---|---|
| Film | title, year, description, releaseDate, duration |
| Category | name |
| Actor | name, bio |

Associations :

- un **Film** appartient à **une** Category (une Category a plusieurs Films) ;
- un **Film** a plusieurs **Actors**, un Actor joue dans plusieurs Films (table `film_actor`).

## Architecture en couches

Pour chaque entité : `controller` -> `service` (interface + implémentation) -> `repository` -> `model`.
Le package `common` contient `ExceptionAdvice`, qui centralise les messages d'erreur.

## Lancer l'application

Prérequis : JDK 17.

```bash
./mvnw spring-boot:run
```

- API : http://localhost:8080/api
- Swagger : http://localhost:8080/swagger-ui.html
- Console H2 : http://localhost:8080/h2-console (URL `jdbc:h2:mem:videotheque`, utilisateur `sa`, mot de passe vide)

La base est en mémoire : les données sont perdues à l'arrêt.

## Routes

Chaque entité expose cinq routes (exemple pour les films) :

| Verbe | Route | Rôle |
|---|---|---|
| GET | `/api/films` | liste des films |
| GET | `/api/films/{id}` | un film |
| POST | `/api/films` | créer un film |
| PUT | `/api/films/{id}` | modifier un film |
| DELETE | `/api/films/{id}` | supprimer un film |

Même chose pour `/api/categories` et `/api/actors`.
Route supplémentaire : `GET /api/categories/{id}/films` (films d'une catégorie).

Exemple de création d'un film :

```json
{
  "title": "Nikita",
  "year": 1990,
  "description": "Une jeune femme devient tueuse d'État.",
  "releaseDate": "1990-02-21",
  "duration": 118,
  "category": { "id": 1 },
  "actors": [ { "id": 1 } ]
}
```

## Contrôles et gestion des erreurs

- Validation (`@Valid`) : nom obligatoire, titre obligatoire, durée positive, date de sortie non future.
- Unicité : nom de catégorie unique, couple (titre, année) unique pour un film.
- Les erreurs sont renvoyées en 400 avec un message explicite (`ExceptionAdvice`).

## Tests

```bash
./mvnw test
```

Un test simple par couche, au format GIVEN / WHEN / THEN :
- `FilmRepositoryTest` : `findByCategoryId` sur la base H2 (`@DataJpaTest`) ;
- `FilmServiceTest` : `findAll` avec un faux repository (Mockito) ;
- `FilmControllerTest` : `GET /api/films` avec un faux service (`@WebMvcTest` + MockMvc).

## Choix de sérialisation JSON

Les associations Film/Category et Film/Actor sont bidirectionnelles : sans annotation Jackson,
la sérialisation boucle (StackOverflowError). Le cours propose trois solutions :

- `@JsonManagedReference` / `@JsonBackReference` : la référence arrière doit être une propriété
  simple, pas une liste ; ce couple ne convient pas à `Category.films` ni au ManyToMany Film/Actor.
- `@JsonIdentityInfo` + `@JsonIdentityReference(alwaysAsId = true)` sur `Film.category` :
  le film n'afficherait plus que l'id de sa catégorie au lieu de l'objet complet.
- **Solution retenue : `@JsonIgnore` sur `Category.films` et `Actor.films`.**
  Le film est l'objet central : il affiche sa catégorie et ses acteurs complets.
  Une catégorie et un acteur n'affichent que leurs propres attributs (comme l'Artist du jukebox).
  Les films d'une catégorie restent disponibles avec `GET /api/categories/{id}/films`.
