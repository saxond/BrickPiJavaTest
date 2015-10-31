/*
 *  Copyright ErgoTech Systems, Inc 2014
 *
 * This file is made available online through a Creative Commons Attribution-ShareAlike 3.0  license.
 * (http://creativecommons.org/licenses/by-sa/3.0/)
 *
 *  This is a library of functions to test the BrickPI.
 */

package com.saxon.brickpi.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ergotech.brickpi.BrickPi;
import com.ergotech.brickpi.BrickPiCommunications;
import com.ergotech.brickpi.BrickPiTests;
import com.ergotech.brickpi.RemoteBrickPi;
import com.ergotech.brickpi.motion.Motor;
import com.ergotech.brickpi.motion.Motor.Direction;
import com.ergotech.brickpi.sensors.RawSensor;
import com.ergotech.brickpi.sensors.TouchSensor;
import com.ergotech.brickpi.sensors.UltraSonicSensor;
import com.ergotech.brickpi.sensors.UltraSonicSensorSS;

public class Main {

    private final static boolean REMOTE = false;
    
    public static void main(String[] args) throws Exception {
        BrickPiCommunications brickPi;
        if (REMOTE) {
            brickPi = new RemoteBrickPi("192.168.1.44");
        } else {            
            brickPi = new BrickPi();
        }        
        
        Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        
        // add touch sensors to all the ports.
        brickPi.setSensor(new RawSensor(), 3);
        brickPi.setSensor(new UltraSonicSensor(), 0);
        brickPi.setSensor(new RawSensor(), 2);
        brickPi.setSensor(new TouchSensor(), 1);
        try {
            // configure the sensors
            brickPi.setupSensors();
        } catch (IOException ex) {
            Logger.getLogger(BrickPiTests.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int counter = 0; counter < 5; counter++) {
            System.out.println("Update Values");
            try {
                // get the updated values.
                Thread.sleep(200); // wait for the values to be read....
            } catch (InterruptedException ex) {
                Logger.getLogger(BrickPiTests.class.getName()).log(Level.SEVERE, null, ex);
            }
            // here're the values
            System.out.println("Sensors: " + brickPi.getSensor(0).getValue() + " " + brickPi.getSensor(1).getValue() + " " + brickPi.getSensor(2).getValue() + " " + brickPi.getSensor(3).getValue());
        }

        brickPi.setSensor(new UltraSonicSensorSS(), 1);
        try {
            // configure the sensors
            brickPi.setupSensors();
        } catch (IOException ex) {
            Logger.getLogger(BrickPiTests.class.getName()).log(Level.SEVERE, null, ex);
        }

        //System.exit(0);

        Motor motor = new Motor();
        motor.setCommandedOutput(0);
        motor.setEnabled(true);
        motor.resetEncoder();
        
        brickPi.setMotor(motor, 0);
        
        for (int i = 0; i < 1000; i++) {
            motor.rotate(i, 50);
        }
        
        motor.setCommandedOutput(100);

        brickPi.getMotor(0).setDirection(Direction.CLOCKWISE);
        for (int counter = 0; counter < 50; counter++) {
            
            try {
                System.out.println("Forward Motors: Speed " + brickPi.getMotor(0).getCurrentSpeed() + " encoder "
                        + brickPi.getMotor(0).getCurrentEncoderValue() + " Time " + System.currentTimeMillis() % 10000);
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                // ignore
            }
        }
//        motor.setCommandedOutput(-25);
//        for (int counter = 0; counter < 50; counter++) {
//            try {
//                Thread.sleep(200);
//                System.out.println("Reverse Motors: Speed " + brickPi.getMotor(0).getCurrentSpeed() + " encoder " + brickPi.getMotor(0).getCurrentEncoderValue());
//            } catch (InterruptedException ex) {
//                // ignore
//            }
//        }
//        motor.setCommandedOutput(0);
//        motor.setEnabled(false);
//        try {
//            // get the updated values.
//            Thread.sleep(200); // wait for the values to be read....
//        } catch (InterruptedException ex) {
//            Logger.getLogger(BrickPiTests.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        // there's a problem here since the code will exit before the rotation is complete...
        try {
            // get the updated values.
            Thread.sleep(5000); // wait for the values to be read....
        } catch (InterruptedException ex) {
            Logger.getLogger(BrickPiTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
