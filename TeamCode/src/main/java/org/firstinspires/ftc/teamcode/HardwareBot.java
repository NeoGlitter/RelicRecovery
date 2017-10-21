package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class is used to define all the specific hardware for our robot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case, with words separated by underscores.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Parallelogram arm drive motor:  "arm"
 * * Motor channel:  Horizontal linear slide drive motor:  "linear_drive"
 * Servo channel:  Servo to flick down jewel arm:  "jewel_arm"
 * Servo channel:  Servo to open right claw: "right_claw"
 * Servo channel:  Servo to open left claw: "left_claw"
 */
public class HardwareBot
{
    /* Public OpMode members. */
    public DcMotor  leftDrive   = null;
    public DcMotor  rightDrive  = null;
    public DcMotor  arm         = null;
    public DcMotor  linearDrive = null;
    public Servo    leftClaw    = null;
    public Servo    rightClaw   = null;
    public Servo    jewelArm    = null;

    public static final double LEFT_CLAW_OPEN       =  0.05;
    public static final double RIGHT_CLAW_OPEN      =  0.85;
    public static final double JEWEL_ARM_UP         =  0.9;



    /* local OpMode members. */
    HardwareMap hwMap           = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareBot(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize DC Motors
        leftDrive   = hwMap.get(DcMotor.class, "left_drive");
        rightDrive  = hwMap.get(DcMotor.class, "right_drive");
        arm         = hwMap.get(DcMotor.class, "left_arm");
        linearDrive = hwMap.get(DcMotor.class, "linear_drive");
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        arm.setPower(0);

        // Set all motors to run without encoders, as they are not currently installed and connected
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        leftClaw  = hwMap.get(Servo.class, "left_claw");
        rightClaw = hwMap.get(Servo.class, "right_claw");
        jewelArm  = hwMap.get(Servo.class, "jewel_arm");
        leftClaw.setPosition(LEFT_CLAW_OPEN);
        rightClaw.setPosition(RIGHT_CLAW_OPEN);
        jewelArm.setPosition(JEWEL_ARM_UP);
    }
 }

