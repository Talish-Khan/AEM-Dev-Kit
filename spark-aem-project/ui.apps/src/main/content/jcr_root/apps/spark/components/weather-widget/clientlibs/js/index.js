(function () {
    function initWeatherWidget(widget) {
        const city = widget.getAttribute("data-city");
        const unit = widget.getAttribute("data-unit");

        if (!city || !unit) {
            console.error("Missing city or unit on widget");
            return;
        }

        console.log("Fetching weather for:", city, unit);

        fetch(`/bin/weather?city=${encodeURIComponent(city)}&unit=${encodeURIComponent(unit)}`)
            .then((res) => {
                if (!res.ok) throw new Error("Failed to fetch weather");
                return res.json();
            })
            .then((data) => {
                widget.querySelector(".weather-loader").classList.add("hidden");
                widget.querySelector(".weather-content").classList.remove("hidden");

                widget.querySelector(".weather-location").textContent = data.city;
                widget.querySelector(".weather-temp").textContent = `${data.temp}°${unit === "metric" ? "C" : "F"}`;
                widget.querySelector(".weather-description").textContent = data.description;
                widget.querySelector(".weather-icon").src = `https://openweathermap.org/img/wn/${data.icon}@2x.png`;
            })
            .catch((err) => {
                console.error("Weather API error:", err);
                widget.querySelector(".weather-loader").textContent = "Failed to load weather";
            });
    }

    // Run after page load
    window.addEventListener("load", function () {
        document.querySelectorAll(".weather-widget").forEach(initWeatherWidget);
    });
})();
