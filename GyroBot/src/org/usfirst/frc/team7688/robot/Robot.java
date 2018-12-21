/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7688.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private final static int GYRO_SAMPLE = 100;  // in Hertz
	private final static double ACCEL_CORRECTION = 0.046;
	
    private final static byte MPU6050_ADDRESS = 0x68;
    private final static int REGISTER_PWR_MGMT_1 = 0x6B;
    private final static int REGISTER_GYRO = 0x43;
    private final static int REGISTER_ACCEL = 0x3B;
    private final static int MPU6050_GYROCONFIG = 0x1B;
    private final static int MPU6050_GYRO_XOUT_HI = 0x43;
    private final static int MPU6050_GYRO_ZOUT_HI = 0x47;
    
    private final static int MPU6050_WHO_AM_I = 0x75;
    
    protected static int direction=0;

    // Hey Justin!  it is supposed to be kOnboard - kMXP is a different protocol.
    private I2C accelerometer = new I2C(I2C.Port.kOnboard,MPU6050_ADDRESS);    

    
    @Override
    public void robotInit() {
        // Take the accelerometer out of sleep mode
        
    	 accelerometer.write(REGISTER_PWR_MGMT_1, 0);
     
    	// accelerometer.write(0x6B, 0x03); // Power
    	// accelerometer.write(0x1A, 0x18); // Basic Config
    	// accelerometer.write(0x1B, 0x00); // Gyro Config
    }

    @Override
    public void teleopPeriodic() {
  
    }
    
    
    @Override
    public void testPeriodic()
    {
    	byte[] angle = new byte[6], accel = new byte[6], temp = new byte[1];
    	accelerometer.read(REGISTER_GYRO, 6, angle);
		accelerometer.read(REGISTER_ACCEL, 6, accel);


		int x_rot = ((angle[0]<<8) | angle[1])/ GYRO_SAMPLE;
		int y_rot = ((angle[2]<<8) | angle[3])/ GYRO_SAMPLE;
		int z_rot = ((angle[4]<<8) | angle[5])/ GYRO_SAMPLE;
		double x_acc = ((accel[0]<<8) | accel[1]) * ACCEL_CORRECTION;
		double y_acc = ((accel[2]<<8) | accel[3]) * ACCEL_CORRECTION;
		double z_acc = ((accel[4]<<8) | accel[5]) * ACCEL_CORRECTION ;
		
		direction += z_rot;
		
		System.out.println("X: " + x_rot + ", Y: " + y_rot + ", Z: " + z_rot);
		System.out.println("Rotation: " + direction/100);
//		System.out.printf("Xa: %.2f",x_acc);
//		System.out.printf(", Ya: %.2f", y_acc);
//		System.out.printf(", Za: %.2f", z_acc);
//		System.out.println();
    	
//		System.out.println("Rotation: " + rotation);
//		System.out.println(angle.toString());
		

		
		
    	
    }
}