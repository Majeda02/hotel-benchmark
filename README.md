# Étude de Cas : Analyse Scalabilité/Performance des APIs Modernes à travers un Cas Réel de Gestion d’Hôtel

## Contexte
Une plateforme de réservation d'hôtels souhaite implémenter une API qui permet de gérer les opérations suivantes :
* Créer une réservation : Les utilisateurs soumettent des informations sur le client, les dates de séjour, et les préférences de chambre.
* Consulter une réservation : L'API récupère les informations détaillées d'une réservation existante.
* Modifier une réservation : Les utilisateurs peuvent mettre à jour les dates ou les informations client.
* Supprimer une réservation : Une réservation peut être annulée par un utilisateur ou un administrateur.
Le système doit prendre en charge des millions de requêtes et être compatible avec des environnements multi-utilisateurs. Il doit également fonctionner efficacement pour des volumes de données variables (petits, moyens et grands messages).

## Objectifs de l'Étude
Comparer REST, SOAP, GraphQL, et gRPC dans un scénario réaliste en termes de :
* Performances (latence, débit, consommation des ressources).
* Scalabilité (capacité à gérer des charges croissantes).
* Simplicité d'implémentation.
* Sécurité et flexibilité.
Fournir des recommandations sur la technologie la plus adaptée à différents cas d'usage.

## Détails de l'Implémentation : Architecture
#### Backend :
* Implémenté avec Spring Boot pour REST et SOAP.
* Utilisation d'Apollo Server pour GraphQL.
* Utilisation de la bibliothèque Java gRPC.
* Hébergement sur un serveur local ou cloud avec des configurations identiques.
#### Base de Données :
* MySQL ou PostgreSQL pour stocker les données des réservations.
* Les mêmes requêtes SQL sont utilisées pour garantir une équité dans la comparaison.
#### Frontend :
* Un simple client en React.js pour tester les fonctionnalités CRUD.
<img width="536" height="274" alt="image" src="https://github.com/user-attachments/assets/8fc66298-187d-42d4-9806-dd9430306948" />

## Détails de l'Implémentation : Scénarios de Test
#### Opérations Testées :
* Créer une réservation (POST/Mutation/gRPC call).
* Consulter une réservation (GET/Query/gRPC call).
* Modifier une réservation (PUT/Mutation/gRPC call).
* Supprimer une réservation (DELETE/Mutation/gRPC call).
#### Variables :
* Nombre de Requêtes Simultanées : 10, 100, 500, 1000 requêtes.
* Taille des Messages :
   * Petit : 1 KB (réservation simple).
   * Moyen : 10 KB (réservation avec détails supplémentaires).
   * Grand : 100 KB (réservation complexe avec pièces jointes ou historique).

## Auteur
* Nom : BEN-LAGHFIRI Majeda
* Cours: Architecture Microservices : Conception, Déploiement et Orchestration
* Date : Janvier 2026
* Encadré par : Pr.Mohamed LACHGAR
