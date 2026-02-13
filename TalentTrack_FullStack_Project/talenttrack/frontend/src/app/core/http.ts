export function qs(obj: Record<string, any>) {
  const params = new URLSearchParams();
  Object.entries(obj).forEach(([k,v]) => {
    if (v !== null && v !== undefined && v !== '') params.set(k, String(v));
  });
  const s = params.toString();
  return s ? `?${s}` : '';
}
