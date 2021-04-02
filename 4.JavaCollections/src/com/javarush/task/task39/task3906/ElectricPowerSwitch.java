package com.javarush.task.task39.task3906;

public class ElectricPowerSwitch {
    private Switchable switchableDevice;

    public ElectricPowerSwitch(Switchable switchableDevice) {
        this.switchableDevice = switchableDevice;
    }

    public void press() {
        System.out.println("Power switch flipped.");
        if (switchableDevice.isOn()) {
            switchableDevice.turnOff();
        } else {
            switchableDevice.turnOn();
        }
    }
}
