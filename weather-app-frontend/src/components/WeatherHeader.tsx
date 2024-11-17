function WeatherHeader(): JSX.Element {

    return(
        <div className="text-center py-8 bg-gradient-to-r from-blue-500 to-blue-600">
            <h1 className="text-4xl font-bold text-white mb-2">Weather App</h1>
            <p className="text-blue-100">Check current weather conditions worldwide</p>
        </div>
    );
}

export default WeatherHeader;
