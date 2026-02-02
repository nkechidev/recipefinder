# Recipe Finder

Recipe Finder is an Android app that helps you discover meals and recipes. Browse by category, search by name, get a random meal, or save favorites for later.

## Video

*(Upload your video so it plays inline: edit this README on GitHub, then drag and drop your video file into the editor where this line is. GitHub will insert an embedded player and host the file.)*

**Steps (do this on GitHub):**
1. Open your repo on GitHub and click the **pencil icon** (Edit) on this README.
2. Delete the placeholder line above (or the line where you want the video).
3. **Drag and drop** your video file (e.g. `videos/demo.mp4` from your computer, or the file from your repo) into the editor.
4. GitHub will upload it and insert the video. **Commit** the change.

Your demo will then play inline in the README.

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
