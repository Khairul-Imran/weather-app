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
    };

    function clearSearch() {
        setSearchTerm("");
    }

    return (
        <form
            onSubmit={handleSubmit}
            className="flex flex-col items-center gap-4"
        >
            <div className="w-1/2 relative mb-6">
                <input
                    type="text"
                    name="text"
                    value={searchTerm}
                    // To change this when we add validation
                    onChange={(e) => setSearchTerm(e.target.value)}
                    placeholder="Search a location"
                    className="w-full px-4 py-2 border rounded focus:outline-none"
                />
                {/* To insert a "x" button to clear search */}
                <button
                    type="button"
                    onClick={() => clearSearch()}
                    className="absolute right-2 top-1/2 -translate-y-1/2 
                                 px-3 py-1 text-sm text-red-600 hover:text-red-800
                                 border border-red-600 hover:bg-red-50 rounded-full
                                 focus:outline-none focus:ring-2 focus:ring-red-500"
                >
                    x
                </button>
            </div>

            <button
                type="submit"
                className="px-6 py-2 bg-blue-500 text-white rounded-md 
                         hover:bg-blue-600 focus:outline-none focus:ring-2 
                         focus:ring-blue-500 font-medium"
            >
                Search Weather
            </button>
        </form>
    );
}

export default WeatherSearch;
