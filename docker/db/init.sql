CREATE TABLE IF NOT EXISTS clients (
  id BIGSERIAL PRIMARY KEY,
  nom VARCHAR(100) NOT NULL,
  prenom VARCHAR(100) NOT NULL,
  email VARCHAR(200) NOT NULL UNIQUE,
  telephone VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS chambres (
  id BIGSERIAL PRIMARY KEY,
  type VARCHAR(50) NOT NULL,
  prix NUMERIC(10,2) NOT NULL,
  disponible BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS reservations (
  id BIGSERIAL PRIMARY KEY,
  client_id BIGINT NOT NULL REFERENCES clients(id),
  chambre_id BIGINT NOT NULL REFERENCES chambres(id),
  date_debut DATE NOT NULL,
  date_fin DATE NOT NULL,
  preferences TEXT NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_res_client ON reservations(client_id);
CREATE INDEX IF NOT EXISTS idx_res_chambre ON reservations(chambre_id);

-- Seed minimal data (repeatable)
INSERT INTO chambres (type, prix, disponible)
SELECT * FROM (VALUES
  ('simple', 350.00, TRUE),
  ('double', 500.00, TRUE),
  ('suite', 1200.00, TRUE)
) AS v(type, prix, disponible)
ON CONFLICT DO NOTHING;

INSERT INTO clients (nom, prenom, email, telephone)
SELECT * FROM (VALUES
  ('Doe', 'John', 'john.doe@example.com', '+212600000001'),
  ('Smith', 'Anna', 'anna.smith@example.com', '+212600000002'),
  ('El Amrani', 'Yassine', 'yassine.elamrani@example.com', '+212600000003')
) AS v(nom, prenom, email, telephone)
ON CONFLICT (email) DO NOTHING;
