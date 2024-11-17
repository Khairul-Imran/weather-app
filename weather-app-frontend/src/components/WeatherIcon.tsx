interface WeatherIconProps {
    icon: string;
}

function WeatherIcon({ icon }: WeatherIconProps) {
    return (
        <div className="flex items-center justify-center w-24 h-24">
            <img
                src={icon.startsWith("//") ? `https:${icon}` : icon}
                alt="Weather condition"
                className="w-full h-full object-contain"
            />
        </div>
    );
}

export default WeatherIcon;
