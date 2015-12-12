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
public class Teleop0_3_1 extends OpMode {

    /*
     * Note: the configuration of the servos is such that
     * as the arm servo approaches 0, the arm position moves up (away from the floor).
     * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
     */
    // TETRIX VALUES.
    final float MAXDRIVEPOWER = 1.0f;
    final float MAXSPECIALPOWER = 0.7f;
    final float PEOPLECHANGE = 0.01f;
    final float TRIGGERTHRESHOLD = .65f;
    final float TILTCHANGE = 0.01f;
    final float ZIPLINECHANGE = 0.01f;
    final float WALLCHANGE = 0.01f;

    //boolean sMotors = false;

    DcMotor motorRU;    // Right front
    DcMotor motorLU;    // Right back
    DcMotor motorLD;    // Left back
    DcMotor motorRD;    // Left front
    DcMotor extend1;
    DcMotor spinHelper;

    Servo people;
    Servo tilt;
    Servo zipline;
    Servo wall;
    float peoplePlace = 0.9f;
    float tiltPlace = 0.5f;
    float ziplinePlace = 0.1f;
    float wallPlace = 0.9f;
    //DcMotor arm;		// Arm

    /**
     * Constructor
     */
    public Teleop0_3_1() {

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
        spinHelper = hardwareMap.dcMotor.get("spinHelper");

        people = hardwareMap.servo.get("people");
        tilt = hardwareMap.servo.get("tilt");
        zipline = hardwareMap.servo.get("zipline");
        wall = hardwareMap.servo.get("wall");


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



        if (gamepad2.left_trigger > TRIGGERTHRESHOLD) {//in
            extend1.setPower(MAXSPECIALPOWER);
            //spinHelper.setPower(-MAXSPECIALPOWER);
        } else if (gamepad2.right_trigger > TRIGGERTHRESHOLD) {//out
            extend1.setPower(-MAXSPECIALPOWER);
           // spinHelper.setPower(-MAXSPECIALPOWER);
        } else {
            extend1.setPower(0);
            spinHelper.setPower(0);
        }


        if (gamepad2.a && peoplePlace < 0.98) {
            peoplePlace += PEOPLECHANGE;
        } else if (gamepad2.b && peoplePlace > 0.1) {
            peoplePlace -= PEOPLECHANGE;
        }

        if (gamepad2.x && tiltPlace < 0.98) {
            tiltPlace += TILTCHANGE;
        } else if (gamepad2.y && tiltPlace > 0.1) {
            tiltPlace -= TILTCHANGE;
        }

        if (gamepad2.left_bumper && ziplinePlace < 0.98) {
            ziplinePlace += ZIPLINECHANGE;
        } else if (gamepad2.right_bumper && ziplinePlace > 0.1) {
            ziplinePlace -= ZIPLINECHANGE;
        }

        if (gamepad2.dpad_left && wallPlace < 0.98) {
            wallPlace += WALLCHANGE;
        } else if (gamepad2.dpad_right && ziplinePlace > 0.1) {
            wallPlace -= WALLCHANGE;
        }
        // write the values to the motors
        motorRU.setPower(right);
        motorLU.setPower(left);
        motorRD.setPower(right);
        motorLD.setPower(left);

        people.setPosition(peoplePlace);
        tilt.setPosition(tiltPlace);
        zipline.setPosition(ziplinePlace);
        wall.setPosition(wallPlace);

		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, thenoh my godtPower() method
		 * will return a null value. The legacy NXT-compaoh my godotor controllers
		 * are currently write only.
		 */

        telemetry.addData("2ltrigger", String.format("%.2f", gamepad2.left_trigger));
        telemetry.addData("2rtrigger", String.format("%.2f", gamepad2.right_trigger));
        telemetry.addData("2x", String.format("%.2b", gamepad2.x));
        telemetry.addData("2y", String.format("%.2b", gamepad2.y));
        telemetry.addData("peopleplace", String.format("%.2f", peoplePlace));


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
