function WeatherHeader(): JSX.Element {

    return(
        <header className="w-full bg-gradient-to-r from-blue-500 to-blue-600">
            <div className="container mx-auto px-4 py-8">
                <h1 className="text-4xl font-bold text-white mb-2">Weather App</h1>
                <p className="text-blue-100">Check current weather conditions worldwide</p>
            </div>
        </header>
    );
}

export default WeatherHeader;
