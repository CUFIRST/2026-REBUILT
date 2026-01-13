// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SmartDashMotor extends SubsystemBase {
  TalonFX motor;
  int increment;
  String name;
  String increaseLabel;
  String decreaseLabel;
  double targetRPM = 0;
  NetworkPushButton increaseButton;
  NetworkPushButton decreaseButton;

  SmartDashMotor(int id, String name, int increment) {
    motor = new TalonFX(id);
    this.name = name;
    increaseLabel = name + " Increase";
    decreaseLabel = name + " Decrease";

    increaseButton = new NetworkPushButton(increaseLabel, () -> {
      targetRPM += increment;
      updateTargetSpeed();
    }, false);
    decreaseButton = new NetworkPushButton(decreaseLabel, () -> {
      targetRPM -= increment;
      updateTargetSpeed();
    }, false);
  }

  SmartDashMotor(int id, String name) {
    this(id, name, 100);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber(name + " RPM", motor.getVelocity().getValueAsDouble() * 60);
    SmartDashboard.putNumber(name + " Target RPM", motor.getClosedLoopReference().getValueAsDouble() * 60);
  }

  private void updateTargetSpeed() {
    motor.setControl(new VelocityVoltage(targetRPM / 60));
  }
}
