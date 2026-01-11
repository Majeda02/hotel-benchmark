import express from 'express';
import { ApolloServer } from '@apollo/server';
import { expressMiddleware } from '@apollo/server/express4';
import { typeDefs } from './schema.js';
import { makePool } from './db.js';
import { makeResolvers } from './resolvers.js';
import client from 'prom-client';

const app = express();
app.use(express.json({ limit: '2mb' }));

const register = new client.Registry();
client.collectDefaultMetrics({ register });

const httpRequestDuration = new client.Histogram({
  name: 'graphql_http_request_duration_seconds',
  help: 'Duration of GraphQL HTTP requests in seconds',
  labelNames: ['operation'],
  buckets: [0.005, 0.01, 0.02, 0.05, 0.1, 0.2, 0.5, 1, 2, 5]
});
register.registerMetric(httpRequestDuration);

app.get('/metrics', async (_req, res) => {
  res.set('Content-Type', register.contentType);
  res.end(await register.metrics());
});

const pool = makePool();
const resolvers = makeResolvers(pool);

const apollo = new ApolloServer({ typeDefs, resolvers });
await apollo.start();

app.use('/graphql', (req, res, next) => {
  const start = Date.now();
  res.on('finish', () => {
    const op = (req.body && req.body.operationName) ? req.body.operationName : 'anonymous';
    httpRequestDuration.labels(op).observe((Date.now() - start) / 1000);
  });
  next();
}, expressMiddleware(apollo));

const port = parseInt(process.env.PORT || '8083', 10);
app.listen(port, () => console.log(`GraphQL running on http://localhost:${port}/graphql`));
