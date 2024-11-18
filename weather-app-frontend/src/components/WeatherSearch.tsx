import { useState } from "react";

interface WeatherSearchProps {
    onSearch: (location: string) => Promise<void>;
}

interface ValidationState {
    text: string; // the error message
    isDirty: boolean; // has the field been interacted with
    isTouched: boolean; // has the field lost focus
}

function WeatherSearch({ onSearch }: WeatherSearchProps) {
    const [searchTerm, setSearchTerm] = useState("");
    // const [searchErrors, setSearchErrors] = useState({ text: "" });
    const [validation, setValidation] = useState<ValidationState>({
        text: "",
        isDirty: false,
        isTouched: false,
    });

    // Validation requirements
    const validateField = (name: string, value: string) => {
        const trimmedValue = value.trim();

        if (!trimmedValue) {
            return "Location is required.";
        }
        if (trimmedValue.length < 3) {
            return "Location must be at least 3 characters long.";
        }
        if (trimmedValue.length > 30) {
            return "Location cannot exceed 30 characters.";
        }

        const specialCharsRegex = /[!@#$%^&*(),.?":{}|<>]/;
        if (specialCharsRegex.test(trimmedValue)) {
            return "Location cannot contain any special characters.";
        }

        if (/\d/.test(trimmedValue)) {
            return "Location cannot contain numbers.";
        }

        return "";
    };

    // Handling input changes with validation
    function handleInputChange(e: React.ChangeEvent<HTMLInputElement>) {
        const { name, value } = e.target;
        setSearchTerm(value);

        setValidation((prev) => ({
            ...prev,
            isDirty: true,
            text: validateField(name, value),
        }));
    }

    function handleBlur() {
        setValidation((prev) => ({
            ...prev,
            isTouched: true,
        }));
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        console.log("Form submitted with location: ", searchTerm);

        const error = validateField("text", searchTerm);
        if (error) {
            setValidation((prev) => ({
                ...prev,
                text: error,
                isTouched: true,
            }));
            return;
        }

        await onSearch(searchTerm.trim());
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
                    <div className="relative">
                        <input
                            type="text"
                            name="text"
                            value={searchTerm}
                            // To change this when we add validation ***
                            // onChange={(e) => setSearchTerm(e.target.value)}
                            onChange={handleInputChange}
                            onBlur={handleBlur}
                            placeholder="Enter city name..."
                            className={`w-full px-4 py-3 pr-12 border rounded-lg 
                                    focus:outline-none focus:ring-2
                                    ${
                                        validation.text && validation.isTouched
                                            ? "border-red-500 focus:ring-red-500"
                                            : "border-gray-200 focus:ring-blue-500"
                                    }`}
                        />

                        {/* Clear button */}
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

                    {/* Validation text */}
                    {validation.text && validation.isTouched && (
                        <p className="absolute -bottom-6 left-1 text-sm text-red-500">
                            {validation.text}
                        </p>
                    )}
                </div>

                <div className="mt-10 flex justify-center">
                    <button
                        type="submit"
                        disabled={!!validation.text}
                        className={`px-6 py-3 rounded-lg font-medium
                        ${
                            !!validation.text
                                ? "bg-gray-400 cursor-not-allowed"
                                : "bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700"
                        }`}
                    >
                        Search Weather
                    </button>
                </div>
            </form>
        </div>
    );
}

export default WeatherSearch;
