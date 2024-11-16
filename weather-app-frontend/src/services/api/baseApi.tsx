// const BASE_URL = 'http://localhost:8080/api/weather/';
const BASE_URL = '/api/weather';

export async function apiClient<T>(endpoint: string = '', options: RequestInit = {}): Promise<T> {
    const url = `${BASE_URL}/${endpoint}`;
    console.log('Making API request to:', url); // Debug log

    try {
        const response = await fetch(url, {
            ...options,
            headers: {
                'Content-Type': 'application/json',
                ...options.headers,
            }
        });

        // Log the full response for debugging
        // console.log('Response status:', response.status);
        // console.log('Response headers:', response.headers);

        if (!response.ok) {
            const errorText = await response.text();
            console.error('Error response body:', errorText);
            throw new Error(`API Error: ${response.status}, ${response.statusText}`);
        }

        const data = await response.json();
        console.log('API response:', data); // Debug log
        return data;
    } catch (error) {
        console.error('API request failed:', error); // Debug log
        throw error;
    }
}

// Old
// export async function apiClient<T>(endpoint: string = '', options: RequestInit = {}): Promise<T> {
//     const response = await fetch(`${BASE_URL}/${endpoint}`, {
//         ...options,
//         headers: {
//             'Content-Type' : 'application/json',
//             ...options.headers,
//         }
//     });

//     if (!response.ok) {
//         throw new Error(`API Error: ${response.statusText}`);
//     }

//     return response.json();
// }
