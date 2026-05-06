import java.util.ArrayList;   // cette librairie a ete importer pour manipuler le tableau arralyste qui est une collection


// creation de la classe Employee avec les attributs 
abstract class Employee {
    protected String nom;
    protected String prenom;
    private int age;
    private int depart;

    public Employee(String nom, String prenom, int age, int depart) {
        // CORRECTION : assignation des valeurs reçues
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.depart = depart;
    }

    public abstract double CalculerSalaire();

    public String getNom() {
        return "L'employé " + nom + " " + prenom;
    }
}

// la classe des ventes qui herite de la classe Employee qui sert de base
class Vente extends Employee {
    private double chiffre_daffaire;
    private int bonus = 400;
    private final double c = 0.2;

    public Vente(String nom, String prenom, int age, int depart, double chiffre_daffaire) {
        super(nom, prenom, age, depart);
        this.chiffre_daffaire = chiffre_daffaire;
    }

    @Override
    public double CalculerSalaire() {
        return (chiffre_daffaire * c) + bonus;
    }

    @Override
    public String getNom() {
        return "Le vendeur " + nom + " " + prenom;
    }
}


// la classe des Representation qui herite de la classe Employee qui sert de base

class Representation extends Employee {
    private double chiffre_daffaire;
    private final int bonus = 800;
    private final double c = 0.2;

    public Representation(String nom, String prenom, int age, int depart, double chiffre_daffaire) {
        super(nom, prenom, age, depart);
        this.chiffre_daffaire = chiffre_daffaire;
    }

    @Override
    public double CalculerSalaire() {
        return (chiffre_daffaire * c) + bonus;
    }
}


// la classe des Production qui herite de la classe Employee qui sert de base

class Production extends Employee {
    protected int nbre_unite;
    protected final int c = 5;

    public Production(String nom, String prenom, int age, int depart, int nbre_unite) {
        super(nom, prenom, age, depart);
        this.nbre_unite = nbre_unite;
    }

    @Override
    public double CalculerSalaire() {
        return (nbre_unite * c);
    }
}


// la classe des Manutention qui herite de la classe Employee qui sert de base

class Manutention extends Employee {
    protected int nbre_heures;
    protected int c = 65;

    public Manutention(String nom, String prenom, int age, int depart, int nbre_heures) {
        super(nom, prenom, age, depart);
        this.nbre_heures = nbre_heures;
    }

    @Override
    public double CalculerSalaire() {
        return (nbre_heures * c);
    }
}


interface employe_risque {
    double prime_mensuelle = 200;
}

// Tes classes à risques qui héritent de tes classes de base
class ProductionRisque extends Production implements employe_risque {
    public ProductionRisque(String nom, String prenom, int age, int depart, int nbre_unite) {
        super(nom, prenom, age, depart, nbre_unite);
    }

    @Override
    public double CalculerSalaire() {
        return super.CalculerSalaire() + prime_mensuelle;
    }
}

class ManutentionRisque extends Manutention implements employe_risque {
    public ManutentionRisque(String nom, String prenom, int age, int depart, int nbre_heures) {
        super(nom, prenom, age, depart, nbre_heures);
    }

    @Override
    public double CalculerSalaire() {
        return super.CalculerSalaire() + prime_mensuelle;
    }
}

class Personnel {
    private ArrayList<Employee> staff = new ArrayList<>();

    void ajouterEmploye(Employee employe) {
        staff.add(employe);
    }

    void calculerSalaires() {
        for (Employee e : staff) {
            System.out.println(e.getNom() + " gagne " + e.CalculerSalaire() + " CFA.");
        }
    }

    double salaireMoyen() {
        if (staff.isEmpty()) return 0;
        double total = 0;
        for (Employee e : staff) {
            total += e.CalculerSalaire();
        }
        return total / staff.size();
    }
}

// Le Main pour tester
public class Salaires {
    public static void main(String[] args) {
        Personnel p = new Personnel();
        p.ajouterEmploye(new Vente("Kouassi", "Business", 45, 1995, 30000));
        p.ajouterEmploye(new Representation("Balo", "Vendtout", 25, 2001, 20000));
        p.ajouterEmploye(new Production("Yves", "Bosseur", 28, 1998, 1000));
        p.ajouterEmploye(new Manutention("Mélanie", "Stocketout", 32, 1998, 45));
        p.ajouterEmploye(new ProductionRisque("Eman", "Flippe", 28, 2000, 1000));
        p.ajouterEmploye(new ManutentionRisque("Cris", "Abordage", 30, 2001, 45));


        p.calculerSalaires();
        System.out.println("le salaire moyen de l'entreprise est  : " + p.salaireMoyen() + " "+ "Francs" );
    }
}
