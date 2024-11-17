import "./App.css";
import WeatherDashboard from "./components/WeatherDashboard";
import WeatherHeader from "./components/WeatherHeader";

function App(): JSX.Element {

    return (
        <div className="min-h-screen bg-gray-50">
            <WeatherHeader />
            <main className="container mx-auto px-4 py-8">
                <WeatherDashboard />
            </main>
        </div>
    );
}

export default App;
