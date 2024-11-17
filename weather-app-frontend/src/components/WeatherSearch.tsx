import { useState } from "react";

interface WeatherSearchProps {
    onSearch: (location: string) => Promise<void>;
}

function WeatherSearch({ onSearch }: WeatherSearchProps) {
    const [searchTerm, setSearchTerm] = useState("");
    // TODO later
    // const [errors, setErrors] = useState({ text: "" });

    // TODO later
    // const validateField = (value: string) => {

    // }

    // function handleInputChange(e: React.ChangeEvent<HTMLInputElement>) {

    // }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        console.log("Form submitted with location: ", searchTerm);
        if (searchTerm.trim()) {
            await onSearch(searchTerm.trim());
        }
        clearSearch();
    };

    function clearSearch() {
        setSearchTerm("");
    }

    return (
        <div className="max-w-2xl mx-auto">
            <form
                onSubmit={handleSubmit}
                className="bg-white p-6 rounded-lg shadow-md"
            >
                <div className="relative">
                    <input
                        type="text"
                        name="text"
                        value={searchTerm}
                        // To change this when we add validation
                        onChange={(e) => setSearchTerm(e.target.value)}
                        placeholder="Enter city name..."
                        className="w-full px-4 py-3 pr-12 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent placeholder-gray-400 text-gray-700"
                    />

                    {searchTerm && (
                        <button
                            type="button"
                            onClick={clearSearch}
                            className="absolute right-3 top-1/2 -translate-y-1/2 
                            p-1.5 text-gray-400 hover:text-gray-600
                            rounded-full hover:bg-gray-100
                            focus:outline-none focus:ring-2 
                            focus:ring-gray-400 transition-colors"
                        >
                            <svg 
                                className="w-5 h-5" 
                                fill="none" 
                                stroke="currentColor" 
                                viewBox="0 0 24 24"
                            >
                                <path 
                                    strokeLinecap="round" 
                                    strokeLinejoin="round" 
                                    strokeWidth={2} 
                                    d="M6 18L18 6M6 6l12 12" 
                                />
                            </svg>
                            {/* x */}
                        </button>
                    )}
                </div>

                <div className="mt-4 flex justify-center">
                    <button
                        type="submit"
                        className="px-6 py-3 bg-gradient-to-r from-blue-500 to-blue-600 
                        text-white rounded-lg font-medium
                        hover:from-blue-600 hover:to-blue-700 
                        focus:outline-none focus:ring-2 
                        focus:ring-blue-500 focus:ring-offset-2
                        transform transition-transform hover:scale-105
                        shadow-md"
                    >
                        Search Weather
                    </button>
                </div>
            </form>
        </div>
    );
}

export default WeatherSearch;
