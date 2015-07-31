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
 * <p>
 * Enables control of the robot via the gamepad
 */
public class TEST_Joy extends OpMode {

	/**
	 * Constructor
	 */
	public TEST_Joy() {

	}

	/*
	 * Code to run when the op mode is first enabled goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
	 */
	@Override
	public void start() {

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
		 * 
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */

        // tank drive
        // note that if y equal -1 then joystick is pushed all of the way forward.
        float leftX = -gamepad1.left_stick_x;
        float leftY = -gamepad1.left_stick_y;
        float rightX = -gamepad1.right_stick_x;
        float rightY = -gamepad1.right_stick_x;

		// clip the right/left values so that the values never exceed +/- 1
/*        telemetry.addData("joy1LX", "1LX: " + String.format("%f", leftX));
        telemetry.addData("joy1LY", "1LY: " + String.format("%f", leftY));
        telemetry.addData("joy1RX", "1RX: " + String.format("%f", rightX));
        telemetry.addData("joy1RY", "1RY: " + String.format("%f", rightY));*/
		// update the position of the arm.
		if (gamepad1.a) {
            telemetry.addData("aPressed", "A");
        }

        if (gamepad1.x) {
            telemetry.addData("xPressed", "X");
        }

		if (gamepad1.y) {
            telemetry.addData("yPressed", "Y");
		}

        if (gamepad1.b) {
            telemetry.addData("bPressed", "B");
        }

        if (gamepad1.left_bumper) {
            telemetry.addData("lbPressed", "LB");
        }

        if (gamepad1.left_trigger > 0.25) {
            telemetry.addData("ltPressed", "LT");
        }

        if (gamepad1.right_bumper) {
            telemetry.addData("rbPressed", "RB");
        }
        if (gamepad1.right_trigger > 0.25){
            telemetry.addData("rtPressed", "RT");
        }





		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */

	}

	/*
	 * Code to run when the op mode is first disabled goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
	 */
	@Override
	public void stop() {

	}

}
