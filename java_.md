# Java 8 - 101 : Guide Complet pour Débutants
  
---
  
## 1. Java 8 - Nouveautés du Langage
  
Java 8 (sorti en mars 2014) est l'une des versions les plus importantes de Java. Elle a introduit des fonctionnalités majeures qui ont modernisé le langage.
  
### Principales nouveautés :
- **Lambda expressions** : écrire du code fonctionnel concis
- **Streams API** : manipulation de collections de données en pipeline
- **Optional** : éviter les NullPointerException
- **Nouvelles API Date/Time** : `java.time` (LocalDate, LocalDateTime…)
- **Méthodes `default` et `static` dans les interfaces**
  
---
  
## 2. Structure du Langage Java
  
### Anatomie d'un programme Java
  
```java
// Un programme Java minimal
public class HelloWorld {          // Déclaration de la classe
    public static void main(String[] args) {  // Point d'entrée
        System.out.println("Bonjour Java 8 !"); // Instruction
    }
}
```
  
### Concepts fondamentaux
  
| Concept       | Description                                              |
|---------------|----------------------------------------------------------|
| Classe        | Modèle/plan pour créer des objets                        |
| Objet         | Instance d'une classe                                    |
| Méthode       | Fonction appartenant à une classe                        |
| Attribut      | Variable appartenant à une classe                        |
| Constructeur  | Méthode spéciale appelée lors de la création d'un objet  |
  
### Exemple : Classe Personne
  
```java
public class Personne {
    // Attributs
    private String nom;
    private int age;
  
    // Constructeur
    public Personne(String nom, int age) {
        this.nom = nom;
        this.age = age;
    }
  
    // Méthode
    public void sePresenter() {
        System.out.println("Je m'appelle " + nom + " et j'ai " + age + " ans.");
    }
  
    // Getters
    public String getNom() { return nom; }
    public int getAge() { return age; }
}
  
// Utilisation
Personne p = new Personne("Alice", 25);
p.sePresenter(); // → "Je m'appelle Alice et j'ai 25 ans."
```
  
---
  
## 3. Classe Abstraite / Interface / Gestion des Exceptions
  
### 3.1 Classe Abstraite
  
Une classe abstraite est un **modèle incomplet** qui ne peut pas être instancié directement. Elle sert de base à d'autres classes.
  
```java
// Classe abstraite : Animal
public abstract class Animal {
    protected String nom;
  
    public Animal(String nom) {
        this.nom = nom;
    }
  
    // Méthode abstraite (DOIT être implémentée par les sous-classes)
    public abstract void faireDuBruit();
  
    // Méthode concrète (héritée telle quelle)
    public void sePresenter() {
        System.out.println("Je suis " + nom);
    }
}
  
// Sous-classe concrète
public class Chien extends Animal {
    public Chien(String nom) {
        super(nom);
    }
  
    @Override
    public void faireDuBruit() {
        System.out.println(nom + " dit : Wouf !");
    }
}
  
// Utilisation
Animal chien = new Chien("Rex");
chien.sePresenter();      // → "Je suis Rex"
chien.faireDuBruit();     // → "Rex dit : Wouf !"
```
  
### 3.2 Interface
  
Une interface est un **contrat** : elle définit ce qu'un objet peut faire, sans dire comment.
  
```java
// Interface
public interface Volant {
    void voler();  // méthode abstraite (pas de corps)
  
    // Java 8 : méthode default (avec une implémentation par défaut)
    default void atterrir() {
        System.out.println("Atterrissage en douceur...");
    }
}
  
// Implémentation
public class Oiseau implements Volant {
    @Override
    public void voler() {
        System.out.println("L'oiseau bat des ailes et s'envole !");
    }
}
  
// Utilisation
Volant oiseau = new Oiseau();
oiseau.voler();      // → "L'oiseau bat des ailes et s'envole !"
oiseau.atterrir();   // → "Atterrissage en douceur..."
```
  
### Différence clé : Classe Abstraite vs Interface
  
