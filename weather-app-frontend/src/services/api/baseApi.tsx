// const BASE_URL = 'http://localhost:8080/api/weather/';
const BASE_URL = '/api/weather';

// Our ApiError interface inherits from JS' built-in Error class
// Gives basic error functionality (stack traces, etc.)

// Example usage: You can use it like a regular Error
// throw new ApiError(404, "Not Found", 1006);
export class ApiError extends Error {
    // Note this way of doing the constructor is the shorthand way. You could also do this the normal way like in Java
    constructor( 
        public status: number,
        public message: string,
        public code?: number
    ) {
        super(message); // Clarify further what this is for
        this.name = 'ApiError';

        // Calls the parent Error class constructor
        // Ensures proper error setup
        // Maintains the error message chain
    }
}

export async function apiClient<T>(endpoint: string = '', options: RequestInit = {}): Promise<T> {
    const url = `${BASE_URL}/${endpoint}`;
    console.log('Making API request to:', url);

    try {
        const response = await fetch(url, {
            ...options,
            headers: {
                'Content-Type': 'application/json',
                ...options.headers,
            }
        });

        // Log the full response for debugging
        console.log('Response status:', response.status);
        console.log('Response headers:', response.headers);

        // Old Error handling
        // if (!response.ok) {
        //     const errorText = await response.text();
        //     console.error('Error response body:', errorText);
        //     throw new Error(`API Error: ${response.status}, ${response.statusText}`);
        // }

        if (!response.ok) {
            const errorData = await response.json();
            console.log('Error response:', errorData);

            // Handling specific error cases
            if (errorData?.error) {
                throw new ApiError(
                    response.status,
                    errorData.error.message,
                    errorData.error.code
                );
            }

            // Fallback for unexpected error format
            throw new ApiError(
                response.status,
                'An unexpected error occurred.',
                undefined
            );
        }

        const data = await response.json();
        console.log('API response:', data); // Debug log
        return data;
    } catch (error) {
        console.error('API request failed:', error); // Debug log

        // Rethrow Api Error instances
        if (error instanceof ApiError) {
            throw error;
        }

        // Handle network errors
        if (error instanceof TypeError && error.message === "Failed to fetch") {
            throw new ApiError(0, 'Unable to connect to the server. Please check your internet connection.', undefined);
        }

        throw new ApiError(500, "An unexpected error occurred.", undefined);
    }
}
