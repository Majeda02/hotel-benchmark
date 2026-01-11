import { randomUUID } from 'crypto';

export function makeResolvers(pool) {
  return {
    Query: {
      reservation: async (_parent, { id }) => {
        const r = await pool.query(
          `SELECT r.id, r.client_id, r.chambre_id, r.date_debut, r.date_fin, r.preferences
           FROM reservations r WHERE r.id = $1`,
          [id]
        );
        if (r.rowCount === 0) return null;

        const row = r.rows[0];
        const client = await pool.query(`SELECT * FROM clients WHERE id = $1`, [row.client_id]);
        const chambre = await pool.query(`SELECT * FROM chambres WHERE id = $1`, [row.chambre_id]);

        return {
          id: String(row.id),
          client: client.rows[0],
          chambre: {
            ...chambre.rows[0],
            prix: String(chambre.rows[0].prix),
          },
          dateDebut: row.date_debut.toISOString().slice(0, 10),
          dateFin: row.date_fin.toISOString().slice(0, 10),
          preferences: row.preferences,
        };
      }
    },
    Mutation: {
      createReservation: async (_parent, { input }) => {
        const { client, chambreId, dateDebut, dateFin, preferences } = input;

        const existing = await pool.query(`SELECT id FROM clients WHERE email = $1`, [client.email]);
        let clientId;
        if (existing.rowCount > 0) {
          clientId = existing.rows[0].id;
        } else {
          const ins = await pool.query(
            `INSERT INTO clients(nom, prenom, email, telephone) VALUES ($1,$2,$3,$4) RETURNING id`,
            [client.nom, client.prenom, client.email, client.telephone]
          );
          clientId = ins.rows[0].id;
        }

        const insR = await pool.query(
          `INSERT INTO reservations(client_id, chambre_id, date_debut, date_fin, preferences)
           VALUES ($1,$2,$3,$4,$5) RETURNING id`,
          [clientId, chambreId, dateDebut, dateFin, preferences]
        );

        return await this.Query.reservation(null, { id: String(insR.rows[0].id) });
      },

      updateReservation: async (_parent, { id, input }) => {
        const { client, chambreId, dateDebut, dateFin, preferences } = input;

        const existing = await pool.query(`SELECT id FROM clients WHERE email = $1`, [client.email]);
        let clientId;
        if (existing.rowCount > 0) {
          clientId = existing.rows[0].id;
        } else {
          const ins = await pool.query(
            `INSERT INTO clients(nom, prenom, email, telephone) VALUES ($1,$2,$3,$4) RETURNING id`,
            [client.nom, client.prenom, client.email, client.telephone]
          );
          clientId = ins.rows[0].id;
        }

        await pool.query(
          `UPDATE reservations SET client_id=$1, chambre_id=$2, date_debut=$3, date_fin=$4, preferences=$5 WHERE id=$6`,
          [clientId, chambreId, dateDebut, dateFin, preferences, id]
        );

        return await this.Query.reservation(null, { id: String(id) });
      },

      deleteReservation: async (_parent, { id }) => {
        await pool.query(`DELETE FROM reservations WHERE id = $1`, [id]);
        return true;
      }
    }
  };
}
