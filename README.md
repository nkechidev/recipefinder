# Recipe Finder

Recipe Finder is an Android app that helps you discover meals and recipes. Browse by category, search by name, get a random meal, or save favorites for later.

## Video

[▶️ Watch the demo video](videos/demo.mp4)

<!-- For local Markdown viewers, you can use the following HTML tag: -->
<!--
<video src="videos/demo.mp4" controls width="400">
  Your browser does not support the video tag.
</video>
-->

## What the app does

- **Browse** — Explore meals by category (e.g. Beef, Chicken, Desserts). Tap a category to see its meals, then open any meal for full details and instructions.
- **Search** — Find meals by name so you can look up a specific dish or ingredient.
- **Random** — Get a random meal suggestion when you’re not sure what to cook.
- **Tonight** — Use “Tonight’s Pick” for a curated suggestion for dinner.
- **Favorites** — Save meals you like and access them from the Favorites tab.

Meal details include ingredients, measurements, and step-by-step instructions so you can cook directly from the app.

## Tech

- **Kotlin** + **Jetpack Compose**
- **TheMealDB** API for meal data
- **Room** for local favorites
- **Retrofit** + **Coil** for networking and images

## Requirements

- Android 8.0 (API 26) or higher

## Building and running

1. Clone the repo.
2. Open the project in Android Studio.
3. Sync Gradle and run on a device or emulator.

No API key is required; the app uses the public [TheMealDB](https://www.themealdb.com/) API.
