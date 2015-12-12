/* Copyright (c) 2015 Qualcomm Technologies Inc

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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Timer;
import java.util.TimerTask;

public class Autonomous0_1 extends LinearOpMode {

    final float TIME1 = 2.0f; //1.0 secs
    final float TIME2 = 2.75f; //.75 secs
    final float TIME3 = 4.0f; // 1.25 secs, change if too much distance along the base
    final float TIME4 = 5.25f; // 1.25 secs
    final float TIME5 = 6.25f; //1 sec
    final float POWER = 0.4f;

    DcMotor motorRU;
    DcMotor motorLU;
    DcMotor motorLD;
    DcMotor motorRD;

    Servo people;
    Servo tilt;
    Servo zipline;
    Servo wall;
    float peoplePlace = 0.9f;
    float tiltPlace = 0.5f;
    float ziplinePlace = 0.1f;
    float wallPlace = 0.9f;

    float left = 0.0f;
    float right = 0.0f;

    @Override
    public void runOpMode() throws InterruptedException {

        // set up the hardware devices we are going to use
        motorRU = hardwareMap.dcMotor.get("motorRU");
        motorLU = hardwareMap.dcMotor.get("motorLU");
        motorRD = hardwareMap.dcMotor.get("motorRD");
        motorLD = hardwareMap.dcMotor.get("motorLD");

        people = hardwareMap.servo.get("people");
        tilt = hardwareMap.servo.get("tilt");
        zipline = hardwareMap.servo.get("zipline");
        wall = hardwareMap.servo.get("wall");

        motorLU.setDirection(DcMotor.Direction.REVERSE);
        motorLD.setDirection(DcMotor.Direction.REVERSE);

        // wait for the start button to be pressed
        waitForStart();
        people.setPosition(peoplePlace);
        tilt.setPosition(tiltPlace);
        zipline.setPosition(ziplinePlace);
        wall.setPosition(wallPlace);
        wait(1.0);
        resetStartTime();
        telemetry.addData("time", Double.toString(this.time));
        getRuntime();
        while (this.time <= TIME1) {
            getRuntime();
            telemetry.addData("time", Double.toString(this.time));
            goForward(POWER);
        }
        telemetry.addData("time2", Double.toString(this.time));
        while (this.time <= TIME2) {
            getRuntime();
            telemetry.addData("time", Double.toString(this.time));
            pivotLeft(POWER);
        }
        wall.setPosition(0.1);
        while (this.time <= TIME3) {
            getRuntime();
            goForward(POWER);
        }
        while (this.time <= TIME4) {
            getRuntime();
            pivotLeft(POWER);
        }
        while (this.time <= TIME5) {
            getRuntime();
            goForward(POWER);
        }

            /*if (this.time <= TIME1) {
                left = POWER;
                right = POWER;
            }
            if (this.time > TIME1 && this.time <= TIME2) {
                left = -POWER;
                right = POWER;
            }
            if (this.time > TIME2 && this.time <= TIME3) {
                left = POWER;
                right = POWER;
            }
            if (this.time > TIME3 && this.time <= TIME4) {
                left = -POWER;
                right = POWER;
            }
            if (this.time > TIME4 && this.time <= TIME5) {
                left = POWER;
                right = POWER;
            }
            if (this.time > TIME5) {
                left = 0;
                right = 0;
            }
            telemetry.addData("time", Double.toString(this.time));
            motorLD.setPower(left);
            motorLU.setPower(left);
            motorRD.setPower(right);
            motorRU.setPower(right);*/
    }


    void wait(double msecs) {
        resetStartTime();
        getRuntime();
        while (this.time <= msecs) {
            getRuntime();
        }
        resetStartTime();
        return;
    }

    void goForward(float power) {
        motorLD.setPower(power);
        motorLU.setPower(power);
        motorRD.setPower(power);
        motorRU.setPower(power);
    }

    void pivotLeft(float power) {
        motorLD.setPower(-power);
        motorLU.setPower(-power);
        motorRD.setPower(power);
        motorRU.setPower(power);
    }
}