| Critère               | Classe Abstraite          | Interface                          |
|-----------------------|---------------------------|------------------------------------|
| Héritage              | `extends` (1 seule)       | `implements` (plusieurs possibles) |
| Attributs             | Oui (avec état)           | Non (sauf constantes)              |
| Constructeur          | Oui                       | Non                                |
| Méthodes avec corps   | Oui                       | Oui depuis Java 8 (`default`)      |
| Utilisation           | Relation "est un"         | Relation "peut faire"              |
  
### 3.3 Gestion des Exceptions
  
Une exception est une **erreur survenant à l'exécution**. Java permet de les attraper et de les gérer.
  
```java
public class GestionExceptions {
    public static void main(String[] args) {
  
        // try : code qui peut échouer
        try {
            int[] tableau = {1, 2, 3};
            System.out.println(tableau[10]); // IndexOutOfBoundsException !
  
        } catch (ArrayIndexOutOfBoundsException e) {
            // catch : que faire si l'erreur se produit
            System.out.println("Erreur : index hors du tableau !");
            System.out.println("Détail : " + e.getMessage());
  
        } finally {
            // finally : s'exécute TOUJOURS (erreur ou pas)
            System.out.println("Bloc finally : toujours exécuté.");
        }
    }
}
```
  
#### Créer sa propre exception
  
```java
// Exception personnalisée
public class AgeInvalideException extends Exception {
    public AgeInvalideException(String message) {
        super(message);
    }
}
  
// Utilisation
public class Validation {
    public static void verifierAge(int age) throws AgeInvalideException {
        if (age < 0 || age > 150) {
            throw new AgeInvalideException("Age invalide : " + age);
        }
        System.out.println("Age valide : " + age);
    }
  
    public static void main(String[] args) {
        try {
            verifierAge(200); // va lever l'exception
        } catch (AgeInvalideException e) {
            System.out.println("Problème : " + e.getMessage());
        }
    }
}
```
  
---
  
## 4. La Programmation Fonctionnelle : Lambda Expressions
  
### 4.1 C'est quoi une Lambda ?
  
Avant Java 8, pour passer du comportement (une fonction) en paramètre, il fallait créer des classes anonymes verbeuses. Les lambdas simplifient cela.
  
**Syntaxe :**
```
(paramètres) -> { corps }
```
  
### 4.2 Exemples progressifs
  
```java
// Sans lambda (ancienne façon)
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Bonjour sans lambda !");
    }
};
  
// Avec lambda (Java 8)
Runnable r2 = () -> System.out.println("Bonjour avec lambda !");
  
r1.run(); // → "Bonjour sans lambda !"
r2.run(); // → "Bonjour avec lambda !"
```
  
### 4.3 Lambdas avec les Collections
  
```java
import java.util.*;
import java.util.stream.*;
  
List<String> prenoms = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");
  
// Afficher tous les prénoms (forEach + lambda)
prenoms.forEach(p -> System.out.println(p));
  
// Filtrer les prénoms de plus de 4 lettres
List<String> longs = prenoms.stream()
    .filter(p -> p.length() > 4)   // garder ceux > 4 lettres
    .collect(Collectors.toList());
System.out.println(longs); // [Alice, Charlie, David]
  
// Transformer en majuscules
List<String> majuscules = prenoms.stream()
    .map(p -> p.toUpperCase())      // transformer chaque élément
    .collect(Collectors.toList());
System.out.println(majuscules); // [ALICE, BOB, CHARLIE, DAVID, EVE]
  
// Compter les prénoms commençant par "A"
long compte = prenoms.stream()
    .filter(p -> p.startsWith("A"))
    .count();
System.out.println("Prénoms commençant par A : " + compte); // 1
```
  
### 4.4 Interfaces Fonctionnelles (`@FunctionalInterface`)
  
Java 8 fournit des interfaces prêtes à l'emploi pour les lambdas :
  
| Interface         | Entrée      | Sortie    | Usage                          |
|-------------------|-------------|-----------|--------------------------------|
| `Predicate<T>`    | T           | boolean   | Tester une condition           |
| `Function<T,R>`   | T           | R         | Transformer un élément         |
| `Consumer<T>`     | T           | void      | Consommer (ex: afficher)       |
| `Supplier<T>`     | rien        | T         | Produire une valeur            |
  
