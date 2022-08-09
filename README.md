# Samaritan Technical Challenge
App built using MVVM architecture and utilizes Jetpack Compose for UI, Kotlin Coroutines for concurrency, Kotlin Serialization , K-tor for HTTP requests and Dagger Hilt for dependency injection.
[Task description](https://arridoarfiadi.notion.site/Samaritan-Mobile-Take-Home-Assignment-c2cd5c8ca99d4fc7994a4e2e3f422957)


### Link to App APK
Install Apk by visiting the below link on an android phone or computer with the newest version of Android studio installed.

On Android studio open the emulator then drag and drop the file on to the emulator this will install the app

On Android phone you may have to enable third party apps in settings.

* **[PokeDex APK](https://github.com/ahrenswett/Samaritan/blob/main/PokeDex.apk)**

* [Dependency Documentation links](#Dependency-documentation-links)

## Task: Create a Pokédex like app.

**User Requirements:** App should display a list of Pokémon and allow users to get details about them and save them. The saved data should be available to the user in a seperate "Captured Pokemon" screen

### The app consist of 4 main screens:
1. Homepage

	**The homepage acts as as the landing/main page for the app.**
	* [x] Shows a list of Pokemon in a grid format.
	* [x] If a user selects one of the items, they are taken to the detail page of that item (currently buggy and does not show anything)
	* [x] Scrolling list loads more items.
	* [ ] Floating action Poké ball button takes the user to the list of captured Pokemon

2. Pokemon detail page
	**Allows the user to see more information regarding the Pokemon on this page.**
	* [x] Shows more detail about the Pokemon
	* [ ] This page has one action button. It takes the user to a "capture Pokemon page".
	* [ ] After the user has performed the action, this page updates to reflect the action every time the user opens this specific item’s detail page. You should be able to get this saved information from the local storage (More details on the action section)


3. Capture Pokemon page: Main Concepts: Textfields, action button, & local persistent

	**This is a page where the user can capture the Pokemon.**
	* [ ] Contains a few textfields that are either required or optional
	* [ ] A button that can only be selected when all required fields are filled.
	* [ ] Once the button is selected, the data is stored in a local storage.
	* [ ] TextFields below are implemented:
		* Nickname (Optional String)
		* Captured Date (Required Date)
		* How you want to capture this date is up to you. Date selector, automatically filled with today’s date, etc..

4. Captured List Page

	**This is a list view of all the Pokemon the user has captured.**
	* [ ] Gets Saved actions from the local storage.
	* [ ] Displays information as list.
	* [ ] Displays Picture: localStorage.currentPokemon.pokemon_detail.sprites.other.official-artwork.front_default
	* [ ] Displays Nickname: localStorage.currentPokemon.nickname or localStorage.currentPokemon.name
	* [ ] Displays Captured on: localStorage.currentPokemon.capturedDate
	* [ ] Displays Captured Level: localStorage.currentPokemon.capturedLevel


# Dependency documentation links
* [Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
* [Ktor](https://ktor.io/docs/welcome.html)
* [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html)
* [Dagger Hilt](https://dagger.dev/hilt/)
* [Coil](https://coil-kt.github.io/coil/)
