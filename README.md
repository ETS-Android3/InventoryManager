## InventoryManager
### By Guilherme Pereira

## Requirements & Goals
The requirements for the inventory app was that it would be able to execute basic CRUD applications. Additionally, fundamental app designs were needed such as a login screen
and menu. A grid had to be implemented to show the data stored by the user. For this application, I opted to use a RecyclerView to making the application more appealing. Finally,
an SMS permission had to be grnated for the user to receive notifications about going low in amount or being completely depleted.

The goal of this application was to help users keep track of their items by adding them to an SQLite database. This app allows users to create their items and give it a name
and amount. Users may also change the amount value or update the name of the item. Additionally, the RecyclerView in the Menu will show all the current items saved in the 
database, and allows the user to increment or decrement the count, as well as delete the item from the list.

## Screens & Activities
The necessary screens for this application included the login, menu, add, and update activity. Each screen served a role to meet the need of a user who wants to keep track of
their items. The UI is simple and is consistent to a theme as well. The RecyclerView in particular was used to help the user sort through the items more easily had more been 
added. I believe my design is successful as it is simple enough to follow and gets straight to the point for users.

## Coding the App
There were many resources and lessons used when coding this app, but the most important ones came from notes taken from zyBooks and learning from YouTube developers. Having 
experience with building simpler demos allowed me to understand how the app should work better in the end. While notes did help, I believe creating the demos for specific
tasks, such as a login page or adding an item activity, allowed me to have a stronger understanding of how the code should be formatted and written.

## Testing Code
To test the code written for this application, I have decided to put myself in the shoes of a client and image what I would want to do had I this app. I have gone through the
basic tests such as verifying user identity at the login page, ensuring that the items being incremented and decremented within the RecyclerView are also being updated in the
database, and ensuring that the update item activity worked as intended by verifying the existence of an existing item. Handling these errors was not difficult, but there may
have been more I did not test for that were outside of my scope. While I am somewhat familiar with using JUnit testing, Android Studio did not seem to have any sort of JUnit 
tests I could generate. Regardless, the code has passed the basic user needs and I believe this makes it work for what it was designed to be.

## Innovation & Skill
One aspect of this application that comes to me as innovative would be in regards to styling the rows within the RecylerView. As mentioned plenty of times in this README, I 
most likely spent the most time working with it as it initially gave me a lot of problems. After successfully creating a working demo, I have decided to make it more user
friendly by adding buttons with special functionalities on them. Ensuring that the buttons changed both the row's TextView and it's respective amount in the database is not
necessarilly innovative to me, but worth mentioning. 

I gained a lot of skills developing this application as I have never had any experience with app development beforehand. Despite the difficulties, I was able to work hard
and build a successful app that I am proud to post here and submit to my instructors.