```java
import java.util.function.*;
  
// Predicate : est-ce un nombre pair ?
Predicate<Integer> estPair = n -> n % 2 == 0;
System.out.println(estPair.test(4));  // true
System.out.println(estPair.test(7));  // false
  
// Function : doubler un nombre
Function<Integer, Integer> doubler = n -> n * 2;
System.out.println(doubler.apply(5)); // 10
  
// Consumer : afficher un message
Consumer<String> afficher = msg -> System.out.println(">> " + msg);
afficher.accept("Hello !"); // >> Hello !
  
// Supplier : fournir une valeur
Supplier<String> fournir = () -> "Valeur par défaut";
System.out.println(fournir.get()); // Valeur par défaut
```
  
---
  
## 5. Tour d'Horizon d'un EDI (Eclipse / IntelliJ / NetBeans)
  
### Qu'est-ce qu'un EDI ?
  
Un **EDI** (Environnement de Développement Intégré) ou **IDE** en anglais, est un logiciel qui regroupe tout ce dont vous avez besoin pour développer :
- Éditeur de code avec coloration syntaxique
- Compilateur intégré
- Débogueur
- Gestionnaire de projets
  
### Comparaison des 3 EDI principaux
  
| Fonctionnalité    | Eclipse           | IntelliJ IDEA         | NetBeans            |
|-------------------|-------------------|-----------------------|---------------------|
| Éditeur           | Eclipse Foundation| JetBrains             | Apache              |
| Licence           | Gratuit (open source)| Gratuit (Community) / Payant (Ultimate) | Gratuit (open source) |
| Performance       | Moyen             | Excellent             | Bon                 |
| Facilité          | Intermédiaire     | Très facile           | Facile              |
| Popularité        | Très populaire    | N°1 en entreprise     | Moins utilisé       |
| Maven/Gradle      | Via plugin        | Intégré nativement    | Intégré nativement  |
  
### Premiers pas avec IntelliJ IDEA (recommandé débutants)
  
1. Télécharger IntelliJ IDEA Community (gratuit) : https://www.jetbrains.com/idea/
2. Installer le JDK 8+ : https://adoptium.net/
3. Créer un nouveau projet Java
4. Créer une classe `Main.java`
5. Écrire votre premier `Hello World` et appuyer sur ▶️
  
### Raccourcis clavier essentiels (IntelliJ)
  
| Action                     | Windows/Linux        | Mac                  |
|----------------------------|----------------------|----------------------|
| Exécuter le programme       | `Shift + F10`        | `Ctrl + R`           |
| Débogage                   | `Shift + F9`         | `Ctrl + D`           |
| Auto-complétion            | `Ctrl + Espace`      | `Ctrl + Espace`      |
| Rechercher partout         | `Double Shift`       | `Double Shift`       |
| Formater le code           | `Ctrl + Alt + L`     | `Cmd + Alt + L`      |
| Commentaire rapide         | `Ctrl + /`           | `Cmd + /`            |
  
---
  
## Récapitulatif
  
```
Java 8 101
├── Structure du langage
│   ├── Classes, Objets, Méthodes, Attributs
│   └── Syntaxe de base
├── Nouveautés Java 8
│   ├── Lambda expressions
│   ├── Streams API
│   ├── Optional
│   └── Nouvelle API Date/Time
├── POO Avancée
│   ├── Classes abstraites (extends)
│   ├── Interfaces (implements)
│   └── Gestion des exceptions (try/catch/finally)
├── Programmation fonctionnelle
│   ├── Lambdas : (params) -> corps
│   ├── Streams : filter, map, collect...
│   └── Interfaces fonctionnelles : Predicate, Function...
└── EDI
    ├── Eclipse (open source, très répandu)
    ├── IntelliJ IDEA (recommandé, le plus populaire)
    └── NetBeans (simple, bon pour débutants)
```
  
---
  
*Document préparé pour une présentation Java 8 - 101*
  