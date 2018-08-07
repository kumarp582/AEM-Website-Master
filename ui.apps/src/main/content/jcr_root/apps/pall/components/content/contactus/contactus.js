"use strict";
use(function () {
    var contactus = {};


    contactus.countryItemsData = granite.resource.properties["countries"];
	contactus.countryItems = [];

	contactus.timingData = granite.resource.properties["timing"];
	contactus.timingItems = [];

    if (contactus.countryItemsData) {
        for (var i = 0; i < contactus.countryItemsData.length; i++) {
            var countryData = JSON.parse(contactus.countryItemsData[i]);
            if (countryData.countryname && countryData.countrycode) {
            	contactus.countryItems.push(countryData);
            }

        }
    }

	if (contactus.timingData) {
        for (var i = 0; i < contactus.timingData.length; i++) {
            var timing = JSON.parse(contactus.timingData[i]);
            if (timing.timetocall ) {
            	contactus.timingItems.push(timing);
            }

        }
    }




    return contactus;
});