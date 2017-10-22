package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.HardwareBot;

/**
 * This file provides Telop driving for our bot.
 * The code is structured as an Iterative OpMode, where one block is repeated until stop is pressed
 *
 * This OpMode uses the common HardwareBot hardware class to define the devices on the robot.
 * All device access is managed through the HardwareBot class.
 *
 * This particular OpMode executes a basic Steering Drive Teleop for the robot using the left joystick
 * It also moves the arm up and down using the y axis of the right joystick
 * The jewel arm moves up and down slowly using the left and right Bumper buttons.
 * The linear lift, when built and connected can be operated from the Y and A buttons
 * The claw can be operated using the X and B buttons
 *
 */

@TeleOp(name="DriveSteering", group="Drive")

public class BotTeleOPSteer extends OpMode{

    /* Declare OpMode members. */
    HardwareBot  robot        = new HardwareBot(); // use the class created to define the Bots hardware

    double       clawOffset   = 0;
    double       jewelOffset  = 0;
    final double SERVO_SPEED  = 0.01 ;             // sets rate to move servos on the claw

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry to signify robot initialized and ready;
        telemetry.addData("Robot", "Ready to start");
        telemetry.update();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double throttle;
        double steering;
        double left;
        double right;
        // Get values from left joystick for Steering mode driving
        throttle = -gamepad1.left_stick_y;
        steering = gamepad1.left_stick_x;

        // Smooth the range with an exponential equation
        throttle = smoothCurve(throttle);
        steering = smoothCurve(steering);

        // Actual steering logic
        right = Range.clip((throttle-steering), -1, 1);
        left = Range.clip((throttle+steering), -1, 1);
        robot.leftDrive.setPower(left);
        robot.rightDrive.setPower(right);

        // Use gamepad left & right Bumpers to raise and lower the jewel arm
        if (gamepad1.right_bumper)
            jewelOffset += SERVO_SPEED;
        else if (gamepad1.left_bumper)
            jewelOffset -= SERVO_SPEED;

        // Move the jewel servo to the new position, assuming positive jewel offset moves further down when subtracted
        // (i.e. the servo starts near 1 when raised up)
        jewelOffset = Range.clip(jewelOffset, 0, 0.8);
        robot.jewelArm.setPosition(robot.JEWEL_ARM_UP + jewelOffset);



        // Use gamepad right joystick y axis to control the arm motor power
        robot.arm.setPower(gamepad1.right_stick_y);

        // Use gamepad buttons to open the claw (X) and close the claw(B)
        if (gamepad1.x)
            clawOffset += SERVO_SPEED;
        else if (gamepad1.a)
            clawOffset -= SERVO_SPEED;

        // Move both claw servos to new position, as they are mirror image of each other, with different open positions
        // set in HardwareBot
        clawOffset = Range.clip(clawOffset, 0, 0.8);
        robot.leftClaw.setPosition(robot.LEFT_CLAW_OPEN + clawOffset);
        robot.rightClaw.setPosition(robot.RIGHT_CLAW_OPEN - clawOffset);

        // Send telemetry message to signify robot running;
        telemetry.addData("left",  "%.2f", left);
        telemetry.addData("right", "%.2f", right);
        telemetry.addData("claw",  "Offset = %.2f", clawOffset);
        telemetry.addData("jewel", "Offset = %.2f", jewelOffset);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }


    public double smoothCurve(double power) {
        return Math.pow(power,3);
    }
}
