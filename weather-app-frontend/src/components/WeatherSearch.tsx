import { useState } from "react";

interface WeatherSearchProps {
    onSearchLocation: (text: string) => void;
}

function WeatherSearch({ onSearchLocation }: WeatherSearchProps) {
    const [text, setText] = useState("");
    const [errors, setErrors] = useState({ text: "" }); // TODO

    // TODO
    // const validateField = (value: string) => {

    // }

    // function handleInputChange(e: React.ChangeEvent<HTMLInputElement>) {

    // }

    function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();

        // To validate the field

        // Only submit if no errors
        if (text.trim()) {
            onSearchLocation(text.trim());
            setText("");
        }
    }

    function clearSearch() {
        setText("");
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
                    value={text}
                    onChange={(e) => setText(e.target.value)}
                    placeholder="Search a region"
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
