# 🍃 Formation Spring Boot — Dyma (Complète)
> **URL officielle :** [dyma.fr/formations/spring-boot](https://www.dyma.fr/formations/spring-boot)  
> **Durée :** ~40 heures | **19 chapitres** | **85 leçons** | **170 quiz**  
> **Prérequis :** Bases en Java (recommandé : SQL)  
> **Projet fil rouge :** Dyma Tennis — API REST complète de gestion de joueurs de tennis

---

## 📋 Table des matières

1. [Introduction](#chapitre-1--introduction)
2. [Mise en place d'une application Spring Boot](#chapitre-2--mise-en-place-dune-application-spring-boot)
3. [Structure et configuration](#chapitre-3--structure-et-configuration-dune-application-spring-boot)
4. [Projet Dyma Tennis – Partie 1 : mise en place](#chapitre-4--projet-dyma-tennis---partie-1--mise-en-place)
5. [Partie Web (Spring MVC)](#chapitre-5--spring-boot--partie-web)
6. [Projet Dyma Tennis – Partie 2 : couche web](#chapitre-6--projet-dyma-tennis---partie-2--couche-web)
7. [Partie Services (Clean Code & SOLID)](#chapitre-7--spring-boot--partie-services)
8. [Projet Dyma Tennis – Partie 3 : couche services](#chapitre-8--projet-dyma-tennis---partie-3--couche-services)
9. [Partie Data (Spring Data JPA)](#chapitre-9--spring-boot--partie-data)
10. [Projet Dyma Tennis – Partie 4 : couche data](#chapitre-10--projet-dyma-tennis---partie-4--couche-data)
11. [Projet Dyma Tennis – Partie 5 : tests automatisés](#chapitre-11--projet-dyma-tennis---partie-5--tests-automatisés)
12. [Partie Sécurité (Spring Security)](#chapitre-12--spring-boot---partie-sécurité)
13. [Projet Dyma Tennis – Partie 6 : sécurisation](#chapitre-13--projet-dyma-tennis---partie-6--sécurisation)
14. [Partie Déploiement](#chapitre-14--spring-boot--partie-déploiement)
15. [Projet Dyma Tennis – Partie 7 : packaging et déploiement](#chapitre-15--projet-dyma-tennis---partie-7--packaging-et-déploiement)
16. [Partie Monitoring](#chapitre-16--spring-boot--partie-monitoring)
17. [Projet Dyma Tennis – Partie 8 : surveillance et monitoring](#chapitre-17--projet-dyma-tennis---partie-8--surveillance-et-monitoring)
18. [Projet Dyma Tennis – Partie 9 : nouvelles fonctionnalités](#chapitre-18--projet-dyma-tennis---partie-9--nouvelles-fonctionnalités)
19. [Projet Dyma Tennis – Partie 10 : authentification avancée](#chapitre-19--projet-dyma-tennis---partie-10--authentification-avancée)
20. [Projets supplémentaires pour maîtriser Spring Boot](#-projets-supplémentaires-pour-maîtriser-spring-boot)

---

## Chapitre 1 : Introduction

### 📚 Leçons
- À l'abordage !
- À quoi sert Spring Boot ?
- L'architecture d'une application Spring Boot
- Environnement de développement
- Notions à connaître

### 🧠 Explication

**Spring Boot** est un framework Java open source qui simplifie le développement d'applications web et de microservices. Il repose sur le **Spring Framework** (créé en 2002) et résout un problème majeur : la configuration excessive.

**L'architecture n-tiers** (ou architecture en couches) est le modèle de référence :

```
┌─────────────────────────────────────┐
│         Couche Présentation         │  ← Contrôleurs REST (ce que voit le monde)
├─────────────────────────────────────┤
│          Couche Services            │  ← Logique métier
├─────────────────────────────────────┤
│       Couche Accès aux Données      │  ← Repositories / Base de données
└─────────────────────────────────────┘
```

**Fonctionnalités clés de Spring Boot :**
- **Auto-configuration** : configure automatiquement les composants selon les dépendances
- **Starters** : dépendances prêtes à l'emploi (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`...)
- **Embedded Server** : Tomcat intégré, pas besoin d'un serveur externe
- **Fat JAR** : déploiement simplifié en un seul fichier `.jar` exécutable

**Environnement de développement à installer :**
- JDK 17 ou 21 (LTS recommandé)
- IntelliJ IDEA (ou Eclipse / VS Code)
- Maven ou Gradle
- Postman (pour tester les APIs)
- Docker Desktop

---

## Chapitre 2 : Mise en place d'une application Spring Boot

### 📚 Leçons
- Création d'une application
- Création d'un premier contrôleur REST
- Création d'un premier service
- Création d'un premier repository

### 🧠 Explication

**Création du projet via Spring Initializr :** [start.spring.io](https://start.spring.io)

```
Project : Maven
Language : Java
Spring Boot : 3.x.x
Dependencies : Spring Web, Spring Data JPA, H2 Database
```

**Structure du projet :**

```
src/
├── main/
│   ├── java/
│   │   └── com/dyma/demo/
│   │       ├── DemoApplication.java        ← Point d'entrée
│   │       ├── controller/
│   │       │   └── HelloController.java
│   │       ├── service/
│   │       │   └── HelloService.java
│   │       └── repository/
│   │           └── HelloRepository.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/...
```

**Exemple — Premier contrôleur REST :**

```java
@RestController
@RequestMapping("/api")
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public String hello() {
        return helloService.getMessage();
    }
}
```

**Exemple — Premier service :**

```java
@Service
public class HelloService {

    public String getMessage() {
        return "Bonjour depuis Spring Boot !";
    }
}
```

**Exemple — Premier repository (avec Spring Data JPA) :**

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```

### 🔨 Mini-projet : API de gestion de tâches (Todo)

Créer une API simple avec :
- `GET /api/todos` → lister toutes les tâches
- `POST /api/todos` → créer une tâche
- `PUT /api/todos/{id}` → modifier une tâche
- `DELETE /api/todos/{id}` → supprimer une tâche

---

## Chapitre 3 : Structure et configuration d'une application Spring Boot

### 📚 Leçons
- Immersion dans notre projet Spring Boot
- Le build du projet
- L'auto-configuration
- Personnaliser la configuration

### 🧠 Explication

**Le fichier `application.properties` (ou `application.yml`) :**

```properties
# Server
server.port=8080

# Base de données
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# Logging
logging.level.com.dyma=DEBUG
```

**Version YAML (plus lisible) :**

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
```

**L'auto-configuration :** Spring Boot détecte automatiquement les dépendances présentes dans le classpath et configure les beans nécessaires. Par exemple, si `spring-boot-starter-web` est présent, Tomcat est configuré automatiquement.

**Le build Maven :**

```bash
# Compiler et packager
mvn clean package

# Lancer l'application
java -jar target/demo-0.0.1-SNAPSHOT.jar

# Ou directement avec Maven
mvn spring-boot:run
```

---

## Chapitre 4 : Projet Dyma Tennis - Partie 1 : mise en place

### 📚 Leçons
- Présentation du projet
- Mise à jour de l'existant
- Mise en place des tests automatisés
- Mise en place d'une interface de démo

### 🧠 Explication

**Présentation du projet Dyma Tennis :**

On va construire une **API REST complète** pour gérer des joueurs de tennis. Le projet couvre :
- CRUD complet sur les joueurs
- Gestion des tournois et inscriptions
- Sécurisation avec Spring Security + JWT
- Déploiement sur le Cloud avec Docker

**Structure du projet Dyma Tennis :**

```
com.dyma.tennis/
├── controller/         ← Couche présentation (REST)
├── service/            ← Couche logique métier
├── repository/         ← Couche accès aux données
├── model/              ← Entités JPA
├── dto/                ← Data Transfer Objects
├── exception/          ← Gestion des erreurs
└── config/             ← Configuration Spring Security, etc.
```

**Dépendances `pom.xml` :**

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    <!-- Tests -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## Chapitre 5 : Spring Boot : partie web

### 📚 Leçons
- Introduction à Spring MVC
- Les contrôleurs
- La validation des données
- La gestion des erreurs
- Le serveur embarqué

### 🧠 Explication

**Spring MVC** est le module qui gère les requêtes HTTP. Le flux est :

```
Client HTTP → DispatcherServlet → Controller → Service → Response
```

**Annotations des contrôleurs :**

```java
@RestController          // = @Controller + @ResponseBody
@RequestMapping("/api/players")
public class PlayerController {

    @GetMapping                    // GET /api/players
    @PostMapping                   // POST /api/players
    @PutMapping("/{id}")          // PUT /api/players/{id}
    @DeleteMapping("/{id}")       // DELETE /api/players/{id}
    @PatchMapping("/{id}")        // PATCH /api/players/{id}
}
```

**Récupérer des données de la requête :**

```java
@GetMapping("/{id}")
public Player getPlayer(
    @PathVariable Long id,                    // depuis l'URL
    @RequestParam(required = false) String name,  // query param
    @RequestBody PlayerDto dto               // corps JSON
) { ... }
```

**Validation des données avec Bean Validation :**

```java
public class PlayerDto {

    @NotNull(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @Min(value = 0)
    @Max(value = 100)
    private int ranking;

    @Email
    private String email;
}
```

```java
@PostMapping
public ResponseEntity<Player> createPlayer(@Valid @RequestBody PlayerDto dto) {
    // @Valid déclenche la validation
    Player player = playerService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(player);
}
```

**Gestion des erreurs avec `@ControllerAdvice` :**

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePlayerNotFound(PlayerNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Erreur de validation",
            LocalDateTime.now(),
            errors
        );
        return ResponseEntity.badRequest().body(error);
    }
}
```

---

## Chapitre 6 : Projet Dyma Tennis - Partie 2 : couche web

### 📚 Leçons
- Design d'API
- Implémentation du contrôleur
- Validation des données
- Gestion des erreurs

### 🧠 Explication

**Design de l'API Dyma Tennis :**

| Méthode | URL | Description |
|---------|-----|-------------|
| `GET` | `/api/players` | Lister tous les joueurs |
| `GET` | `/api/players/{id}` | Obtenir un joueur |
| `POST` | `/api/players` | Créer un joueur |
| `PUT` | `/api/players/{id}` | Modifier un joueur |
| `DELETE` | `/api/players/{id}` | Supprimer un joueur |

**Implémentation du contrôleur Player :**

```java
@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        List<PlayerDto> players = playerService.findAll();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable Long id) {
        PlayerDto player = playerService.findById(id);
        return ResponseEntity.ok(player);
    }

    @PostMapping
    public ResponseEntity<PlayerDto> createPlayer(@Valid @RequestBody CreatePlayerDto dto) {
        PlayerDto created = playerService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePlayerDto dto) {
        PlayerDto updated = playerService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

**DTO :**

```java
// Réponse
public record PlayerDto(Long id, String firstName, String lastName, int ranking) {}

// Création
public record CreatePlayerDto(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @Min(1) @Max(1000) int ranking
) {}

// Modification
public record UpdatePlayerDto(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @Min(1) @Max(1000) int ranking
) {}
```

---

## Chapitre 7 : Spring Boot : partie services

### 📚 Leçons
- Principes de Clean Code
- Principes SOLID
- Stratégies de refactoring de code
- Stratégies d'organisation de code
- L'inversion de contrôle

### 🧠 Explication

**Clean Code — Règles essentielles :**

```java
// ❌ Mauvais
public List<P> getP(boolean f) {
    List<P> r = new ArrayList<>();
    for (P p : repo.findAll()) {
        if (f && p.getRk() < 10) r.add(p);
        else if (!f) r.add(p);
    }
    return r;
}

// ✅ Bon
public List<Player> findAllPlayers() {
    return playerRepository.findAll();
}

public List<Player> findTopTenPlayers() {
    return playerRepository.findTop10ByOrderByRankingAsc();
}
```

**Principes SOLID :**

| Principe | Description | Exemple |
|----------|-------------|---------|
| **S** – Single Responsibility | Une classe = une responsabilité | `PlayerService` ne gère pas les emails |
| **O** – Open/Closed | Ouvert à l'extension, fermé à la modification | Utiliser des interfaces |
| **L** – Liskov Substitution | Les sous-classes remplacent les parents | Bonne hiérarchie de classes |
| **I** – Interface Segregation | Des interfaces spécifiques plutôt qu'une grosse interface | `PlayerReader`, `PlayerWriter` |
| **D** – Dependency Inversion | Dépendre des abstractions, pas des implémentations | Injection de dépendances |

**L'Inversion de Contrôle (IoC) et l'injection de dépendances :**

```java
// Spring gère la création et l'injection des beans

// ❌ Couplage fort (new = dépendance directe)
public class PlayerService {
    private PlayerRepository repo = new PlayerRepositoryImpl(); // mauvais
}

// ✅ Injection par constructeur (recommandée)
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    // Spring injecte automatiquement l'implémentation
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
}
```

**Les stéréotypes Spring :**

```java
@Component      // Composant générique
@Service        // Logique métier
@Repository     // Accès aux données (+ gestion exceptions JPA)
@Controller     // Contrôleur web
@RestController // Contrôleur REST
@Configuration  // Classe de configuration
```

---

## Chapitre 8 : Projet Dyma Tennis - Partie 3 : couche services

### 📚 Leçons
- Design de la couche services
- Service : lister les joueurs
- Service : consulter les informations d'un joueur
- Service : créer un joueur
- Service : modifier les informations d'un joueur
- Service : supprimer un joueur

### 🧠 Explication

**Interface et implémentation du service :**

```java
// Interface
public interface PlayerService {
    List<PlayerDto> findAll();
    PlayerDto findById(Long id);
    PlayerDto create(CreatePlayerDto dto);
    PlayerDto update(Long id, UpdatePlayerDto dto);
    void delete(Long id);
}

// Implémentation
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public PlayerServiceImpl(PlayerRepository playerRepository,
                              PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    @Override
    public List<PlayerDto> findAll() {
        return playerRepository.findAll()
                .stream()
                .map(playerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlayerDto findById(Long id) {
        return playerRepository.findById(id)
                .map(playerMapper::toDto)
                .orElseThrow(() -> new PlayerNotFoundException(id));
    }

    @Override
    public PlayerDto create(CreatePlayerDto dto) {
        Player player = playerMapper.toEntity(dto);
        Player saved = playerRepository.save(player);
        return playerMapper.toDto(saved);
    }

    @Override
    public PlayerDto update(Long id, UpdatePlayerDto dto) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));

        player.setFirstName(dto.firstName());
        player.setLastName(dto.lastName());
        player.setRanking(dto.ranking());

        Player updated = playerRepository.save(player);
        return playerMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new PlayerNotFoundException(id);
        }
        playerRepository.deleteById(id);
    }
}
```

**Exception personnalisée :**

```java
public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(Long id) {
        super("Joueur introuvable avec l'ID : " + id);
    }
}
```

---

## Chapitre 9 : Spring Boot : partie data

### 📚 Leçons
- Introduction aux bases de données
- Introduction à Spring Data
- Les entités
- Les repositories

### 🧠 Explication

**Spring Data JPA** est une couche d'abstraction qui simplifie l'accès aux bases de données relationnelles via JPA/Hibernate.

**Entité JPA :**

```java
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false)
    private int ranking;

    @Column(unique = true)
    private String email;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructeurs, getters, setters...
}
```

**Repository Spring Data :**

```java
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    // Méthodes générées automatiquement par Spring Data
    List<Player> findByLastName(String lastName);
    Optional<Player> findByEmail(String email);
    List<Player> findByRankingLessThan(int ranking);
    List<Player> findTop10ByOrderByRankingAsc();
    boolean existsByEmail(String email);

    // Requête JPQL personnalisée
    @Query("SELECT p FROM Player p WHERE p.ranking BETWEEN :min AND :max ORDER BY p.ranking")
    List<Player> findByRankingBetween(@Param("min") int min, @Param("max") int max);

    // Requête SQL native
    @Query(value = "SELECT * FROM players WHERE first_name LIKE %:name%", nativeQuery = true)
    List<Player> searchByFirstName(@Param("name") String name);
}
```

**Relations JPA :**

```java
// One-to-Many : Un joueur peut participer à plusieurs tournois
@OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private List<Registration> registrations = new ArrayList<>();

// Many-to-One : Plusieurs inscriptions pour un joueur
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "player_id", nullable = false)
private Player player;

// Many-to-Many : Joueurs dans des tournois
@ManyToMany
@JoinTable(
    name = "player_tournament",
    joinColumns = @JoinColumn(name = "player_id"),
    inverseJoinColumns = @JoinColumn(name = "tournament_id")
)
private List<Tournament> tournaments = new ArrayList<>();
```

---

## Chapitre 10 : Projet Dyma Tennis - Partie 4 : couche data

### 📚 Leçons
- Design du modèle de données
- Initialisation du modèle de données
- Repository : lister, consulter, créer, modifier, supprimer les joueurs

### 🧠 Explication

**Modèle de données Dyma Tennis :**

```sql
-- players
CREATE TABLE players (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    ranking INT NOT NULL,
    email VARCHAR(100) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- tournaments
CREATE TABLE tournaments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    start_date DATE,
    end_date DATE
);

-- registrations
CREATE TABLE registrations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    player_id BIGINT NOT NULL,
    tournament_id BIGINT NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (player_id) REFERENCES players(id),
    FOREIGN KEY (tournament_id) REFERENCES tournaments(id)
);
```

**Initialisation avec `data.sql` ou `CommandLineRunner` :**

```java
@Component
public class DataInitializer implements CommandLineRunner {

    private final PlayerRepository playerRepository;

    public DataInitializer(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void run(String... args) {
        if (playerRepository.count() == 0) {
            List<Player> players = List.of(
                new Player("Rafael", "Nadal", 1),
                new Player("Novak", "Djokovic", 2),
                new Player("Roger", "Federer", 3),
                new Player("Carlos", "Alcaraz", 4)
            );
            playerRepository.saveAll(players);
            System.out.println("✅ Données initialisées !");
        }
    }
}
```

---

## Chapitre 11 : Projet Dyma Tennis - Partie 5 : tests automatisés

### 📚 Leçons
- Les différents types de tests
- Tests unitaires dans la couche services
- Tests d'intégration des couches services et data
- Tests unitaires dans la couche web
- Tests de bout en bout

### 🧠 Explication

**Pyramide des tests :**

```
        /\
       /  \
      / E2E\          Tests de bout en bout (peu nombreux, lents)
     /------\
    /  Intég. \       Tests d'intégration (couverture couches)
   /------------\
  / Unitaires    \    Tests unitaires (nombreux, rapides)
 /________________\
```

**Tests unitaires du service (avec Mockito) :**

```java
@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    void findById_ShouldReturnPlayer_WhenPlayerExists() {
        // Given
        Player player = new Player(1L, "Rafael", "Nadal", 1);
        PlayerDto dto = new PlayerDto(1L, "Rafael", "Nadal", 1);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(playerMapper.toDto(player)).thenReturn(dto);

        // When
        PlayerDto result = playerService.findById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.firstName()).isEqualTo("Rafael");
        verify(playerRepository).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenPlayerNotFound() {
        // Given
        when(playerRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> playerService.findById(999L))
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessageContaining("999");
    }
}
```

**Tests unitaires du contrôleur (avec MockMvc) :**

```java
@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllPlayers_ShouldReturn200_WithListOfPlayers() throws Exception {
        // Given
        List<PlayerDto> players = List.of(
            new PlayerDto(1L, "Rafael", "Nadal", 1),
            new PlayerDto(2L, "Novak", "Djokovic", 2)
        );
        when(playerService.findAll()).thenReturn(players);

        // When & Then
        mockMvc.perform(get("/api/players")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Rafael"));
    }

    @Test
    void createPlayer_ShouldReturn400_WhenInvalidData() throws Exception {
        // Given — données invalides (prénom vide)
        CreatePlayerDto dto = new CreatePlayerDto("", "Nadal", 1);

        // When & Then
        mockMvc.perform(post("/api/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
```

**Tests d'intégration :**

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class PlayerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void createAndRetrievePlayer_ShouldWork() throws Exception {
        // Créer un joueur
        String json = """
            {
                "firstName": "Carlos",
                "lastName": "Alcaraz",
                "ranking": 1
            }
            """;

        MvcResult result = mockMvc.perform(post("/api/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        // Vérifier qu'il est en base
        assertThat(playerRepository.count()).isEqualTo(1);
    }
}
```

---

## Chapitre 12 : Spring Boot - partie sécurité

### 📚 Leçons
- Quelques notions de sécurité
- Introduction à Spring Security
- Gestion de l'authentification
- Gestion des autorisations

### 🧠 Explication

**Spring Security** protège les applications via :
- **Authentification** : qui es-tu ? (login/password, JWT, OAuth2...)
- **Autorisation** : as-tu le droit de faire ça ? (rôles, permissions)

**Configuration de Spring Security :**

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()        // Routes publiques
                .requestMatchers(HttpMethod.GET, "/api/players").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Admin seulement
                .anyRequest().authenticated()                       // Tout le reste : authentifié
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();

        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin123"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
```

**Autorisation au niveau des méthodes :**

```java
@Service
@PreAuthorize("hasRole('USER')")
public class PlayerServiceImpl {

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }
}
```

---

## Chapitre 13 : Projet Dyma Tennis - Partie 6 : sécurisation

### 📚 Leçons
- Activation de Spring Security
- Configuration de l'authentification
- Configuration des autorisations
- Sécurisation en profondeur

### 🧠 Explication

**Authentification avec base de données :**

```java
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
```

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                    "Utilisateur introuvable : " + username));
    }
}
```

---

## Chapitre 14 : Spring Boot : partie déploiement

### 📚 Leçons
- Les différents types d'environnements
- Le cycle de vie de l'application
- Introduction à Docker
- Packaging et déploiement d'une application Spring Boot

### 🧠 Explication

**Les environnements :**

| Environnement | Usage | Base de données |
|---------------|-------|-----------------|
| `dev` | Développement local | H2 (in-memory) |
| `test` | Tests CI/CD | H2 ou PostgreSQL de test |
| `prod` | Production | PostgreSQL, MySQL... |

**Profils Spring Boot :**

```yaml
# application.yml (partagé)
spring:
  application:
    name: dyma-tennis

---
# application-dev.yml
spring:
  config:
    activate:
      on-profile: dev
  datasource:
    url: jdbc:h2:mem:tennisdb
  jpa:
    show-sql: true

---
# application-prod.yml
spring:
  config:
    activate:
      on-profile: prod
  datasource:
    url: jdbc:postgresql://localhost:5432/tennisdb
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    show-sql: false
```

**Lancer avec un profil :**

```bash
java -jar tennis.jar --spring.profiles.active=prod
# ou
SPRING_PROFILES_ACTIVE=prod java -jar tennis.jar
```

**Packaging en Fat JAR :**

```bash
mvn clean package -DskipTests
java -jar target/dyma-tennis-1.0.0.jar
```

---

## Chapitre 15 : Projet Dyma Tennis - Partie 7 : packaging et déploiement

### 📚 Leçons
- Création des profils développement et production
- Packaging et déploiement local
- Conteneurisation avec Docker
- Déploiement sur le Cloud

### 🧠 Explication

**Dockerfile pour Spring Boot :**

```dockerfile
# Build stage
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

**docker-compose.yml :**

```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - DB_USERNAME=tennis_user
      - DB_PASSWORD=secret
    depends_on:
      - db

  db:
    image: postgres:16-alpine
    environment:
      POSTGRES_DB: tennisdb
      POSTGRES_USER: tennis_user
      POSTGRES_PASSWORD: secret
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

**Commandes Docker :**

```bash
# Build l'image
docker build -t dyma-tennis:latest .

# Lancer avec docker-compose
docker-compose up -d

# Voir les logs
docker-compose logs -f app

# Arrêter
docker-compose down
```

---

## Chapitre 16 : Spring Boot : partie monitoring

### 📚 Leçons
- Introduction au monitoring
- Introduction aux logs
- La gestion des exceptions
- Introduction à Spring Boot Actuator

### 🧠 Explication

**Spring Boot Actuator** expose des endpoints de monitoring :

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,env
  endpoint:
    health:
      show-details: always
```

**Endpoints disponibles :**

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Santé de l'application |
| `/actuator/info` | Informations de l'app |
| `/actuator/metrics` | Métriques (CPU, mémoire...) |
| `/actuator/env` | Variables d'environnement |
| `/actuator/loggers` | Gestion des loggers |

**Configuration des logs avec Logback :**

```yaml
logging:
  level:
    root: WARN
    com.dyma: DEBUG
    org.springframework.web: INFO
  pattern:
    console: "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/tennis-app.log
```

**Utiliser SLF4J dans le code :**

```java
@Service
public class PlayerServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);

    public PlayerDto create(CreatePlayerDto dto) {
        log.info("Création d'un nouveau joueur : {} {}", dto.firstName(), dto.lastName());
        try {
            Player saved = playerRepository.save(player);
            log.debug("Joueur créé avec succès : ID={}", saved.getId());
            return playerMapper.toDto(saved);
        } catch (Exception e) {
            log.error("Erreur lors de la création du joueur", e);
            throw e;
        }
    }
}
```

---

## Chapitre 17 : Projet Dyma Tennis - Partie 8 : surveillance et monitoring

### 📚 Leçons
- Gestion des exceptions
- Gestion des logs
- Analyse de logs avec Graylog
- Activation de Spring Boot Actuator

### 🧠 Explication

**Custom Health Indicator :**

```java
@Component
public class TennisHealthIndicator implements HealthIndicator {

    private final PlayerRepository playerRepository;

    @Override
    public Health health() {
        try {
            long count = playerRepository.count();
            return Health.up()
                    .withDetail("playersCount", count)
                    .withDetail("status", "Base de données accessible")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}
```

---

## Chapitre 18 : Projet Dyma Tennis - Partie 9 : nouvelles fonctionnalités

### 📚 Leçons
- Mise à jour de la base de données
- Gestion de l'identité des joueurs
- Gestion des tournois
- Gestion des inscriptions

### 🧠 Explication

**Migrations de base de données avec Flyway :**

```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

```sql
-- src/main/resources/db/migration/V1__init.sql
CREATE TABLE players (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    ranking INT NOT NULL
);

-- src/main/resources/db/migration/V2__add_tournaments.sql
CREATE TABLE tournaments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE
);

CREATE TABLE registrations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    player_id BIGINT NOT NULL,
    tournament_id BIGINT NOT NULL,
    FOREIGN KEY (player_id) REFERENCES players(id),
    FOREIGN KEY (tournament_id) REFERENCES tournaments(id)
);
```

**Service d'inscription aux tournois :**

```java
@Service
public class RegistrationService {

    private final PlayerRepository playerRepository;
    private final TournamentRepository tournamentRepository;
    private final RegistrationRepository registrationRepository;

    public void registerPlayerToTournament(Long playerId, Long tournamentId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId));

        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException(tournamentId));

        boolean alreadyRegistered = registrationRepository
                .existsByPlayerAndTournament(player, tournament);

        if (alreadyRegistered) {
            throw new AlreadyRegisteredException(playerId, tournamentId);
        }

        Registration registration = new Registration(player, tournament);
        registrationRepository.save(registration);
    }
}
```

---

## Chapitre 19 : Projet Dyma Tennis - Partie 10 : authentification avancée

### 📚 Leçons
- Authentification avancée avec JWT
- Gestion de l'authentification avec JWT
- Authentification avancée avec un tiers (OAuth2)
- Gestion de l'authentification avec Keycloak

### 🧠 Explication

**JWT (JSON Web Token) — Authentification stateless :**

```xml
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>
```

**Service JWT :**

```java
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
```

**Filtre JWT :**

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
```

**Endpoint d'authentification :**

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = authService.authenticate(request.username(), request.password());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
```

---

## 🚀 Projets supplémentaires pour maîtriser Spring Boot

### Projet 1 — Blog API (Débutant)

**Fonctionnalités :**
- CRUD articles, catégories, commentaires
- Pagination et tri
- Recherche full-text

**Endpoints :**
```
GET    /api/articles?page=0&size=10&sort=createdAt,desc
GET    /api/articles/{id}
POST   /api/articles
PUT    /api/articles/{id}
DELETE /api/articles/{id}
GET    /api/articles/{id}/comments
POST   /api/articles/{id}/comments
```

---

### Projet 2 — E-commerce API (Intermédiaire)

**Fonctionnalités :**
- Gestion produits, catégories, stock
- Panier et commandes
- Paiement simulé
- JWT Authentication
- Rôles ADMIN / CUSTOMER

**Entités :**
```
Product ──── Category
   │
Order ──────── OrderItem
   │
Customer ───── Cart ──── CartItem
```

---

### Projet 3 — Microservices (Avancé)

**Architecture :**
```
API Gateway (Spring Cloud Gateway)
    ├── User Service     → gestion utilisateurs
    ├── Product Service  → catalogue produits
    ├── Order Service    → commandes
    └── Notification Service → emails/SMS

+ Eureka (Service Discovery)
+ Config Server (configuration centralisée)
+ RabbitMQ / Kafka (communication asynchrone)
```

---

## 📌 Récapitulatif des annotations clés

| Annotation | Rôle |
|-----------|------|
| `@SpringBootApplication` | Point d'entrée de l'app |
| `@RestController` | Contrôleur REST |
| `@Service` | Composant service |
| `@Repository` | Accès aux données |
| `@Entity` | Entité JPA |
| `@GetMapping` / `@PostMapping` | Mapping HTTP |
| `@PathVariable` | Variable de l'URL |
| `@RequestBody` | Corps JSON |
| `@Valid` | Validation Bean Validation |
| `@Transactional` | Gestion des transactions |
| `@SpringBootTest` | Test d'intégration |
| `@WebMvcTest` | Test du contrôleur |
| `@MockBean` | Mock Spring bean |
| `@PreAuthorize` | Autorisation méthode |

---

## 🔗 Ressources complémentaires

- 📘 [Documentation officielle Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- 🌐 [Spring Initializr](https://start.spring.io)
- 📖 [Baeldung — Spring Tutorials](https://www.baeldung.com/spring-tutorial)
- 🎓 [Formation Dyma Spring Boot](https://www.dyma.fr/formations/spring-boot)
- 🐙 [Spring Boot Samples GitHub](https://github.com/spring-projects/spring-boot/tree/main/spring-boot-samples)

---

> 💡 **Conseil :** Reproduis le projet Dyma Tennis de A à Z sans regarder la solution. C'est en pratiquant que tu maîtriseras vraiment Spring Boot !