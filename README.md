# thetimessearch
This is CodePath Week 2 Project to search NY Times API.

![Times Search Gif](/thetimesfinal.gif "An animated image showing use.")

## This is my Project #2 for CodePath Android Camp

The purpose of this app is to take advantage of the [New York Times Search ](http://developer.nytimes.com/). The application takes advantage of search filters along with AsyncHttpRequestHandler, a RecyclerView which uses a Grid Layout.  

## User Stories

* [ ] User can search for news article by submitting a query.
* [ ] User can click on "Filter" which has a Settings icon to select **advanced search options**
* [ ] Consequent searches will apply the same filter
* [ ] When a new search is initialized, the application will restart the adapter with empty results
* [ ] User can configure advanced search filters such as: Arts, Fashion & Style, Sports
* [ ] User can tap on the search result to display a full text of article  
* [ ] User can scroll down and open infinite results

## Optional Stories

* [ ] User can enter a search query in the action bar instead of using an EditText field.

GIF created with [EZGif](http://ezgif.com/video-to-gif/db6e243d4a.mp4).

## Notes

The biggest challenge was implementing a data passing from one activity to another. The basic walkthrough gets you to a functional API call, but you have to really set aside the time to know how you're going to organize your data from one view to another.  

## Time required to complete

Overall, I have spent about 9.5 hours on this project. I feel that the good amount of time to commit would've been 12 to 15 hours.  

## Open Source Libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android