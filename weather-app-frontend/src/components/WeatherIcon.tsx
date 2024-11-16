interface WeatherIconProps {
    icon: string
}

function WeatherIcon({ icon }: WeatherIconProps) {


    return(
        <div>
            <p>{icon}</p>
        </div>
    );
}

export default WeatherIcon;
