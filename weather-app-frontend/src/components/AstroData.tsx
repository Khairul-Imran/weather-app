import { DailyAstroData } from "../types/weatherData";

interface AstroDataProps {
    data: DailyAstroData;
}

function AstroData({ data }: AstroDataProps) {
    return (
        <div className="p-6 bg-gradient-to-br from-indigo-50 to-blue-50">
            <h3 className="text-lg font-semibold text-gray-800 mb-4">
                Sun & Moon
            </h3>
            <div className="grid grid-cols-2 gap-6">
                <div className="space-y-4">
                    <h4 className="text-sm font-medium text-gray-600">Sun</h4>
                    <div className="flex justify-between items-center">
                        <div className="flex items-center gap-2">
                            <span className="text-yellow-500">🌅</span>
                            <span className="text-sm">Rise</span>
                        </div>
                        <span className="font-medium">{data.sunriseTime}</span>
                    </div>
                    <div className="flex justify-between items-center">
                        <div className="flex items-center gap-2">
                            <span className="text-orange-500">🌇</span>
                            <span className="text-sm">Set</span>
                        </div>
                        <span className="font-medium">{data.sunsetTime}</span>
                    </div>
                </div>
                <div className="space-y-4">
                    <h4 className="text-sm font-medium text-gray-600">Moon</h4>
                    <div className="flex justify-between items-center">
                        <div className="flex items-center gap-2">
                            <span>🌙</span>
                            <span className="text-sm">Rise</span>
                        </div>
                        <span className="font-medium">{data.moonriseTime}</span>
                    </div>
                    <div className="flex justify-between items-center">
                        <div className="flex items-center gap-2">
                            <span>🌑</span>
                            <span className="text-sm">Set</span>
                        </div>
                        <span className="font-medium">{data.moonsetTime}</span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AstroData;
