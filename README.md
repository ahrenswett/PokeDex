# Samaritan Technical Challenge
## Task: Create a Pokédex like app. 
This app should display a list of Pokémon and allow users to get details about them and save them to a list this could be saved in `Local Storage` or a `Database`. 

### The app consist of 4 main screens:
1. Homepage: Main Concepts: Grid UI, Pagination, Networking and Navigate to Detail page & Captured List
      
      The homepage acts as as the landing/main page for the app. Shows a list of Pokemon in a grid format. If a user selects one of the items, they are taken to the detail page of that item. Scrolling list loads more items. Poké ball button takes the user to the list of captured Pokemon.

2. Pokemon detail page

* [ ] Allows the user to see more information regarding the Pokemon on this page. 
* [ ] This page has one action button that takes the user to a capture Pokemon page.
* [ ] After the user has performed the action, this page updates to reflect the action every time the user opens this specific item’s detail page. You should be able to get this saved information from the local storage (More details on the action section)

3. Capture Pokemon page:

     This is a page where the user can capture the Pokemon. It should contain a few textfields that are either required or optional, and a button that can only be selected when all required fields are filled. Once the button is selected, the data should be stored in a local storage. Here are the list of textfields (more details in design file):
    * Nickname (Optional String)
    * Captured Date (Required Date)
    * How you want to capture this date is up to you. Date selector, automatically filled with today’s date, etc..
    * Capture Level (Required Int)

     Main Concepts: Textfields, action button, & local persistent



Data

Data Saved:

{
	"name": "bulbasaur",
	"nickname": "optionalString" // user input
	"captured_date": 2022-05-19T11:03:06.800-07:00 //auto populate
	"captured_level": 5 //random # generator
	"pokemon_detail": detailData 
}
4. List of Captured Pokemon page (Optional for Hackers/Interns)


