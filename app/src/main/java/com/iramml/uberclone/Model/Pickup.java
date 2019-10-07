package com.iramml.uberclone.Model;

import com.google.android.gms.location.places.Place;
public class Pickup {
    //    Place pickUp;
//    Place dropOff;
    String ID;
    Token token;
    Double pickLat, pickLng, dropLat, dropLng;

    public Pickup() {
    }

    public Pickup(Place pickUp, Place dropOff, String ID, Token token, Double pickLat, Double pickLng, Double dropLat, Double dropLng) {
//        this.pickUp = pickUp;
//        this.dropOff = dropOff;
        this.ID = ID;
        this.token = token;
        this.pickLat = pickLat;
        this.pickLng = pickLng;
        this.dropLat = dropLat;
        this.dropLng = dropLng;
    }

//    public Place getDropOffPlace() {
//        return dropOff;
//    }
//
//    public void setDropOffPlace(Place dropOff) {
//        this.dropOff = dropOff;
//    }
//
//
//    public Place getPickUpPlace() {
//        return pickUp;
//    }
//
//    public void setPickUpPlace(Place pickUp) {
//        this.pickUp = pickUp;
//    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Double coord(int i) {
        if (i == 0) {
            return pickLat;
        } else if (i == 1) {
            return pickLng;
        } else if (i == 2) {
            return dropLat;
        } else if (i == 3) {
            return dropLng;
        }
        return -1.00;
    }
}