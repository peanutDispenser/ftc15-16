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
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Timer;

public class Autonomous0_2 extends LinearOpMode {

    final float TIME1 = 4.0f;
    final float TIME2 = 1.0f;
    final float TIME3 = 1.5f;
    final float POWER = 1.0f;

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


    Timer clock;

    @Override
    public void runOpMode() throws InterruptedException {

        // set up the hardware devices we are going to use
        motorRU = hardwareMap.dcMotor.get("motorRU");
        motorLU = hardwareMap.dcMotor.get("motorLU");
        motorRD = hardwareMap.dcMotor.get("motorRD");
        motorLD = hardwareMap.dcMotor.get("motorLD");
        extend1 = hardwareMap.dcMotor.get("extend1");
        extend2 = hardwareMap.dcMotor.get("extend2");
        turn = hardwareMap.dcMotor.get("turn");
        wall = hardwareMap.servo.get("wall");
        people = hardwareMap.servo.get("people");

        motorLU.setDirection(DcMotor.Direction.REVERSE);
        motorLD.setDirection(DcMotor.Direction.REVERSE);

        // wait for the start button to be pressed
        waitForStart();
        float left = 0.0f;
        float right = 0.0f;
        while (true) {
            getRuntime();
            if (this.time <= TIME1) {
                left = POWER;
                right = POWER;
            }
            else
            {
                left = 0;
                right = 0;
            }

            telemetry.addData("time", Double.toString(this.time));
            motorLD.setPower(left);
            motorLU.setPower(left);
            motorRD.setPower(right);
            motorRU.setPower(right);
        }
    }
    void wait(double msecs){
        resetStartTime();
        getRuntime();
        while (this.time <= msecs)
        {
            getRuntime();
        }
        resetStartTime();
        return;
    }
}
