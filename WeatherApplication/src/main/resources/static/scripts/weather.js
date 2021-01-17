const api = {
    urlCitySuffix: "citydetails",
    urlRemoveAllCitiesSuffix: "removeallcities",
    urlCallForSuggestions: "citysearch"
}

// This function returns the path of the URL from the application context until the current page string
function getAppPath() {
    var pathArray = window.location.pathname.split('/');
    var appPath = "/";
    for(var i=1; i<pathArray.length-1; i++) {
        appPath += pathArray[i] + "/";
    }
    return appPath;
}

// Obtaining the URL until the application context, including '/'
var baseURL = window.location.origin + getAppPath();

// This function creates a box and inserts it into the DOM
function updateDOM(data) {

    // Storing a reference to the message to be printed
    var $messageRef = $('#message');

    // Attributes of the data of the entered city
    var cityName = data.cityName;
    var cityId = data.cityId;
    var temperature = data.temperature.toFixed(1);
    var description = data.description;
    var cityIcon = data.icon;

    // Storing the number of times the city's weather was added into the DOM
    var cityFrequency = $('#' + cityId.toString()).length;

    // If the city's weather is already in the DOM, print a message and return
    if (cityFrequency != 0) {

        // Printing a message
        $messageRef.text('This city was already chosen.');
        return;
    }

    // Capitalizing the first character
    description = description[0].toUpperCase() + description.slice(1);

    var $insertContent = $(
        '<span class="weatherBox">' +
            '<header id="weatherBoxHeader">' +
                '<div id="nameOfTheCity">' + cityName + '</div>' +
                '<input type="button" id="closeButton" value="x">' +
            '</header>' +

            '<div class="middle">' +
                '<div class="imageHolder">' +
                    '<img class="image" src=images/' + cityIcon + '.png alt="icon" id="icon">' +
                '</div>' +

                '<div class="temperatureValue">' +
                    '<h1 id="number">' + temperature + ' <sup>o</sup>C</h1>' +
                '</div>' +
            '</div>' +
            '<footer id="weatherBoxFooter">' + description + '</footer>' +
        '</span>'
    );

    $('div#weatherBoxes').append($insertContent);
    $($insertContent).attr('id', cityId);
    $($insertContent).attr('cityname', $('#chooseCity').val());

    // Printing a message that the city has successfully been added
    $messageRef.text('The entered city has been added.');
}

// This function calls the weather API on the web to get details of the weather of the selected city
function callWeatherAPI(cityId) {

    // The URL with which the weather api is called
    var URL = baseURL + api.urlCitySuffix;

    // Calling the weather api
    $.post(URL, "cityid=" + cityId, function (data) {
        updateDOM(data);
    });
}

// This function removes the weather box from the DOM
function removeWeatherBox(event) {

    // The URL with which the weather api is called
    var URL = baseURL + api.urlCitySuffix;

    // Storing a reference to the weather box which needs to be deleted
    var $weatherBox = $(this).parent().parent();

    var $cityId = $weatherBox.attr('id');

    // Calling the API to delete a weather box
    $.ajax({
        url: URL,
        type: 'DELETE',
        data: "cityid=" + $cityId,
        success: function (result) {
            // Removing the appropriate weather box
            $weatherBox.remove();

            // Printing a message that the selected weather box has been deleted
            $('#message').text('The selected city has been removed.');
        }
    });
}

// This function removes all the city boxes from the DOM
function removeAllCities(event) {

    // The url to be called
    var URL = baseURL + api.urlRemoveAllCitiesSuffix;

    $.ajax({
        url: URL,
        type: 'DELETE',
        success: function (result) {
            // Each weather box needs to be removed
            $('.weatherBox').each(function () {
                $(this).remove();
            });

            // Printing a message that all the weather boxes have been removed
            $('#message').text('All the cities have been removed.');
        }
    });
}

// This function calls for suggestions of the possible city names
function callForSuggestions() {

    // This is the URL to call
    var URL = baseURL + api.urlCallForSuggestions;

    // Storing the text entered until now
    var $cityText = $('#chooseCity').val();

    // Storing the data returned into list of strings
    var $cityNamesStrings = [];

    // Making a get request to the controller for the city suggestions
    $.ajax({
        url: URL,
        type: 'GET',
        data: "cityText=" + $cityText,
        success: function(result){
            // The result obtained is a list of objects with various attributes

            // Converting the information to a string to be displayed as a suggestion
            for(var i = 0; i < result.length; ++i){

                // Converting the data into a single string

                var $value = result[i].cityName;

                var $label = $value +  ', ' + result[i].countryCode + ' (' + result[i].latitude
                    + ', ' + result[i].longitude + ')';

                var $cityId = result[i].cityId;

                // Appending the string to the variable to be returned
                $cityNamesStrings.push({label: $label, value: $value, cityId: $cityId});
            }
        }
    });

    // Returning the suggestions which need to be displayed
    return $cityNamesStrings;
}

// Enabling prompting of cities
$('#chooseCity').on('keyup', function(){

    var $cityNamesStrings = callForSuggestions();
    console.log($cityNamesStrings);
    
    // Enabling the autocomplete service on the search box
    $('#chooseCity').autocomplete(
        {
            source: $cityNamesStrings,

            // The first suggestions will automatically be hovered upon
            autoFocus: true,

            // If an autocomplete suggestion is selected
            select: function(event, ui){

                // Obtaining the cityId of the selected city
                var cityId = ui.item.cityId;

                // Calling the function to put a post request 
                callWeatherAPI(cityId);
            }
        }
    );
});

// On clicking the close button, the weather box has to be removed
// Note that the weather box is a dynamically-added entity in the DOM
$('#weatherBoxes').on('click', '.weatherBox > #weatherBoxHeader > #closeButton', removeWeatherBox);

// On clicking the option to remove all the cities, all the city boxes need to be removed from the DOM
$('#removeOption').on('click', removeAllCities);