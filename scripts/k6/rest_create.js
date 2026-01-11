import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: __ENV.VUS ? parseInt(__ENV.VUS) : 10,
  duration: __ENV.DURATION || '30s',
};

const baseUrl = __ENV.BASE_URL || 'http://host.docker.internal:8081';
const payloadPath = __ENV.PAYLOAD || 'scripts/payloads/reservation_1kb.json';

export default function () {
  const payload = open(payloadPath);
  const res = http.post(`${baseUrl}/api/reservations`, payload, {
    headers: { 'Content-Type': 'application/json' },
  });
  check(res, { 'status is 201': (r) => r.status === 201 });
  sleep(0.2);
}
