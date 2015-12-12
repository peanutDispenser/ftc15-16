/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */
public class Teleop0_1 extends OpMode {

    /*
     * Note: the configuration of the servos is such that
     * as the arm servo approaches 0, the arm position moves up (away from the floor).
     * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
     */
    // TETRIX VALUES.
    final float MAXDRIVEPOWER = 1.0f;
    final float MAXSPECIALPOWER = 0.7f;
    final float PEOPLECHANGE = 0.1f;
    final float TRIGGERTHRESHOLD = .65f;
    final float WALLCHANGE = 0.1f;

    //boolean sMotors = false;

    DcMotor motorRU;    // Right front
    DcMotor motorLU;    // Right back
    DcMotor motorLD;    // Left back
    DcMotor motorRD;    // Left front
    DcMotor extend1;
    DcMotor extend2;
    DcMotor turn;

    Servo wall;
    float wallPlace;

    Servo people;
    float peoplePlace;
    //DcMotor arm;		// Arm

    /**
     * Constructor
     */
    public Teleop0_1() {

    }

    @Override
    public void init() {
        /*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */

        motorRU = hardwareMap.dcMotor.get("motorRU");
        motorLU = hardwareMap.dcMotor.get("motorLU");
        motorRD = hardwareMap.dcMotor.get("motorRD");
        motorLD = hardwareMap.dcMotor.get("motorLD");
        extend1 = hardwareMap.dcMotor.get("extend1");
        extend2 = hardwareMap.dcMotor.get("extend2");
        turn = hardwareMap.dcMotor.get("turn");
        wall = hardwareMap.servo.get("wall");
        people = hardwareMap.servo.get("people");

        //arm = hardwareMap.dcMotor.get("arm");

        motorRU.setDirection(DcMotor.Direction.REVERSE);
        motorRD.setDirection(DcMotor.Direction.REVERSE);
    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {

		/*
		 * Gamepad 1
		 */

        // tank drive
        // note that if y equal -1 then joystick is pushed all of the way forward.
        float left = gamepad1.left_stick_y;
        float right = gamepad1.right_stick_y;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -MAXDRIVEPOWER, MAXDRIVEPOWER);
        left = Range.clip(left, -MAXDRIVEPOWER, MAXDRIVEPOWER);

        float leftTrigger = gamepad1.left_trigger;
        float rightTrigger = gamepad1.right_trigger;

        if (leftTrigger > TRIGGERTHRESHOLD) {
            extend1.setPower(MAXSPECIALPOWER);
            extend2.setPower(-MAXSPECIALPOWER);
        } else if (rightTrigger > TRIGGERTHRESHOLD) {
            extend1.setPower(-MAXSPECIALPOWER);
            extend2.setPower(MAXSPECIALPOWER);
        } else {
            extend1.setPower(0);
            extend2.setPower(0);
        }

        if (gamepad1.left_bumper) {
            turn.setPower(MAXSPECIALPOWER);
        } else if (gamepad1.right_bumper) {
            turn.setPower(-MAXSPECIALPOWER);
        } else {
            turn.setPower(0);
        }

        if (gamepad1.a && peoplePlace < .9) {
            peoplePlace += PEOPLECHANGE;
        } else if (gamepad1.b && peoplePlace > .2) {
            peoplePlace -= PEOPLECHANGE;
        }

        if (gamepad1.x && wallPlace < 0.9) {
            wallPlace += WALLCHANGE;
        } else if (gamepad1.y && wallPlace > 0.2) {
            wallPlace -= WALLCHANGE;
        }
        // write the values to the motors
        motorRU.setPower(right);
        motorLU.setPower(left);
        motorRD.setPower(right);
        motorLD.setPower(left);

        wall.setPosition(wallPlace);
        people.setPosition(peoplePlace);

//		if (gamepad1.start) { //Must change motor controller mode to read, then back to read
//			if (motorLD.getDirection() == DcMotor.Direction.REVERSE)
//				motorLD.setDirection(DcMotor.Direction.FORWARD);
//			else
//				motorLD.setDirection(DcMotor.Direction.REVERSE);
//
//
//			if (motorLU.getDirection() == DcMotor.Direction.REVERSE)
//				motorLU.setDirection(DcMotor.Direction.FORWARD);
//			else
//				motorLU.setDirection(DcMotor.Direction.REVERSE);
//
//			if (motorRD.getDirection() == DcMotor.Direction.REVERSE)
//				motorRD.setDirection(DcMotor.Direction.FORWARD);
//			else
//				motorRD.setDirection(DcMotor.Direction.REVERSE);
//
//			if (motorRU.getDirection() == DcMotor.Direction.REVERSE)
//				motorRU.setDirection(DcMotor.Direction.FORWARD);
//			else
//				motorRU.setDirection(DcMotor.Direction.REVERSE);
//		}

		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, thenoh my godtPower() method
		 * will return a null value. The legacy NXT-compaoh my godotor controllers
		 * are currently write only.
		 */

        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("a", "a: " + String.format("%.2b", gamepad1.a));
        telemetry.addData("b", "b: " + String.format("%.2b", gamepad1.b));
        telemetry.addData("x", "y: " + String.format("%.2b", gamepad1.x));
        telemetry.addData("y", "x: " + String.format("%.2b", gamepad1.y));
        telemetry.addData("wallplace", "wallplace: " + String.format("%.2f", wallPlace));
        telemetry.addData("peopleplace", "peopleplace: " + String.format("%.2f", peoplePlace));


        //telemetry.addData("special motors", "special motors: " + String.valueOf(sMotors));


    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
u	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }
}
