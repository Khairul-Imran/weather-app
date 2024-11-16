// const BASE_URL = 'localhost:8080/api/weather/';
const BASE_URL = '/api/weather/';

export async function apiClient<T>(endpoint: string = '', options: RequestInit = {}): Promise<T> {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
        ...options,
        headers: {
            'Content-Type' : 'application/json',
            ...options.headers,
        }
    });

    if (!response.ok) {
        throw new Error(`API Error: ${response.statusText}`);
    }

    return response.json();
}