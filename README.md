# Petro Papi ⛽

**Real-time fuel and EV insights from tu amigo "Petro Papi"**

Petro Papi is a multi‑component project consisting of an Android mobile application and a lightweight Node.js backend service. The mobile app uses the GasBuddy GraphQL API to display up‑to‑date gasoline prices and station information on an interactive Google Map, while the backend service provides a simple API for community reviews, station occupancy, schedules and demo authentication. Together, these components help users find nearby gas stations, view price data and share feedback about their experience.

📱 Android app

The Android application is built with Kotlin/Java on top of the Android SDK. It integrates Google Maps to show the user’s current location and nearby stations and uses Retrofit to call the GasBuddy API. Data is cached locally with Room so that previously fetched stations are still available when the network is unavailable. Key parts of the app include:

Interactive Map: The main screen displays a Google Map centered on the user’s location. As you pan the map, a “Search this area” button appears, allowing you to refresh the results for the currently visible area.
Station list: Below the map is a RecyclerView that lists the stations returned by GasBuddy. Each list item shows the station’s name, address, available fuel prices and hours, along with a brand image if one is provided. Selecting a station centers the map on that location.
Location & permissions: The app requests location permission on startup and falls back to default coordinates if permission is denied. It checks Google Play Services availability before enabling map features.
Data layer: The StationRepository class wraps calls to the GasBuddy GraphQL API and caches results in a Room database. When the API fails, cached stations from recent queries are returned to provide a graceful offline experience【17†L61-L80】【19†L90-L102】.
Models & networking: The models/gasbuddy package contains POJOs such as Station, PriceReport and Address that mirror the GraphQL schema. Network requests are made via Retrofit with a custom GasBuddyApiService and GraphQLRequest classes.
Splash screen: A simple SplashActivity plays a short animation when the app launches before loading the main activity【21†L15-L33】.
🛠️ Backend service

The backend folder contains a minimal Express server that aggregates user feedback and status data for stations. It stores information in memory and exposes several endpoints:

Method & path	Description
GET /health	Returns { status: "ok" } to indicate the service is running【12†L12-L14】.
POST /reviews	Submits a rating and optional comment for a station. Requires stationId and rating; stores the review in memory【12†L16-L29】.
GET /reviews/:stationId	Retrieves all reviews for a station and calculates the average rating【12†L32-L39】.
POST /occupancy	Updates the occupancy level (e.g., “busy”, “moderate”, “empty”) for a station【12†L41-L48】.
GET /occupancy/:stationId	Returns the latest occupancy information for a station or 404 if none exists【12†L51-L58】.
POST /schedule	Saves a weekly schedule (such as opening hours) for a station【12†L60-L68】.
GET /schedule/:stationId	Fetches the stored schedule for a station【12†L70-L77】.
POST /auth/token	Generates a demo authentication token for a user. The token is a simple string with no real authentication behind it【12†L79-L85】.
GET /aggregate/:stationId	Returns reviews, occupancy, schedule and average rating combined into one JSON payload【12†L87-L101】.

To run the backend locally:

cd backend
npm install
npm start
# The service listens on port 4000 by default
📂 Project structure
Petro-Papi/
├── app/                    # Android application source
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/petropapi/
│   │   │   │   ├── MainActivity.java    # Displays map, station list and search button【17†L47-L107】
│   │   │   │   ├── SplashActivity.java  # Shows splash animation【21†L15-L33】
│   │   │   │   ├── GasStationAdapter.java # RecyclerView adapter for stations【26†L22-L39】
│   │   │   │   ├── data/                # Data layer (Room DB, repository)【19†L42-L52】
│   │   │   │   ├── models/gasbuddy/     # POJOs for GasBuddy GraphQL responses【24†L7-L24】
│   │   │   │   └── network/             # Retrofit service definitions
│   │   ├── res/              # Layouts, drawables, string resources
│   │   └── AndroidManifest.xml
│   └── build.gradle         # App‑level Gradle config
├── backend/                 # Node.js API service
│   ├── server.js            # Express server with review/occupancy/schedule endpoints【12†L1-L107】
│   └── package.json         # Defines dependencies (express) and start script【13†L4-L14】
├── gradlew, gradlew.bat     # Gradle wrapper scripts
├── gradle/                  # Gradle wrapper JAR and properties
├── build.gradle             # Project‑level Gradle configuration
└── README.md                # Project documentation (you’re reading the updated version)
🚀 Getting started
Prerequisites
Android Studio Arctic Fox (or later) – to build and run the mobile app
Android SDK with API 21 or higher
Node.js >= 14 and npm – to run the backend service
Git – to clone the repository
Google Maps API key – required to display maps; add your key to the google_maps_api.xml resource file in the Android project
Installation & running

Clone the repository:

git clone https://github.com/Eljiin1904/Petro-Papi.git
cd Petro-Papi

Backend setup (optional) – the mobile app communicates directly with GasBuddy, but you can run the backend to experiment with reviews and occupancy aggregation:

cd backend
npm install
npm start
# Service available at http://localhost:4000
Open the Android app:
Launch Android Studio.
Select “Open an existing project” and choose the Petro-Papi/app folder.
Allow Gradle to sync dependencies; if it doesn’t start automatically, select File → Sync Project with Gradle Files.
Replace the placeholder Google Maps API key in app/src/main/res/values/google_maps_api.xml with your own key.
Connect a device or start an emulator and click the Run button (green triangle) to build and launch the app.
Using the app
When the app starts it shows a splash screen, then requests location permission. Allow the permission to see nearby stations; otherwise the map centers on a default location.
Use pinch/zoom and pan to move around. After moving the map, tap “Search this area” to reload stations for the visible region【17†L159-L168】.
Tap a station in the list to view its location on the map. The list displays fuel prices (if available), opening hours and brand imagery【26†L76-L134】.
If the network is unavailable, previously cached stations will continue to appear thanks to the Room cache【19†L90-L103】.
API reference (backend)

While the mobile app does not currently call the backend, you can interact with it manually using tools like curl or Postman:

# Submit a review
curl -X POST http://localhost:4000/reviews \
     -H "Content-Type: application/json" \
     -d '{"stationId":"123", "rating":4.5, "comment":"Clean and friendly"}'

# Get reviews and average rating
curl http://localhost:4000/reviews/123

# Report occupancy
curl -X POST http://localhost:4000/occupancy \
     -H "Content-Type: application/json" \
     -d '{"stationId":"123", "level":"busy"}'

# Fetch combined station data
curl http://localhost:4000/aggregate/123
🤝 Contributing

Contributions are welcome!  To propose a feature or bug fix:

Fork this repository
Create a new branch (git checkout -b feature/MyFeature)
Commit your changes (git commit -m "Add my feature")
Push to your branch (git push origin feature/MyFeature)
Open a pull request describing your changes
📝 License

This project is licensed under the MIT License – see the LICENSE file for details.

📧 Contact

Project maintainer: @Eljiin1904

🗺️ Roadmap & future work

The current codebase provides a working Android client and a simple backend aggregator. Planned improvements include:

Support for EV charging stations and plug‑type filtering
User favourites and persistent account storage
Price history and trend charts
Push notifications for significant price drops or nearby deals
A proper authentication system for the backend and mobile client
Internationalization for multiple languages

—

Made with ❤️ to help you save money at the pump
