package com.example.tests.flightreservation.model;
/*selcted record while creating a new class
after java 17, we don't have to explicitly specify the setters and getter methods.
Using Record, we can pass all our variables as part of the constructor and it will automatically
create the getters and setters for us.
*/

public record FlightReservationTestData(String firstname, String lastname, String email,
                                        String password, String street, String city,
                                        String zip, String passengersCount, String expectedPrice) {
    /*
    these should be the exact variables as we have declared in the json file.
     If we have any mismatch, it will throw an error while reading the json file.
     */


}
