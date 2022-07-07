# Samaritan Technical Challenge
App built using MVVM architecture and utilizes Jetpack Compose for UI, Kotlin Coroutiens for concurency, Kotlin Serialization , and K-tor for HTTP requests.


## Task: Create a Pokédex like app. 

**User Requirements:** App should display a list of Pokémon and allow users to get details about them and save them. The saved data should be available to the user in a seperate "Captured Pokemon" screen

### The app consist of 4 main screens:
1. Homepage

	**The homepage acts as as the landing/main page for the app.**
	* [ ] Shows a list of Pokemon in a grid format.
	* [ ] If a user selects one of the items, they are taken to the detail page of that item
	* [ ] Scrolling list loads more items.
	* [ ] Floating action Poké ball button takes the user to the list of captured Pokemon

2. Pokemon detail page

	**Allows the user to see more information regarding the Pokemon on this page.**
	* [ ] This page has one action button. It takes the user to a "capture Pokemon page".
	* [ ] After the user has performed the action, this page updates to reflect the action every time the user opens this specific item’s detail page. You should be able to get this saved information from the local storage (More details on the action section)
	* [ ] Shows a list of Pokemon in a grid format.
	* [ ] If a user selects one of the items, they are taken to the detail page of that item.
	* [ ] Scrolling list loads more items.
	* [ ] Floating action Poké ball button takes the user to the list of captured Pokemon.

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

