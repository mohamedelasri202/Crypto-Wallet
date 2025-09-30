# 🚀 Crypto Wallet Simulator

## 📌 Description du projet
Ce projet est un **simulateur de portefeuille crypto** développé en **Java 8** qui permet de :
- Créer des portefeuilles (Bitcoin ou Ethereum),
- Créer des transactions avec différents niveaux de frais,
- Simuler l’ajout des transactions dans un **mempool**,
- Calculer la **position d’une transaction** dans la file d’attente,
- Comparer l’impact des frais (ÉCONOMIQUE, STANDARD, RAPIDE),
- Afficher l’état actuel du mempool en console.

L’objectif est d’aider les utilisateurs à comprendre le rôle des frais dans les transactions blockchain et de **mettre en pratique les concepts essentiels** (transactions, fees, mempool, priorités).

---

## 🛠️ Technologies utilisées
- **Java 8** (langage principal)
- **PostgreSQL** (persistance des données via JDBC)
- **ArrayList / HashMap** (structures de données principales)
- **Enums Java** (gestion des constantes métier)
- **Java Time API** (dates et durées)
- **Logging** : `java.util.logging` (gestion des erreurs et logs métier)
- **JUnit 4/5** (tests unitaires)
- **Git** (gestion de version, branches multiples)

---

## 📂 Structure du projet
- `UI` : Couche de présentation (menus, affichage console)
- `Metier` : Couche métier (Wallet, Transaction, Mempool, Services)
- `Utilitaire` : Outils communs, gestion de la base de données
- `Repository` : Gestion de la persistance (Repository Pattern)
- `Test` : Tests unitaires

---

## 📋 Fonctionnalités principales
1. **Créer un wallet crypto**
   - Type au choix : Bitcoin ou Ethereum
   - Génération automatique d’adresse crypto valide
   - Initialisation avec solde nul et ID unique

2. **Créer une transaction**
   - Adresse source, destination, montant
   - Choix du niveau de frais (ÉCONOMIQUE, STANDARD, RAPIDE)
   - Calcul automatique des frais selon le type de crypto
   - Transaction ajoutée en statut `PENDING`

3. **Voir la position dans le mempool**
   - Calcul de la position en fonction des frais
   - Estimation du temps d’attente (`position × 10 minutes`)

4. **Comparer les 3 niveaux de frais**
   - Affichage comparatif (coût vs rapidité)
   - Tableau ASCII ou tout autre affichage console

5. **Consulter l’état actuel du mempool**
   - Génération de transactions aléatoires pour simulation
   - Liste des transactions avec leurs frais
   - Mise en évidence de la transaction de l’utilisateur

---

## 📐 Diagramme UML
👉 Voici l’emplacement du diagramme UML du projet. Remplace simplement le lien ci-dessous par le tien :

![Diagramme UML](https://raw.githubusercontent.com/mohamedelasri202/Crypto-Wallet/main/Screenshot%202025-09-30%20204638.png)



---

## 🖼️ Captures d’écran
👉 Voici l’emplacement pour les captures d’écran. Tu peux ajouter plusieurs images, par exemple pour le menu principal, la création de wallet, la comparaison des fees, etc.

### Exemple : Menu principal
![Menu Principal](https://github.com/ton-utilisateur/crypto-wallet-simulator/blob/main/docs/menu.png)

### Exemple : Création de wallet
![Création Wallet](https://github.com/ton-utilisateur/crypto-wallet-simulator/blob/main/docs/create-wallet.png)

### Exemple : Comparaison des frais
![Comparaison Fees](https://github.com/ton-utilisateur/crypto-wallet-simulator/blob/main/docs/compare-fees.png)

### Exemple : État du mempool
![État du mempool](https://github.com/ton-utilisateur/crypto-wallet-simulator/blob/main/docs/mempool.png)

---

## ⚙️ Prérequis & Installation
### Prérequis
- Java 8 (obligatoire, pas de version supérieure)
- PostgreSQL installé et configuré
- Git

### Installation
```bash
# Cloner le dépôt
git clone https://github.com/ton-utilisateur/crypto-wallet-simulator.git
cd crypto-wallet-simulator

# Compiler les fichiers
javac -d bin src/**/*.java

# Exécuter l’application
java -cp bin UI.MainMenu



