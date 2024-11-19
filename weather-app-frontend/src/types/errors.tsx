export interface ApiError {
    code: number,
    message: string
};

export interface WeatherApiErrorResponse {
    error: ApiError
};