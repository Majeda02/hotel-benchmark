import pg from 'pg';

export function makePool() {
  const { Pool } = pg;
  const connectionString = process.env.DATABASE_URL || 'postgresql://hotel:hotel@localhost:5432/hotel';
  return new Pool({ connectionString });
}
