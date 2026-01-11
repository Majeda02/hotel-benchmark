export const typeDefs = `#graphql
type Client {
  id: ID!
  nom: String!
  prenom: String!
  email: String!
  telephone: String!
}

type Chambre {
  id: ID!
  type: String!
  prix: String!
  disponible: Boolean!
}

type Reservation {
  id: ID!
  client: Client!
  chambre: Chambre!
  dateDebut: String!
  dateFin: String!
  preferences: String!
}

input ClientInput {
  nom: String!
  prenom: String!
  email: String!
  telephone: String!
}

input ReservationInput {
  client: ClientInput!
  chambreId: ID!
  dateDebut: String!
  dateFin: String!
  preferences: String!
}

type Query {
  reservation(id: ID!): Reservation
}

type Mutation {
  createReservation(input: ReservationInput!): Reservation!
  updateReservation(id: ID!, input: ReservationInput!): Reservation!
  deleteReservation(id: ID!): Boolean!
}
`;
