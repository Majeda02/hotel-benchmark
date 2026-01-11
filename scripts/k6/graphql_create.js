import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: __ENV.VUS ? parseInt(__ENV.VUS) : 10,
  duration: __ENV.DURATION || '30s',
};

const baseUrl = __ENV.BASE_URL || 'http://host.docker.internal:8083/graphql';
const payloadPath = __ENV.PAYLOAD || 'scripts/graphql/create_1kb.json';

export default function () {
  const payload = open(payloadPath);
  const res = http.post(baseUrl, payload, {
    headers: { 'Content-Type': 'application/json' },
  });
  check(res, { 'status is 200': (r) => r.status === 200 });
  sleep(0.2);
}
