/* File: MainActivity.java
 * Author: Stanley Pieda
 * Date: March 2018
 */

package com.algonquincollege.assignment4androidclient_rest;

/**
 * Created by Philip Deck, Mahad Osman, Adam Tremblett and Ivan Zubaryev on 2018-03-13.
 * FishStick Entity
 *  Has the same attributes as the Fishstick in the database
 */

public class FishStick {
    public final String id;
    public final String recordNumber;
    public final String omega;
    public final String lambda;
    public final String uuid;

    /**
     * Constructor
     *
     * @param id
     * @param recordNumber
     * @param omega
     * @param lambda
     * @param uuid
     */
    public FishStick(String id, String recordNumber, String omega, String lambda, String uuid){
        this.id = id;
        this.recordNumber = recordNumber;
        this.omega = omega;
        this.lambda = lambda;
        this.uuid = uuid;
    }
}
