#include "Controller.h"
#include "tchar.h" 

#include <windows.h>
#include <iostream>
#include <stdexcept>
#include <XInput.h>
#include <bitset>

using namespace std;

namespace Controller
{
	pICFUNC getControllerData;
	XINPUT_VIBRATION vibration;
	HINSTANCE hGetProcIDDLL;
	XINPUT_BATTERY_INFORMATION batteryInformation;

	int initController(void)
	{
		//HINSTANCE hGetProcIDDLL = LoadLibrary("xinput1_3.dll");  //for most compilers
		hGetProcIDDLL = LoadLibrary(L"xinput1_3.dll");  //for visual studio

		if (hGetProcIDDLL == NULL)
		{
			cout << "Unable to load xinput1_3.dll - Make sure DirectX is installed" << endl;
			DWORD dw = GetLastError();
			cout << "Error " << dw << endl;
			return 0;
		}

		//Get the address of ordinal 100.
		FARPROC lpfnGetProcessID = GetProcAddress(HMODULE(hGetProcIDDLL), (LPCSTR)100);

		//Assign it to getControllerData for easier use
		getControllerData = pICFUNC(lpfnGetProcessID); 
		return 1;
	}

	void startVibration(int controller, int leftMotor, int rightMotor)
	{
		ZeroMemory(&vibration, sizeof(XINPUT_VIBRATION));
		vibration.wLeftMotorSpeed = leftMotor; // use any value between 0-65535 here
		vibration.wRightMotorSpeed = rightMotor; // use any value between 0-65535 here
		XInputSetState(controller, &vibration);
	}

	void stopVibration(int controller)
	{
		ZeroMemory(&vibration, sizeof(XINPUT_VIBRATION));
		vibration.wLeftMotorSpeed = 0;
		vibration.wRightMotorSpeed = 0;
		XInputSetState(controller, &vibration);
	}

	//Only works for xbox controllers
	void turnControllerOff(int controller)
	{
		FARPROC lpfnGetProcessID = GetProcAddress(HMODULE(hGetProcIDDLL), (LPCSTR)103);
		pc toff = pc(lpfnGetProcessID);
		toff(controller);
	}

	void close(void)
	{
		FreeLibrary(hGetProcIDDLL);
	}

	void getBatteryState(int controller)
	{
		ZeroMemory(&batteryInformation, sizeof(XINPUT_BATTERY_INFORMATION));
		int result = XInputGetBatteryInformation(controller, BATTERY_DEVTYPE_GAMEPAD, &batteryInformation);
		cout << "Result: " << result << endl;
		cout << "Battery type:" << batteryInformation.BatteryType << endl;
		cout << "Battery level:" << batteryInformation.BatteryLevel << endl;
	}


	void populateData(int data[22], ButtonStruct bs, int result)
	{
		data[0] = bs.aButton;
		data[1] = bs.bButton;
		data[2] = bs.xButton;
		data[3] = bs.yButton;
		data[4] = bs.lButton;
		data[5] = bs.rButton;
		data[6] = bs.start;
		data[7] = bs.back;
		data[8] = bs.l3;
		data[9] = bs.r3;
		data[10] = bs.guideButton;
		data[11] = bs.down;
		data[12] = bs.up;
		data[13] = bs.left;
		data[14] = bs.right;

		//analog
		data[15] = bs.l2;
		data[16] = bs.r2;
		data[17] = bs.leftStickX;
		data[18] = bs.leftStickY;
		data[19] = bs.rightStickX;
		data[20] = bs.rightStickY;

		
		//result returns 1167 when disconected and 0 when connected
		if (result > 0)
			data[21] = 0;
		else
			data[21] = 1;
	}

	void populateControllerData(int controller, int data[22])
	{
		ButtonStruct bs;
		ZeroMemory(&bs, sizeof(ButtonStruct));
		int result = getControllerData(controller, bs);
		populateData(data, bs, result);
	}
}