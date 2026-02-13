import { Injectable } from '@angular/core';

type LoginPayload = { token: string; email: string; name: string; role: string };

@Injectable({ providedIn: 'root' })
export class AuthService {
  private key = 'tt_token';
  private profileKey = 'tt_profile';

  isLoggedIn(): boolean { return !!localStorage.getItem(this.key); }
  token(): string | null { return localStorage.getItem(this.key); }
  profile(): LoginPayload | null {
    const raw = localStorage.getItem(this.profileKey);
    return raw ? JSON.parse(raw) as LoginPayload : null;
  }

  saveLogin(payload: LoginPayload) {
    localStorage.setItem(this.key, payload.token);
    localStorage.setItem(this.profileKey, JSON.stringify(payload));
  }

  logout() {
    localStorage.removeItem(this.key);
    localStorage.removeItem(this.profileKey);
    location.href = '/login';
  }
}
