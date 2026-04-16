# Java 8 — 101 : Guide Complet et Détaillé

> **Objectif :** Comprendre Java 8 en profondeur, avec toutes les explications, les raisons derrière chaque concept, et des cas pratiques complets que vous pouvez exécuter et expliquer.

---

## TABLE DES MATIÈRES

1. [Java 8 — Nouveautés du Langage](#1-java-8--nouveautés-du-langage)
2. [Structure du Langage Java](#2-structure-du-langage-java)
3. [Classe Abstraite / Interface / Gestion des Exceptions](#3-classe-abstraite--interface--gestion-des-exceptions)
4. [La Programmation Fonctionnelle — Lambda Expressions](#4-la-programmation-fonctionnelle--lambda-expressions)
5. [Tour d'Horizon d'un EDI (Eclipse / IntelliJ / NetBeans)](#5-tour-dhorizon-dun-edi)

---

# 1. Java 8 — Nouveautés du Langage

## 1.1 Qu'est-ce que Java 8 et pourquoi est-il important ?

Java 8 est sorti en **mars 2014** et représente l'une des mises à jour les plus significatives depuis la création de Java en 1995.

Avant Java 8, Java était un langage **purement orienté objet** : tout devait être encapsulé dans des classes et des objets. Cela rendait certaines opérations simples (comme trier une liste ou filtrer des données) inutilement verbeux.

Java 8 a apporté des concepts de **programmation fonctionnelle** dans Java, permettant d'écrire du code plus court, plus lisible et plus expressif.

## 1.2 Les Grandes Nouveautés de Java 8

### A) Lambda Expressions

**Qu'est-ce que c'est ?**
Une lambda est une **fonction anonyme** (sans nom) qu'on peut passer comme paramètre à une méthode. Cela permet d'écrire du comportement en ligne, sans créer toute une classe.

**Syntaxe :**
```
(paramètres) -> expression
(paramètres) -> { bloc de code }
```

**Avant Java 8 :**
```java
// Pour trier une liste de prénoms, on devait écrire :
List<String> noms = Arrays.asList("Charlie", "Alice", "Bob");
Collections.sort(noms, new Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return a.compareTo(b);
    }
});
// Beaucoup de code pour une simple comparaison !
```

**Avec Java 8 :**
```java
List<String> noms = Arrays.asList("Charlie", "Alice", "Bob");
Collections.sort(noms, (a, b) -> a.compareTo(b));
// Une seule ligne ! Même résultat.
System.out.println(noms); // → [Alice, Bob, Charlie]
```

---

### B) Streams API

**Qu'est-ce que c'est ?**
Un Stream (flux) permet de **traiter une collection de données** (liste, tableau...) en enchaînant des opérations comme un pipeline d'usine : chaque étape reçoit les données, les transforme, et les passe à l'étape suivante.

```
Source → filter() → map() → collect()
```

**Exemple concret :**
```java
import java.util.*;
import java.util.stream.*;

List<Integer> notes = Arrays.asList(8, 15, 12, 18, 6, 14, 9);

// Trouver la moyenne des notes supérieures à 10
double moyenne = notes.stream()
    .filter(n -> n > 10)          // Garder seulement > 10 : [15,12,18,14]
    .mapToInt(Integer::intValue)   // Convertir en int
    .average()                     // Calculer la moyenne
    .orElse(0.0);                  // Si vide, retourner 0

System.out.println("Moyenne des notes > 10 : " + moyenne); // → 14.75
```

---

### C) Optional

**Qu'est-ce que c'est ?**
`Optional<T>` est un **conteneur** qui peut soit contenir une valeur, soit être vide. Il force le développeur à gérer le cas "pas de valeur" et évite les `NullPointerException`.

**Problème sans Optional :**
```java
String nom = getNomDepuisBaseDeDonnees(); // peut retourner null !
System.out.println(nom.length()); // CRASH si nom est null → NullPointerException
```

**Solution avec Optional :**
```java
Optional<String> nomOpt = Optional.ofNullable(getNomDepuisBaseDeDonnees());

// Vérifier avant d'utiliser
if (nomOpt.isPresent()) {
    System.out.println("Longueur : " + nomOpt.get().length());
} else {
    System.out.println("Aucun nom trouvé");
}

// Encore plus court avec orElse :
String nom = nomOpt.orElse("Inconnu");
System.out.println("Nom : " + nom); // jamais de NullPointerException
```

---

### D) Nouvelle API Date/Time (`java.time`)

**Pourquoi une nouvelle API ?**
L'ancienne API (`java.util.Date`, `Calendar`) était complexe, incohérente et difficile à utiliser. Java 8 a tout refait proprement.

```java
import java.time.*;
import java.time.format.*;

// Date du jour
LocalDate aujourd_hui = LocalDate.now();
System.out.println("Aujourd'hui : " + aujourd_hui); // → 2024-01-15

// Date spécifique
LocalDate naissance = LocalDate.of(2000, 3, 25);
System.out.println("Date naissance : " + naissance); // → 2000-03-25

// Calculer l'âge
int age = Period.between(naissance, aujourd_hui).getYears();
System.out.println("Age : " + age + " ans");

// Date + heure
LocalDateTime maintenantPrecis = LocalDateTime.now();
System.out.println("Maintenant : " + maintenantPrecis);

// Formater une date
DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
System.out.println("Formaté : " + aujourd_hui.format(format)); // → 15/01/2024

// Manipuler les dates facilement
LocalDate demain = aujourd_hui.plusDays(1);
LocalDate anneeProchaine = aujourd_hui.plusYears(1);
LocalDate lundiDernier = aujourd_hui.minusDays(aujourd_hui.getDayOfWeek().getValue() - 1);
System.out.println("Demain : " + demain);
System.out.println("Année prochaine : " + anneeProchaine);
```

---

### E) Méthodes `default` et `static` dans les Interfaces

**Nouveauté importante :** Avant Java 8, une interface ne pouvait contenir QUE des méthodes abstraites (sans corps). Java 8 permet maintenant d'ajouter des méthodes avec implémentation.

```java
public interface Calculatrice {
    // Méthode abstraite classique
    double calculer(double a, double b);

    // Méthode default : a un corps, héritée par défaut
    default void afficherResultat(double a, double b) {
        System.out.println("Résultat : " + calculer(a, b));
    }

    // Méthode static : appelée directement sur l'interface
    static String getVersion() {
        return "Calculatrice v2.0 (Java 8)";
    }
}

// Implémentation
public class Addition implements Calculatrice {
    @Override
    public double calculer(double a, double b) {
        return a + b;
    }
    // afficherResultat() est hérité automatiquement !
}

// Utilisation
Calculatrice calc = new Addition();
calc.afficherResultat(5, 3);              // → "Résultat : 8.0"
System.out.println(Calculatrice.getVersion()); // → "Calculatrice v2.0 (Java 8)"
```

---

# 2. Structure du Langage Java

## 2.1 Comprendre le Paradigme Orienté Objet

Java est un langage **orienté objet (POO)**. Cela signifie que tout dans Java est modélisé avec des **objets** qui représentent des entités du monde réel.

**Analogie :** Pensez à un formulaire de banque.
- Le **formulaire vierge** = la **Classe** (le modèle)
- Un **formulaire rempli** au nom de "Alice" = un **Objet** (une instance du modèle)

Vous pouvez créer 1000 formulaires remplis (objets) à partir d'un seul formulaire vierge (classe).

## 2.2 Anatomie Complète d'une Classe Java

```java
// Déclaration de la classe
public class CompteBancaire {

    // ===== ATTRIBUTS (les données de l'objet) =====
    private String titulaire;    // private = accessible seulement dans cette classe
    private double solde;        // le solde du compte
    private String numeroCompte;

    // Constante : valeur qui ne change jamais (static final = appartient à la classe)
    public static final double TAUX_INTERET = 0.03; // 3%

    // ===== CONSTRUCTEUR (initialise un nouvel objet) =====
    // Appelé automatiquement quand on écrit : new CompteBancaire(...)
    public CompteBancaire(String titulaire, double soldeInitial) {
        this.titulaire = titulaire;        // this.xxx = l'attribut de l'objet
        this.solde = soldeInitial;
        this.numeroCompte = genererNumero(); // appel d'une méthode interne
    }

    // ===== MÉTHODES (les comportements de l'objet) =====

    // Déposer de l'argent
    public void deposer(double montant) {
        if (montant <= 0) {
            System.out.println("Erreur : le montant doit être positif !");
            return; // sortir de la méthode
        }
        this.solde += montant;
        System.out.println("Dépôt de " + montant + "€. Nouveau solde : " + this.solde + "€");
    }

    // Retirer de l'argent
    public boolean retirer(double montant) {
        if (montant > this.solde) {
            System.out.println("Fonds insuffisants ! Solde actuel : " + this.solde + "€");
            return false; // retourne false = échec
        }
        this.solde -= montant;
        System.out.println("Retrait de " + montant + "€. Nouveau solde : " + this.solde + "€");
        return true; // retourne true = succès
    }

    // Virement vers un autre compte
    public void virementVers(CompteBancaire destination, double montant) {
        if (this.retirer(montant)) { // retirer du compte source
            destination.deposer(montant); // déposer sur le compte destination
            System.out.println("Virement de " + montant + "€ vers " + destination.titulaire);
        }
    }

    // Afficher le solde
    public void afficherSolde() {
        System.out.println("Compte de " + titulaire + " | Solde : " + solde + "€");
    }

    // Méthode privée (utilitaire interne)
    private String genererNumero() {
        return "FR" + (int)(Math.random() * 900000 + 100000);
    }

    // ===== GETTERS ET SETTERS (accès contrôlé aux attributs privés) =====
    public String getTitulaire() { return titulaire; }
    public double getSolde()     { return solde; }
    public String getNumeroCompte() { return numeroCompte; }

    // Pas de setter pour solde → on ne peut changer le solde qu'en déposant/retirant
}
```

**Utilisation dans le programme principal :**
```java
public class Main {
    public static void main(String[] args) {
        // Créer deux comptes
        CompteBancaire compteAlice = new CompteBancaire("Alice", 1000.0);
        CompteBancaire compteBob   = new CompteBancaire("Bob", 500.0);

        compteAlice.afficherSolde(); // → "Compte de Alice | Solde : 1000.0€"
        compteBob.afficherSolde();   // → "Compte de Bob | Solde : 500.0€"

        compteAlice.deposer(200);    // → "Dépôt de 200€. Nouveau solde : 1200.0€"
        compteAlice.retirer(50);     // → "Retrait de 50€. Nouveau solde : 1150.0€"
        compteAlice.retirer(9999);   // → "Fonds insuffisants ! Solde actuel : 1150.0€"

        // Virement d'Alice vers Bob
        compteAlice.virementVers(compteBob, 300);
        // → "Retrait de 300€. Nouveau solde : 850.0€"
        // → "Dépôt de 300€. Nouveau solde : 800.0€"
        // → "Virement de 300€ vers Bob"
    }
}
```

## 2.3 Les Types de Données en Java

### Types primitifs (valeurs simples)

| Type      | Taille  | Valeurs possibles               | Exemple             |
|-----------|---------|----------------------------------|---------------------|
| `int`     | 32 bits | -2,147,483,648 à 2,147,483,647  | `int age = 25;`     |
| `long`    | 64 bits | très grands entiers              | `long pop = 8000000000L;` |
| `double`  | 64 bits | décimaux (haute précision)       | `double pi = 3.14;` |
| `float`   | 32 bits | décimaux (moins précis)          | `float x = 2.5f;`  |
| `boolean` | 1 bit   | true / false                     | `boolean ok = true;`|
| `char`    | 16 bits | un caractère                     | `char c = 'A';`     |
| `byte`    | 8 bits  | -128 à 127                       | `byte b = 100;`     |
| `short`   | 16 bits | -32,768 à 32,767                 | `short s = 1000;`   |

### Types référence (objets)

```java
String nom = "Alice";                      // Chaîne de caractères
int[] notes = {12, 15, 8, 18};            // Tableau d'entiers
List<String> liste = new ArrayList<>();    // Liste dynamique
Map<String, Integer> map = new HashMap<>(); // Dictionnaire clé→valeur
```

## 2.4 Les Structures de Contrôle

### Conditions

```java
int score = 75;

// if / else if / else
if (score >= 90) {
    System.out.println("Mention Très Bien");
} else if (score >= 75) {
    System.out.println("Mention Bien");       // → affiché ici
} else if (score >= 60) {
    System.out.println("Mention Passable");
} else {
    System.out.println("Insuffisant");
}

// Switch (pour des valeurs précises)
String jour = "LUNDI";
switch (jour) {
    case "LUNDI":
    case "MARDI":
    case "MERCREDI":
    case "JEUDI":
    case "VENDREDI":
        System.out.println("Jour de semaine");
        break;
    case "SAMEDI":
    case "DIMANCHE":
        System.out.println("Week-end !");
        break;
    default:
        System.out.println("Jour inconnu");
}
```

### Boucles

```java
// for classique : quand on connaît le nombre d'itérations
for (int i = 0; i < 5; i++) {
    System.out.println("Tour " + i); // 0, 1, 2, 3, 4
}

// for-each : pour parcourir une collection (plus lisible)
String[] fruits = {"pomme", "banane", "orange"};
for (String fruit : fruits) {
    System.out.println("Fruit : " + fruit);
}

// while : tant que la condition est vraie
int compteur = 10;
while (compteur > 0) {
    System.out.print(compteur + " ");
    compteur -= 3;
}
// → 10 7 4 1

// do-while : s'exécute AU MOINS une fois avant de vérifier
int tentative = 0;
do {
    System.out.println("Tentative " + tentative);
    tentative++;
} while (tentative < 3);
// → Tentative 0, Tentative 1, Tentative 2
```

## 2.5 Les Modificateurs d'Accès

Ces mots-clés contrôlent **qui peut accéder** à une classe, méthode ou attribut :

| Modificateur  | Même classe | Même package | Sous-classe | Partout |
|---------------|:-----------:|:------------:|:-----------:|:-------:|
| `private`     | ✅           | ❌            | ❌           | ❌       |
| (aucun)       | ✅           | ✅            | ❌           | ❌       |
| `protected`   | ✅           | ✅            | ✅           | ❌       |
| `public`      | ✅           | ✅            | ✅           | ✅       |

**Règle d'or :** Mettre les attributs en `private` et les méthodes utiles en `public`. C'est le principe **d'encapsulation**.

---

# 3. Classe Abstraite / Interface / Gestion des Exceptions

## 3.1 L'Héritage en Java

Avant de parler de classes abstraites, il faut comprendre l'héritage.

**L'héritage** permet à une classe (enfant) de **réutiliser** le code d'une autre classe (parent) et d'y **ajouter** ses propres comportements.

```java
// Classe parent (superclasse)
public class Vehicule {
    protected String marque;
    protected int annee;
    protected double vitesseMax;

    public Vehicule(String marque, int annee, double vitesseMax) {
        this.marque = marque;
        this.annee = annee;
        this.vitesseMax = vitesseMax;
    }

    public void demarrer() {
        System.out.println(marque + " démarre...");
    }

    public void afficherInfos() {
        System.out.println("Marque: " + marque + " | Année: " + annee
                           + " | Vitesse max: " + vitesseMax + " km/h");
    }
}

// Classe enfant (sous-classe) : hérite de Vehicule
public class Voiture extends Vehicule {
    private int nombrePortes;

    public Voiture(String marque, int annee, double vitesseMax, int portes) {
        super(marque, annee, vitesseMax); // appel du constructeur parent
        this.nombrePortes = portes;
    }

    // Méthode propre à Voiture
    public void klaxonner() {
        System.out.println(marque + " fait : TOOT TOOT !");
    }

    // Redéfinition (override) d'une méthode du parent
    @Override
    public void afficherInfos() {
        super.afficherInfos(); // appel de la version parent
        System.out.println("Nombre de portes : " + nombrePortes);
    }
}

// Test
Voiture ma_voiture = new Voiture("Toyota", 2022, 180.0, 5);
ma_voiture.demarrer();      // hérité de Vehicule → "Toyota démarre..."
ma_voiture.klaxonner();     // propre à Voiture  → "Toyota fait : TOOT TOOT !"
ma_voiture.afficherInfos(); // redéfini : affiche les infos de base + nb portes
```

## 3.2 Classe Abstraite — Explication Complète

### Qu'est-ce que c'est et pourquoi s'en servir ?

Une classe abstraite est une classe **incomplète** qui sert de modèle de base. On l'utilise quand :
1. On veut définir un comportement **commun** à plusieurs sous-classes
2. Mais certaines méthodes **ne peuvent pas être implémentées** sans connaître la sous-classe spécifique

**Analogie :** Un plan d'architecte pour "un bâtiment" est abstrait. Vous ne pouvez pas construire "un bâtiment" directement — vous construisez une maison, une école ou une usine. Mais toutes partagent des caractéristiques communes (surface, hauteur, entrée...).

### Règles des Classes Abstraites

1. Mot-clé `abstract` devant `class`
2. Peut avoir des méthodes abstraites (sans corps) → les sous-classes DOIVENT les implémenter
3. Peut avoir des méthodes concrètes (avec corps) → les sous-classes les héritent
4. **Ne peut pas être instanciée directement** (pas de `new ClasseAbstraite()`)
5. Peut avoir des attributs, un constructeur, etc.

### Cas Pratique Complet : Système de Formes Géométriques

```java
import java.util.*;

// Classe abstraite : on ne peut pas calculer l'aire "d'une forme" en général
// mais on sait que TOUTES les formes ont une aire et un périmètre
public abstract class FormeGeometrique {

    // Attribut commun à toutes les formes
    protected String couleur;

    // Constructeur (les sous-classes l'appellent avec super())
    public FormeGeometrique(String couleur) {
        this.couleur = couleur;
    }

    // Méthodes ABSTRAITES : chaque forme doit définir son propre calcul
    public abstract double calculerAire();
    public abstract double calculerPerimetre();
    public abstract String getNom();

    // Méthode CONCRÈTE : commune à toutes les formes (même logique)
    public void afficherInfos() {
        System.out.println("=== " + getNom() + " (" + couleur + ") ===");
        System.out.printf("  Aire      : %.2f cm²%n", calculerAire());
        System.out.printf("  Périmètre : %.2f cm%n", calculerPerimetre());
    }

    // Méthode utilitaire (static) : comparer deux formes par aire
    public static FormeGeometrique plusGrande(FormeGeometrique f1, FormeGeometrique f2) {
        return f1.calculerAire() >= f2.calculerAire() ? f1 : f2;
    }
}

// Sous-classe Cercle
public class Cercle extends FormeGeometrique {
    private double rayon;

    public Cercle(String couleur, double rayon) {
        super(couleur); // appel obligatoire du constructeur parent
        this.rayon = rayon;
    }

    @Override
    public double calculerAire() {
        return Math.PI * rayon * rayon; // π × r²
    }

    @Override
    public double calculerPerimetre() {
        return 2 * Math.PI * rayon; // 2πr
    }

    @Override
    public String getNom() { return "Cercle (r=" + rayon + ")"; }
}

// Sous-classe Rectangle
public class Rectangle extends FormeGeometrique {
    private double largeur, hauteur;

    public Rectangle(String couleur, double largeur, double hauteur) {
        super(couleur);
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    @Override
    public double calculerAire() { return largeur * hauteur; }

    @Override
    public double calculerPerimetre() { return 2 * (largeur + hauteur); }

    @Override
    public String getNom() { return "Rectangle (" + largeur + "x" + hauteur + ")"; }
}

// Sous-classe Triangle
public class Triangle extends FormeGeometrique {
    private double a, b, c; // les 3 côtés

    public Triangle(String couleur, double a, double b, double c) {
        super(couleur);
        this.a = a; this.b = b; this.c = c;
    }

    @Override
    public double calculerAire() {
        // Formule de Héron
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s-a) * (s-b) * (s-c));
    }

    @Override
    public double calculerPerimetre() { return a + b + c; }

    @Override
    public String getNom() { return "Triangle (" + a + ", " + b + ", " + c + ")"; }
}

// PROGRAMME PRINCIPAL
public class Main {
    public static void main(String[] args) {
        // Créer des formes
        Cercle    cercle = new Cercle("rouge", 5);
        Rectangle rect   = new Rectangle("bleu", 4, 6);
        Triangle  tri    = new Triangle("vert", 3, 4, 5);

        // Afficher les infos de chaque forme
        cercle.afficherInfos();
        rect.afficherInfos();
        tri.afficherInfos();

        // Stocker dans une liste de FormeGeometrique (polymorphisme !)
        List<FormeGeometrique> formes = new ArrayList<>();
        formes.add(cercle);
        formes.add(rect);
        formes.add(tri);

        // Trouver la forme avec la plus grande aire (grâce à l'abstraction)
        System.out.println("\n--- Formes triées par aire ---");
        formes.sort((f1, f2) -> Double.compare(f2.calculerAire(), f1.calculerAire()));
        for (FormeGeometrique f : formes) {
            System.out.printf("%-30s → %.2f cm²%n", f.getNom(), f.calculerAire());
        }
    }
}
```

**Sortie du programme :**
```
=== Cercle (r=5.0) (rouge) ===
  Aire      : 78.54 cm²
  Périmètre : 31.42 cm
=== Rectangle (4.0x6.0) (bleu) ===
  Aire      : 24.00 cm²
  Périmètre : 20.00 cm
=== Triangle (3.0, 4.0, 5.0) (vert) ===
  Aire      : 6.00 cm²
  Périmètre : 12.00 cm

--- Formes triées par aire ---
Cercle (r=5.0)                 → 78.54 cm²
Rectangle (4.0x6.0)            → 24.00 cm²
Triangle (3.0, 4.0, 5.0)       → 6.00 cm²
```

## 3.3 Interface — Explication Complète

### Qu'est-ce qu'une Interface ?

Une interface est un **contrat pur** : elle dit "voilà ce que tu sais faire" sans dire comment.

**Différence clé avec la classe abstraite :**
- Classe abstraite = "tu ES un type de chose" → relation `est-un`
- Interface = "tu PEUX faire quelque chose" → relation `peut-faire`

**Exemple :**
- Un `Dauphin` **EST un** Mammifère (classe abstraite)
- Un `Dauphin` **PEUT** nager, donc il implémente l'interface `Nageur`
- Un `Humain` **PEUT** aussi nager → il implémente aussi `Nageur`
- Un `Humain` et un `Dauphin` n'ont rien en commun comme "type d'être", mais tous les deux peuvent nager !

### Avantage majeur : Implémenter plusieurs interfaces

En Java, une classe ne peut hériter que d'**une seule** classe (abstraite ou non). Mais elle peut implémenter **autant d'interfaces qu'elle veut**.

### Cas Pratique Complet : Système de Paiement

```java
// Interface 1 : tout ce qui peut être payé
public interface Payable {
    double getMontant();  // méthode abstraite
    void confirmerPaiement();

    // Méthode default Java 8 : comportement par défaut
    default String getStatutPaiement() {
        return "En attente de paiement de " + getMontant() + "€";
    }
}

// Interface 2 : tout ce qui peut être remboursé
public interface Remboursable {
    boolean rembourser(double montant);

    default String getPolitiqueRemboursement() {
        return "Remboursement sous 30 jours";
    }
}

// Interface 3 : tout ce qui peut être facturé
public interface Facturable {
    String genererFacture();
    static String getFormatFacture() { return "PDF"; } // méthode static Java 8
}

// Classe CommandeEnLigne : implémente les 3 interfaces
public class CommandeEnLigne implements Payable, Remboursable, Facturable {
    private String client;
    private double montant;
    private List<String> articles;
    private boolean estPayee;

    public CommandeEnLigne(String client, double montant, List<String> articles) {
        this.client = client;
        this.montant = montant;
        this.articles = articles;
        this.estPayee = false;
    }

    // --- Implémentation de Payable ---
    @Override
    public double getMontant() { return montant; }

    @Override
    public void confirmerPaiement() {
        this.estPayee = true;
        System.out.println("✓ Paiement de " + montant + "€ confirmé pour " + client);
    }

    // --- Implémentation de Remboursable ---
    @Override
    public boolean rembourser(double montantRemb) {
        if (!estPayee) {
            System.out.println("✗ Impossible : commande non payée");
            return false;
        }
        if (montantRemb > montant) {
            System.out.println("✗ Montant de remboursement trop élevé");
            return false;
        }
        System.out.println("✓ Remboursement de " + montantRemb + "€ effectué");
        return true;
    }

    // --- Implémentation de Facturable ---
    @Override
    public String genererFacture() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== FACTURE ==========\n");
        sb.append("Client  : ").append(client).append("\n");
        sb.append("Articles:\n");
        for (String article : articles) {
            sb.append("  - ").append(article).append("\n");
        }
        sb.append("Total   : ").append(montant).append("€\n");
        sb.append("Payée   : ").append(estPayee ? "Oui" : "Non").append("\n");
        sb.append("=============================");
        return sb.toString();
    }
}

// PROGRAMME PRINCIPAL
public class Main {
    public static void main(String[] args) {
        CommandeEnLigne commande = new CommandeEnLigne(
            "Alice",
            89.99,
            Arrays.asList("Livre Java 8", "Clé USB 32Go", "Stylo")
        );

        // Utilisation via l'interface Payable
        System.out.println(commande.getStatutPaiement()); // méthode default
        commande.confirmerPaiement();

        // Utilisation via l'interface Remboursable
        commande.rembourser(200); // trop élevé → refusé
        commande.rembourser(20);  // ok

        // Utilisation via Facturable
        System.out.println(commande.genererFacture());
        System.out.println("Format : " + Facturable.getFormatFacture()); // static
    }
}
```

### Résumé Comparatif : Classe Abstraite vs Interface

| Critère                     | Classe Abstraite                  | Interface                              |
|-----------------------------|-----------------------------------|----------------------------------------|
| Héritage                    | `extends` (une seule)             | `implements` (plusieurs possibles)     |
| Attributs                   | Oui (avec état)                   | Uniquement des constantes (`static final`) |
| Constructeur                | Oui                               | Non                                    |
| Méthode avec corps          | Oui                               | Oui depuis Java 8 (`default`, `static`)|
| Méthode abstraite           | Oui (optionnel)                   | Oui (toutes par défaut sauf default)   |
| Usage principal             | Partager du code commun           | Définir un contrat de comportement     |
| Relation                    | "est-un" (`Chien est un Animal`)  | "peut-faire" (`Chien peut se Déplacer`)|
| Quand utiliser ?            | Classes très liées, partage code  | Comportements transversaux             |

## 3.4 Gestion des Exceptions — Explication Complète

### Qu'est-ce qu'une Exception ?

Une exception est un **événement anormal** qui se produit pendant l'exécution d'un programme et qui interrompt son déroulement normal.

**Types d'exceptions :**

| Catégorie | Exemples | Obligatoire à gérer ? |
|-----------|----------|-----------------------|
| `RuntimeException` | `NullPointerException`, `ArrayIndexOutOfBoundsException`, `ClassCastException` | Non (unchecked) |
| `Exception` (checked) | `IOException`, `SQLException`, exception personnalisée | **OUI** |
| `Error` | `OutOfMemoryError`, `StackOverflowError` | Non (ne pas essayer) |

### La Structure try-catch-finally en Détail

```java
try {
    // Code qui POURRAIT lever une exception
    // Si une exception est levée ici, on saute directement au catch
    // Le reste du try n'est pas exécuté

} catch (TypeException1 e) {
    // Gérer le premier type d'exception
    // e.getMessage()  → description de l'erreur
    // e.printStackTrace() → affiche toute la trace de l'erreur

} catch (TypeException2 e) {
    // Gérer un second type d'exception

} catch (Exception e) {
    // Attrape TOUTES les autres exceptions (filet de sécurité)

} finally {
    // Exécuté TOUJOURS, que l'exception ait eu lieu ou non
    // Typiquement : fermer des fichiers, connexions BD, etc.
}
```

### Cas Pratique Complet : Système de Gestion d'Étudiants

```java
// Exceptions personnalisées
public class EtudiantNotFoundException extends Exception {
    private int id;
    public EtudiantNotFoundException(int id) {
        super("Etudiant avec l'id " + id + " introuvable.");
        this.id = id;
    }
    public int getId() { return id; }
}

public class NoteInvalideException extends Exception {
    public NoteInvalideException(double note) {
        super("Note invalide : " + note + ". Doit être entre 0 et 20.");
    }
}

// Classe Etudiant
public class Etudiant {
    private int id;
    private String nom;
    private List<Double> notes;

    public Etudiant(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.notes = new ArrayList<>();
    }

    // Ajouter une note avec validation
    public void ajouterNote(double note) throws NoteInvalideException {
        if (note < 0 || note > 20) {
            throw new NoteInvalideException(note); // lever l'exception
        }
        notes.add(note);
        System.out.println("Note " + note + " ajoutée pour " + nom);
    }

    // Calculer la moyenne (peut échouer si aucune note)
    public double getMoyenne() {
        if (notes.isEmpty()) {
            throw new IllegalStateException(nom + " n'a aucune note !");
        }
        return notes.stream().mapToDouble(Double::doubleValue).average().orElse(0);
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
}

// Gestionnaire d'étudiants
public class GestionnaireEtudiants {
    private Map<Integer, Etudiant> etudiants = new HashMap<>();
    private int prochainId = 1;

    public Etudiant ajouterEtudiant(String nom) {
        Etudiant e = new Etudiant(prochainId++, nom);
        etudiants.put(e.getId(), e);
        return e;
    }

    // throws = déclare que cette méthode peut lever une exception checked
    public Etudiant trouverEtudiant(int id) throws EtudiantNotFoundException {
        Etudiant e = etudiants.get(id);
        if (e == null) {
            throw new EtudiantNotFoundException(id);
        }
        return e;
    }

    public void afficherRanking() {
        System.out.println("\n===== CLASSEMENT DES ÉTUDIANTS =====");
        etudiants.values().stream()
            .filter(e -> {
                try { e.getMoyenne(); return true; }
                catch (IllegalStateException ex) { return false; }
            })
            .sorted((e1, e2) -> Double.compare(e2.getMoyenne(), e1.getMoyenne()))
            .forEach(e -> System.out.printf("%-15s → %.2f/20%n", e.getNom(), e.getMoyenne()));
    }
}

// PROGRAMME PRINCIPAL
public class Main {
    public static void main(String[] args) {
        GestionnaireEtudiants gestion = new GestionnaireEtudiants();

        // Ajouter des étudiants
        Etudiant alice = gestion.ajouterEtudiant("Alice");
        Etudiant bob   = gestion.ajouterEtudiant("Bob");
        Etudiant carol = gestion.ajouterEtudiant("Carol");

        // Ajouter des notes avec gestion d'erreur
        try {
            alice.ajouterNote(15.5);
            alice.ajouterNote(12.0);
            alice.ajouterNote(18.5);

            bob.ajouterNote(9.0);
            bob.ajouterNote(25.0); // ← ERREUR : note > 20

        } catch (NoteInvalideException e) {
            System.out.println("ERREUR : " + e.getMessage());
            // Le programme continue malgré l'erreur
        }

        // Ajouter des notes à carol
        try {
            carol.ajouterNote(14.0);
            carol.ajouterNote(16.5);
        } catch (NoteInvalideException e) {
            System.out.println("ERREUR : " + e.getMessage());
        }

        // Chercher un étudiant inexistant
        try {
            Etudiant inconnu = gestion.trouverEtudiant(999);
        } catch (EtudiantNotFoundException e) {
            System.out.println("ERREUR : " + e.getMessage());
            System.out.println("(ID recherché : " + e.getId() + ")");
        } finally {
            System.out.println("→ Recherche terminée.");
        }

        // Afficher le classement
        gestion.afficherRanking();
    }
}
```

**Sortie :**
```
Note 15.5 ajoutée pour Alice
Note 12.0 ajoutée pour Alice
Note 18.5 ajoutée pour Alice
Note 9.0 ajoutée pour Bob
ERREUR : Note invalide : 25.0. Doit être entre 0 et 20.
Note 14.0 ajoutée pour Carol
Note 16.5 ajoutée pour Carol
ERREUR : Etudiant avec l'id 999 introuvable.
(ID recherché : 999)
→ Recherche terminée.

===== CLASSEMENT DES ÉTUDIANTS =====
Alice           → 15.33/20
Carol           → 15.25/20
Bob             → 9.00/20
```

---

# 4. La Programmation Fonctionnelle — Lambda Expressions

## 4.1 Comprendre le Concept de Programmation Fonctionnelle

### Deux styles de programmation

**Style impératif (traditionnel) :** On dit à l'ordinateur **comment faire** étape par étape.

```java
// Impératif : filtrer les nombres pairs et les doubler
List<Integer> nombres = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
List<Integer> resultat = new ArrayList<>();

for (int n : nombres) {           // étape 1 : parcourir
    if (n % 2 == 0) {             // étape 2 : filtrer les pairs
        resultat.add(n * 2);      // étape 3 : doubler et ajouter
    }
}
System.out.println(resultat); // [4, 8, 12, 16, 20]
```

**Style fonctionnel (Java 8) :** On dit à l'ordinateur **quoi faire**, pas comment.

```java
// Fonctionnel : même résultat, plus lisible
List<Integer> resultat = nombres.stream()
    .filter(n -> n % 2 == 0)  // quoi : garder les pairs
    .map(n -> n * 2)           // quoi : doubler
    .collect(Collectors.toList());
System.out.println(resultat); // [4, 8, 12, 16, 20]
```

## 4.2 Les Lambda Expressions en Profondeur

### Syntaxe Complète

```java
// Forme 1 : sans paramètre
() -> System.out.println("Hello !")

// Forme 2 : un paramètre (parenthèses optionnelles)
x -> x * 2
(x) -> x * 2  // équivalent

// Forme 3 : plusieurs paramètres
(x, y) -> x + y

// Forme 4 : avec bloc de code (plusieurs instructions)
(x, y) -> {
    int somme = x + y;
    System.out.println("Somme : " + somme);
    return somme;
}

// Forme 5 : avec types explicites (rarement nécessaire)
(int x, int y) -> x + y
```

### Cas Pratiques Progressifs

```java
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

// --- Exemple 1 : Trier des objets ---
List<String> villes = Arrays.asList("Paris", "Lyon", "Marseille", "Bordeaux", "Lille");

// Par longueur du nom
villes.sort((v1, v2) -> v1.length() - v2.length());
System.out.println("Par longueur : " + villes);
// → [Lyon, Lille, Paris, Bordeaux, Marseille]

// Alphabétique inversé
villes.sort((v1, v2) -> v2.compareTo(v1));
System.out.println("Alphabétique inversé : " + villes);

// --- Exemple 2 : Filtrage complexe ---
List<String> prenoms = Arrays.asList(
    "Alice", "Antoine", "Bob", "Beatrice", "Charlie", "Anne"
);

// Prénoms commençant par 'A', triés, en majuscules
List<String> filtres = prenoms.stream()
    .filter(p -> p.startsWith("A"))          // ["Alice","Antoine","Anne"]
    .sorted()                                 // ["Alice","Anne","Antoine"]
    .map(p -> p.toUpperCase())               // ["ALICE","ANNE","ANTOINE"]
    .collect(Collectors.toList());
System.out.println("Prénoms A : " + filtres);

// --- Exemple 3 : Réduction ---
List<Integer> salaires = Arrays.asList(2500, 3200, 1800, 4100, 2900);

int totalSalaires = salaires.stream()
    .reduce(0, (acc, salaire) -> acc + salaire);
System.out.println("Masse salariale : " + totalSalaires + "€");

// Version plus courte avec référence de méthode
int totalBis = salaires.stream().reduce(0, Integer::sum);
System.out.println("Total (bis) : " + totalBis + "€");

// --- Exemple 4 : Groupement ---
List<String> fruits = Arrays.asList(
    "pomme", "poire", "pêche", "banane", "bleuet", "cassis"
);

// Grouper par première lettre
Map<Character, List<String>> parLettre = fruits.stream()
    .collect(Collectors.groupingBy(f -> f.charAt(0)));

parLettre.forEach((lettre, liste) ->
    System.out.println(lettre + " → " + liste)
);
// b → [banane, bleuet]
// c → [cassis]
// p → [pomme, poire, pêche]
```

## 4.3 Les Interfaces Fonctionnelles en Détail

Une **interface fonctionnelle** est une interface avec **exactement une méthode abstraite**. C'est le type attendu quand on passe une lambda en paramètre.

### `Predicate<T>` — Tester une condition

```java
Predicate<Integer> estPositif  = n -> n > 0;
Predicate<Integer> estPair     = n -> n % 2 == 0;
Predicate<String>  estLong     = s -> s.length() > 5;

// Test simple
System.out.println(estPositif.test(5));   // true
System.out.println(estPositif.test(-3));  // false

// Combinaisons logiques
Predicate<Integer> estPositifEtPair = estPositif.and(estPair);
Predicate<Integer> estPositifOuPair = estPositif.or(estPair);
Predicate<Integer> estNegatif       = estPositif.negate();

System.out.println(estPositifEtPair.test(4));  // true (>0 ET pair)
System.out.println(estPositifEtPair.test(3));  // false (>0 mais impair)
System.out.println(estNegatif.test(-5));       // true (négation de positif)

// Utilisation avec filter
List<Integer> nombres = Arrays.asList(-3, -1, 0, 2, 4, 5, 7, 8);
List<Integer> positifsEtPairs = nombres.stream()
    .filter(estPositifEtPair)
    .collect(Collectors.toList());
System.out.println("Positifs et pairs : " + positifsEtPairs); // [2, 4, 8]
```

### `Function<T, R>` — Transformer une valeur

```java
Function<String, Integer> longueur      = s -> s.length();
Function<Integer, String> enTexte       = n -> "Nombre: " + n;
Function<String, String>  majuscules    = s -> s.toUpperCase();

System.out.println(longueur.apply("Bonjour"));   // 7
System.out.println(enTexte.apply(42));           // "Nombre: 42"

// Chaîner des fonctions avec andThen et compose
Function<String, String> longueurEnTexte = longueur.andThen(enTexte);
System.out.println(longueurEnTexte.apply("Java")); // "Nombre: 4"

// Utilisation avec map
List<String> mots = Arrays.asList("Bonjour", "Java", "Lambda");
List<Integer> longueurs = mots.stream()
    .map(longueur)
    .collect(Collectors.toList());
System.out.println("Longueurs : " + longueurs); // [7, 4, 6]
```

### `Consumer<T>` — Consommer (effectuer une action)

```java
Consumer<String>  afficher   = msg -> System.out.println("→ " + msg);
Consumer<Integer> afficherCarre = n -> System.out.println(n + "² = " + (n*n));

afficher.accept("Hello World");  // → Hello World
afficherCarre.accept(5);         // 5² = 25

// Chaîner des consumers avec andThen
Consumer<String> afficherEtCompter = afficher
    .andThen(msg -> System.out.println("  (longueur : " + msg.length() + ")"));

afficherEtCompter.accept("Bonjour");
// → Bonjour
//   (longueur : 7)

// Utilisation avec forEach
List<String> courses = Arrays.asList("pain", "lait", "fromage");
courses.forEach(afficher);
// → pain
// → lait
// → fromage
```

### `Supplier<T>` — Produire une valeur (sans entrée)

```java
Supplier<Double>  nombreAleatoire = () -> Math.random();
Supplier<String>  salutation      = () -> "Bonjour, monde !";
Supplier<List<String>> listVide   = () -> new ArrayList<>();

System.out.println(nombreAleatoire.get());  // ex: 0.7284...
System.out.println(salutation.get());       // Bonjour, monde !

// Utilisation avec Optional
Optional<String> nom = Optional.empty();
String resultat = nom.orElseGet(salutation); // si vide, utilise le supplier
System.out.println(resultat); // Bonjour, monde !
```

### `BiFunction<T, U, R>` — Transformer deux valeurs

```java
BiFunction<String, Integer, String> repeter =
    (s, n) -> s.repeat(n); // Java 11, ou faire manuellement

BiFunction<Double, Double, Double> puissance =
    (base, exp) -> Math.pow(base, exp);

System.out.println(puissance.apply(2.0, 10.0)); // 1024.0
System.out.println(puissance.apply(3.0, 3.0));  // 27.0
```

## 4.4 Streams — API Complète

### Vue d'ensemble du Pipeline de Streams

```
Collection/Tableau
        ↓
    .stream()          ← Créer le stream
        ↓
  [Opérations intermédiaires]   ← Paresseuses, retournent un Stream
    .filter(...)
    .map(...)
    .sorted(...)
    .distinct()
    .limit(n)
    .skip(n)
        ↓
  [Opération terminale]         ← Déclenche le traitement
    .collect(...)
    .forEach(...)
    .count()
    .findFirst()
    .anyMatch(...)
    .reduce(...)
```

### Cas Pratique Complet : Analyse d'une Bibliothèque

```java
import java.util.*;
import java.util.stream.*;

// Classe Livre
class Livre {
    private String titre;
    private String auteur;
    private int annee;
    private double prix;
    private String genre;

    public Livre(String titre, String auteur, int annee, double prix, String genre) {
        this.titre = titre; this.auteur = auteur;
        this.annee = annee; this.prix = prix; this.genre = genre;
    }

    // Getters
    public String getTitre()  { return titre;  }
    public String getAuteur() { return auteur; }
    public int    getAnnee()  { return annee;  }
    public double getPrix()   { return prix;   }
    public String getGenre()  { return genre;  }

    @Override
    public String toString() {
        return String.format("'%s' par %s (%d) - %.2f€", titre, auteur, annee, prix);
    }
}

public class Bibliotheque {
    public static void main(String[] args) {
        List<Livre> livres = Arrays.asList(
            new Livre("Clean Code",        "Robert Martin",   2008, 35.0,  "Informatique"),
            new Livre("Java Effectif",     "Joshua Bloch",    2018, 42.0,  "Informatique"),
            new Livre("Design Patterns",   "GoF",             1994, 55.0,  "Informatique"),
            new Livre("Le Petit Prince",   "Saint-Exupéry",   1943, 8.0,   "Littérature"),
            new Livre("1984",              "George Orwell",   1949, 9.5,   "Littérature"),
            new Livre("Dune",              "Frank Herbert",   1965, 12.0,  "SF"),
            new Livre("Fondation",         "Isaac Asimov",    1951, 10.0,  "SF"),
            new Livre("Le Seigneur...",    "Tolkien",         1954, 15.0,  "Fantasy"),
            new Livre("Harry Potter",      "J.K. Rowling",    1997, 11.0,  "Fantasy")
        );

        // 1. Livres d'informatique, triés par prix décroissant
        System.out.println("=== Livres Informatique (par prix décroissant) ===");
        livres.stream()
            .filter(l -> l.getGenre().equals("Informatique"))
            .sorted((l1, l2) -> Double.compare(l2.getPrix(), l1.getPrix()))
            .forEach(System.out::println);

        // 2. Prix moyen par genre
        System.out.println("\n=== Prix moyen par genre ===");
        Map<String, Double> prixParGenre = livres.stream()
            .collect(Collectors.groupingBy(
                Livre::getGenre,
                Collectors.averagingDouble(Livre::getPrix)
            ));
        prixParGenre.forEach((genre, prixMoyen) ->
            System.out.printf("%-15s : %.2f€%n", genre, prixMoyen)
        );

        // 3. Livre le moins cher de chaque genre
        System.out.println("\n=== Livre le moins cher par genre ===");
        Map<String, Optional<Livre>> moinsChersParGenre = livres.stream()
            .collect(Collectors.groupingBy(
                Livre::getGenre,
                Collectors.minBy((l1, l2) -> Double.compare(l1.getPrix(), l2.getPrix()))
            ));
        moinsChersParGenre.forEach((genre, livre) ->
            livre.ifPresent(l -> System.out.printf("%-15s : %s%n", genre, l))
        );

        // 4. Statistiques globales
        System.out.println("\n=== Statistiques ===");
        DoubleSummaryStatistics stats = livres.stream()
            .mapToDouble(Livre::getPrix)
            .summaryStatistics();
        System.out.println("Nombre de livres : " + stats.getCount());
        System.out.printf("Prix min   : %.2f€%n", stats.getMin());
        System.out.printf("Prix max   : %.2f€%n", stats.getMax());
        System.out.printf("Prix moyen : %.2f€%n", stats.getAverage());
        System.out.printf("Total      : %.2f€%n", stats.getSum());

        // 5. Tous les auteurs distincts, triés
        System.out.println("\n=== Auteurs (triés) ===");
        String auteurs = livres.stream()
            .map(Livre::getAuteur)
            .distinct()
            .sorted()
            .collect(Collectors.joining(", "));
        System.out.println(auteurs);

        // 6. Existe-t-il un livre avant 1950 ?
        boolean aVieuxLivre = livres.stream()
            .anyMatch(l -> l.getAnnee() < 1950);
        System.out.println("\nLivre avant 1950 ? " + aVieuxLivre); // true
    }
}
```

## 4.5 Références de Méthodes (Method References)

Une référence de méthode est un raccourci encore plus court pour certaines lambdas.

```java
// lambda : s -> System.out.println(s)
// équivalent : System.out::println

// Les 4 types de références de méthode :

// 1. Méthode statique : ClassName::methodName
Function<String, Integer> parser = Integer::parseInt;
System.out.println(parser.apply("42")); // 42

// 2. Méthode d'instance d'un objet particulier : instance::methodName
String prefixe = "Bonjour ";
Function<String, String> saluer = prefixe::concat;
System.out.println(saluer.apply("Alice")); // "Bonjour Alice"

// 3. Méthode d'instance d'un type arbitraire : ClassName::methodName
Function<String, String> enMaj = String::toUpperCase;
List<String> noms = Arrays.asList("alice", "bob", "carol");
noms.stream().map(String::toUpperCase).forEach(System.out::println);
// ALICE, BOB, CAROL

// 4. Constructeur : ClassName::new
Supplier<ArrayList<String>> creerListe = ArrayList::new;
List<String> maListe = creerListe.get();
```

---

# 5. Tour d'Horizon d'un EDI

## 5.1 Qu'est-ce qu'un EDI et Pourquoi l'Utiliser ?

Un **EDI** (Environnement de Développement Intégré), ou **IDE** en anglais, est un logiciel qui regroupe tous les outils nécessaires pour développer dans un seul endroit.

**Sans EDI** : vous devez utiliser un éditeur de texte simple (Notepad), puis ouvrir un terminal pour compiler (`javac MonFichier.java`) et exécuter (`java MonFichier`) manuellement. C'est long et source d'erreurs.

**Avec un EDI**, vous bénéficiez automatiquement de :

| Fonctionnalité         | Ce que ça fait concrètement                                              |
|------------------------|--------------------------------------------------------------------------|
| Coloration syntaxique  | Mots-clés en bleu, chaînes en rouge → code plus lisible                  |
| Auto-complétion        | Tapez `syst` et l'EDI propose `System.out.println`                       |
| Détection d'erreurs    | Souligne en rouge AVANT même de compiler                                 |
| Refactoring            | Renommer une variable partout en un clic                                 |
| Débogueur              | Exécuter ligne par ligne et voir la valeur de chaque variable            |
| Gestion de projets     | Organisation automatique des fichiers source, tests, ressources          |
| Intégration Git        | Committer, push, pull directement depuis l'EDI                          |
| Gestionnaire de dépendances | Maven/Gradle intégré pour ajouter des bibliothèques                 |

## 5.2 Comparaison Détaillée : Eclipse / IntelliJ / NetBeans

| Critère                | Eclipse                     | IntelliJ IDEA                      | NetBeans                  |
|------------------------|-----------------------------|------------------------------------|---------------------------|
| **Éditeur**            | Eclipse Foundation          | JetBrains                          | Apache Software Foundation|
| **Licence**            | Gratuit (open source)       | Community : Gratuit / Ultimate : Payant (~500€/an) | Gratuit (open source) |
| **Première version**   | 2001                        | 2001                               | 1997                      |
| **Performance**        | Moyen (peut être lent)      | Très rapide et réactif             | Bon                       |
| **Facilité d'utilisation** | Courbe d'apprentissage assez longue | Très intuitif, meilleure UX | Simple, bon pour débuter |
| **Auto-complétion**    | Bonne (via plugin)          | Excellente (IA assistée)           | Bonne                     |
| **Support Java**       | Excellent                   | Excellent                          | Excellent                 |
| **Support Web/JS**     | Via plugins                 | Excellente (Ultimate)              | Bonne                     |
| **Intégration Maven/Gradle** | Via plugin M2Eclipse  | Natif et transparent               | Natif                     |
| **Débogueur**          | Complet                     | Excellent (le meilleur)            | Complet                   |
| **Popularité entreprise** | Très populaire           | **Numéro 1 en entreprise**         | Moins répandu             |
| **Recommandé pour**    | Développeurs expérimentés, projets Eclipse legacy | Tout le monde, surtout débutants | Débutants, projets simples |

## 5.3 Installation et Premiers Pas avec IntelliJ IDEA

### Étape 1 : Installer le JDK (Java Development Kit)

Le JDK est l'ensemble d'outils pour compiler et exécuter Java. Sans lui, rien ne fonctionne.

1. Aller sur : https://adoptium.net/
2. Télécharger **Temurin 17 LTS** (recommandé) ou Temurin 8
3. Installer avec les options par défaut
4. Vérifier l'installation :
   ```
   java -version
   javac -version
   ```

### Étape 2 : Installer IntelliJ IDEA Community

1. Aller sur : https://www.jetbrains.com/idea/download/
2. Télécharger la version **Community** (gratuite)
3. Installer avec les options par défaut
4. Cocher "Add to PATH" si proposé

### Étape 3 : Créer votre Premier Projet

1. Ouvrir IntelliJ IDEA
2. Cliquer sur **New Project**
3. Sélectionner **Java**
4. Choisir votre JDK installé dans "SDK"
5. Donner un nom au projet (ex: `MonPremierProjet`)
6. Cliquer **Create**

### Étape 4 : Créer une Classe Java

1. Dans le panneau de gauche, clic droit sur le dossier `src`
2. Cliquer **New → Java Class**
3. Nommer la classe `Main`
4. Taper le code suivant :

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Mon premier programme Java !");

        // Variables
        String prenom = "Alice";
        int age = 25;
        double salaire = 2500.50;

        System.out.println("Nom : " + prenom);
        System.out.println("Age : " + age);
        System.out.println("Salaire : " + salaire + "€");
    }
}
```

5. Appuyer sur **▶** (bouton vert) ou `Shift + F10`

### Étape 5 : Utiliser le Débogueur

Le débogueur permet de mettre le programme **en pause** à une ligne précise et d'inspecter toutes les variables.

1. Cliquer dans la marge gauche à côté d'une ligne → un point rouge apparaît (breakpoint)
2. Appuyer sur **🐞** (bouton debug) ou `Shift + F9`
3. Le programme s'arrête à votre breakpoint
4. Vous voyez toutes les variables et leurs valeurs dans le panneau "Variables"
5. Appuyer sur **F8** pour avancer ligne par ligne
6. Appuyer sur **F9** pour continuer jusqu'au prochain breakpoint

## 5.4 Raccourcis Clavier Essentiels

### IntelliJ IDEA

| Action                          | Windows/Linux          | Mac                  |
|---------------------------------|------------------------|----------------------|
| Exécuter le programme           | `Shift + F10`          | `Ctrl + R`           |
| Déboguer le programme           | `Shift + F9`           | `Ctrl + D`           |
| Auto-complétion                 | `Ctrl + Espace`        | `Ctrl + Espace`      |
| Complétion intelligente         | `Ctrl + Shift + Espace`| `Ctrl + Shift + Espace` |
| Rechercher partout              | `Double Shift`         | `Double Shift`       |
| Formater le code                | `Ctrl + Alt + L`       | `Cmd + Alt + L`      |
| Commentaire rapide              | `Ctrl + /`             | `Cmd + /`            |
| Aller à la déclaration          | `Ctrl + B` ou `Ctrl+Clic` | `Cmd + B`         |
| Renommer (refactoring)          | `Shift + F6`           | `Shift + F6`         |
| Copier une ligne                | `Ctrl + D`             | `Cmd + D`            |
| Supprimer une ligne             | `Ctrl + Y`             | `Cmd + Backspace`    |
| Générer code (getter/setter...) | `Alt + Insert`         | `Cmd + N`            |
| Afficher la doc                 | `Ctrl + Q`             | `F1`                 |

### Raccourcis de Live Templates (Gain de temps !)

Tapez le mot-clé puis appuyez sur `Tab` pour générer automatiquement du code :

| Mot-clé  | Code généré                                               |
|----------|-----------------------------------------------------------|
| `psvm`   | `public static void main(String[] args) { }`             |
| `sout`   | `System.out.println();`                                  |
| `fori`   | Boucle `for (int i = 0; i < ; i++) { }`                  |
| `iter`   | Boucle `for (... : collection) { }`                      |
| `ifn`    | `if (xxx == null) { }`                                   |
| `inn`    | `if (xxx != null) { }`                                   |

## 5.5 Structure d'un Projet Java dans IntelliJ

```
MonProjet/
├── src/
│   └── main/
│       └── java/
│           ├── Main.java          ← Classe principale
│           ├── modele/
│           │   ├── Etudiant.java
│           │   └── Cours.java
│           └── service/
│               └── GestionService.java
├── test/
│   └── java/
│       └── EtudiantTest.java      ← Tests unitaires
├── resources/
│   └── config.properties          ← Fichiers de config
└── pom.xml                        ← Dépendances Maven (si utilisé)
```

---

## RÉCAPITULATIF GÉNÉRAL

### Les 5 Concepts à Retenir

| # | Concept              | Mot-clé Java          | Analogie                                |
|---|----------------------|-----------------------|-----------------------------------------|
| 1 | **Classe**           | `class`               | Un formulaire vierge                    |
| 2 | **Héritage**         | `extends`             | Un enfant hérite des traits du parent   |
| 3 | **Classe abstraite** | `abstract class`      | Un plan incomplet qui force la complétude |
| 4 | **Interface**        | `interface` / `implements` | Un contrat de comportement           |
| 5 | **Lambda**           | `->`                  | Une mini-fonction sans nom              |

### Les Nouveautés Java 8 à Connaître

```java
// 1. Lambda
() -> System.out.println("Hello")           // sans paramètre
x -> x * 2                                  // un paramètre
(x, y) -> x + y                             // deux paramètres

// 2. Stream Pipeline
collection.stream()
    .filter(x -> condition)                  // filtrer
    .map(x -> transformation)               // transformer
    .sorted()                               // trier
    .collect(Collectors.toList());          // collecter

// 3. Optional
Optional.of(valeur)                         // valeur non-null
Optional.ofNullable(peutEtreNull)           // peut être null
opt.isPresent()                             // vérifie si valeur présente
opt.orElse("défaut")                        // valeur par défaut

// 4. Date/Time
LocalDate.now()                             // date du jour
LocalDate.of(2024, 1, 15)                  // date précise
LocalDateTime.now()                         // date + heure

// 5. Interface default
interface MaInterface {
    default void methodeAvecCorps() { ... } // nouveauté Java 8
    static void methodeStatique() { ... }   // nouveauté Java 8
}
```

---

*Guide préparé pour une présentation Java 8 — 101*
*Tous les exemples de code ont été conçus pour être exécutables directement.*